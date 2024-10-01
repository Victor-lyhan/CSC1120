package Tests;

import java.util.*;

public class test {
    public static void main(String[] args) {
        //String input = "sdf";
        // String demo = Integer.parseInt(input) != null ? "Yes" : "No";

        Scanner input = new Scanner(System.in);

        VictorHan<String> names = new VictorHan<>();
        for(int i=0; i<3; i++){
            names.add(input.nextLine());
        }

        for(String a: names){
            System.out.println(a.toUpperCase());
            throw new NumpyException("Test");
        }
    }
}
