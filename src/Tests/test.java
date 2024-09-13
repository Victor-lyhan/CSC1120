package Tests;

import java.sql.Array;
import java.util.*;
import Tests.NumpyException;

public class test {
    public static void main(String[] args) {
        //String input = "sdf";
        // String demo = Integer.parseInt(input) != null ? "Yes" : "No";

        Scanner input = new Scanner(System.in);

        ArrayList<String> names = new ArrayList<>();
        for(int i=0; i<3; i++){
            names.add(input.nextLine());
        }

        for(String a: names){
            System.out.println(a.toUpperCase());
            throw new NumpyException("Test");
        }
    }
}
