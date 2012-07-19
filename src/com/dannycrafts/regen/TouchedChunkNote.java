package com.dannycrafts.regen;

import java.io.File;

import com.dannycrafts.*;
import com.dannycrafts.database.Resource;
import com.dannycrafts.plugin.Plugin;

public class TouchedChunkNote extends Resource
{
	public final ChunkId id;
	
	protected TouchedChunkNote( ChunkId id )
	{
		this.id = id;
	}
	
	@Override
	protected void load() throws Exception {}

	@Override
	protected void save() throws Exception
	{
		File file = new File( id.world.getBukkitWorld().getWorldFolder() + "/touched_chunks/" + id.coords.x + "," + id.coords.y );
		file.createNewFile();
	}
}
