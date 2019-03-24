//Hunter Cavers: 1288108
//Sivaram Manoharan: 1299026

class MinHeap{
	public String[] heap;
	public MinHeap(int arrayLength){
		heap = new String[arrayLength];
		heap[0] = "1";
	}
	
	public void Insert(String input)
	{ // Uses up heap to keep sorted
		int index = Integer.parseInt(heap[0]);
		int newIndex = index++;
		if(newIndex < heap.length){
			heap[newIndex] = input;
		}
		//UpHeap();
	} 
	
	public String Remove()
	{ // Uses  down heap to keep sorted
		int index = Integer.parseInt(heap[0]);
		String result = heap[1];
		return "";
	} 
	
	private void DownHeap()
	{
		
	}
	
	private void UpHeap(int index)
	{
		int parentID = GetParent(index);
	}
	
	private int GetLeftChild(int parentID)
	{
		return parentID * 2;
	}
	
	private int GetRightChild(int parentID)
	{
		return (parentID * 2) + 1;
	}
	
	private int GetParent(int childID)
	{
		return childID / 2;
	}
	
	private int GetMin(int index1, int index2)
	{
		String text1 = heap[index1];
		String text2 = heap[index2];
		int result = text1.compareTo(text2);
		if(result > 0)
		{
			return index2;
		}
		else
		{
			return index1;
		}
	}
}