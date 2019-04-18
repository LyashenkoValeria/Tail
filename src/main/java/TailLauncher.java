import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.List;


public class TailLauncher {
    @Option(name = "-o", usage = "Output file")
    private String outputFile = null;

    @Option(name = "-c", usage = "Extracts num characters", forbids = "-n")
    private Integer numberChar = null;

    @Option(name = "-n", usage = "Extracts num lines", forbids = "-c")
    private Integer numberLine = null;

    @Argument(metaVar = "InputFiles", usage = "Input files name")
    private List<String> inputFiles = null;

    public static void main(String[] args) {
        new TailLauncher().launch(args);
    }

    private void launch(String[] args)  {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Tail.jar [-c num|-n num] [-o ofile] file0 file1 ...");
            parser.printUsage(System.err);
            return;
        }
        try {
            Tail tail = new Tail(numberChar, numberLine, outputFile, inputFiles);
            tail.result();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

