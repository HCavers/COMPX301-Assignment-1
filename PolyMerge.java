import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class PolyMerge
{
	public static void main(String[] args)
	{
		try
		{
			if(args.length != 1)
			{
				System.out.println("Usage: java PolyMerge <number of files>");
				System.exit(0);
			}
			
			int numFiles = Integer.parseInt(args[0]);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int numRuns = Integer.parseInt(reader.readLine());
			String fileName = reader.readLine();
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
	
	private static int[] getDistrubution(int fileNum, int runNum)
	{
		int[] distribution = getIntialDistribution(fileNum);
		int index = 0;
		while(true)
		{
			int value = distribution[index];
			distribution[index] = 0;
			for(int i = 0; i < distribution.length; i++)
			{
				if(i != index)
				{
					distribution[i] += value;
				}
			}
			int totalRuns = getTotalRuns(distribution);
			if(totalRuns > runNum)
			{
				break;
			}
			index = shiftIndex(index, fileNum);
		}
		return distribution;
	}
	
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
	
	private static int getTotalRuns(int[] distribution)
	{
		int result = 0;
		for(int i = 0; i < distribution.length; i++)
		{
			result += distribution[i];
		}
		return result;
	}
	
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