// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.File;
import java.io.IOException;

// This class is used by the sorter class to handle transitioning 
// between input and output on one file

class FileManager
{
	// Constant value used when naming files
	static final String DEFAULT_NAME = "Temp_File_";
	// The id number for this file
	private int _id;
	// The name of the file
	private String _fileName;
	// The number of runs in the file
	private int _numRuns;
	// Instance of class to handle input
	private FileInput _input;
	// instance of class to handle output
	private FileOutput _output;
	// Whether the file is locked or not
	private boolean _lock;
	
	public FileManager(int id, boolean outputMode) throws IOException
	{
		// Creates a new file using the filename made and depending on the arguments supplied
		// opens a file input or output
		_id = id;
		_numRuns = 0;
		_lock = false;
		_fileName = DEFAULT_NAME + Integer.toString(id);
		File file = new File(_fileName);
		if(file.exists())
		{
			file.delete();
		}
		file.createNewFile();
		if(outputMode)
		{
			_output = new FileOutput(_fileName);
		}
		else 
		{
			_input = new FileInput(_fileName);
		}
	}
	
	// Prints the contents of the file to standard out
	public void print() throws IOException
	{
		// If file is not readable
		if(!(readable()))
		{
			// Change to read mode
			swap();
		}
		while(_input.hasNext())
		{
			String line = _input.Next();
			// Skips the group separator character
			if(!(line.equals(Character.toString((char)29))))
			{
				System.out.println(line);
			}
		}
	}
	
	// Returns the number of runs in the file
	public int getNumRuns()
	{
		return _numRuns;
	}
	
	// Locks the file to prevent any input from being read
	public void lock()
	{
		_lock = true;
	}
	
	// Unlocls the file 
	public void unlock()
	{
		_lock = false;
	}
	
	// Writes a line to the file
	public void writeLine(String input) throws IOException
	{
		if(_output != null)
		{
			// Check if group separator is being sent to file
			// and if it is increase the number of runs by one
			if(input.length() == 1)
			{
				int value = (int)input.charAt(0);
				if(value == 29)
				{
					_numRuns++;
				}
			}
			_output.writeLine(input);
		}
	}
	
	// Returns if the file has any more input
	public boolean hasNext()
	{
		if(_input != null)
		{
			return _input.hasNext();
		}
		else
		{
			return false;
		}
	}
	
	// Returns the next bit of input from the file
	public String getNext() throws IOException
	{
		if(_input != null)
		{
			// If file is locked then return null
			if(_lock == true)
			{
				return null;
			}
			if(_input.hasNext())
			{
				// If input is group separator then decrease then number of runs by one
				String next = _input.Next();
				if(next.length() == 1)
				{
					int value = (int)next.charAt(0);
					if(value == 29)
					{
						_numRuns--;
						lock();
						return null;
					}
				}
				return next;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	// Returns if the file is readble or not
	public boolean readable()
	{
		if(_input == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	// If file is in input mode swap to output mode
	// otherwise if file is in output mode then swap to input mode
	public void swap() throws IOException
	{
		if(_input == null)
		{
			_output.close();
			_output = null;
			_input = new FileInput(_fileName);
		}
		else
		{
			_input.close();
			_input = null;
			_output = new FileOutput(_fileName);
		}
	}
	
	// Closes input or output depending on which is open then deletes the file
	public void close() throws IOException
	{
		if(_input != null)
		{
			_input.close();
		}
		if(_output != null)
		{
			_output.close();
		}
		File file = new File(_fileName);
		file.delete();
	}
}