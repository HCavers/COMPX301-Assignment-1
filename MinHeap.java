//Hunter Cavers: 1288108
//Sivaram Manoharan: 1299026

class MinHeap
{
	public String[] heap;
	public MinHeap(int arrayLength)
	{
		heap = new String[arrayLength];
		heap[0] = Integer.toString(arrayLength);
	}

	public String Next() // Returns the next element to read
	{
		int blockIndex = Integer.parseInt(heap[0]);
		if(blockIndex > 1)
		{
			return heap[1];
		}
		else
		{
			return null;
		}
	}

	public void RemoveRoot() // Removes the current root
	{
		int blockIndex = Integer.parseInt(heap[0]);
		blockIndex--;
		SwapValues(blockIndex, 1);
		heap[blockIndex] = null;
		DownHeap(1);
	}

	public void Block() // Moves the value from root to the end of the heap and shortens the available space of the heap
	{
		int reservedSpace = Integer.parseInt(heap[0]);
		reservedSpace--;
		heap[0] = Integer.toString(reservedSpace);
		SwapValues(reservedSpace, 1);
		DownHeap(1);
	}

	public void Reset() // Clears all blocked space and sorts the data into heap order
	{
		heap[0] = Integer.toString(heap.length);
		Heapify();
	}

	public void Push(String input) // Adds an element to the end of heap then upheaps to keep in priority order
	{ // Uses up heap to keep sorted
		int index = NextSpace();
		if(index > 0)
		{
			heap[index] = input;
		}
		else
		{
			throw new IndexOutOfBoundsException("Min heap is out of room");
		}
		UpHeap(index);
	}

	public void ReplaceRoot(String input) // Replaces the root with the input
	{ // Uses  down heap to keep sorted
		heap[1] = input;
		DownHeap(1);
	}

	private void Heapify() // Sorts the list into heap order
	{
		for(int i = ReservedSpace() - 1; i > 0; i--)
		{
			DownHeap(i);
		}
	}

	public int ReservedSpace()
	{
		return Integer.parseInt(heap[0]);
	} // Returns the amount of available space

	private int NextSpace() // Returns the index of the last space
	{
		for(int i = 1; i < ReservedSpace(); i++)
		{
			if(heap[i] == null)
			{
				return i;
			}
		}
		return -1;
	}

	private void DownHeap(int index) // Downheaps the list
	{

		if(IsLeaf(index))
		{
			return;
		}
		if(IsReserved(index))
		{
			return;
		}
		int smallestChild = GetSmallestChild(index);
		int minID = GetMin(index, smallestChild);
		if(minID == index)
		{
			return;
		}
		else
		{
			SwapValues(index, smallestChild);
			DownHeap(smallestChild);
		}
	}

	private void UpHeap(int index) // Upheaps the list
	{
		int parentID = GetParent(index);
		int minID = GetMin(index, parentID);
		if(minID == parentID)
		{
			return;
		}
		else if(index == 1)
		{
			return;
		}
		else
		{
			SwapValues(index, parentID);
			UpHeap(parentID);
		}
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
		if(text1 == null)
		{
			return index2;
		}
		else if (text2 == null)
		{
			return index1;
		}
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

	private void SwapValues(int index1, int index2)
	{
		String temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}

	private int GetSmallestChild(int index)
	{
		if(IsLeaf(index)){
		    return -1;
        }
        int leftChild = GetLeftChild(index);
        int rightChild = GetRightChild(index);
        if(!(ChildExist(leftChild))){
            return rightChild;
        }
        else if(!(ChildExist(rightChild))){
            return leftChild;
        }

        int smallestChild = GetMin(leftChild, rightChild);
		return smallestChild;
	}

	private boolean IsLeaf(int index)
	{
		int leftChild = GetLeftChild(index);
		int rightChild = GetRightChild(index);
		if(ChildExist(leftChild) || ChildExist(rightChild))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private boolean IsReserved(int index)
	{
		if(index >= ReservedSpace())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean ChildExist(int index)
	{
		if(index >= ReservedSpace())
		{
			return false;
		}
		else if(heap[index] == null)
		{
			return false;
		}
		return true;
	}
}
