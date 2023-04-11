package course;

public class MapT{
    static Course<String,String> course = new Course<>();

    public static void main(String[] args){
        course.$("Tom","CoreJava").
                $("John","Oracle").
                $("John","Oracle").
                $("Susan","Oracle").
                $("Jerry","JDBC").
                $("Jim","Unix").
                $("Kevin","JSP").
                $("Lucy","JSP");
        course.like(null);
        course.$("Allen","JDBC").replace("Lucy","CoreJava");
        System.out.println("==============修改结果=================");
        course.like(null);
        System.out.println("==============查询结果=================");
        course.like("JDBC");
    }
}