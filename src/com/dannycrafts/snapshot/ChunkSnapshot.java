// The ChunkSnapshot class should only be able to read the snapshot, modifications are not intended.

package com.dannycrafts.snapshot;

import java.io.*;

import com.dannycrafts.*;

public class ChunkSnapshot extends Snapshot
{
	private org.bukkit.ChunkSnapshot chunkSnapshot;
	
	public ChunkSnapshot( org.bukkit.Chunk chunk )
	{
		super( 16, 16, 256 );
		
		this.chunkSnapshot = chunk.getChunkSnapshot( false, false, false );
	}
	
	// This information is only required to create a new snapshot, it should not necessarily indicate anything about the snapshot.
	public ChunkSnapshot( org.bukkit.World world, ChunkCoords coords )
	{
		super( 16, 16, 256 );
		
		this.chunkSnapshot = world.getEmptyChunkSnapshot( coords.x, coords.y, false, false );
	}
	
	@Override
	public BlockValue getBlock( int x, int y, int z )
	{		
		int typeId = chunkSnapshot.getBlockTypeId( x, z, y );
		int data = chunkSnapshot.getBlockData( x, z, y );
		
		return new BlockValue( typeId, data );
	}
	// The  getBlock( int i )  function should already be implemented.
	
	public PlotCoords getPlot()
	{
		return new PlotCoords( chunkSnapshot.getX(), chunkSnapshot.getZ(), 0 );
	}
	
	public void save( org.bukkit.World world ) throws IOException
	{
		PlotCoords plot = getPlot();
		File xFolder = new File( world.getWorldFolder() + "/snapshots/" + plot.x );
		xFolder.mkdir();
		File yFile = new File( xFolder + "/" + plot.y );
		
		File bufferFile = new File( "buffer" );
		FileOutputStream fos = new FileOutputStream( bufferFile );
		this.writeTo( fos );
		fos.flush();
		fos.close();
		
		if ( bufferFile.renameTo( yFile ) == false )
			throw new IOException();
	}
}
