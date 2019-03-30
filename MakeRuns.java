import java.io.*;
import java.util.Arrays;

public class MakeRuns {

    public static void main(String[] args) {
        try {
            if (args.length != 1) { // Checks if the user entered a single argument for the size for the minheap
                System.out.println("Usage: java MakeRun [minheapsize]"); // if not print a usage statement and then exit
                System.exit(0);
            }

            int size = Integer.parseInt(args[0]); // set the integer size to the user entered argument
            int numRuns = 1; // set a counter for the number of runs
            boolean isFirstLine = true; // set a flag for if the current output is the first line
            String Line = ""; // Intitates the string Line which holds the current input line from standard.in

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Creates a buffered reader that takes in standard input
            MinHeap PriorityQ = new MinHeap(size); // Creates a minheap structure with the size that the user passed in
            File Output = new File("Output.txt"); // Creates output file to write data to
            BufferedWriter bw = new BufferedWriter(new FileWriter(Output)); // Creates buffered Writer that writes data to Output.txt

            while (Line != null) { // Checks that there is still data from standard input to read
                for (int i = 1; i < size; i++) { // Loops for every element in the minheap so that the minheap is filled up
                    Line = br.readLine();
                    PriorityQ.Push(Line); // Reads a line from standard input and stores that in the minheap
                }
                String lastOutput = PriorityQ.Next(); // Sets the string lastOuput to the next element we will read
                while (PriorityQ.Next() != null) { // While the heap is not empty
                    if (IsGreater(PriorityQ.Next(), lastOutput)) { // if the next item is larger than last output
                        if (isFirstLine) {
                            isFirstLine = false; // Checks if it is the first line and if so changes the flag
                        } else {
                            bw.newLine(); // Otherwise add a newline to seperate the elements
                        }

                        bw.write(PriorityQ.Next()); // Write the next element of data to the output file
                        lastOutput = PriorityQ.Next(); // Sets the string lastOutput to the element that was just written
                        Line = br.readLine(); // Reads next line of input
                        if (Line != null) {
                            PriorityQ.ReplaceRoot(Line); // If it is not the last data element then replace the root of the minheap with the next line of input
                        } else {
                            PriorityQ.RemoveRoot(); // otherwise remove the root
                        }
                    } else {
                        PriorityQ.Block(); // if the read element is smaller than the last output then it is put at the end of the array and the minheap is shrunk by 1
                    }

                    if (PriorityQ.ReservedSpace() == 1) { // if the minheap is fully blocked then it needs to be reset
                        numRuns++; // Increment the number of runs
                        bw.newLine();
                        bw.write(Character.toString((char) 29)); // Writes the group seperator character
                        PriorityQ.Reset(); // Resets the Priority queue so there is no more blocked space
                        lastOutput = PriorityQ.Next(); // Sets the last ouput to the next entry in the minheap
                    }
                }
            }
            if (PriorityQ.ReservedSpace() < size) { // checks if there are still elements in the array that have not been written
                numRuns++;
                bw.newLine();
                bw.write(Character.toString((char) 29)); // if so prints the group seperator character
                PriorityQ.Reset(); // Resets the heap
                while (PriorityQ.Next() != null) {
                    bw.newLine();
                    bw.write(PriorityQ.Next()); // Writes the next element to output
                    PriorityQ.RemoveRoot(); // Removes the root from the heap
                }
            }
            System.out.println(numRuns); // Prints out the number of runs
            System.out.println(Output); // Prints out the output file name
            bw.close(); // Closes the buffered writer
            br.close(); // Closes the buffered reader
        }
        catch(IOException iex){
            System.out.println(iex.getMessage()); // Catches IO exception
        }

        catch(Exception e){
            System.out.println("Error: " + e.getMessage()); // Catches general exception
        }
    }

    public static boolean IsLower (String First, String Second){ // Returns true if first is lower than second
        if(First.compareTo(Second) > 0){
            return false;
        }

        return true;
    }

    public static boolean IsGreater (String First, String Second){ // Returns true if first is greater than second
        return IsLower(Second, First);
    }
}
