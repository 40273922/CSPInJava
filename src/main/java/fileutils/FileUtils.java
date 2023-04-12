package fileutils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

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
    public FileUtils dir(File file) {
        String list = Arrays.toString(file.list());
        String newlist = list.replace("[", file.getAbsolutePath() + "\n- ")
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
