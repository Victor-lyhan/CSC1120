package Week5;

import java.sql.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Arrays;

public class PracticeExam {
    /* List declaring*//*
    List<Integer> a = new ArrayList<>(list);
    Arrays.sort(a);
    */


    /* Functional Interface, Stream*//*
    String[] words = {"are","Hard"};
    List<String> list = Arrays.asList(words);
    List<String> result = list.stream()
            .filter(s -> s.length() >= 3 && s.length() <= 7)
            .map(String::toLowerCase)
            .filter(s -> {
                String vowels = "aeiou";
                return !vowels.contains(String.valueOf(s.charAt(0)));
            })
            .filter(s -> {
                return !s.charAt(0).equals('a');
            })
            .toList();

    //EX
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)    // Filter even numbers
            .map(n -> n * n)            // Square each number
            .toList();

    Predicate<String> isLongString = s -> s.length() > 5; // true or false
    Consumer<String> printConsumer = s -> System.out.println(s);
    */



    /* JavaFX */
    /*
    psvm{ launch(args)}
    public void start(stage stage)
    hvbox = new hvbox(xxx);
    h,vbox.getChildren.add(xxx);
    stage.setScene(new Scene(h,bbox));
    stage.setTitle(0, show()


    @FXML
    private void search(){
    try(Scanner input = new Scanner(Paths.get(xxx.txt))){
        boolean found = false;
        while(input.hasNext() && !found){
            found = input.next().equals(resultField.getText());
        }
        resultField.setText(found? "true": "false");
     } catch (IOException e){
        Alert alt = new Alert(Alert.Alerttype.ERROR);
        alert.setContent("xxx"),show();
     }
    */



    /* ArrayList DS*/ /*
    public class SJArrayList<E> implements List<E> {
        private static final int INITIAL_CAPACITY = 10;
        // backing data structure is an array
        private E[] data;
        private int size;
        private int capacity;
        public SJArrayList() {
            size = 0;
            capacity = INITIAL_CAPACITY;
            data = (E[]) new Object[INITIAL_CAPACITY];
        }
        @Override
        public boolean add(E e) {
            if(size == capacity) {
                reallocate();
            }
            data[size++] = e;
            return true;
        }
        @Override
        public E remove(int index) {
            if(index < 0 || index >= data.length){
                throw new IndexOutOfBoundsException("Invalid index " + index);
            }
            E old = data[index];
            for(int i=index; i<data.length -1 ; i++){
                data[i] = data[i+1];
            }

            E[] newArray = (E()) new Object[data.length -1];
            for(int i = 0; i < newArray.length; i++) {
                newArray[i] = data[i]
            }

            data = newData;
            return old;
        }

        private void reallocate() {
            capacity *= 2;
            E[] newData = (E[])new Object[capacity];
            System.arraycopy(data, 0, newData, 0, data.length); // same as a for loop
            data = newData;
        }
      }*/

    /* file input stream *//*
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

    */


}

