import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tail {

    private Integer numberChar;
    private Integer numberLine;
    private String ofile;
    private List<String> inputFiles;


    public Tail(Integer numberChar, Integer numberLine, String ofile, List<String> inputFiles){
        this.numberChar = numberChar;
        this.numberLine = numberLine;
        this.ofile = ofile;
        this.inputFiles = inputFiles;
    }

    public static List<String> reader (InputStream input) {//Читает строки из файла и складывает их в лист
        Scanner reader = new Scanner(new InputStreamReader(input));
        List<String> lines = new ArrayList<String>();
        while ((reader.hasNextLine())){
            lines.add(reader.nextLine());
        }
        //reader.close();
        return (lines);
    }




    public String getLines(List<String> file) throws IOException { //Получает нужное число строк из одного заданного файла
        //На вход подается входной файл в виде листа и число строк, которое нужно извлечь
        List<String> resultList; //Лист для считывания нужного числа строк
        if (file.size() >= numberLine) {
            resultList = file.subList(file.size() - numberLine, file.size());
        }
        else {
            throw new IOException("You can't take such a number of lines");
        }
        StringBuilder result = new StringBuilder();
        for (String aResultList : resultList) {
            result.append(aResultList).append("\r\n");
        }
        return result.toString();
    }



    public String getCharacters (List<String> file) throws IOException { //Получает n количество символов из заданного файла
        //На вход получает файл в виде листа и количество символов, которые нужно извлечь
        int count = 0; //Счётчик символов во всём файле
        StringBuilder result = new StringBuilder();
        for(String i : file){
            count += i.length();
        }
        int num = numberChar;
        if (count >= numberChar) {
                for(int i = file.size()-1; i > -1 && num > 0; i--){
                    String line = file.get(i);
                    if (line.length() > num) {
                        line = line.substring(line.length() - num);
                    }
                        result.insert(0, line + "\r\n");
                        num -= line.length();
                }
        }
        else
        {
            throw new IOException("You can't such a number of chars");
        }
        return result.toString();
    }



    public String getLinesForSeveralFiles (List<String> listOfFiles) throws IOException {
        StringBuilder result = new StringBuilder();
        if (listOfFiles.size() == 1) {
            result = new StringBuilder(getLines(reader(new FileInputStream(listOfFiles.get(0)))));
        } else {
            for (String listOfFile : listOfFiles) {
                result.append(listOfFile).append("\r\n").append(getLines(reader(new FileInputStream(listOfFile)))).append("\r\n");
            }
        }
            return result.toString();
    }



    public String getCharsForSeveralFiles (List<String> listOfFiles) throws IOException {
        StringBuilder result = new StringBuilder();
        if (listOfFiles.size() == 1) {
            result = new StringBuilder(getCharacters(reader(new FileInputStream(listOfFiles.get(0)))));
        } else {
            for (String listOfFile : listOfFiles) {
                result.append(listOfFile).append("\r\n").append(getCharacters(reader(new FileInputStream(listOfFile)))).append("\r\n");
            }
        }
        return result.toString();
    }



    public  void writer(String outputName, String output) throws IOException { //Выводит данные в выходной файл
        //На вход получает имя выходного файла и выводимые данные
        if (ofile == null) {
            System.out.print(output);
        } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            writer.write(output);
            writer.close();
        }
    }


    public void result()throws IOException{


        if (numberLine != null && numberChar != null) throw new IOException("You can't use both flags -c and -n");

        if (numberLine == null && numberChar == null) {
            numberLine = 10;
        }

        if (numberLine != null && numberLine < 0) throw new IOException("You can't use negative number with -n");

        if (numberChar != null && numberChar < 0) throw new IOException("You can't use negative number with -c");

        if (inputFiles != null) {
            for (String inputFile : inputFiles) {
                File in = new File(inputFile);
                if (!in.exists()) throw new IOException("This input file is unreal");
            }
            }

            if(ofile != null){
                File out = new File(ofile);
                if (!out.exists()) out.createNewFile();
            }


            String result;
            if (inputFiles != null) {
                if (numberLine != null) {
                    result = getLinesForSeveralFiles(inputFiles);
                } else {
                    result = getCharsForSeveralFiles(inputFiles);
                }
            }
            else {
                if (numberLine != null) {
                    result = getLines(reader(System.in));
                } else {
                    result = getCharacters(reader(System.in));
                }
            }

            writer(ofile, result);
    }



    public String outFile () {
        return ofile;
    }
}


