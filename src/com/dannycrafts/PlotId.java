package com.dannycrafts;

public class PlotId
{
	public WorldId world;
	public PlotCoords coords;
	
	public PlotId( WorldId world, PlotCoords coords )
	{
		this.world = world;
		this.coords = coords;
	}
	
	public boolean equals( Object other )
	{
		if ( other instanceof WorldId == false ) return false;
		PlotId _other = (PlotId)other;
		return this.world.equals( _other.world ) && this.coords.equals( _other.coords );
	}
	
	public int hashCode()
	{
		return coords.hashCode();
	}
}
