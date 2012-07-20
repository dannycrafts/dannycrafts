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
	public boolean equals( Object other )
	{
		if ( other instanceof PlotDimensions == false ) return false;
		PlotDimensions _other = (PlotDimensions)other;
		return this.length == _other.length && this.width == _other.width && this.height == _other.height;
	}

	@Override
	public int hashCode()
	{
		return length ^ width ^ height;
	}
	
	@Override
	public String toString()
	{
		return length + "," + width + "," + height;
	}
}
