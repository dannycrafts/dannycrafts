package com.dannycrafts;

import java.io.*;
import java.util.*;

import com.dannycrafts.plugin.Plugin;

public class WorldId
{
	private final int id;
	
	private static List<String> worlds = new ArrayList<String>();
	
	public WorldId( org.bukkit.World world )
	{
		this( world.getName() );
	}
	
	public WorldId( String worldName )
	{
		this.id = worlds.indexOf( worldName );
	}
	
	public String getName()
	{
		return worlds.get( id );
	}
	
	public org.bukkit.World getBukkitWorld()
	{
		return Plugin.getBukkitServer().getWorld( getName() );
	}
	
	public File getDataFolder()
	{
		return getBukkitWorld().getWorldFolder();
	}
	
	protected static void loadWorld( org.bukkit.World world )
	{
		worlds.add( world.getName() );
	}
}
