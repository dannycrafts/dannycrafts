package com.dannycrafts.regen;

import com.dannycrafts.*;
import com.dannycrafts.database.Resource;

public class TouchedChunkNote extends Resource
{
	public final ChunkId id;
	
	protected TouchedChunkNote( ChunkId id )
	{
		this.id = id;
	}
	
	@Override
	protected void load() throws Exception
	{
		
	}

}
