package test;

import fileutils.FileUtils;
import java.io.IOException;
/**
 * @author Zhai Jinpei
 */
public class FileUTest{
    public static void main(String[] args) throws IOException{
        FileUtils file = new FileUtils("D:\\").mk("Myfile").mk("myDir").
                getFile("m.txt").replCont("我的名字是{name},是{Type}").logs("替换之前").
                readToConsole();
        file.replCont("我的名字是欧欧，是一只狗").logs("替换之后").
                readToConsole().
                readTo(file.cdU().cf("logger.txt").getAbsolutePath());
        file.cdU().getFile("logger.txt").
                logs("------------------logger.txt-------------------").
                readToConsole();
    }
}
