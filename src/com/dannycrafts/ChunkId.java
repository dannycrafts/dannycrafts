package com.dannycrafts;

public class ChunkId
{
	public WorldId world;
	public ChunkCoords coords;
	
	public ChunkId( WorldId world, ChunkCoords coords )
	{
		this.world = world;
		this.coords = coords;
	}
	
	public ChunkId( org.bukkit.Chunk chunk )
	{
		this( new WorldId( chunk.getWorld() ), new ChunkCoords( chunk.getX(), chunk.getZ() ) );
	}
	
	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof ChunkId == false ) return false;
		ChunkId _other = (ChunkId)other;
		return this.world.equals( world ) && this.coords.equals( _other.coords );
	}

	@Override
	public int hashCode()
	{
		return world.hashCode() ^ coords.hashCode();
	}
	
	@Override
	public String toString()
	{
		return world.toString() + ": " + coords.toString();
	}
}
