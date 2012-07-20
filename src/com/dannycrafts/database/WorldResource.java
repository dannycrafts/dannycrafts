package com.dannycrafts.database;

import com.dannycrafts.WorldId;

public abstract class WorldResource extends Resource
{
	protected final WorldId world;
	
	protected WorldResource( WorldId world )
	{
		this.world = world;
	}
	
	@Override
	protected void load() throws Exception { load( world ); }
	
	protected abstract void load( WorldId world ) throws Exception;
	
	@Override
	protected void save() throws Exception { save( world ); }
	
	protected abstract void save( WorldId world ) throws Exception;
}
