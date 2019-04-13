import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tail {

    private Integer numberChar;
    private Integer numberLine;


    public void tail(Integer numberChar, Integer numberLine){
        this.numberChar = null;
        this.numberLine = null;
    }

    public List<String> readerFromFile (String input) throws IOException {   //Читает строки из файла и складывает их в массив
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
        System.out.print("Enter input data. To complete, write EnterEnd");
        Scanner reader = new Scanner(System.in);
        String line = "";
        while (line.equals("EnterEnd")){
            line = reader.nextLine();
            resultList.add(line);
        }
        resultList.remove(resultList.size() - 1);
        reader.close();
        return resultList;
    }



    public String getLines(List<String> file, Integer numberLine) throws IOException { //Получает нужное число строк из одного заданного файла
        //На вход подается входной файл в виде листа и число строк, которое нужно извлечь
        List<String> resultList = new ArrayList<String>(); //Лист для считывания нужного числа строк
        if (file.size() <= numberLine && numberLine > -1) {
            resultList = file.subList(file.size() - numberLine, file.size());
        }
        String result = "";
        for (int i = 0; i < resultList.size(); i++){
            result = resultList.get(i) + "\n";
        }
        return result;
    }



    public String getCharacters (List<String> file, Integer numberChar) throws IOException { //Получает n количество символов из заданного файла
        //На вход получает файл в виде листа и количество символов, которые нужно извлечь
        int count = 0; //Счётчик символов во всём файле
        String result = "";
        for(String i : file){
            count += i.length();
        }
        if (count >= numberChar && numberChar > 0) {
                for(int i = file.size(); i > -1 && count>0; i--){
                    String line = file.get(i);
                    if (line.length() > count) {
                        line = line.substring(line.length() - count);
                    }
                        result = line + "\n" + result;
                        count -= line.length();
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

