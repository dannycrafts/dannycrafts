package com.dannycrafts.regen;

import java.util.*;

import com.dannycrafts.*;

public class Regenerator
{
	private static Map<WorldId, Map<ChunkCoords, TouchedChunkNote>> chunkNoteCollection;
	
	public static void init()
	{
		chunkNoteCollection = new HashMap<WorldId, Map<ChunkCoords, TouchedChunkNote>>();
	}
	
	public static void uninit()
	{
		chunkNoteCollection = null;
	}
}
