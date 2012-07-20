package com.dannycrafts;

public class ChunkCoords
{
	public int x;
	public int y;
	
	public ChunkCoords( int x, int y )
	{
		this.x = x;
		this.y = y;
	}
	
	public ChunkCoords( org.bukkit.Chunk chunk )
	{
		this( chunk.getX(), chunk.getZ() );
	}
}
