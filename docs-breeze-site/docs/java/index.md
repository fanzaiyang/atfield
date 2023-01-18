---
title: Java9～19新特性
order: 1
---

## 说明

该模块是为了方便开发者了解各个版本新特性。仅针对较为突出的便于理解的特性进行说明。

> **SpringBoot3.x默认JDK版本是17。**

Java版本时间

![Java版本时间](https://breeze.fanzy.cn/java_timeline.png)

## Java9 新特性

### 接口私有方法

在jdk9中新增了接口私有方法，即我们可以在接口中声明private修饰的方法了，这样的话，接口越来越像抽象类。

```java
public interface MyInterface {
    //定义私有方法
    private void m1() {
        System.out.println("123");
    }
    //default中调用
    default void m2() {
        m1();
    }
}
```

### 改进try with resource

在Java7中新增了try with resource语法用来自动关闭资源文件，在IO流和jdbc部分使用的比较多。使用方式是将需要自动关闭的资源对象的创建放到try后面的小括号中，在jdk9中我们可以将这些资源对象的创建代码放到小括号外面，然后将需要关闭的对象名放到try后面的小括号中即可。

```java
public class TryWithResource {
    public static void main(String[] args) throws FileNotFoundException {
        //jdk8以前
        try (FileInputStream fileInputStream = new FileInputStream("");
             FileOutputStream fileOutputStream = new FileOutputStream("")) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        //jdk9
        FileInputStream fis = new FileInputStream("");
        FileOutputStream fos = new FileOutputStream("");
        //多资源用分号隔开
        try (fis; fos) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### 不能使用下划线命名变量

下面语句在jdk9之前可以正常编译通过,但是在jdk9（含）之后编译报错，在后面的版本中会将下划线作为关键字来使用

```java
String _ = "test";
```

### String字符串的变化

**底层由char数组改成了byte数据，当字符串为纯英文是占用空间减少一半。**

写程序的时候会经常用到String字符串，在以前的版本中String内部使用了`char`数组存储，对于使用英语的人来说，一个字符用一个字节就能存储，使用char存储字符会浪费一半的内存空间，因此在jdk9中将String内部的`char数组`改成了`byte数组`，这样就节省了一半的内存占用。

```java
char c = 'a';//2个字节
byte b = 97;//1个字节
```

String中增加了下面2个成员变量

- `COMPACT_STRINGS`：判断是否压缩，默认是true，若为false，则不压缩，使用UTF16编码。
- `coder`用来区分使用的字符编码，分别为LATIN1（值为0）和UTF16（值为1）。

> byte数组如何存储中文呢？通过源码（StringUTF16类中的toBytes方法）得知，在使用中文字符串时，1个中文会被存储到byte数组中的两个元素上，即存储1个中文，byte数组长度为2，存储2个中文，byte数组长度为4。

### @Deprecated注解的变化

该注解用于标识废弃的内容，在jdk9中新增了2个内容：

- String since() default “”：标识是从哪个版本开始废弃
- boolean forRemoval() default false：标识该废弃的内容会在未来的某个版本中移除

### 模块化

**模块化带来的直观感受是jvm内存减少，如在开发web项目中，不需要awt包，现在可以不加在。**

java8中有个非常重要的包rt.jar，里面涵盖了java提供的类文件，在程序员运行java程序时jvm会加载rt.jar。这里有个问题是rt.jar中的某些文件我们是不会使用的，比如使用java开发服务器端程序的时候通常用不到图形化界面的库java.awt），这就造成了内存的浪费。

java9中将rt.jar分成了不同的模块，一个模块下可以包含多个包，模块之间存在着依赖关系，其中java.base模块是基础模块，不依赖其他模块。上面提到的java.awt被放到了其他模块下，这样在不使用这个模块的时候就无需让jvm加载，减少内存浪费。让jvm加载程序的必要模块，并非全部模块，达到了瘦身效果。

模块化的优点：

- 精简jvm加载的class类，提升加载速度
- 对包更精细的控制，提高安全

## Java10

### 局部变量类型推断

> **当然这个var的使用是有限制的，仅适用于局部变量，增强for循环的索引，以及普通for循环的本地变量；**
> 
> **它不能使用于方法形参，构造方法形参，方法返回类型等。**

```java
// jdk10之前
String a = "jack";
int b = 10;
long c = 88888888L;
Object d = new Object();
// jdk10之后
var a = "jack";
var b = 10;
var c = 88888888L;
var d = new Object();
```

## Java11

### 直接运行

在以前的版本中，我们在命令提示下，需要先编译，生成class文件之后再运行，例如:

```shell
javac HelloWorld.java
java HelloWorld
```

在java 11中，我们可以这样直接运行

```shell
java HelloWorld.java
```

### String新增方法

#### strip方法

strip方法，可以去除首尾空格，与之前的trim的区别是还可以去除unicode编码的空白字符，例如：

```java
char c = '\u2000';//Unicdoe空白字符
String str = c + "abc" + c;
System.out.println(str.strip());
System.out.println(str.trim());

System.out.println(str.stripLeading());//去除前面的空格
System.out.println(str.stripTrailing());//去除后面的空格
```

#### isBlank方法

sBlank方法，判断字符串长度是否为0，或者是否是空格，制表符等其他空白字符。

```java
String str = " ";
System.out.println(str.isBlank());
```

#### repeat方法

repeat方法，字符串重复的次数。

```java
String str = "abc";
System.out.println(str.repeat(4));
```

### lambda表达式中的变量类型推断

jdk11中允许在lambda表达式的参数中使用var修饰。

```java
@FunctionalInterface
public interface MyInterface {
   void m1(String a, int b);
}
```

测试类：

```java
 MyInterface mi = (var a,var b)->{
       System.out.println(a);
       System.out.println(b);
   };
   mi.m1("abc",123);
```

## Java12

### 升级的switch语句「预览版」

在jdk12之前的switch语句中，如果没有写break，则会出现case穿透现象，下面是对case穿透的一个应用，根据输入的月份打印相应的季节。

```java
int month = 3;
switch (month) {
    case 3:
    case 4:
    case 5:
        System.out.println("spring");
    break;
    case 6:
    case 7:
    case 8:
        System.out.println("summer");
    break;
    case 9:
    case 10:
    case 11:
        System.out.println("autumn");
    break;
    case 12:
    case 1:
    case 2:
        System.out.println("winter");
    break;
    default:
        System.out.println("wrong");
    break;
}
```

在jdk12之后我们<u>可以省略全部的break和部分case</u>，这样使用

```java
int month = 3;
switch (month) {
    case 3,4,5 -> System.out.println("spring");
    case 6,7,8 -> System.out.println("summer");
    case 9,10,11 -> System.out.println("autumn");
    case 12, 1,2 -> System.out.println("winter");
    default -> System.out.println("wrong");
}
```

## Java13

### 升级的switch语句「预览版」

jdk13中对switch语句又进行了升级，<u>可以switch的获取返回值</u>。

示例：

```java
int month = 3;
String result = switch (month) {
    case 3,4,5 -> "spring";
    case 6,7,8 -> "summer";
    case 9,10,11 -> "autumn";
    case 12, 1,2 -> "winter";
    default -> "wrong";
};

System.out.println(result);
```

### 文本块的变化

在jdk13之前的版本中如果输入的字符串中有换行的话，需要添加换行符

```java
String s = "Hello\nWorld\nLearn\nJava";
```

jdk13之后可以直接这样写，这样的字符串更加一目了然。

```java
String s = """
            Hello
            World
            Learn
            Java
           """;
```

## Java14

> jdk12和jdk13中预览版的switch特性，在jdk14中已经是正式的语法了。

### instanceof模式匹配「预览版」

该特性可以减少强制类型转换的操作，简化了代码，代码示例：

```java
public class TestInstanceof{
    public static void main(String[] args){

        //jdk14之前的写法
        Object obj = new Integer(1);
        if(obj instanceof Integer){
            Integer i = (Integer)obj;
            int result = i + 10;
            System.out.println(i);
        }

        //jdk14新特性  不用再强制转换了
        //这里相当于是将obj强制为Integer之后赋值给i了
        if(obj instanceof Integer i){
            int result = i + 10;
            System.out.println(i);
        }else{
            //作用域问题，这里是无法访问i的
        }
    }
}
```

### 友好的空指针（NullPointerException）提示

jdk14中添加了对于空指针异常友好的提示，便于开发者快速定位空指针的对象。示例代码：

```java
class Machine{
    public void start(){
        System.out.println("启动");
    }
}

class Engine{
    public Machine machine;
}

class Car{
    public Engine engine;

}

public class TestNull{
    public static void main(String[] args){
        //这里会报出空指针，但是哪个对象是null呢？
        new Car().engine.machine.start();
    }
}
```

我们在运行上面代码的时候，错误信息就可以明确的指出那个对象为null了。此外，还可以使用下面参数来查看:

```shell
java -XX:+ShowCodeDetailsInExceptionMessages TestNull
```

### record类型「预览版」

之前在编写javabean类的时候，需要编写成员变量，get方法，构造方法，toString方法，hashcode方法，equals方法。这些方法通常会通过开发工具来生成，在jdk14中新增了record类型，通过该类型可以省去这些代码的编写。

jdk14编写User

```java
public record User(String name,Integer age){}
```

编写测试类：

```java
public class TestUser{
    public static void main(String[] args){
        User u = new User("jack",15);
        System.out.println(u);
        System.out.println(u.name());
    }
}
```

记录类型有自动生成的成员，包括：

- 状态描述中的每个组件都有对应的private final字段。
- 状态描述中的每个组件都有对应的public访问方法。方法的名称与组件名称相同。
- 一个包含全部组件的公开构造器，用来初始化对应组件。
- 实现了equals()和hashCode()方法。equals()要求全部组件都必须相等。
- 实现了toString()，输出全部组件的信息。

## Java15

### Sealed Classes

密封类和接口，作用是限制一个类可以由哪些子类继承或者实现。

1. 如果指定模块的话，sealed class和其子类必须在同一个模块下。如果没有指定模块，则需要在同一个包下。
2. sealed class指定的子类必须直接继承该sealed class。
3. sealed class的子类要用`final`修饰。
4. sealed class的子类如果不想用`final`修饰的话，可以将子类声明为sealed class。

Animal类，在指定允许继承的子类时可以使用全限定名

```java
//多个子类之间用,隔开
public sealed class Animal permits Cat, Dog{
      public void eat(){}
}
```

Cat类

```java
public final class Cat extends Animal{
    public void eat(){
        System.out.println("Cat123");
    }
}
```

Dog类

```java
public final class Dog extends Animal permits Husky {}
```

Husky类

```java
public final class Husky extends Dog{
}
```

### CharSequence新增的方法

该接口中新增了default方法isEmpty()，作用是判断CharSequence是否为空。

### TreeMap新增方法

- putIfAbsent
- computeIfAbsent
- computeIfPresent
- compute
- merge

### 文本块由预览版变为正式版

### 无需配置环境变量

win系统中安装完成之后会自动将java.exe, javaw.exe, javac.exe, jshell.exe这几个命令添加到环境变量中。这部分可以打开环境变量看下。

## Java16

### 包装类构造方法的警告

使用包装类的构造方法在编译的时候会出现警告，不建议再使用包装类的构造方法。下面代码在javac编译之后会出现警告。

```java
Integer i = new Integer(8);
```

不建议使用包装类作为锁对象，倘若使用包装类作为锁对象，在编译时会出现警告。

```java
Integer i = 8;
synchronized(i){

}
```

### 新增日时段

在DateTimeFormatter.ofPattern传入B可以获取现在时间对应的日时段，上午，下午等

```java
System.out.println(DateTimeFormatter.ofPattern("B").format(LocalDateTime.now()));
```

### InvocationHandler新增方法

在该接口中添加了下面方法

```java
public static Object invokeDefault(Object proxy, Method method, Object... args)
```

该方法可以调用父接口中defalut方法，比如有下面接口

```java
interface Girl{
    default void eat(){
        System.out.println("cucumber");
    }

}
```

实现类

```java
public class Lucy implements Girl{
    public void eat(){
        System.out.println("banana");
    }
}
```

测试类

```java
public class Test{
    public static void main(String[] args) {
        Girl girl = new Lucy();


        //不使用invokeDefault会调用重写的eat方法
        Girl proxy1 = (Girl)Proxy.newProxyInstance(girl.getClass().getClassLoader(),girl.getClass().getInterfaces(),
            (obj,method,params)->{
            Object invoke = method.invoke(girl);
            return invoke;
        });
        proxy1.eat();

        //使用invokeDefault会调用父接口中的default方法
        Girl proxy2 = (Girl)Proxy.newProxyInstance(Girl.class.getClassLoader(),new Class<?>[]{Girl.class},
            (obj,method,params)->{
            if (method.isDefault()) {
                return InvocationHandler.invokeDefault(obj, method, params);
            }
            return null;
        });
        proxy2.eat();

    }

}
```

### 其他

在之前jdk版本中作为预览功能的Record类，模式匹配的instanceof，打包工具jpackage，已成为正式版。jdk16对GC，jvm运行时内存等内容有一些变化，例如：**ZGC并发栈处理**，**弹性meta space**。

## Java17 LTS

> SpringBoot 3.x之后默认JDK版本为17。

java17是一个LTS（long term support）长期支持的版本，根据计划来看java17会支持到2029年（java8会支持到2030年，OMG），同时Oracle提议下一个LTS版本是java21，在2023年9月发布，这样讲LST版本的发布周期由之前的3年变为了2年。这里只介绍一些跟开发关联度较大的特性，除此之外JDK17还更新了一些其他新特性，感兴趣的同学可以从这里查看：[Oracle Releases Java 17](https://www.oracle.com/news/announcement/oracle-releases-java-17-2021-09-14/)

### switch语法的变化(预览)

在之前版本中新增的instanceof模式匹配的特性在switch中也支持了，即我们可以在switch中减少强转的操作。比如下面的代码：

Rabbit和Bird均实现了Animal接口

```java
interface Animal{}

class Rabbit implements Animal{
    //特有的方法
    public void run(){
        System.out.println("run");
    }
}

class Bird implements Animal{
    //特有的方法
    public void fly(){
        System.out.println("fly");
    }
}
```

新特性可以减少Animal强转操作代码的编写：

```java
public class Switch01{
    public static void main(String[] args) {
        Animal a = new Rabbit();
        animalEat(a);
    }

    public static void animalEat(Animal a){
        switch(a){
            //如果a是Rabbit类型，则在强转之后赋值给r，然后再调用其特有的run方法
            case Rabbit r -> r.run();
            //如果a是Bird类型，则在强转之后赋值给b，然后调用其特有的fly方法
            case Bird b -> b.fly();
            //支持null的判断
            case null -> System.out.println("null");
            default -> System.out.println("no animal");
        }
    }

}
```

### Sealed Classes

在jdk15中已经添加了Sealed Classes，只不过当时是作为预览版，经历了2个版本之后，在jdk17中Sealed Classes已经成为正式版了。Sealed Classes的作用是可以限制一个类或者接口可以由哪些子类继承或者实现。

### 伪随机数的变化

增加了伪随机数相关的类和接口来让开发者使用stream流进行操作

- RandomGenerator
- RandomGeneratorFactory

之前的java.util.Random和java.util.concurrent.ThreadLocalRandom都是RandomGenerator接口的实现类。

### 去除了AOT和JIT

AOT（Ahead-of-Time）是java9中新增的功能，可以先将应用中中的字节码编译成机器码。

Graal编译器作为使用java开发的JIT（just-in-time ）即时编译器在java10中加入（注意这里的JIT不是之前java中的JIT，在JEP 317中有说明https://openjdk.java.net/jeps/317）。

## Java18

### 默认使用UTF-8字符编码

从jdk18开始，默认使用UTF-8字符编码。我们可以通过如下参数修改其他字符编码：

```shell
-Dfile.encoding=UTF-8 
```

### 简单的web服务器

可以通过jwebserver命令启动jdk18中提供的静态web服务器，可以利用该工具查看一些原型，做简单的测试。在命令提示符中输入jwebserver命令后会启动，然后在浏览器中输入:[http://127.0.0.1:8000/](http://127.0.0.1:8000/) 即可看到当前命令提示符路径下的文件了。

### 将被移除的方法

在jdk18中标记了Object中的finalize方法，Thread中的stop方法将在未来被移除。

### @snippet注解

以前在文档注释中编写代码时需要添加code标签，使用较为不便，通过@snippet注解可以更方便的将文档注释中的代码展示在api文档中。

## Java19

### Virtual Threads (Preview)（虚拟线程）

#### 简介

该特性在java19中是预览版，虚拟线程是一种用户态下的线程，类似go语言中的goroutines 和Erlang中的processes，虚拟线程并非比线程快，而是提高了应用的吞吐量，相比于传统的线程是由操作系统调度来看，虚拟线程是我们自己程序调度的线程。如果你对之前java提供的线程API比较熟悉了，那么在学习虚拟线程的时候会比较轻松，传统线程能运行的代码，虚拟线程也可以运行。虚拟线程的出现，并没有修改java原有的并发模型，也不会替代原有的线程。**虚拟线程主要作用是提升服务器端的吞吐量。**

#### 吞吐量的瓶颈

服务器应用程序的伸缩性受利特尔法则（Little’s Law）的制约，与下面3点有关

1. 延迟：请求处理的耗时
2. 并发量：同一时刻处理的请求数量
3. 吞吐量：单位时间内处理的数据数量

比如一个服务器应用程序的延迟是50ms，处理10个并发请求，则吞吐量是200请求/秒（10 / 0.05），如果吞吐量要达到2000请求/秒，则处理的并发请求数量是100。按照1个请求对应一个线程的比例来看，要想提高吞吐量，线程数量也要增加。

java中的线程是在操作系统线程（OS thread）进行了一层包装，而操作系统中线程是重量级资源，在硬件配置确定的前提下，我们就不能创建更多的线程了，此时线程数量就限制了系统性能，为了解决该问题，虚拟线程就出现了。

#### 虚拟线程的应用场景

在服务器端的应用程序中，可能会有大量的并发任务需要执行，而虚拟线程能够明显的提高应用的吞吐量。下面的场景能够显著的提高程序的吞吐量：

- 至少几千的并发任务量
- 任务为io密集型

下面代码中为每个任务创建一个线程，当任务量较多的时候，你的电脑可以感受到明显的卡顿（如果没有，可以增加任务数量试下）：

```java
//ExecutorService实现了AutoCloseable接口，可以自动关闭了
try (ExecutorService executor = Executors.newCachedThreadPool()) {
    //向executor中提交1000000个任务
    IntStream.range(0, 1000000).forEach(
        i -> {
            executor.submit(() -> {
                try {
                    //睡眠1秒，模拟耗时操作
                    Thread.sleep(Duration.ofSeconds(1));
                    System.out.println("执行任务:" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        });
} catch (Exception e) {
    e.printStackTrace();
}
```

将上面的代码改成虚拟线程之后，电脑不会感受到卡顿了：

```java
//newVirtualThreadPerTaskExecutor为每个任务创建一个虚拟线程
try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 1000_000).forEach(i -> {
        executor.submit(() -> {
            try {
                //睡眠1秒，模拟耗时操作
                Thread.sleep(Duration.ofSeconds(1));
                System.out.println("执行任务:" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    });
} 
```

#### 平台线程和虚拟线程

平台线程（platform thread）：指java中的线程，比如通过Executors.newFixedThreadPool()创建出来的线程，我们称之为平台线程。

虚拟线程并不会直接分配给cpu去执行，而是通过调度器分配给平台线程，平台线程再被调度器管理。java中虚拟线程的调度器采用了工作窃取的模式进行FIFO的操作，调度器的并行数默认是jvm获取的处理器数量（通过该方法获取的数量Runtime.getRuntime().availableProcessors()），调度器并非分时（time sharing）的。在使用虚拟线程编写程序时，不能控制虚拟线程何时分配给平台线程，也不能控制平台线程何时分配给cpu。

#### 携带器

调度器将虚拟线程挂载到平台线程之后，该平台线程叫做虚拟线程的携带器，调度器并不维护虚拟线程和携带器之间的关联关系，因此在一个虚拟线程的生命周期中可以被分配到不同的携带器，即虚拟线程运行了一小段代码后，可能会脱离携带器，此时其他的虚拟线程会被分配到这个携带器上。

携带器和虚拟线程是相互独立的，比如：

- 虚拟线程不能使用携带器的标识，Thread.current()方法获取的是虚拟线程本身。
- 两者有各自的栈空间。
- 两者不能访问对方的Thread Local变量。

在程序的执行过程中，虚拟线程遇到阻塞的操作时大部分情况下会被解除挂载，阻塞结束后，虚拟线程会被调度器重新挂载到携带器上，因此虚拟线程会频繁的挂载和解除挂载，这并不会导致操作系统线程的阻塞。下面的代码在执行两个get方法和send方法（会有io操作）时会使虚拟线程发生挂载和解除挂载：

```java
response.send(future1.get() + future2.get());
```

有些阻塞操作并不会导致虚拟线程解除挂载，这样会同时阻塞携带器和操作系统线程，例如：操作系统基本的文件操作，java中的Object.wait()方法。下面两种情况不会导致虚拟线程的解除挂载：

1. 执行synchronized同步代码（会导致携带器阻塞，所以建议使用ReentrantLock替换掉synchronized）
2. 执行本地方法或外部函数

#### 虚拟线程和平台线程api的区别

从内存空间上来说，虚拟线程的栈空间可以看作是一个大块的栈对象，它被存储在了java堆中，相比于单独存储对象，堆中存储虚拟线程的栈会造成一些空间的浪费，这点在后续的java版本中应该会得到改善，当然这样也是有一些好处的，就是可以重复利用这部分栈空间，不用多次申请开辟新的内存地址。虚拟线程的栈空间最大可以达到平台线程的栈空间容量。

**虚拟线程并不是GC root，其中的引用不会出现stop-world，当虚拟线程被阻塞之后比如BlockingQueue.take()，平台线程既不能获取到虚拟线程，也不能获取到queue队列，这样该平台线程可能会被回收掉，虚拟线程在运行或阻塞时不会被GC**

- 通过Thread构造方法创建的线程都是平台线程
- 虚拟线程是守护线程，不能通过setDaemon方法改成非守护线程
- 虚拟线程的优先级是默认的5，不能被修改，将来的版本可能允许修改
- 虚拟线程不支持stop()，suspend()，resume()方法

#### 创建虚拟线程的方式

java中创建的虚拟线程本质都是通过Thread.Builder.OfVirtual对象进行创建的，我们后面再来讨论这个对象，下面先看下创建虚拟线程的三种方式：

**1.通过Thread.startVirtualThread直接创建一个虚拟线程**

```java
//创建任务
Runnable task = () -> {
    System.out.println("执行任务");
};

//创建虚拟线程将任务task传入并启动
Thread.startVirtualThread(task);

//主线程睡眠，否则可能看不到控制台的打印
TimeUnit.SECONDS.sleep(1);
```

**2.使用Thread.ofVirtual()方法创建**

```java
//创建任务
Runnable task = () -> {
    System.out.println(Thread.currentThread().getName());
};

//创建虚拟线程命名为诺手，将任务task传入
Thread vt1 = Thread.ofVirtual().name("诺手").unstarted(task);
vt1.start();//启动虚拟线程

//主线程睡眠，否则可能看不到控制台的打印
TimeUnit.SECONDS.sleep(1);
```

也可以在创建虚拟线程的时候直接启动

```java
//创建任务
Runnable task = () -> {
    System.out.println(Thread.currentThread().getName());
};

//创建虚拟线程命名为诺手，将任务task传入并启动
Thread vt1 = Thread.ofVirtual().name("诺手").start(task);

//主线程睡眠，否则可能看不到控制台的打印
TimeUnit.SECONDS.sleep(1);
```

**3.通过ExecutorService创建**，为每个任务分配一个虚拟线程，下面代码中提交了100个任务，对应会有100个虚拟线程进行处理。

```java
/*
    通过ExecutorService创建虚拟线程
    ExecutorService实现了AutoCloseable接口，可以自动关闭了
*/
try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    //向executor中提交100个任务
    IntStream.range(0, 100).forEach(i -> {
        executor.submit(() -> {
            //睡眠1秒
            try {
                Thread.sleep(Duration.ofSeconds(1));
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }                    
        });
    });
}  
```

现在平台线程和虚拟线程都是Thread的对象，那该如何区分该对象是平台线程还是虚拟线程？可以利用Thread中的isVirtual()方法进行判断，返回true表示虚拟线程：

```java
//创建任务
Runnable task = () -> {
    System.out.println("执行任务");
};

//创建虚拟线程将任务task传入并启动
Thread vt = Thread.startVirtualThread(task);
System.out.println(vt.isVirtual());
```

#### Thread.builder接口

在jdk19中新增了一个密封（sealed）接口Builder，该接口只允许有两个子接口：

- OfPlatform：创建平台线程的时候使用，是一个密封接口，只允许ThreadBuilders.PlatformThreadBuilder实现。
- OfVirtual：创建虚拟线程的时候使用，是一个密封接口，只允许ThreadBuilders.VirtualThreadBuilder实现。

上面3种创建虚拟线程的方式本质都是通过OfVirtual来进行创建的，OfVirtual和OfPlatform接口中的api很多是相同的，OfPlatform中的方法更多，所以下面我们以OfPlatform为例演示他的使用方式。

通过OfPlatform中的factory()方法可以创建一个ThreadFactory线程工厂，学过线程池的同学对它应该并不陌生，它可以帮助我们创建出平台线程对象。

```java
ThreadFactory threadFactory = Thread.ofPlatform().factory();
```

除了上面的用法之外，还可以用它来创建平台线程对象

```java
//创建任务
Runnable task = () -> {
    System.out.println(Thread.currentThread().getName());
};

//将任务放到t线程中并运行
Thread t = Thread.ofPlatform().start(task);
```

上面创建平台线程的方式跟之前的new Thread是一样的，优点是我们可以用它来实现链式编程，比如要设置线程优先级，线程名字，守护线程：

```java
//创建任务
Runnable task = () -> {
    System.out.println(Thread.currentThread().getName());
};

//链式编程
Thread.ofPlatform().name("小").priority(Thread.MAX_PRIORITY).daemon(true).start(task);
```

#### 虚拟线程中的ThreadLocal

由于虚拟线程的数量会比较多，所以在使用ThreadLocal的时候一定要注意。线程池中的线程在执行多个任务的时候，不要使用ThreadLocal。在Thread.Builder中提供了不支持ThreadLocal的方法。

```java
Thread.ofVirtual().allowSetThreadLocals(false);
Thread.ofVirtual().inheritInheritableThreadLocals(false);
```

#### LockSupport对虚拟线程的支持

LockSupport是支持虚拟线程的，当调用park()方法时，虚拟线程会解除挂载，这样平台线程可以执行其他的操作，当调用unpark()方法时，虚拟线程会被调度器重新挂载到平台线程，再继续工作。

#### java.io包下类的变化

为了减少内存的使用，BufferedOutputStream，BufferedWriter，OutputStreamWriter中默认的初始数组大小由之前的8192变成了512。
