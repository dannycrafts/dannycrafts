package com.dannycrafts.database;

import com.dannycrafts.WorldId;

public abstract class WorldResource extends Resource
{
	protected final WorldId world;
	
	protected WorldResource( WorldId world )
	{
		this.world = world;
	}
	
	protected void load() { load( world ); }
	
	protected abstract void load( WorldId world );
	
	protected void save() { save( world ); }
	
	protected abstract void save( WorldId world );
}
