package com.dannycrafts.plugin;

import java.io.*;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.plugin.PluginManager;

import com.dannycrafts.*;
import com.dannycrafts.database.Database;
import com.dannycrafts.regen.Regenerator;
import com.dannycrafts.snapshot.Snapshots;

public class Plugin extends org.bukkit.plugin.java.JavaPlugin
{
	private static Logger logger;
	private static org.bukkit.Server bukkitServer;
	private static File pluginFolder;
	
	public static org.bukkit.Server getBukkitServer()
	{
		return bukkitServer;
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args )
	{
		
		
		return true;
	}
	
	@Override
	public void onDisable()
	{
		Regenerator.uninit();
		Snapshots.uninit();
		Database.uninit();
		
		logger = null;
		bukkitServer = null;
		pluginFolder = null;
	}
	
	public static void announce( String message )
	{
		synchronized ( logger )
		{
			logger.info( message );
		}
	}
	
	@Override
	public void onEnable()
	{
		logger = this.getLogger();
		bukkitServer = this.getServer();
		pluginFolder = this.getDataFolder();
		
		// Load bukkit worlds:
		for ( World world : this.getServer().getWorlds() )
			WorldId.loadWorld( world );
		
		getPluginFolder().mkdir();
		
		Database.init();
		Snapshots.init();
		Regenerator.init();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents( new Listener(), this );
	}
	
	public static File getPluginFolder()
	{
		return pluginFolder;
	}
	
	public static void report( String message )
	{
		synchronized ( logger )
		{
			logger.severe( message );
		}
	}
	
	public static void warn( String message )
	{
		synchronized ( logger )
		{
			logger.warning( message );
		}
	}
}