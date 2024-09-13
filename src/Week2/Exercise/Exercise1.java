package Week2.Exercise;

import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Exercise1 {
    public static void main(String[] args) {
        System.out.println(Paths.get("data", "exercise1-data.bin").toFile().exists());
        try(Scanner read = new Scanner(Paths.get("data", "exercise1-data.txt").toFile());
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(Paths.get("data", "exercise1-data.bin").toFile()))){
            for(int i=0; i<100; i++){
                dos.writeDouble(read.nextDouble());
            }
            System.out.println("Text file size: " + Paths.get("data", "exercise1-data.txt").toFile().length() + "bytes.");
            System.out.println("Binary file size: " + Paths.get("data", "exercise1-data.bin").toFile().length() + "bytes.");
            System.out.printf("Space savings %.2f%%", ((double)Paths.get("data", "exercise1-data.bin").toFile().length() / Paths.get("data", "exercise1-data.txt").toFile().length()*100));
        } catch(FileNotFoundException e){
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
