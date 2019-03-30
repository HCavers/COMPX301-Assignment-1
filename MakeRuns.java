import java.io.*;
import java.util.Arrays;

public class MakeRuns {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.out.println("Usage: java MakeRun [minheapsize]"); // Checks if the user entered a size for the minheap as the only argument and if not prints a usage statement
                System.exit(0);
            }

            int size = Integer.parseInt(args[0]);
            int numRuns = 1;
            boolean isfirstrun = true;
            String Line = "";

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            MinHeap PriorityQ = new MinHeap(size);
            File Output = new File("Output.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(Output));
            
            while (Line != null) {
                for (int i = 1; i < size; i++) { // phase 1 -> Fill up the heap
                    Line = br.readLine();
                    PriorityQ.Push(Line);
                }
                String lastOutput = PriorityQ.Next();
                while (PriorityQ.Next() != null) { // While the heap is not empty
                    if (IsGreater(PriorityQ.Next(), lastOutput)) { // if the next item is larger than last output
                        if (isfirstrun) {
                            isfirstrun = false;
                        } else {
                            bw.newLine();
                        }

                        bw.write(PriorityQ.Next());
                        lastOutput = PriorityQ.Next();
                        Line = br.readLine();
                        if (Line != null) {
                            PriorityQ.ReplaceRoot(Line);
                        } else {
                            PriorityQ.RemoveRoot();
                        }
                    } else {
                        PriorityQ.Block();
                    }

                    if (PriorityQ.ReservedSpace() == 1) {
                        numRuns++;
                        bw.newLine();
                        bw.write(Character.toString((char) 29));
                        PriorityQ.Reset();
                        lastOutput = PriorityQ.Next();
                    }
                }
            }
            if (PriorityQ.ReservedSpace() < size) {
                numRuns++;
                bw.newLine();
                bw.write(Character.toString((char) 29));
                PriorityQ.Reset();
                while (PriorityQ.Next() != null) {
                    bw.newLine();
                    bw.write(PriorityQ.Next());
                    PriorityQ.RemoveRoot();
                }
            }
            System.out.println(numRuns);
            System.out.println(Output);
            bw.close();
            br.close();
        }
        catch(IOException iex){
            System.out.println(iex.getMessage());
        }

        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
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
