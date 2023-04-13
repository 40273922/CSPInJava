package test;

import fileutils.FileUtils;
import java.io.IOException;
/**
 * @author Zhai Jinpei
 */
public class FileUTest{
    public static void main(String[] args) throws IOException{
        System.out.println("替换之前");
        FileUtils file = new FileUtils("D:\\").mk("Myfile").mk("myDir").cf("m.txt").readToConsole();
        System.out.println("替换之后");
        file.replCont("我的名字是欧欧，是一只狗").readToConsole();
    }
}
