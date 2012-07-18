package com.dannycrafts.snapshot;

import java.util.*;

import com.dannycrafts.*;
import com.dannycrafts.database.*;

public class Snapshots
{
	private static Map<ChunkId,ChunkSnapshotData> chunkSnapshotCollection = new HashMap<ChunkId,ChunkSnapshotData>();
	
	public static void init()
	{
		Database.registerCollection( chunkSnapshotCollection );
	}
	
	public static ChunkSnapshotData loadChunkSnapshot( ChunkId id ) throws Exception
	{
		ChunkSnapshotData snapshot = Database.getResource( chunkSnapshotCollection, id );
		if ( snapshot != null ) return snapshot;
		
		Database.reviveResource( chunkSnapshotCollection, id, snapshot );
		return snapshot;
	}
	
	public static void uninit()
	{
		
	}
}
