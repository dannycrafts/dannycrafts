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
	
	public static void uninit()
	{
		chunkNoteCollection = null;
	}
}
