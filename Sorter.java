// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

class Sorter
{
	// List of files
	private List<FileManager> list;
	// Index of file in output mode
	private int outputId;
	
	public Sorter(int[] distribution, String fileName, int numRuns) throws IOException
	{
		// Setup an list for all files and a an inputfile to read input from
		list = new ArrayList<>();
		FileInput inputFile = new FileInput(fileName);
		// For each file
		for(int i = 0; i < distribution.length; i++)
		{
			// If the distribution value for the file is zero
			if(distribution[i] == 0)
			{
				// Then this is the output file so save the id
				outputId = i;
			}
			// Create new file in output mode
			FileManager file = new FileManager(i, true);
			// While there is input
			while(inputFile.hasNext())
			{
				// If the files number of runs match the distributions
				if(file.getNumRuns() == distribution[i])
				{
					// Then break out of loop
					break;
				}
				else
				{
					// Write line to file
					String line = inputFile.Next();
					file.writeLine(line);
				}				
			}
			// If the number of runs in the file is less than the distributions
			if(file.getNumRuns() < distribution[i])
			{
				// Add dummy runs to make up the distribution number
				int dummyRuns = distribution[i] - file.getNumRuns();
				addDummyRuns(file, dummyRuns);
			}
			// Swap file to input mode and add to list
			file.swap();
			list.add(file);
		}
		// Swap output file back into output mode
		list.get(outputId).swap();
	}
	
	// Performs a poly phase merge sort on the data
	public void sort() throws IOException
	{
		// Get the next input from each input file
		String[] nextValues = new String[list.size()];
		fillNextValues(nextValues);
		while(true)
		{
			// Merge one run from each input file into output file
			mergeRun(nextValues);
			// If there is only file with runs left
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
			// If the is 1 empty file
			else if(emptyFiles() == 1)
			{
				// Get the emptyFile and swap it to output mode
				// and swap the output file to input mode
				int emptyFile = getEmptyFile();
				list.get(outputId).swap();
				list.get(emptyFile).swap();
				// Change to id of the output file to the new output file
				outputId = emptyFile;
				// Unlock all files
				unlockFiles();
				// Get the next input from each input file
				fillNextValues(nextValues);
			}
			// If there are not empty files
			else if(emptyFiles() == 0)
			{
				// Unlock all files
				unlockFiles();
				// Get the next input from each input file then repeat loop
				fillNextValues(nextValues);
			}
		}
	}
	
	// Merges one run from each input file into the output file
	public void mergeRun(String[] input) throws IOException
	{
		// while there is still input
		while(inputNotEmpty(input))
		{
			// Get the smallest value from all the files and write it to the output file
			int smallestValue = getSmallestValue(input);
			writeToFile(input[smallestValue]);
			// Replace with next value from file
			input[smallestValue] = null;
			if(list.get(smallestValue).hasNext())
			{
				input[smallestValue] = list.get(smallestValue).getNext();
			}
		}
		// Once all input has been written to file output the group separator character
		writeToFile(Character.toString((char)29));
	}
	
	// Returns the index of the first file int the collection that still contains runs
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
	
	// Closes all files
	private void closeFiles() throws IOException
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).close();
		}
	}
	
	// Unlocks all files
	private void unlockFiles()
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).unlock();
		}
	}
	
	// Returns the index of the first empty file in the collection
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
	
	// Returns a count of all the files with no runs left
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
	
	// Returns if a file has no more runs
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
	
	// Checks if the collection is empty
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
	
	// Fill a collection with the next values from all the input files
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
	
	// Gets a file with no runs left in it to become the next output file;
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
	
	// Gets the smallest value out of the collection
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
	
	// Gets the minimum value from the 2 indexs in the collection
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

	// Adds empty runs to file to match distribution
	private void addDummyRuns(FileManager file, int numRuns) throws IOException
	{
		for(int i = 0; i < numRuns; i++)
		{
			file.writeLine(Character.toString((char)29));
		}
	}
	
	// writes output to file
	private void writeToFile(String input) throws IOException
	{
		list.get(outputId).writeLine(input);
	}
}