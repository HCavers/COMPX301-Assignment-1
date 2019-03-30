// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// This class was made to be used by the file manager class to 
// handle writing output to a file

class FileOutput
{
	private BufferedWriter writer;
	private boolean firstWrite;
	
	public FileOutput(String fileName) throws IOException
	{
		writer = new BufferedWriter(new FileWriter(fileName));
		firstWrite = true;
	}
	
	// Writes a line to the file
	public void writeLine(String input) throws IOException
	{
		// If this is the first input that is being written don't write newline;
		if(firstWrite == false)
		{
			writer.newLine();
		}
		else
		{
			firstWrite = false;
		}
		writer.write(input, 0, input.length());
		writer.flush();
	}
	
	// Close the writer to the file
	public void close() throws IOException
	{
		writer.close();
	}
}