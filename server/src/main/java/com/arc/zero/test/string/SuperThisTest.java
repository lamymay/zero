package com.arc.zero.test.string;

/**
 * @author yechao
 * @date 2020/10/13 8:38 下午
 */
public class SuperThisTest extends Parent {

    private String avatar;

    public SuperThisTest(String name   ) {
        this.name  = name ;
    }

    public SuperThisTest(String name, String avatar) {
        //super(name);
        this(name);
        this.name  = name ;
        this.avatar = avatar;
        super.name=name;
        //System.out.println(super.order);
    }

}

//https://www.cnblogs.com/newbie27/p/10437587.html

//this是自身的一个对象，代表对象本身，可以理解为：指向对象本身的一个指针。
//　　this的用法在java中大体可以分为3种：
//　　1、普通的直接引用 　this相当于是指向当前对象本身。
//　　2、形参与成员名字重名，用this来区分
//   3、引用本类的构造函数

//　super可以理解为是指向自己超（父）类对象的一个指针，而这个超类指的是离自己最近的一个父类。

//super和this的异同：
//super:　它引用当前对象的直接父类中的成员（用来访问直接父类中被隐藏的父类中成员数据或函数，基类与派生类中有相同成员定义时如：super.变量名    super.成员函数据名（实参）
//this：它代表当前对象名（在程序中易产生二义性之处，应使用this来指明当前对象；如果函数的形参与类中的成员数据同名，这时需用this来指明成员变量名）
//this()和super()不能同时出现在一个构造函数里面，因为this必然会调用其它的构造函数，其它的构造函数必然也会有super语句的存在，所以在同一个构造函数里面有相同的语句，就失去了语句的意义，编译器也不会通过。
//尽管可以用this()调用一个构造器，但却不能调用两个。
//super()和this()类似,区别是，super()在子类中调用父类的构造方法，this()在本类内调用本类的其它构造方法。
//super()和this()均需放在构造方法内第一行。
//this()和super()都指的是对象，所以，均不可以在static环境中使用。包括：static变量,static方法，static语句块。
//从本质上讲，this是一个指向本对象的指针, 然而super是一个Java关键字。

