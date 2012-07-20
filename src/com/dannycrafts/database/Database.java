package com.dannycrafts.database;

import java.util.*;

import com.dannycrafts.*;
import com.dannycrafts.plugin.Plugin;

public class Database
{
	protected static List<Map<?, ? extends Resource>> dataCollections;
	
	private static GarbageCollector garbageCollector;
	
	private static Map<WorldId, WorldDatabase> worldDatabases;
	
	public static <I, R extends Resource> void registerCollection( Map<I, R> collection )
	{
		synchronized ( dataCollections )
		{
			dataCollections.add( collection );
		}
	}
	
	public static <I, R extends Resource> void registerWorldCollection( WorldId world, Map<I, R> collection )
	{
		worldDatabases.get( world ).registerCollection( collection );
	}
	
	public static void saveAllResources()
	{
		garbageCollector.saveAllResources();
	}
	
	public static <K, R extends Resource> R getResource( Map<K, R> dataCollection, K id )
	{
		R resource;
		synchronized ( dataCollection )
		{
			resource = dataCollection.get( id );
			
			if ( resource != null )
				resource.acquire();
		}
		
		return resource;
	}
	
	public static void init()
	{
		dataCollections = new ArrayList<Map<?, ? extends Resource>>();
		
		// Load world databases:
		worldDatabases = new HashMap<WorldId, WorldDatabase>( Plugin.getBukkitServer().getWorlds().size() );
		for ( org.bukkit.World world : Plugin.getBukkitServer().getWorlds() )
		{
			WorldId worldId = new WorldId( world );
			worldDatabases.put( worldId, new WorldDatabase() );
		}
		
		garbageCollector = new GarbageCollector();
	}
	
	public static <K, R extends Resource> void reviveResource( Map<K, R> dataCollection, K id, R resource ) throws Exception
	{
		resource.load();
		resource.acquire();
		
		synchronized ( dataCollection )
		{
			dataCollection.put( id, resource );
		}
	}
	
	public static void uninit()
	{
		try
		{
			garbageCollector.stop();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		garbageCollector = null;
		
		worldDatabases = null;
		
		dataCollections = null;
	}
}
