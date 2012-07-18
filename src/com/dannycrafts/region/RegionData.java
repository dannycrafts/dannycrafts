package com.dannycrafts.region;

import java.io.*;
import java.util.*;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.dannycrafts.*;
import com.dannycrafts.database.Resource;
import com.dannycrafts.exception.*;

public class RegionData extends Resource
{
	public final long id;
	public long parent;
	public String name;		// The name of this region.
	public String description;	// A user written, one-line, description for this region.
	public PlotDimensions plotDimensions;	// The dimensions for the sub-plots.
	public Map<WorldId, List<PlotCoords>> plots; // The plots owned by this region.
	
	protected RegionData( long id )
	{
		this.id = id;
	}

	@Override
	protected void load() throws Exception
	{
		File regionFile = new File( Regions.getDataFolder() + "/" + id );
		YamlConfiguration config = YamlConfiguration.loadConfiguration( regionFile );
		
		this.parent = config.getLong( "parent", -1 );
		if ( this.parent < 0 ) throw new CorruptedException();
		
		this.name = config.getString( "name" );
		if ( this.name == null ) this.name = "[Name Error]";
		
		this.description = config.getString( "description", "" );
		
		int length = config.getInt( "length", 0 );
		if ( length == 0 ) throw new CorruptedException();
		int width = config.getInt( "width", 0 );
		if ( width == 0 ) throw new CorruptedException();
		int height = config.getInt( "height", -1 );
		if ( height == -1 ) throw new CorruptedException();
		this.plotDimensions = new PlotDimensions( length, width, height );
		
		ConfigurationSection plotSection = config.getConfigurationSection( "plots" );
		if ( plotSection != null )
		{
			Set<String> worlds = plotSection.getKeys( false );
			this.plots = new HashMap<WorldId, List<PlotCoords>>( worlds.size() );
			
			for ( String world : worlds )
			{
				List<String> plotStrings = plotSection.getStringList( world );
				List<PlotCoords> plots = new ArrayList<PlotCoords>( plotStrings.size() );
				for ( String plot : plotStrings )
				{
					PlotCoords plotCoords = new PlotCoords( plot );
					
					plots.add( plotCoords );
				}
				
				this.plots.put( new WorldId( world ), plots );
			}
		}
		else throw new CorruptedException();
	}
	
	@Override
	protected void save() throws Exception
	{
		File regionFile = new File( Regions.getDataFolder() + "/" + id );
		YamlConfiguration config = new YamlConfiguration();

		config.set( "parent", this.parent );
		config.set( "name", this.name );
		config.set( "description", this.description );
		config.set( "dimensions", this.plotDimensions.toString() );
		for ( Map.Entry<WorldId, List<PlotCoords>> entry : this.plots.entrySet() )
		{
			List<String> plots = new ArrayList<String>( entry.getValue().size() );
			
			for ( PlotCoords coords : entry.getValue() )
				plots.add( coords.toString() );
			
			config.set( "plots." + entry.getKey().getName(), plots );
		}
		
		config.save( regionFile );
	}
}
