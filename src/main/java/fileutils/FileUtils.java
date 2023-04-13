package fileutils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
@SuppressWarnings("all")
/**
 * @author Zhai Jinpei
 */
public class FileUtils extends File{
    public FileUtils(@NotNull String pathname){
        super(pathname);
    }
    public FileUtils mk(String s){
        FileUtils fileUtils = new FileUtils(this.getAbsolutePath()+File.separator+s);
        if(!(fileUtils).exists())
            fileUtils.mkdir();
        return fileUtils;
    }
    public FileUtils logs(String s){
        System.out.println("-------------------Logs:--------------------\n"+s);
        return this;
    }
    public FileUtils cf(String s) throws IOException{
        String path = this.getAbsolutePath()+"\\"+s;
        FileUtils fileUtil = new FileUtils(path);
        if(!fileUtil.exists())
            fileUtil.createNewFile();
        return fileUtil;
    }
    public FileUtils write(String... strings) throws FileNotFoundException{
        System.setOut(new PrintStream(this));
        System.out.println(Arrays.toString(strings).trim().replaceAll("\\[|]",""));
        return this;
    }
    @SuppressWarnings("all")
    public FileUtils readTo(String p) throws IOException{
        var s = Files.readString(Path.of(this.getAbsolutePath()));
        try(
                OutputStreamWriter osw = new OutputStreamWriter(
                        new FileOutputStream(p, true),
                        "utf-8");
        ){
            osw.write(s);
            osw.flush();
        }
        return this;
    }
    public FileUtils readToConsole() throws IOException{
        var s = Files.readString(Path.of(this.getAbsolutePath()));
        System.out.println(s);
        return this;
    }
    public FileUtils replCont(String p){
        try(
            FileWriter fileWriter = new FileWriter(Path.of(this.getAbsolutePath()).toFile())){
            fileWriter.write(p);
            fileWriter.flush();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return this;
    }
    public FileUtils cdU(){
        return new FileUtils(this.getParentFile().getAbsolutePath());
    }
    public FileUtils getFile(String p){
        return new FileUtils(this.getAbsolutePath()+File.separator+p);
    }
    public FileUtils copyTo(FileUtils fileUtilsSrc,FileUtils fileUtilsDec){
        byte[] write = new byte[1024];
        int n;
        try(
                FileInputStream fileInputStream = new FileInputStream(fileUtilsSrc);
                FileOutputStream fileOutputStream = new FileOutputStream( fileUtilsDec)){
            while((n = fileInputStream.read(write)) != -1){
                fileOutputStream.write(write,0,n);
            }
        }catch(IOException ignored){}
        return this;
    }
    public FileUtils clear() throws IOException{
        var clr = new FileWriter(this);
        clr.write("");
        clr.flush();
        clr.close();
        return this;
    }
    public FileUtils dir() {
        String list = Arrays.toString(this.list());
        String newlist = list.replace("[", this.getAbsolutePath() + "\n- ")
                .replace("]", "\n")
                .replace(",", "\n-");
        System.out.println(newlist);
        return this;
    }
    public FileUtils rmv(String p){
        this.cd(p).delete();
        return this;
    }
    @SuppressWarnings("all")
    public FileUtils cd(String name) {
        File[] files = this.listFiles();
        for (int i = 0;i < Objects.requireNonNull(files).length;i++) {
            if (files[i].getName().equals(name)) {
                return new FileUtils(this.getAbsolutePath() + File.separator + name);
            }
        }
        System.out.println("none");
        return this;
    }
}
