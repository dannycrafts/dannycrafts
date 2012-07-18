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
}
