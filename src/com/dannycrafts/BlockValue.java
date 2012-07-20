package com.dannycrafts;

public class BlockValue {
	
	int id;
	int data;
	
	public BlockValue()
	{
		this.id = 0;
		this.data = 0;
	}
	
	public BlockValue( int id, int data )
	{
		this.id = id;
		this.data = data;
	}
	
	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof BlockValue == false ) return false;
		BlockValue _other = (BlockValue)other;
		
		return this.id == _other.id && this.data == _other.data;
	}
	@Override
	public int hashCode()
	{
		return id ^ data;
	}
	
	@Override
	public String toString()
	{
		return id + ":" + data;
	}
	
	public int getBlockId()
	{
		return this.id;
	}
	
	public int getBlockData()
	{
		return this.data;
	}
}
