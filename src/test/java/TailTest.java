import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;


public class TailTest {

    public boolean comparison (String expect, String real) throws IOException {
        List<String> expectedList = Tail.reader(new FileInputStream(expect));
        List<String> realList = Tail.reader(new FileInputStream(real));
        return expectedList.equals(realList);
    }

    String way = "src" + File.separator + "main" + File.separator + "java" + File.separator;
    String Expect1 = way + "Expect1.txt";
    String Expect2 = way + "Expect2.txt";
    String Expect3 = way + "Expect3.txt";
    String Expect4 = way + "Expect4.txt";

    @After
    public void deleteFile() {
        File testFile = new File(way + "ofile.txt");
        testFile.delete();
    }

    @Test
    public void fromFileToFile() throws IOException{
        List<String> input = new ArrayList<String>();
        input.add(way + "file0.txt");
        Tail tail = new Tail(null, null, way +"ofile1.txt", input);
        tail.result();
        assertTrue(comparison(Expect1, tail.outFile()));
    }


    @Test
    public void fromConsoleToFile() throws IOException {
        System.setIn(new FileInputStream(way + "ConsoleIn.txt"));
        Tail tail = new Tail(25, null, way +"ofile1.txt", null);
        tail.result();
        assertTrue(comparison(Expect2, tail.outFile()));
    }

    @Test
    public void fromSeveralFilesToFile() throws IOException {
        List<String> input = new ArrayList<String>();
        input.add(way + "file0.txt");
        input.add(way + "file1.txt");
        Tail tail = new Tail(null, 5, way +"ofile1.txt", input);
        tail.result();
        assertTrue(comparison(Expect3, tail.outFile()));
    }

    @Test
    public void fromFileToConsole() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        List<String> input = new ArrayList<String>();
        input.add(way + "file0.txt");
        Tail tail = new Tail(null, 10, null , input);
        tail.result();

        String getFromCons = out.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(way + "ConsoleOut.txt"));
        writer.write(getFromCons);
        writer.close();
        assertTrue(comparison(Expect1, way + "ConsoleOut.txt"));
    }

    @Test
    public void fromConsoleToConsole() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        System.setIn(new FileInputStream(way + "ConsoleIn.txt"));
        Tail tail = new Tail(4, null, null, null);
        tail.result();

        String getFromCons = out.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(way + "ConsoleOut.txt"));
        writer.write(getFromCons);
        writer.close();
        assertTrue(comparison(Expect4, way + "ConsoleOut.txt"));
    }

    @Test
    public void useTwoFlags()  {
        final Tail tail = new Tail(10, 10, null, null);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                tail.result();
            }
        });
    }

    @Test
    public void useNegativeArgument() {
        final Tail tail = new Tail(-10, null, null, null);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                tail.result();
            }
        });
    }

    @Test
    public void takeMoreThanThereIs() {
        List<String> input = new ArrayList<String>();
        input.add(way + "file2.txt");
        final Tail tail = new Tail(null, 3, way + "ofile1.txt", input);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                tail.result();
            }
        });
    }

    @Test
    public void readUnrealFile() {
        List<String> input = new ArrayList<String>();
        input.add(way + "file.txt");
        final Tail tail = new Tail(null, 7, way + "ofile1.txt", input);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                tail.result();
            }
        });
    }

    @Test
    public void writeUnrealFile() throws IOException {
        List<String> input = new ArrayList<String>();
        input.add(way + "file0.txt");
        final Tail tail = new Tail(null, 10, way + "ofile.txt", input);
        tail.result();
        assertTrue(comparison(Expect1, tail.outFile()));

    }
}

