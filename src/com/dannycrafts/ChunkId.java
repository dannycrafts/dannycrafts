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
}
