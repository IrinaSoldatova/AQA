package org.example.lesson_13.task01;

import java.util.*;

public class UniqueWordsList {
    public static void main(String[] args) {
        String[] wordsArray = {
                "ручка", "карандаш", "пенал", "учебник", "рюкзак",
                "ручка", "ластик", "пенал", "фломастер", "ручка",
                "тетрадь", "дневник", "пенал", "карандаш", "ручка",
                "линейка", "тетрадь", "рюкзак", "дневник", "учебник"
        };
        uniqueWordsCount(wordsArray);
    }

    public static void uniqueWordsCount(String[] wordsArray) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String word : wordsArray) {
            if (wordCountMap.containsKey(word)) {
                wordCountMap.put(word, wordCountMap.get(word) + 1);
            } else {
                wordCountMap.put(word, 1);
            }
        }

        System.out.println("Уникальные слова и их количество:");
        for (String word : wordCountMap.keySet()) {
            System.out.println(word + " - " + wordCountMap.get(word) + " раз(а)");
        }
    }
}
