# CSPInJava[Java中CSP风格的简单递归函数详解.md](https://github.com/40273922/CSPInJSP/files/11180611/Java.CSP.md)
#[个人博客原文](https://blog.csdn.net/m0_73469755/article/details/130022550?spm=1001.2014.3001.5502)
# Java中CSP风格的简单递归函数详解

## 什么是CSP?

```
Continuation-passing style (CPS) originated as a style of programming in the 1970s, 
and it rose to prominence as an intermediate representation 
for compilers of advanced programming languages in the 1980s and 1990s.

It's now being rediscovered as a style of programming 
for non-blocking (usually distributed) systems.
```

[原文链接:by-example-continuation-passing-style](https://matt.might.net/articles/by-example-continuation-passing-style/)
CSP(continuation-passing-style)是一种编程风格：所有的控制块都通过 continuation 来显式传递。简单来说，在CPS风格中，函数不能有返回语句，它的调用者要想获得它的结果，需要显式传递一个回调函数来获取结果并继续执行。而为了保证整个程序执行下去，这个回调函数还会一直嵌套下去。这里的回调函数就是一个“continuation”。
[原文链接：Jenkins Pipeline 手记（1）—— 什么是CPS编程](https://blog.csdn.net/jaytalent/article/details/105425616)

## JavaScript中的CSP递归

```javascript
//简化版
function f(n,r) {
    if (n === 0)
        r(1) ;
    else
        f(n-1, function (t) {
            r(n * t) }) ;
}
f (5, function (n) {
    console.log(n) ;//输出120
})

```

## Java中的CSP递归函数实现

	由于Java不支持直接将匿名函数传参的写法且是一种强类型静态语言，所以我们主要思路就是借助函数式接口。

### Consumer接口

该接口只有一个需要我们实现的accept单参数方法，还有一个默认实现的addThen方法。
我们只需要实现accept方法即可，它的作用形式上相当于上面JS代码中的==r==这个匿名函数。

### 代码

```java
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
        if(n==0)
            consumer.accept(1);
        else f(n - 1,integer->consumer.accept(n*integer));//integer从何处传入？
    }

    public static void main(String[] args){
        f(5,System.out::println);//函数式方法调用
    }
}
```

### CSP代码分析

由于从Java8开始引入函数式编程中某些逻辑的支持，使得上面这段代码和JS很相似。
我们主要关心这个==内嵌回调函数 Consumer&lt;Integer&gt; consumer==的参数==integer==是怎么传入的，即什么时候传入的。
当从main函数开始调用f函数栈帧时，
![debuger界面](https://img-blog.csdnimg.cn/ad31cb4f90334e36a04632d69c91f3a2.png)
我们注意到下面的情况，注意多个consumer的地址之间的嵌套关系：
![debuger](https://img-blog.csdnimg.cn/9130d1a1b992446cb6ffc36eb62aecac.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/a2977c87e0dd44c0a9477a405cb032ed.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/5f00bce961dc4ab3a1b2bb2d7cb14d1c.png![在这里插入图片描述](https://img-blog.csdnimg.cn/d4d41f25edd04fbb9abd275c5bfc7411.png)
接下来你会发现
![在这里插入图片描述](https://img-blog.csdnimg.cn/3def092e14e447aab406028103137bae.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/c4fd69fd8f2b4d86957dbb4db3c1fa96.png)
每个consumer弹出栈时都会将自己的n交给integer且回调函数将自身接受的n-1交给r函数与integer相乘得到n*(n-1）
![在这里插入图片描述](https://img-blog.csdnimg.cn/719c3583891d4feabcac789a2a1d4e8c.png)
当栈帧清空时，在控制台会输出最终结果。

## 结语

实际上，integer只在n==0时被赋值为1，然后这个1就会随着每次n的传入而自乘n，即1*1*2*3*4*5。
以上就是csp递归在Java中的简单实现。主要是理解函数式接口和底层栈帧的知识。
## CSP以及函数式编程在Java中的应用（长期更新）
### [打印杨辉三角](https://github.com/40273922/CSPInJSP/blob/master/src/cspimpl/CSP_Appliaction.java)

