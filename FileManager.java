import java.io.File;
import java.io.IOException;

class FileManager
{
	static final String DEFAULT_NAME = "Temp_File_";
	
	private int _id;
	private String _fileName;
	private int _numRuns;
	private FileInput _input;
	private FileOutput _output;
	private boolean _lock;
	
	public FileManager(int id, boolean outputMode) throws IOException
	{
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
	
	public void print() throws IOException
	{
		if(!(readable()))
		{
			swap();
		}
		while(_input.hasNext())
		{
			String line = _input.Next();
			if(!(line.equals(Character.toString((char)29))))
			{
				System.out.println(line);
			}
		}
	}
	
	public int getNumRuns()
	{
		return _numRuns;
	}
	
	public void lock()
	{
		_lock = true;
	}
	
	public void unlock()
	{
		_lock = false;
	}
	
	public void writeLine(String input) throws IOException
	{
		if(_output != null)
		{
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
	
	public String getNext() throws IOException
	{
		if(_input != null)
		{
			if(_lock == true)
			{
				return null;
			}
			if(_input.hasNext())
			{
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