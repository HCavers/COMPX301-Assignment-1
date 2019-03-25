import java.io.*;
import java.util.Arrays;

public class MakeRuns {

  public static void main(String[] args) throws IOException {
         if (args.length != 2){
             System.out.println("Usage: java MakeRun [minheapsize] [textfilepath]");
             System.exit(0);
         }

         int size = Integer.parseInt(args[0]);
         String filePath = args[1];

         int NumLines = LineCount(filePath);
         String[] LineList= new String[NumLines];

         BufferedReader br = new BufferedReader(new FileReader(filePath));
         String currLine = br.readLine();
         int count = 0;
         while (currLine != null){
             LineList[count] = currLine;
             count++;
             currLine = br.readLine();
         }
         int TotalRuns = NumRuns(NumLines,size);
         System.out.println(TotalRuns);
         System.out.println(Arrays.toString(LineList)); // For Testing
     }




public static int LineCount(String Path) throws IOException {
       BufferedReader br = new BufferedReader(new FileReader(Path));
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
          while(Lines[currIndex] != null && currIndex < startIndex + heapsize){
              CurrRun[currIndex] = Lines[startIndex + currIndex];
              currIndex++;
          }
          return CurrRun;
    }

    public static void WriteRun(File output, int startIndex, int heapsize, String[] Lines)  throws IOException{
        String[] Run = CreateRun(startIndex,heapsize,Lines);
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        bw.write(Arrays.toString(Run));
    }
}
