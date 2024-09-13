/*
 * Course: CSC1020
 * Homework 2 - File IO
 * Driver
 * Name: Victor Han
 * Last Updated: 9/6/2024
 */
package Week2.HW;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This is the driver class
 * that has helper methods and the main
 */
public class Driver {
    private static final int WHOLE_NUM = 57;
    private static final double FRAC = 4.876;
    private static final String STR = "I am Mr. Data";

    /**
     * Main method
     *
     * @param args input
     */
    public static void main(String[] args) {

        try (PrintWriter pw = new PrintWriter(Paths.get("data","data.txt").toFile());
             Scanner read = new Scanner(Paths.get( "data","data.txt").toFile())) {
            writeText(pw);
            System.out.println(readText(read));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                String.valueOf(Path.of("data","data.bin"))));
                DataInputStream dis = new DataInputStream(new FileInputStream(
                        Path.of( "data","data.bin").toFile()))) {
            writeBinary(dos);
            System.out.println(readBinary(dis));
        } catch (FileNotFoundException e) {

            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                Path.of("data","object.bin").toFile()));
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                        Path.of("data","object.bin").toFile()))) {
            writeObject(oos);
            System.out.println(readObject(ois));
        } catch (FileNotFoundException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException");
        }

    }

    private static void writeText(PrintWriter pw) {
        pw.write("57 4.876 I am Mr. Data");
        pw.close();
    }

    private static void writeBinary(DataOutputStream dos) throws IOException {
        dos.writeInt(WHOLE_NUM);
        dos.writeDouble(FRAC);
        dos.writeUTF(STR);
    }

    private static void writeObject(ObjectOutputStream oos) throws IOException {
        MyObject obj = new MyObject(WHOLE_NUM, FRAC, STR);
        oos.writeObject(obj);
    }

    private static String readText(Scanner read) {
        return read.nextLine();
    }

    private static String readBinary(DataInputStream dis) throws IOException {
        return Integer.toString(dis.readInt()) + " " +
                Double.toString(dis.readDouble()) + " " + dis.readUTF();
    }

    private static String readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        Object obj = ois.readObject();
        return obj.toString();
    }

}
