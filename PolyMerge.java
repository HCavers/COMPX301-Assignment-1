// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class PolyMerge
{
	public static void main(String[] args)
	{
		// Try catch to handle any errors
		try
		{
			// Checks if correct arguments have been supplied
			if(args.length != 1)
			{
				System.out.println("Usage: java PolyMerge <number of files>");
				System.exit(0);
			}
			int numFiles = Integer.parseInt(args[0]);
			// Get other input from standard in
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int numRuns = Integer.parseInt(reader.readLine());
			String fileName = reader.readLine();
			// Gets the distribution needed to perform a poly-phase merge sort
			int[] distribution = getDistrubution(numFiles, numRuns);
			Sorter sorter = new Sorter(distribution, fileName, numRuns);
			sorter.sort();
		}
		catch (IOException iex)
		{
			System.out.println(iex.getMessage());
		}
		catch (Exception ex)
		{
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	// Returns distribution based on the number of runs and number of files
	private static int[] getDistrubution(int fileNum, int runNum)
	{
		// Get initial distribution 
		int[] distribution = getIntialDistribution(fileNum);
		int index = 0;
		// Start at index 0 and keep looping through array
		while(true)
		{
			// Take value at index
			int value = distribution[index];
			// Set value at index position to zero
			distribution[index] = 0;
			// Add value to every other position except index position 
			for(int i = 0; i < distribution.length; i++)
			{
				if(i != index)
				{
					distribution[i] += value;
				}
			}
			// Get the total number of runs in distribution
			int totalRuns = getTotalRuns(distribution);
			// If total number of runs in distribution is greater than number of runs we have return distribution;
			if(totalRuns > runNum)
			{
				break;
			}
			// Otherwise shift index and repeat;
			index = shiftIndex(index, fileNum);
		}
		// Once loop has been broken out of return the distrubution
		return distribution;
	}
	
	// Returns a distribution where there is only one run in one file
	private static int[] getIntialDistribution(int fileNum)
	{
		int[] distribution = new int[fileNum];
		distribution[0] = 1;
		for(int i = 1; i < distribution.length; i++)
		{
			distribution[i] = 0;
		}
		return distribution;
	}
	
	// Returns the total number of runs in the distribution
	private static int getTotalRuns(int[] distribution)
	{
		int result = 0;
		for(int i = 0; i < distribution.length; i++)
		{
			result += distribution[i];
		}
		return result;
	}
	
	// shifts the index to the next space wrapping around if index is larger than the array
	private static int shiftIndex(int index, int fileNum)
	{
		int result = index + 1;
		if(result == fileNum)
		{
			result = 0;
		}
		return result;
	}
}