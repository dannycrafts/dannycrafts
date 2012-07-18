package com.dannycrafts.debug;

import com.dannycrafts.plugin.Plugin;

public class Debug
{
	private static boolean enabled( boolean option )
	{
		return DebugOptions.all | option;
	}
	
	public static void debug( String message )
	{
		Plugin.announce( message );
	}
	
	public static boolean general()
	{
		return enabled( DebugOptions.general );
	}
	
	public static boolean garbageCollector()
	{
		return enabled( DebugOptions.garbageCollector );
	}
}
