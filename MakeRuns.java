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


}
