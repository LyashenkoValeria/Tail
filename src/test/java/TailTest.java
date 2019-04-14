import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TailTest {
    String way = "C:\\Users\\user\\IdeaProjects\\Task2\\src\\main\\java\\";
    String Expect1 = "C:\\Users\\user\\IdeaProjects\\Task2\\src\\main\\java\\Expect1.txt";

    @Test
    public void fromFileToFile() {
        String[] data = {"-c", "120", "-o", way +"ofile1.txt", way + "file0.txt"};
        TailLauncher.main(data);
    }
}