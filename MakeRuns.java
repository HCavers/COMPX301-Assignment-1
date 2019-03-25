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
}
