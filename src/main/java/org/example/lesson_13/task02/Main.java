package org.example.lesson_13.task02;

public class Main {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        phoneBook.add("Иванов", "+79991234567");
        phoneBook.add("Иванов", "+79997654321");
        phoneBook.add("Петров", "+79992345678");
        phoneBook.add("Сидоров", "+79998765432");
        phoneBook.add("Петров", "+79993456789");

        phoneBook.printPhones("Иванов");
        phoneBook.printPhones("Петров");
        phoneBook.printPhones("Сидоров");
    }
}
