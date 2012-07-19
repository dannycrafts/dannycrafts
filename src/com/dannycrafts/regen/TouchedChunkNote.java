package com.dannycrafts.regen;

import com.dannycrafts.*;
import com.dannycrafts.database.Resource;

public class TouchedChunkNote extends Resource
{
	public final ChunkCoords id;
	
	protected TouchedChunkNote( ChunkCoords id )
	{
		this.id = id;
	}
	
	@Override
	protected void load() throws Exception
	{
		
	}

}
