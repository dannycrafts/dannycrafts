package com.dannycrafts;

public class PlotDimensions
{
	public int length; // Along the X-axis.
	public int width; // Along the Y-axis.
	public int height; // Along the Z-axis.
	
	public PlotDimensions( int length, int width, int height )
	{
		this.length = length;
		this.width = width;
		this.height = height;
	}
	
	public PlotDimensions( String parse ) throws NumberFormatException
	{
		String[] coords = parse.split( ",", 3 );
		this.length = Integer.parseInt( coords[0] );
		this.width = Integer.parseInt( coords[1] );
		this.height = Integer.parseInt( coords[2] );
	}
	
	@Override
	public String toString()
	{
		return length + "," + width + "," + height;
	}
}
