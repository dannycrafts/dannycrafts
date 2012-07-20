package com.dannycrafts;

import java.io.*;
import java.util.UUID;

import com.dannycrafts.plugin.Plugin;

public class WorldId
{
	private final UUID uid;
	
	public WorldId( org.bukkit.World world )
	{
		this.uid = world.getUID();
	}
	
	public WorldId( String worldName )
	{
		this( Plugin.getBukkitServer().getWorld( worldName ) );
	}
	
	public String getName()
	{
		return getBukkitWorld().getName();
	}
	
	public org.bukkit.World getBukkitWorld()
	{
		return Plugin.getBukkitServer().getWorld( uid );
	}
	
	public File getDataFolder()
	{
		return getBukkitWorld().getWorldFolder();
	}
}
