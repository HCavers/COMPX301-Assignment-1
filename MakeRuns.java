import java.io.*;
import java.util.Arrays;

public class MakeRuns {

    public static void main(String[] args) throws IOException {
        if (args.length != 2){
            System.out.println("Usage: java MakeRun [minheapsize] [textfile]");
            System.exit(0);
        }

        int size = Integer.parseInt(args[0]);
        //File Input = new File (args[1]);

        System.out.println(args[1]);
        //int NumLines = LineCount(filePath);
        //String[] LineList= new String[NumLines];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader br = new BufferedReader(new FileReader(Input));
        MinHeap PriorityQ = new MinHeap(size);
        String Line = br.readLine();
        PriorityQ.Print();
        File Output = new File("Output.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(Output));
        while(Line != null){
            for(int i = 1; i < size ; i++){ // phase 1 -> Fill up the heap
                PriorityQ.Push(Line);
                Line = br.readLine();
                PriorityQ.Print(); // For testing
            }
            System.out.println("Heap is filled up");
            String lastOutput = PriorityQ.Next();
            bw.write(lastOutput);
            bw.newLine();
            // Write to output
            while(PriorityQ.Next() != null){ // While the heap is not empty
                if(IsGreater(PriorityQ.Next(),lastOutput)){ // if the next item is larger than last output
                    // Write to output
                    System.out.println("Is Greater");
                    bw.write(PriorityQ.Next());
                    bw.newLine();
                    lastOutput = PriorityQ.Next();
                    Line = br.readLine();
                    if(Line != null){
                        PriorityQ.ReplaceRoot(Line);
                    }
                    //PriorityQ.RemoveRoot();

                }
                else{
                    System.out.println("Block");
                    PriorityQ.Block();
                }

                if(PriorityQ.ReservedSpace() == 1){
                    // min heap is blocked -> Write seperator character to output
                    bw.write(Character.toString((char)29));
                    bw.newLine();
                    //bw.write("End OF RUN");
                    System.out.println("Reset");
                    PriorityQ.Reset();
                }
            }
            System.out.println("Heap is filled up");
        }
        bw.close();
        br.close();


        // Old Code
        //String currLine = br.readLine();
        //int count = 0;
        // while (currLine != null){
        //     LineList[count] = currLine;
        //     count++;
        //     currLine = br.readLine();
        //}
        // int TotalRuns = NumRuns(NumLines,size);
        // System.out.println(TotalRuns);
        // System.out.println(Arrays.toString(LineList)); // For Testing
        // String OutputFilePath = filePath.substring(0, filePath.lastIndexOf("\\")) + "\\" + "Output.txt";
        // System.out.println(OutputFilePath);
        // GenerateRuns(OutputFilePath,TotalRuns,size,LineList);


    }

    public static boolean IsLower (String First, String Second){ // Returns true if first is lower than second
        if(First.compareTo(Second) > 0){
            return false;
        }

        return true;
    }

    public static boolean IsGreater (String First, String Second){
        return IsLower(Second, First);
    }

    public static int LineCount(String Path) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String currLine = br.readLine();
        int i = 0;
        while (currLine != null){
            i++;
            currLine = br.readLine();
        }
        br.close();
        return i;
    }

    public static int NumRuns(int NumList, int HeapSize){
        if(NumList % HeapSize == 0){
            return NumList / HeapSize;
        }

        else{
            return (NumList / HeapSize) + 1;
        }
    }

    public static String[] CreateRun(int startIndex, int heapsize, String[] Lines){
        String[] CurrRun = new String[heapsize];
        int currIndex = 0;
        while(startIndex + currIndex < Lines.length && currIndex < heapsize){
            CurrRun[currIndex] = Lines[startIndex + currIndex];
            //System.out.println("Currindex is: " + currIndex);
            //System.out.println("startindex is: " +  startIndex);
            currIndex++;
        }
        //System.out.println("Ended Run");
        System.out.println(Arrays.toString(CurrRun));
        return CurrRun;
    }

    public static void WriteRun(BufferedWriter bw, int startIndex, int heapsize, String[] Lines)  throws IOException{
        String[] Run = CreateRun(startIndex,heapsize,Lines);
        bw.write(Arrays.toString(Run));

    }

    public static void GenerateRuns(String filepath, int numRuns,int heapsize,String[] Lines)throws IOException{
        int startIndex = 0;
        int currRuns = 0;
        File Output = new File(filepath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(Output));
        while(currRuns < numRuns){
            //System.out.println("Writing Run");
            WriteRun(bw,startIndex,heapsize,Lines);
            startIndex = startIndex + heapsize;
            currRuns++;
        }
        bw.close();

    }

}
