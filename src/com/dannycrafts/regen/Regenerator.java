package com.dannycrafts.regen;

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
			Map<ChunkCoords, TouchedChunkNote> worldCollection = new HashMap<ChunkCoords, TouchedChunkNote>();
			chunkNoteCollection.put( new WorldId( world ), worldCollection );
			Database.registerCollection( worldCollection );
		}
	}
	
	public static TouchedChunkNote loadTouchedChunk( WorldId world, ChunkCoords chunk ) throws Exception
	{
		Map<ChunkCoords, TouchedChunkNote> worldCollection = chunkNoteCollection.get( world );
		if ( worldCollection == null ) return null;
		
		TouchedChunkNote touchedChunk = Database.getResource( worldCollection, chunk );
		if ( touchedChunk != null ) return touchedChunk;
			
		touchedChunk = new TouchedChunkNote( chunk );
		Database.reviveResource( worldCollection, chunk, touchedChunk );
		return touchedChunk;
	}
	
	public static void uninit()
	{
		chunkNoteCollection = null;
	}
}
