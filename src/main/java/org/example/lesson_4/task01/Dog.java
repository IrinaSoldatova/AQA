package org.example.lesson_4.task01;
public class Dog extends Animal {
    private static int totalDogs = 0;
    private static final int MAX_RUN_DISTANCE = 500;
    private static final int MAX_SWIM_DISTANCE = 10;

    public Dog(String name) {
        super(name);
        totalDogs++;
    }

    @Override
    public void run(int distance) {
        if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(getName() + " пробежал " + distance + " м.");
        } else {
            System.out.println(getName() + " не может пробежать больше " + MAX_RUN_DISTANCE + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        if (distance <= MAX_SWIM_DISTANCE) {
            System.out.println(getName() + " проплыл " + distance + " м.");
        } else {
            System.out.println(getName() + " не может проплыть больше " + MAX_SWIM_DISTANCE + " м.");
        }
    }

    public static int getTotalDogs() {
        return totalDogs;
    }
}