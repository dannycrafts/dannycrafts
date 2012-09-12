package com.dannycrafts.snapshot;

import java.io.*;

import com.dannycrafts.*;
import com.dannycrafts.exception.*;

public class Snapshot
{

	public final int xSize;
	public final int ySize;
	public final int zSize;

	public Snapshot( int xSize, int ySize, int zSize )
	{
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
	}

	private final int pillarLength()
	{
		return zSize;
	}

	private final int pillarRowLength()
	{
		return zSize * xSize;
	}

	private final int snapshotLength()
	{
		return zSize * xSize * ySize;
	}

	public BlockValue getBlock( int i )
	{
		return getBlock( ( i / pillarLength() ) % pillarRowLength(), i / pillarRowLength(), i % pillarLength() );
	}

	public BlockValue getBlock( int x, int y, int z )
	{
		//Plugin.announce( "i = " + Integer.toString( x * pillarLength() + y * pillarRowLength() + z ) );
		return getBlock( x * pillarLength() + y * pillarRowLength() + z );
	}

	public void writeTo( OutputStream output ) throws IOException
	{
		// File version:
		output.write( 0 );

		// The first column:
		BlockValue blockValue = getBlock( 0 );
		int stringLength = 1;
		int i = 1;
		for ( ; i < pillarLength(); i++ )
		{
			if ( blockValue.equals( getBlock( i ) ) )
				stringLength++;
			else
			{
				// Add data
				output.write( stringLength );
				writeBlockValue( output, blockValue );

				// Start counting new string:
				blockValue = getBlock( i );
				stringLength = 1;
			}
		}
		// If the last string is only air, just write a 0 byte:
		if ( blockValue.getBlockId() == 0 && blockValue.getBlockId() == 0 )
		output.write( 0 );
		// If not, just write data like normal:
		else
		{
			output.write( stringLength );
			writeBlockValue( output, blockValue );
		}

		// Then expand to a row in the y-axis direction
		for ( i = pillarRowLength(); i < snapshotLength(); )
		{
			int offset = 0, k = 0, pillarSize = 0; stringLength = 0; blockValue = null;

			for ( int j = 0; j < pillarLength(); i++, j++, k++ )
			{
				// If the block from this pillar is different than the block from the other pillar
				if ( !getBlock( i ).equals( getBlock( i - pillarRowLength() ) ) )
				{
					if ( stringLength == 0 )
					{
						blockValue = getBlock( i );
						offset = k;
						stringLength = 1;
					}
					else
					{
						if ( getBlock( i ).equals( blockValue ) )
							stringLength++;
						else
						{
							pillarSize += offset + stringLength;

							// Add data
							output.write( stringLength );
							output.write( offset );
							writeBlockValue( output, blockValue );

							// Start new string
							blockValue = getBlock( i );
							offset = 0; k = 0;
							stringLength = 1;
						}
					}
				}
				// If the blocks are the same when a string was counting, it means the string has 
				else
				{
					if ( stringLength > 0 )
					{
						pillarSize += offset + stringLength;

						output.write( stringLength );
						output.write( offset );
						writeBlockValue( output, blockValue );

						k = 0; // offset does not need to be reset, it will be assigned the value of k when a new string starts.
						stringLength = 0;
					}
				}
			}

			if ( pillarSize != pillarLength() )
			{
				output.write( 0 );
			}
			else
			{
				output.write( stringLength );
				output.write( offset );
				writeBlockValue( output, blockValue );
			}

			i += ( pillarRowLength() - pillarLength() );
		}

		// Then complete the rows in the x-axis direction
		i = 0;
		for ( int y = 0; y < 16; y++ )
		{
			i += pillarLength();
			for ( int x = 1; x < 16; x++ )
			{
				int offset = 0, k = 0, pillarSize = 0; stringLength = 0; blockValue = null;

				for ( int j = 0; j < pillarLength(); i++, j++, k++ )
				{
					//Plugin.announce( this.blocks[i].getRawValue() + " == " + this.blocks[i - pillarLength()].getRawValue() + "?" );

					if ( !getBlock( i ).equals( getBlock( i - pillarLength() ) ) )
					{
						if ( stringLength == 0 )
						{
							blockValue = getBlock( i );
							offset = k;
							stringLength = 1;
						}
						else
						{
							if ( getBlock( i ).equals( blockValue ) )
								stringLength++;
							else
							{
								pillarSize += offset + stringLength;

								// Add data
								output.write( stringLength );
								output.write( offset );
								writeBlockValue( output, blockValue );

								// Start new string
								blockValue = getBlock( i );
								offset = 0; k = 0;
								stringLength = 1;
							}
						}
					}
					else
					{
						if ( stringLength > 0 )
						{
							pillarSize += offset + stringLength;

							output.write( stringLength );
							output.write( offset );
							writeBlockValue( output, blockValue );

							k = 0; // offset does not need to be reset, it will be assigned the value of k when a new string starts.
							stringLength = 0;
						}
					}
				}

				if ( pillarSize != pillarLength() )
				{
					output.write( 0 );
				}
				else
				{
					output.write( stringLength );
					output.write( offset );
					writeBlockValue( output, blockValue );
				}
			}
		}
	}

	private void writeBlockValue( OutputStream output, BlockValue blockValue ) throws IOException
	{
		output.write( blockValue.getBlockId() );
		output.write( blockValue.getBlockData() << 4 );
	}

	public void setBlock( int i, BlockValue value )
	{
		setBlock( ( i / pillarLength() ) % pillarRowLength(), i / pillarRowLength(), i % pillarLength(), value );
	}

	public void setBlock( int x, int y, int z, BlockValue value )
	{
		setBlock( x * pillarLength() + y * pillarRowLength() + z, value );
	}

	public void readFrom( InputStream input ) throws IOException, VersionNotSupportedException, CorruptedException
	{
		// Check file version:
		if ( input.read() != 0 )
			throw new VersionNotSupportedException();

		// Scan first column (0, 0)
		int i = 0;
		BlockValue blockValue;
		int stringLength = input.read();
		while ( stringLength != 0 )
		{
			blockValue = readBlockValue( input );

			for ( int j = 0; j < stringLength; j++, i++ )
			{
				setBlock( i, blockValue );
			}

			if ( i == pillarLength() )
				break;
			else if ( i > pillarLength() )
				throw new CorruptedException();

			stringLength = input.read();
		}
		blockValue = new BlockValue(); // Air
		for ( ; i < pillarLength(); i++ )
			setBlock( i, blockValue );

		// Row 0 in y-axis direction:
		for ( i = pillarRowLength(); i < snapshotLength(); )
		{
			stringLength = input.read();
			int j = 0;

			// For each string:
			while ( stringLength != 0 && j < pillarLength() )
			{
				int offset = input.read();

				// Fill the unspecified blocks:
				for ( int k = 0; k < offset; k++, j++, i++ )
				{
					setBlock( i, getBlock( i - pillarRowLength() ) );
				}

				blockValue = readBlockValue( input );

				// Apply the string of blocks:
				for ( int k = 0; k < stringLength; k++, j++, i++ )
				{
					setBlock( i, blockValue );
				}

				stringLength = input.read();
			}

			if ( j > pillarLength() ) throw new CorruptedException();

			// Fill the remaining blocks:
			for ( ; j < pillarLength(); j++, i++ )
				setBlock( i, getBlock( i - pillarRowLength() ) );

			i += (pillarRowLength() - pillarLength());
		}

		i = 0;
		for ( int y = 0; y < 16; y++ )
		{
			i += pillarLength();
			for ( int x = 1; x < 16; x++ )
			{
				stringLength = input.read();
				int j = 0;

				// For each string:
				while ( stringLength != 0 && j < pillarLength() )
				{
					int offset = input.read();

					// Fill the unspecified blocks:
					for ( int k = 0; k < offset; k++, j++, i++ )
					{
						setBlock( i, getBlock( i - pillarLength() ) );
					}

					blockValue = readBlockValue( input );

					// Apply the string of blocks:
					for ( int k = 0; k < stringLength; k++, j++, i++ )
					{
						setBlock( i, blockValue );
					}

					stringLength = input.read();
				}

				if ( j > pillarLength() ) throw new CorruptedException();

				// Fill the remaining blocks:
				for ( ; j < pillarLength(); j++, i++ )
					setBlock( i, getBlock( i - pillarLength() ) );
			}
		}
	}

	private BlockValue readBlockValue( InputStream input  ) throws IOException
	{
		int field1 = input.read();
		int field2 = input.read();

		return new BlockValue( field1, field2 >> 4 );
	}
}
