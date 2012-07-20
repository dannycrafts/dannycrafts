package com.dannycrafts.database;

import java.util.*;

public class WorldDatabase
{
	protected List<Map<?, ? extends Resource>> dataCollections = new ArrayList<Map<?, ? extends Resource>>();
	
	public static <I, R extends Resource> void createResource( Map<I, R> dataCollection, I id, R resource ) throws Exception
	{
		resource.create();
	}
	
	public <I, R extends WorldResource> void registerCollection( Map<I, R> collection )
	{
		synchronized ( dataCollections )
		{
			dataCollections.add( collection );
		}
	}
	
	public static <I, R extends Resource> void spawnResource( Map<I, R> dataCollection, I id, R resource ) throws Exception
	{
		resource.create();
		resource.acquire();
		
		synchronized ( dataCollection )
		{
			dataCollection.put( id, resource );
		}
	}
	
	public static <K, R extends WorldResource> R getResource( Map<K, R> dataCollection, K id )
	{
		R resource;
		synchronized ( dataCollection )
		{
			resource = dataCollection.get( id );
			
			if ( resource != null )
				resource.acquire();
		}
		
		return resource;
	}

	public static <K, R extends WorldResource> void reviveResource( Map<K, R> dataCollection, K id, R resource ) throws Exception
	{
		resource.load();
		resource.acquire();
		
		synchronized ( dataCollection )
		{
			dataCollection.put( id, resource );
		}
	}
	
}
