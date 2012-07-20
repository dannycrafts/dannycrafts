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
	protected void create() throws Exception { create( world ); }
	
	protected abstract void create( WorldId world ) throws Exception;
	
	@Override
	protected boolean exists() throws Exception { return exists( world ); }
	
	protected abstract boolean exists( WorldId world ) throws Exception;
	
	@Override
	protected void load() throws Exception { load( world ); }
	
	protected abstract void load( WorldId world ) throws Exception;
	
	@Override
	protected void save() throws Exception { save( world ); }
	
	protected void save( WorldId world ) throws Exception {}
}
