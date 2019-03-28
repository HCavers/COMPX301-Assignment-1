import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class FileOutput
{
	private BufferedWriter writer;
	private boolean firstWrite;
	
	public FileOutput(String fileName) throws IOException
	{
		writer = new BufferedWriter(new FileWriter(fileName));
		firstWrite = true;
	}
	
	public void writeLine(String input) throws IOException
	{
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
	
	public void close() throws IOException
	{
		writer.close();
	}
}