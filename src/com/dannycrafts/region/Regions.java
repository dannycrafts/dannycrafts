package com.dannycrafts.region;

import java.io.File;
import java.util.*;

import com.dannycrafts.database.Database;
import com.dannycrafts.plugin.Plugin;

public class Regions
{
	private static Map<Long,RegionData> regionCollection = new HashMap<Long,RegionData>();
	
	public static void init()
	{
		// Create data folder:
		getDataFolder().mkdir();
		
		// Register collection:
		Database.registerCollection( regionCollection );
	}
	
	public static File getDataFolder()
	{
		return new File( Plugin.getPluginFolder() + "/regions" );
	}
	
	public static RegionData loadRegion( long id ) throws Exception
	{
		RegionData region = Database.getResource( regionCollection, id );
		if ( region != null ) return region;
		
		region = new RegionData( id );
		Database.reviveResource( regionCollection, id, region );
		return region;
	}
	
	public static void uninit()
	{
		
	}
}
