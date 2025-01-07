package org.example.lesson_4.task01;

public class Cat extends Animal {
    private static final int MAX_RUN_DISTANCE = 200;
    private static final int MAX_SWIM_DISTANCE = 0;
    private boolean isFull = false;
    private static int totalCats = 0;


    public Cat(String name) {
        super(name);
        totalCats++;
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
        System.out.println(getName() + " не умеет плавать.");
    }

    public boolean eat(Bowl bowl, int foodAmount) {
        if (bowl.getFoodAmount() >= foodAmount) {
            bowl.decreaseFood(foodAmount);
            isFull = true;
            System.out.println(getName() + " поел из миски.");
            return true;
        } else {
            System.out.println(getName() + " не хватает еды в миске.");
            return false;
        }
    }

    public static int getTotalCats() {
        return totalCats;
    }

    public boolean isFull() {
        return isFull;
    }
}