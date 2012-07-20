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
	
	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof ChunkCoords == false ) return false;
		ChunkCoords _other = (ChunkCoords)other;
		return this.x == _other.x && this.y == _other.y;
	}

	@Override
	public int hashCode()
	{
		return x ^ y;
	}
	
	@Override
	public String toString()
	{
		return x + "," + y;
	}
}
