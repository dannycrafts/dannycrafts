package com.dannycrafts.plugin;

import java.io.*;

import org.bukkit.event.*;
import org.bukkit.event.world.*;

import com.dannycrafts.database.Database;
import com.dannycrafts.debug.Debug;
import com.dannycrafts.snapshot.ChunkSnapshot;

public class Listener implements org.bukkit.event.Listener
{
	@EventHandler
	public void onChunkPopulate( ChunkPopulateEvent event )
	{
		try
		{
			ChunkSnapshot cs = new ChunkSnapshot( event.getChunk() );
			
			cs.save( event.getWorld() );
			
			if ( Debug.chunkSnapshots() )
				Debug.debug( "Wrote chunk (" + event.getChunk().getX() + "," + event.getChunk().getZ() + ")." );
		}
		catch ( Exception e )
		{
			Plugin.report( "Unable to write snapshot down for chunk (" + event.getChunk().getX() + "," + event.getChunk().getZ() + "), shutting down." );
			e.printStackTrace();
			Runtime.getRuntime().exit( 1 );
		}
	}
	
	@EventHandler
	public void onWorldInit( WorldInitEvent event )
	{
		File snapshotsFolder = new File( event.getWorld().getWorldFolder() + "/snapshots" );
		snapshotsFolder.mkdir();
	}
	
	@EventHandler
	public void onWorldSave( WorldSaveEvent event )
	{
		Database.saveAllResources();
		
		// Note: This may not be the best way to notify the garbage collector to save resources because this will probably be triggered for every world.
		//       However, it will probably not really cause a lot of double notifications anyway.
	}
}
