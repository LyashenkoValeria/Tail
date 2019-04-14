import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.List;
import java.io.IOException;

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

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Tail.jar [-c num|-n num] [-o ofile] file0 file1 ...");
            parser.printUsage(System.err);
            return;
        }

        if (numberLine != null && numberChar != null) {
            try {
                throw new IOException("You can't use both flags -c and -n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (numberLine == null && numberChar == null) {
            numberLine = 10;
        }

        if (numberLine != null && numberLine < 0) {
            try {
                throw new IOException("You can't use negative number with -f");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (numberChar != null && numberChar < 0) {
            try {
                throw new IOException("You can't use negative number with -c");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Tail tail = new Tail(numberChar, numberLine, outputFile, inputFiles);
             String result;

           if (inputFiles != null) {
               if (numberLine != null) {
                   result = tail.getLinesForSeveralFiles(inputFiles);
               }
               else {
                   result = tail.getCharsForSeveralFiles(inputFiles);
               }
           }
           else {
              List<String> listFromConsole = tail.readerFromConsole();
              if (numberLine != null) {
                  result = tail.getLines(listFromConsole);
              }
              else
              {
                  result = tail.getCharacters(listFromConsole);
              }
           }

           if (outputFile != null) {
               tail.writerInFile(outputFile, result);
           }
           else {
               tail.writerInConsole(result);
           }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}

