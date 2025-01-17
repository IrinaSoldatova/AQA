package org.example.lesson_13.task02;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
    private Map<String, Set<String>> phoneBook;

    public PhoneBook() {
        phoneBook = new HashMap<>();
    }

    public void add(String lastName, String phoneNumber) {
        phoneBook.putIfAbsent(lastName, new HashSet<>());
        phoneBook.get(lastName).add(phoneNumber);
    }

    public Set<String> get(String lastName) {
        return phoneBook.getOrDefault(lastName, new HashSet<>());
    }

    public void printPhones(String lastName) {
        Set<String> phones = get(lastName);
        if (phones.isEmpty()) {
            System.out.println("Телефоны по фамилии " + lastName + " не найдены.");
        } else {
            System.out.println("Телефоны по фамилии " + lastName + ": " + phones);
        }
    }
}
