package org.example.lesson_4.task01;

public abstract class Animal {
    private String name;
    private static int totalAnimals = 0;

    public Animal(String name) {
        this.name = name;
        totalAnimals++;
    }

    public abstract void run(int distance);

    public abstract void swim(int distance);

    public String getName() {
        return name;
    }

    public static int getTotalAnimals() {
        return totalAnimals;
    }
}