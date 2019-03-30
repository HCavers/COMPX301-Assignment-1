class PolyMerge
{
	public static void main(String[] args)
	{
		int fileNum = 5;
		int runNum = 47;
		int[] dis = getDistrubution(fileNum, runNum);
		printDis(dis);
	}
	
	private static void printDis(int[] distribution)
	{
		for(int i = 0; i < distribution.length; i++)
		{
			System.out.println(distribution[i]);
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