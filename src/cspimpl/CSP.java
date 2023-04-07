package cspimpl;

import java.util.function.Consumer;

/**
 * @author Zhai Jinpei
 * csp风格的Java递归函数
 * <a href="https://matt.might.net/articles/by-example-continuation-passing-style/">csp在JS中的应用</a>
 * <a href="https://www.zhihu.com/search?q=%E7%8E%8B%E5%9E%A0&search_source=Entity&type=content">csp在Lisp编译器中</a>
 * <a href="https://blog.csdn.net/jaytalent/article/details/105425616#:~:text=%E4%BB%80%E4%B9%88%E6%98%AFCPS%20%E5%9C%A8%E5%87%BD%E6%95%B0%E5%BC%8F%E7%BC%96%E7%A8%8B%E4%B8%AD%EF%BC%8C%20CPS%20%28Continuation-Passing%20Style,%EF%BC%89%E6%98%AF%E4%B8%80%E7%A7%8D%E7%BC%96%E7%A8%8B%E9%A3%8E%E6%A0%BC%EF%BC%9A%20%E6%89%80%E6%9C%89%E7%9A%84%E6%8E%A7%E5%88%B6%E5%9D%97%E9%83%BD%E9%80%9A%E8%BF%87%20continuation%20%E6%9D%A5%E6%98%BE%E5%BC%8F%E4%BC%A0%E9%80%92%20%E3%80%82">csp的概念</a>
 * <a href="https://www.jianshu.com/p/0800f683cdfe">王垠</a>
 */
public class CSP{
    public static void f(int n,Consumer<Integer> consumer){//传入消费式函数
        if(n == 0)
            consumer.accept(1);
        else f(n - 1,integer->consumer.accept(n * integer));//integer从何处传入？
    }

    public static void main(String[] args){
        f(5,System.out::println);//函数式方法调用
    }
}
