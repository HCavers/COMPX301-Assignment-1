import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FileInput
{
	private String nextInput;
	private BufferedReader reader;
	
	public FileInput(String fileName) throws IOException
	{
		reader = new BufferedReader(new FileReader(fileName));
		nextInput = reader.readLine();
	}
	
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
	
	public String Next() throws IOException
	{
		String result = nextInput;
		nextInput = reader.readLine();
		return result;
	}
	
	public void close() throws IOException
	{
		reader.close();
	}
}