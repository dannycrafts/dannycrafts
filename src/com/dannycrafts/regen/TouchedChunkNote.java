package com.dannycrafts.regen;

import java.io.File;

import com.dannycrafts.*;
import com.dannycrafts.database.*;

public class TouchedChunkNote extends WorldResource
{
	public final ChunkCoords id;
	
	protected TouchedChunkNote( ChunkId id )
	{
		super( id.world );
		
		this.id = id.coords;
	}
	
	@Override
	protected void create( WorldId world ) throws Exception
	{
		dataFile( world ).createNewFile();
	}
	
	private File dataFile( WorldId world )
	{
		return new File( world.getBukkitWorld().getWorldFolder() + "/touched_chunks/" + id.x + "," + id.y );
	}

	@Override
	protected boolean exists( WorldId world ) throws Exception
	{
		return dataFile( world ).exists();
	}
	
	@Override
	protected void load( WorldId world ) throws Exception {}

	@Override
	protected void save( WorldId world ) throws Exception {}
}
