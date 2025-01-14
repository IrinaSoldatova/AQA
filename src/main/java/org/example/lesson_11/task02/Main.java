package org.example.lesson_11.task02;

public class Main {
    public static void main(String[] args) {
        Circle circle = new Circle(5, "Синий", "Красный");
        Rectangle rectangle = new Rectangle(4, 6, "Желтый", "Зеленый");
        Triangle triangle = new Triangle(3, 4, 5, "Фиолетовый", "Черный");

        circle.printInfo();
        rectangle.printInfo();
        triangle.printInfo();
    }
}
