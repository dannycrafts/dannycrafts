package com.dannycrafts.database;

import java.util.*;

import com.dannycrafts.debug.Debug;
import com.dannycrafts.plugin.Plugin;

public class GarbageCollector
{
	private WorkerThread workerThread = null;
	
	protected GarbageCollector()
	{
		workerThread = new WorkerThread();
		workerThread.start();
	}
	
	// This function should only be called when resources will NOT be loaded anymore!
	protected void stop() throws Exception
	{
		workerThread.workerFlag = false;
		synchronized ( workerThread )
		{
			// Notify the worker thread in case it is sleeping
			workerThread.notify();
		}
		workerThread.join();
	}
	
	protected void saveAllResources()
	{
		workerThread.saveFlag = true;
		synchronized ( workerThread )
		{
			// Notify the worker thread in case it is sleeping
			workerThread.notify();
		}
	}
	
	private static class WorkerThread extends Thread
	{
		private boolean workerFlag = true;
		private boolean saveFlag = false;
		
		public void run()
		{
			while ( this.workerFlag == true )
			{
				// Do cleaning cycle:
				List<Map<?, ? extends Resource>> dataCollectionsCopy;
				synchronized ( Database.dataCollections )
				{
					dataCollectionsCopy = new ArrayList<Map<?, ? extends Resource>>( Database.dataCollections.size() );
					dataCollectionsCopy.addAll( Database.dataCollections );
				}
				for ( Map<?, ? extends Resource> dataCollection : dataCollectionsCopy )
				{
					synchronized ( dataCollection )
					{
						for ( Iterator<? extends Resource> i = dataCollection.values().iterator(); i.hasNext(); )
						{
							Resource resource = i.next();
							
							// Check if not used anymore:
							if ( resource.isOld() )
							{
								// Remove from collection:
								i.remove();
								
								// Save if changed:
								if ( resource.hasChanged == true )
								{
									try
									{
										resource.save();
									}
									catch (Exception e)
									{
										Plugin.report( "Unable to save resource " + resource + "." );
										e.printStackTrace();
									}
								}
								
								if ( Debug.garbageCollector() ) Debug.debug( "Resource (" + resource.toString() + ") has been released." );
							}
						}
					}
				}
				
				// Wait for next cycle:
				try
				{
					// Check if needing save before going to sleep:
					if ( saveFlag == true )
						saveCycle();
					
					synchronized ( this )
					{
						this.wait( Resource.oldTime );
					}
					
					// Check if notified for save:
					if ( saveFlag == true )
						saveCycle();
				}
				catch ( InterruptedException e )
				{
					Plugin.report( "Garbage collector died." );
					e.printStackTrace();
				}
			}
			
			// Clean all objects still alive and check for memory leaks:
			for ( Map<?, ? extends Resource> dataCollection : Database.dataCollections )
			{
				for ( Resource resource : dataCollection.values() )
				{
					if ( resource.useCounter > 0 )
						Plugin.warn( "Memory leak (" + resource.useCounter + ") for " + resource + "." );
					else if ( resource.useCounter < 0 )
						Plugin.warn( "Segmentation fault (" + resource.useCounter + ") for " + resource + "." );
					
					// Save if changed:
					if ( resource.hasChanged == true )
					{
						try
						{
							// This does not have to be synchronized because at this point the main thread is not able to access this resource anymore anyway.
							resource.save();
						}
						catch (Exception e)
						{
							Plugin.report( "Unable to save resource " + resource + "." );
							e.printStackTrace();
						}
						
						if ( Debug.garbageCollector() ) Debug.debug( "Resource (" + resource.toString() + ") has been saved." );
					}
				}
			}
			
			Plugin.announce( "Garbage collector stopped." );
		}
		
		private void saveCycle()
		{
			saveCycle( Database.dataCollections );
		}
		
		private void saveCycle( List<Map<?, ? extends Resource>> dataCollections )
		{
			List<Map<?, ? extends Resource>> dataCollectionsCopy;
			synchronized ( dataCollections )
			{
				dataCollectionsCopy = new ArrayList<Map<?, ? extends Resource>>( dataCollections.size() );
				dataCollectionsCopy.addAll( dataCollections );
			}
			for ( Map<?, ? extends Resource> dataCollection : dataCollectionsCopy )
			{
				synchronized ( dataCollection )
				{
					for ( Iterator<? extends Resource> i = dataCollection.values().iterator(); i.hasNext(); )
					{
						Resource resource = i.next();
						
						// Save if changed:
						if ( resource.hasChanged == true )
						{
							// Resource needs to be synchronized because it is 'locked' while being modified.
							synchronized ( resource )
							{
								try
								{
									resource.save();
								}
								catch (Exception e)
								{
									Plugin.report( "Unable to save resource " + resource + "." );
									e.printStackTrace();
								}
							}
							
							Plugin.announce( "Resource " + resource + " saved." );
						}
					}
				}
			}
		}
	}
}
