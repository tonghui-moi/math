# 简介
该工具用于数学表达式计算，最适合用于含有未知数的重复性计算场合（我用于安卓几何画板中）

把UnitPro.java添加到你的java工程目录下即可使用

使用
```java
    UnitPro u = new UnitPro();
    u.parse("sin(10*x)+x");
    u.cal(10);//给x赋值为10并计算表达式的值
    u.cal(Math.PI);//和上面的类似
```

# Brief Introduction
This is a fast calculation tool for mathematical expression which performs best in repetitive calculations.

Add UnitPro.java into your java project to use the tool.

Usage
```java
    UnitPro u = new UnitPro();
    u.parse("sin(10*x)+x");
    u.cal(10);//assign 10 to X and do a calculation.
    u.cal(Math.PI);//similar to the last statement
```