package cspimpl;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Zhai Jinpei
 */
public class CSP_Appliaction{
    static BiConsumer<Integer,Integer> s = (n,m)->f(n,m,System.out::print);

    public static void f(int n,int m,Consumer<Integer> c){
        if(n == 0) c.accept(1);
        else f(n - 1,m,integer->{
            if((n - m) <= 0) c.accept(integer * n / n);
            else c.accept(integer * n / (n - m));
        });
    }

    static void p(int n){
        for(int i = 0;i < n;++i){
            for(int j = 0;j < (n - i);++j){
                System.out.print(" ");
            }
            for(int j = 0;j <= i;++j){
                System.out.print(" ");
                s.accept(i,j);
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        p(9);
    }
}
