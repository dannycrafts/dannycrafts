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
	
	public static void init()
	{
		chunkNoteCollection = new HashMap<WorldId, Map<ChunkCoords, TouchedChunkNote>>( Plugin.getBukkitServer().getWorlds().size() );
		for ( World world : Plugin.getBukkitServer().getWorlds() )
		{
			// Create data folders:
			new File( world.getWorldFolder() + "/touched_chunks" ).mkdir();
			
			Map<ChunkCoords, TouchedChunkNote> worldCollection = new HashMap<ChunkCoords, TouchedChunkNote>();
			chunkNoteCollection.put( new WorldId( world ), worldCollection );
			Database.registerCollection( worldCollection );
		}
	}
	
	public static TouchedChunkNote loadTouchedChunk( ChunkId id ) throws Exception
	{
		Map<ChunkCoords, TouchedChunkNote> worldCollection = chunkNoteCollection.get( id.world );
		if ( worldCollection == null ) return null;
		
		TouchedChunkNote touchedChunk = Database.getResource( worldCollection, id.coords );
		if ( touchedChunk != null ) return touchedChunk;
			
		touchedChunk = new TouchedChunkNote( id );
		Database.reviveResource( worldCollection, id.coords, touchedChunk );
		return touchedChunk;
	}
	
	public static void uninit()
	{
		chunkNoteCollection = null;
	}
}
