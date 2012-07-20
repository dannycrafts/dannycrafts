package com.dannycrafts.regen;

import java.io.*;
import java.util.*;

import org.bukkit.World;

import com.dannycrafts.*;
import com.dannycrafts.database.Database;
import com.dannycrafts.plugin.Plugin;

public class Regenerator
{
	private static Map<WorldId, Map<ChunkCoords, TouchedChunkNote>> chunkNoteCollection;
	
	public static TouchedChunkNote spawnTouchedChunk( ChunkId id ) throws Exception
	{
		Map<ChunkCoords, TouchedChunkNote> worldCollection = chunkNoteCollection.get( id.world );
		
		// If already loaded, it exists
		TouchedChunkNote touchedChunk = new TouchedChunkNote( id );
		Database.spawnResource( worldCollection, id.coords, touchedChunk );
		
		return touchedChunk;
	}
	
	public static void init()
	{
		chunkNoteCollection = new HashMap<WorldId, Map<ChunkCoords, TouchedChunkNote>>( Plugin.getBukkitServer().getWorlds().size() );
		for ( World world : Plugin.getBukkitServer().getWorlds() )
		{
			// Create data folders:
			new File( world.getWorldFolder() + "/touched_chunks" ).mkdir();
			
			Map<ChunkCoords, TouchedChunkNote> worldCollection = new HashMap<ChunkCoords, TouchedChunkNote>();
			chunkNoteCollection.put( new WorldId( world ), worldCollection );
		}
		
		Database.registerWorldCollections( chunkNoteCollection );
	}
	
	public static boolean touchedChunkExists( ChunkId id ) throws Exception
	{
		Map<ChunkCoords, TouchedChunkNote> worldCollection = chunkNoteCollection.get( id.world );
		if ( worldCollection == null ) return false;
		
		// If already loaded, it exists
		if ( Database.resourceIsLoaded( worldCollection, id.coords ) == true ) return true;
			
		TouchedChunkNote touchedChunk = new TouchedChunkNote( id );
		return Database.resourceExists( touchedChunk );
	}
	
	public static void touchChunk( ChunkId id )
	{
		
	}
	
	public static void uninit()
	{
		chunkNoteCollection = null;
	}
}
