// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// This class was made to be used by the file manager class to 
// handle reading input from a file

class FileInput
{
	private String nextInput;
	private BufferedReader reader;
	
	public FileInput(String fileName) throws IOException
	{
		reader = new BufferedReader(new FileReader(fileName));
		nextInput = reader.readLine();
	}
	
	// Returns whether the file still has input or not
	public boolean hasNext()
	{
		if(nextInput == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	// Returns the next input from the file
	public String Next() throws IOException
	{
		String result = nextInput;
		nextInput = reader.readLine();
		return result;
	}
	
	// Closes the reader to the file
	public void close() throws IOException
	{
		reader.close();
	}
}