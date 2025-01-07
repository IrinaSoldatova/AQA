package org.example.lesson_4.task01;

public class Bowl {
    private int foodAmount;

    public Bowl(int initialFood) {
        if (initialFood < 0) {
            this.foodAmount = 0;
        } else {
            this.foodAmount = initialFood;
        }
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void decreaseFood(int amount) {
        if (foodAmount - amount >= 0) {
            foodAmount -= amount;
        }
    }

    public void addFood(int amount) {
        if (amount > 0) {
            foodAmount += amount;
        }
    }
}