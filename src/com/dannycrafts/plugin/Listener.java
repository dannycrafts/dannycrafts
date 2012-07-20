package com.dannycrafts.plugin;

import java.io.*;

import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.world.*;

import com.dannycrafts.*;
import com.dannycrafts.database.*;
import com.dannycrafts.debug.*;
import com.dannycrafts.regen.*;
import com.dannycrafts.snapshot.*;

public class Listener implements org.bukkit.event.Listener
{
	@EventHandler( priority = EventPriority.LOWEST, ignoreCancelled = true )
	public void onBlockBreak( BlockBreakEvent event )
	{
		try
		{
			Regenerator.touchChunk( new ChunkId( new WorldId( event.getBlock().getWorld() ), new ChunkCoords( event.getBlock().getChunk() ) ) );
		
			Debug.debug( "Touched chunk: (" + event.getBlock().getChunk().getX() + ", " + event.getBlock().getChunk().getZ() );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	@EventHandler( priority = EventPriority.LOWEST, ignoreCancelled = true )
	public void onBlockPlace( BlockPlaceEvent event )
	{
		try
		{
			Regenerator.touchChunk( new ChunkId( new WorldId( event.getBlock().getWorld() ), new ChunkCoords( event.getBlock().getChunk() ) ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
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
