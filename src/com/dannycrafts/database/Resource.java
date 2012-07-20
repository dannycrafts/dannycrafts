package com.dannycrafts.database;

import java.util.*;

public abstract class Resource
{
	// The time it takes for the resource to be considered old
	protected static final long oldTime = 30000;
	
	// The following variables are updated on the main thread, and only read from the garbage collector:
	protected Integer useCounter = 0;
	protected long idleSince = 0;
	protected boolean hasChanged = false;
	
	protected void acquire()
	{
		synchronized ( useCounter )
		{
			useCounter++;
		}
	}
	
	protected void create() throws Exception
	{
		save();
	}
	
	protected boolean isOld()
	{
		int ucCopy; long isCopy;
		synchronized ( useCounter )
		{
			ucCopy = useCounter;
			isCopy = idleSince;
		}
		
		return ucCopy == 0 && ( new Date().getTime() - isCopy ) > oldTime;
	}
	
	protected abstract void load() throws Exception;
	
	// This function should be called explicitly every time the resource is done editing.
	public void touch()
	{
		hasChanged = true;
	}
	
	public void release()
	{
		synchronized ( useCounter )
		{
			useCounter--;
			
			idleSince = new Date().getTime();
		}
	}
	
	protected void save() throws Exception {}
}
