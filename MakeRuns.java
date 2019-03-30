import java.io.*;
import java.util.Arrays;

public class MakeRuns {

    public static void main(String[] args) throws IOException {
        if (args.length != 2){
            System.out.println("Usage: java MakeRun [minheapsize] [textfile]");
            System.exit(0);
        }

        int size = Integer.parseInt(args[0]);
        File Input = new File (args[1]);
        int numRuns = 1;

        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader(Input));
        MinHeap PriorityQ = new MinHeap(size);
        String Line = "";
        File Output = new File("Output.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(Output));
        while(Line != null){
            for(int i = 1; i < size ; i++){ // phase 1 -> Fill up the heap
                Line = br.readLine();
                PriorityQ.Push(Line);
            }
            String lastOutput = PriorityQ.Next();
            while(PriorityQ.Next() != null){ // While the heap is not empty
                if(IsGreater(PriorityQ.Next(),lastOutput)){ // if the next item is larger than last output
                    bw.write(PriorityQ.Next());
                    bw.newLine();
                    lastOutput = PriorityQ.Next();
                    Line = br.readLine();
                    if(Line != null){
                       PriorityQ.ReplaceRoot(Line);
                    }
                    else{
                        PriorityQ.RemoveRoot();
                    }
                }
                else{
                    PriorityQ.Block();
                }

                if(PriorityQ.ReservedSpace() == 1){
                    numRuns++;
                    bw.write(Character.toString((char)29));
                    bw.newLine();
                    PriorityQ.Reset();
                    lastOutput = PriorityQ.Next();
                }
            }
        }
        if(PriorityQ.ReservedSpace() < size){
            numRuns++;
            bw.write(Character.toString((char)29));
            bw.newLine();
            PriorityQ.Reset();
            while(PriorityQ.Next() != null){
                bw.write(PriorityQ.Next());
                PriorityQ.RemoveRoot();
            }
        }
        System.out.println(numRuns);
        System.out.println(Output);
        bw.close();
        br.close();
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
}
