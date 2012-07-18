// In this class, you can set what debug messages you want to be shown in the standard output.
// Look below to see a 'checklist' of the components you want to enable debugging for.

package com.dannycrafts.debug;

public class DebugOptions
{
	// If you set the following to true, all debug messages will be enabled.
	// Not recommended.
	protected static final boolean all = false;

	
	
	// Informs you about general events.
	// Recommended.
	protected static final boolean general = false;
	
	// Informs you about resources that get saved or released by the garbage collector.
	// Recommended when investigating memory leaks.
	protected static final boolean garbageCollector = false;
}
