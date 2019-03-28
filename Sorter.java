import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

class Sorter
{
	private List<FileManager> list;
	private int outputId;
	
	public Sorter(int[] distribution, String fileName) throws IOException
	{
		list = new ArrayList<>();
		FileInput inputFile = new FileInput(fileName);
		for(int i = 0; i < distribution.length; i++)
		{
			if(distribution[i] == 0)
			{
				outputId = i;
			}
			FileManager file = new FileManager(i, true);
			while(inputFile.hasNext())
			{
				String line = inputFile.Next();
				file.writeLine(line);
				if(file.getNumRuns() == distribution[i])
				{
					break;
				}
			}
			file.swap();
			list.add(file);
		}
		list.get(outputId).swap();
	}
	
	public void sort() throws IOException
	{
		String[] nextValues = new String[list.size()];
		fillNextValues(nextValues);
		while(listNotEmpty(nextValues))
		{
			int smallestValue = getSmallestValue(nextValues);
			writeToFile(nextValues[smallestValue]);
			System.out.println(nextValues[smallestValue]);
			nextValues[smallestValue] = list.get(smallestValue).getNext();
		}	
		//for(int i = 0; i < list.size(); i++)
		//{
			//list.get(i).close();
		//}
	}
	
	private void fillNextValues(String[] nextValues) throws IOException
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).hasNext())
			{
				nextValues[i] = list.get(i).getNext();
			}
		}
	}
	
	private boolean listNotEmpty(String[] nextValues)
	{
		for(int i = 0; i < nextValues.length; i++)
		{
			if(nextValues[i] != null)
			{
				return true;
			}
		}
		return false;
	}
	
	private int getSmallestValue(String[] nextValues)
	{
		if(nextValues.length < 2)
		{
			return -1;
		}
		int smallestValue = getMin(0, 1, nextValues);
		for(int i = 2; i < nextValues.length; i++)
		{
			smallestValue = getMin(smallestValue, i, nextValues);
		}
		return smallestValue;
	}
	
	private int getMin(int index1, int index2, String[] nextValues)
	{
		String firstValue = nextValues[index1];
		String secondValue = nextValues[index2];
		if(firstValue == null)
		{
			return index2;
		}
		if(secondValue == null)
		{
			return index1;
		}
		int result = firstValue.compareTo(secondValue);
		if(result > 0)
		{
			return index2;
		}
		else
		{
			return index1;
		}
	}
	
	private void writeToFile(String input) throws IOException
	{
		list.get(outputId).writeLine(input);
	}
}