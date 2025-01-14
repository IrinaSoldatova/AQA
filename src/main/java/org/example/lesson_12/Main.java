package org.example.lesson_12;

public class Main {
    public static void main(String[] args) {
        String[][] vilidArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String[][] invalidSizeArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11"},
                {"13", "14", "15", "16"}
        };

        String[][] invalidElementArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "A", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int result = sumElementsArray(vilidArray);
            System.out.println("Сумма элементов массива: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }

        try {
            int result = sumElementsArray(invalidSizeArray);
            System.out.println("Сумма элементов массива: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }

        try {
            int result = sumElementsArray(invalidElementArray);
            System.out.println("Сумма элементов массива: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int sumElementsArray(String[][] array2D) throws MyArraySizeException, MyArrayDataException {
        if (array2D.length != 4) {
            throw new MyArraySizeException("Массив должен быть 4х4");
        }

        int sum = 0;
        for (int i = 0; i < array2D.length; i++) {
            if (array2D[i].length != 4) {
                throw new MyArraySizeException("Массив должен быть 4х4");
            }
            for (int j = 0; j < array2D[i].length; j++) {
                try {
                    sum += Integer.parseInt(array2D[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Неверные данные в ячейке [" + i + "][" + j + "]: " + array2D[i][j]);
                }
            }
        }
        return sum;
    }
}