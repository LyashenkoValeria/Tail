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

    public List<String> readerFromFile (String input) throws IOException {   //Читает строки из файла и складывает их в лист
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }
        return (lines);
    }


    public List<String> readerFromConsole (){
        List<String> resultList = new ArrayList<String>();
        System.out.println("Enter data. To complete, write EnterEnd");
        Scanner reader = new Scanner(System.in);
        String line = reader.nextLine();
        while (!line.equals("EnterEnd")) {
            resultList.add(line);
            line = reader.nextLine();
        }
        reader.close();
        return resultList;
    }



    public String getLines(List<String> file) throws IOException { //Получает нужное число строк из одного заданного файла
        //На вход подается входной файл в виде листа и число строк, которое нужно извлечь
        List<String> resultList = new ArrayList<String>(); //Лист для считывания нужного числа строк
        if (file.size() >= numberLine) {
            resultList = file.subList(file.size() - numberLine, file.size());
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
        return result;
    }



    public String getLinesForSeveralFiles (List<String> listOfFiles) throws IOException {
        String result = "";
        if (listOfFiles.size() == 1) {
            result = getLines(readerFromFile(listOfFiles.get(0)));
        } else {
            for (int i = 0; i < listOfFiles.size(); i++) {
                result = listOfFiles.get(i) + "\r\n" + getLines(readerFromFile(listOfFiles.get(i)));
            }
        }
            return result;
    }



    public String getCharsForSeveralFiles (List<String> listOfFiles) throws IOException {
        String result = "";
        if (listOfFiles.size() == 1) {
            result = getCharacters(readerFromFile(listOfFiles.get(0)));
        } else {
            for (int i = 0; i < listOfFiles.size(); i++) {
                result = listOfFiles.get(i) + "\r\n" + getCharacters(readerFromFile(listOfFiles.get(i)))+"\r\n";
            }
        }
        return result;
    }



    public void writerInFile (String outputName, String output) throws IOException { //Выводит данные в выходной файл
        //На вход получает имя выходного файла и выводимые данные
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        writer.write(output);
        writer.close();
    }



    public void writerInConsole(String output){
        System.out.print(output);
    }
}


