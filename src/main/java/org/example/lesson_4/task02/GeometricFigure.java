package org.example.lesson_4.task02;

public interface GeometricFigure {
    double perimeter();
    double area();
    String getFillColor();
    String getBorderColor();

    default void printInfo() {
        System.out.println(this.getClass().getSimpleName() + ":");
        System.out.println("Периметр: " + perimeter());
        System.out.println("Площадь: " + area());
        System.out.println("Цвет фона: " + getFillColor());
        System.out.println("Цвет границ: " + getBorderColor());
        System.out.println();
    }
}
