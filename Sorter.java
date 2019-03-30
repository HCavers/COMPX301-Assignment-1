import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

class Sorter
{
	private List<FileManager> list;
	private int outputId;
	
	public Sorter(int[] distribution, String fileName, int numRuns) throws IOException
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
			if(file.getNumRuns() < distribution[i])
			{
				int dummyRuns = distribution[i] - file.getNumRuns();
				addDummyRuns(file, dummyRuns);
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
		while(true)
		{
			mergeRun(nextValues);
			if(emptyFiles() == (list.size() - 1))
			{
				// print non empty file
				int nonEmptyFile = getNonEmptyFile();
				list.get(nonEmptyFile).print();
				// close all files
				closeFiles();
				// break out of loop
				break;
			}
			else if(emptyFiles() > 1)
			{
			}
			else if(emptyFiles() == 1)
			{
				int emptyFile = getEmptyFile();
				list.get(outputId).swap();
				list.get(emptyFile).swap();
				outputId = emptyFile;
				unlockFiles();
				fillNextValues(nextValues);
			}
			else if(emptyFiles() == 0)
			{
				unlockFiles();
				fillNextValues(nextValues);
			}
		}
	}
	
	public void mergeRun(String[] input) throws IOException
	{
		while(inputNotEmpty(input))
		{
			int smallestValue = getSmallestValue(input);
			writeToFile(input[smallestValue]);
			input[smallestValue] = null;
			if(list.get(smallestValue).hasNext())
			{
				input[smallestValue] = list.get(smallestValue).getNext();
			}
		}
		writeToFile(Character.toString((char)29));
	}
	
	private int getNonEmptyFile()
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getNumRuns() != 0)
			{
				return i;
			}
		}
		return -1;
	}
	
	private void closeFiles() throws IOException
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).close();
		}
	}
	
	private void unlockFiles()
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).unlock();
		}
	}
	
	private int getEmptyFile()
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(fileEmpty(list.get(i)))
			{
				return i;
			}
		}
		return -1;
	}
	
	private int emptyFiles()
	{
		int count = 0;
		for(int i = 0; i < list.size(); i++)
		{
			if(fileEmpty(list.get(i)))
			{
				count++;
			}
		}
		return count;
	}
	
	private boolean fileEmpty(FileManager file)
	{
		if(file.getNumRuns() == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean inputNotEmpty(String[] input)
	{
		for(int i = 0; i < input.length; i++)
		{
			if(input[i] != null)
			{
				return true;
			}
		}
		return false;
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
	
	private int getNextOutputFile()
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getNumRuns() == 0)
			{
				return i;
			}
		}
		return -1;
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
	
	private void addDummyRuns(FileManager file, int numRuns) throws IOException
	{
		for(int i = 0; i < numRuns; i++)
		{
			file.writeLine(Character.toString((char)29));
		}
	}
	
	private void writeToFile(String input) throws IOException
	{
		list.get(outputId).writeLine(input);
	}
}