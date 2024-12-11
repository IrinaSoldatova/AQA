package org.example.lesson_3;

import java.util.ArrayList;
import java.util.List;

public class Park {
    private String parkName;
    private List<Attraction> attractions;

    public Park(String parkName) {
        this.parkName = parkName;
        this.attractions = new ArrayList<>();
    }

    class Attraction {
        String attractionName;
        String workingTime;
        double cost;

        public Attraction(String attractionName, String workingTime, double cost) {
            this.attractionName = attractionName;
            this.workingTime = workingTime;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return attractionName + "\n" +
                    "Часы работы: " + workingTime + "\n" +
                    "Стоимость: " + cost + "\n";
        }
    }

    public void addAttraction(String attractionName, String workingTime, double cost) {
        attractions.add(new Attraction(attractionName, workingTime, cost));
    }
     public void printInfoAttractions() {
         for (Attraction attraction : attractions) {
             System.out.println(attraction);
         }
     }

    @Override
    public String toString() {
        return '"' + parkName + '"';
    }
}
