package com.dannycrafts.database;

import java.util.*;

public class WorldDatabase
{
	protected List<Map<?, ? extends Resource>> dataCollections;
	
	public <I, R extends Resource> void registerCollection( Map<I, R> collection )
	{
		synchronized ( dataCollections )
		{
			dataCollections.add( collection );
		}
	}
}
