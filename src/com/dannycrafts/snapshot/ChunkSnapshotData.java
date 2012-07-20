package com.dannycrafts.snapshot;

import java.io.*;

import com.dannycrafts.*;
import com.dannycrafts.database.*;
import com.dannycrafts.region.Regions;

public class ChunkSnapshotData extends Resource
{
	protected ChunkId id;
	protected ChunkSnapshot data;
	
	protected ChunkSnapshotData( ChunkId id )
	{
		this.id = id;
	}
	
	@Override
	protected boolean exists() throws Exception
	{
		return new File( id.world.getBukkitWorld().getWorldFolder() + "/snapshots/" + id.coords.x + "," + id.coords.y ).exists();
	}

	@Override
	protected void load() throws Exception
	{
		ChunkSnapshot snapshot = new ChunkSnapshot( id.world.getBukkitWorld(), id.coords );
		FileInputStream fis = new FileInputStream( new File( id.world.getBukkitWorld().getWorldFolder() + "/snapshots/" + id.coords.x + "," + id.coords.y ) );
		snapshot.readFrom( fis );
		fis.close();
		
		this.data = snapshot;
	}

}
