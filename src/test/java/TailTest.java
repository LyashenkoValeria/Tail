import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;


public class TailTest {

    public boolean comparison (String expect, String real) throws IOException {
        List<String> expectedList = Tail.reader(expect);
        List<String> realList = Tail.reader(real);
        return expectedList.equals(realList);
    }

    String way = ".\\src\\main\\java\\";
    String Expect1 = ".\\src\\main\\java\\Expect1.txt";
    String Expect2 = ".\\src\\main\\java\\Expect2.txt";
    String Expect3 = ".\\src\\main\\java\\Expect3.txt";
    String Expect4 = ".\\src\\main\\java\\Expect4.txt";


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
        Tail tail = new Tail(10, null, way +"ofile1.txt", null);
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
        List<String> input = new ArrayList<String>();
        input.add(way + "file0.txt");
        Tail tail = new Tail(null, 10, null , input);
        tail.result();
        assertTrue(comparison(Expect1, tail.outFile()));
    }

    @Test
    public void fromConsoleToConsole() throws IOException {
        Tail tail = new Tail(4, null, null, null);
        tail.result();
        assertTrue(comparison(tail.outFile(), Expect4));
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
    public void writeUnrealFile() {
        List<String> input = new ArrayList<String>();
        input.add(way + "file0.txt");
        final Tail tail = new Tail(null, 7, way + "ofile.txt", input);
        assertThrows(IOException.class, new Executable() {
            public void execute() throws Throwable {
                tail.result();
            }
        });
    }
}