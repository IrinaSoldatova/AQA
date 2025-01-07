package org.example.lesson_4.task01;

public class Main {
    public static void main(String[] args) {
        Dog dog1 = new Dog("Данте");
        Dog dog2 = new Dog("Шарик");
        Cat cat1 = new Cat("Матроскин");
        Cat cat2 = new Cat("Леопольд");

        System.out.println("Всего животных: " + Animal.getTotalAnimals());
        System.out.println("Из них котов: " + Cat.getTotalCats());
        System.out.println("Из них собак: " + Dog.getTotalDogs());

        dog1.run(150);
        dog1.swim(5);

        cat1.run(50);
        cat1.swim(10);

        cat2.run(220);
        dog2.run(510);
        dog2.swim(15);

        Bowl bowl = new Bowl(15);

        Cat[] cats = {cat1, cat2};
        feedCats(cats,bowl);
        checkCatSatiation(cats);
    }

    public static void feedCats(Cat[] cats, Bowl bowl) {
        for (Cat cat : cats) {
            cat.eat(bowl, 10);
        }
    }

    public static void checkCatSatiation(Cat[] cats) {
        for (Cat cat : cats) {
            if (cat.isFull()) {
                System.out.println(cat.getName() + " сытый.");
            } else {
                System.out.println(cat.getName() + " голодный.");
            }
        }
    }
}