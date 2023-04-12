package test;

import fileutils.FileUtils;
import java.io.IOException;
/**
 * @author Zhai Jinpei
 */
public class FileUTest{
    public static void main(String[] args) throws IOException{
        FileUtils file = new FileUtils("D:\\").mk("Myfile").mk("myDir").cf("m.txt");
        file.copyTo(file.cdU().getFile("t.png"),file.cdU().cdU().cdU().mk("testJava").cf("d2.png"));
    }
}
