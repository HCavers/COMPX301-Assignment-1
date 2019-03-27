import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class FileOutput
{
	private BufferedWriter writer;
	
	public FileOutput(String fileName) throws IOException
	{
		writer = new BufferedWriter(new FileWriter(fileName));
	}
	
	public void writeLine(String input) throws IOException
	{
		writer.write(input, 0, input.length());
		writer.newLine();
		writer.flush();
	}
	
	public void close() throws IOException
	{
		writer.close();
	}
}