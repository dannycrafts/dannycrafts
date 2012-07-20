package com.dannycrafts.snapshot;

import java.io.*;

import com.dannycrafts.*;
import com.dannycrafts.database.*;

public class ChunkSnapshotData extends WorldResource
{
	protected ChunkCoords id;
	protected ChunkSnapshot data;
	
	protected ChunkSnapshotData( ChunkId id )
	{
		super( id.world );
		
		this.id = id.coords;
	}
	
	@Override
	protected boolean exists( WorldId world ) throws Exception
	{
		return new File( world.getBukkitWorld().getWorldFolder() + "/snapshots/" + id.x + "," + id.y ).exists();
	}

	@Override
	protected void load( WorldId world ) throws Exception
	{
		ChunkSnapshot snapshot = new ChunkSnapshot( world.getBukkitWorld(), id );
		FileInputStream fis = new FileInputStream( new File( world.getBukkitWorld().getWorldFolder() + "/snapshots/" + id.x + "," + id.y ) );
		snapshot.readFrom( fis );
		fis.close();
		
		this.data = snapshot;
	}

}
