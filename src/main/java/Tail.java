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

    public static List<String> reader (String input) throws IOException {//Читает строки из файла и складывает их в лист
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }
        return (lines);
    }




    public String getLines(List<String> file) throws IOException { //Получает нужное число строк из одного заданного файла
        //На вход подается входной файл в виде листа и число строк, которое нужно извлечь
        List<String> resultList = new ArrayList<String>(); //Лист для считывания нужного числа строк
        if (file.size() >= numberLine) {
            resultList = file.subList(file.size() - numberLine, file.size());
        }
        else {
            throw new IOException("You can't take such a number of lines");
        }
        String result = "";
        for (int i = 0; i < resultList.size(); i++){
            result += resultList.get(i) + "\r\n";
        }
        return result;
    }



    public String getCharacters (List<String> file) throws IOException { //Получает n количество символов из заданного файла
        //На вход получает файл в виде листа и количество символов, которые нужно извлечь
        int count = 0; //Счётчик символов во всём файле
        String result = "";
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
                        result = line + "\r\n" + result;
                        num -= line.length();
                }
        }
        else
        {
            throw new IOException("You can't such a number of chars");
        }
        return result;
    }



    public String getLinesForSeveralFiles (List<String> listOfFiles) throws IOException {
        String result = "";
        if (listOfFiles.size() == 1) {
            result = getLines(reader(listOfFiles.get(0)));
        } else {
            for (int i = 0; i < listOfFiles.size(); i++) {
                result += listOfFiles.get(i) + "\r\n" + getLines(reader(listOfFiles.get(i)))+ "\r\n";
            }
        }
            return result;
    }



    public String getCharsForSeveralFiles (List<String> listOfFiles) throws IOException {
        String result = "";
        if (listOfFiles.size() == 1) {
            result = getCharacters(reader(listOfFiles.get(0)));
        } else {
            for (int i = 0; i < listOfFiles.size(); i++) {
                result += listOfFiles.get(i) + "\r\n" + getCharacters(reader(listOfFiles.get(i))) + "\r\n";
            }
        }
        return result;
    }



    public void writer (String outputName, String output) throws IOException { //Выводит данные в выходной файл
        //На вход получает имя выходного файла и выводимые данные
        if (ofile == null) {
            outputName = ".\\src\\main\\java\\ConsoleOut.txt";
            ofile = ".\\src\\main\\java\\ConsoleOut.txt";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        writer.write(output);
        writer.close();   }



    public void result()throws IOException{

        if (numberLine != null && numberChar != null) throw new IOException("You can't use both flags -c and -n");

        if (numberLine == null && numberChar == null) {
            numberLine = 10;
        }

        if (numberLine != null && numberLine < 0) throw new IOException("You can't use negative number with -n");

        if (numberChar != null && numberChar < 0) throw new IOException("You can't use negative number with -c");

        if (inputFiles != null) {
            for (int i = 0; i < inputFiles.size(); i++){
                File in = new File(inputFiles.get(i));
                if (!in.exists()) throw new IOException("This input file is unreal");
                }
            }

            if(ofile != null){
                File out = new File(ofile);
                if (!out.exists()) throw new IOException("This output file is unreal");
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
                String console = ".\\src\\main\\java\\ConsoleIn.txt";
                if (numberLine != null) {
                    result = getLines(reader(console));
                } else {
                    result = getCharacters(reader(console));
                }
            }

            writer(ofile, result);
    }



    public String outFile () {
        return ofile;
    }
}


