package org.example.lesson_2;

public class Main {
    public static void main(String[] args) {
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
        System.out.println(checkSumInRange(4, 17));
        printPositiveNumberOrNegative(0);
        System.out.println(checkIsNegative(0));
        printString("Hello", 4);
        System.out.println(checkIsLeapYear(2000));
        replaceNumbersArray();
        fillArray();
        multiplyNumbersArray();
        fillDiagonals2DArray();
        int[] array = fillArrayValue(10, 5);
        printArray(array);


    }

    /**
     * Метод, который выводит три слова в столбец
     */
    public static void printThreeWords() {
        String word1 = "Orange";
        String word2 = "Banana";
        String word3 = "Apple";
        System.out.printf("%s\n%s\n%s\n", word1, word2, word3);
    }

    /**
     * Метод, который выводит полижительная сумма или отрицательная
     */
    public static void checkSumSign() {
        int a = 5;
        int b = -25;
        int sum = a + b;
        if (sum >= 0) {
            System.out.println("Сумма положительная");
        } else System.out.println("Сумма отрицательная");
    }

    /**
     * Метод, который выводит цвет в зависимости от значения переменной value
     */
    public static void printColor() {
        int value = 101;
        if (value <= 0) {
            System.out.println("Красный");
        } else if (value <= 100) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    /**
     * Метод, который выводит результат сравнения двух чисел a и b
     */
    public static void compareNumbers() {
        int a = 135;
        int b = 135;
        if (a >= b) {
            System.out.println("a >= b");
        } else System.out.println("a < b");
    }

    /**
     * Метод, который принимает на вход числа a и b, проверяет находится ли сумма чисел указанном диапазоне
     * и возвращает значение true или false
     */
    public static boolean checkSumInRange(int a, int b) {
        int sum = a + b;
        return sum >= 10 && sum <= 20;
    }

    /**
     * Метод, который принимает на вход число и выводит сообщение положительное оно или отрицательное
     */
    public static void printPositiveNumberOrNegative(int number) {
        if (number >= 0) {
            System.out.println("Число положительное");
        } else {
            System.out.println("Число отрицательное");
        }
    }

    /**
     * Метод, который принимает на вход число и возвращает значение true, если оно отрицательное
     * и значение false, если оно положительное
     */
    public static boolean checkIsNegative(int number) {
        return number < 0;
    }

    /**
     * Метод, который принмает на вход переменные строку и число, и выводит эту строку равное количеству числа
     */
    public static void printString(String str, int number) {
        for (int i = 0; i < number; i++) {
            System.out.println(str);
        }
    }

    /**
     * Метод, который принимает на вход год и проверяет является ли он високосным
     */
    public static boolean checkIsLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Метод, который заменяет значения в массиве 0 на 1 и 1 на 0
     */
    public static void replaceNumbersArray() {
        int[] array = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                System.out.print(array[i] + 1 + " ");
            } else System.out.print(array[i] - 1 + " ");
        }
        System.out.println();
    }

    /**
     * Метод, который создает массив длинной 100 и заполняет его значениями от 1 до 100
     */
    public static void fillArray() {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * Метод, который умножает числа в масиве меньшие 6, на 2
     */
    public static void multiplyNumbersArray() {
        int[] array = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 6) {
                array[i] = array[i] * 2;
                System.out.print(array[i] + " ");
            } else {
                System.out.print(array[i] + " ");
            }
        }
        System.out.println();
    }

    /**
     * Метод, который создает двумерный массив и заполняет по диагонали единицами
     */
    public static void fillDiagonals2DArray() {
        int[][] array2D = new int[5][5];
        for (int i = 0; i < array2D.length; i++) {
            System.out.println();
            for (int j = 0; j < array2D.length; j++) {
                if (i == j || j == array2D.length - 1 - i) {
                    array2D[i][j] = 1;
                    System.out.print(array2D[i][j] + " ");
                } else {
                    System.out.print(array2D[i][j] + " ");
                }
            }
        }
        System.out.println();
    }

    /**
     * Метод, который принимает на вход длину массива и значение, которым заполняет массив
     */
    public static int[] fillArrayValue(int len, int initialValue) {
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = initialValue;
        }
        return array;
    }

    /**
     * Метод, который выводит массив в консоли
     */
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
