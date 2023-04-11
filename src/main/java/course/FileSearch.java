package course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileSearch{
    public static void main(String[] args){
        sortRead(args[0]);
    }
    static void sortRead(String path){
        try{
            String[] s = Files.readString(Path.of(path)).toLowerCase().split("[\u0000-@]|\\[[-`]|\\[\\{-\u007f]|\\s++");
            printArray(s);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    static void printArray(String[] s){
        Arrays.sort(s);
        for(int i = 0;i < s.length;i++){
            if(s[i].equals("")||s[i].equals(s[i-1])) System.out.print("");
            else System.out.println(s[i]);
        }
    }
}
