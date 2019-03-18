//Hunter Cavers: 1288108
//Sivaram Manoharan: 1299026

class MinHeap{
	private String[] heap;
	public MinHeap(int arrayLength){
		heap = new String[arrayLength];
		heap[0] = 1;
	}
	
	public void Insert(String input){ // Uses up heap to keep sorted
		int index = Integer.parseInt(heap[0]);
		int newIndex = index++;
		if(newIndex < heap.length()){
			heap[newIndex] = input;
		}
		UpHeap();
	} 
	
	public String Remove(){ // Uses  down heap to keep sorted
		int index = Integer.parseInt(heap[0]);
		String result = heap[1];
		
	} 
	
	private void DownHeap(){}
	
	private void UpHeap(){}
}