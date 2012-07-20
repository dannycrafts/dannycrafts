package com.dannycrafts;

public class PlotCoords
{
	public int x;
	public int y;
	public int z;
	
	public PlotCoords( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PlotCoords( String parse ) throws NumberFormatException
	{
		String[] coords = parse.split( ",", 3 );
		this.x = Integer.parseInt( coords[0] );
		this.y = Integer.parseInt( coords[1] );
		this.z = Integer.parseInt( coords[2] );
	}
	
	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof PlotCoords == false ) return false;
		PlotCoords _other = (PlotCoords)other;
		return this.x == _other.x && this.y == _other.y && this.z == _other.z;
	}

	@Override
	public int hashCode()
	{
		return x ^ y ^ z;
	}
	
	@Override
	public String toString()
	{
		return x + "," + y + "," + z;
	}
}
