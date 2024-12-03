package org.example.lesson_3;

public class Park {
    private String parkName;

    public Park(String parkName) {
        this.parkName = parkName;
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
     public void printInfoAttractions() {
        Attraction ferrisWheel = new Attraction("Колесо обозрения", "10:00 - 22:00", 750);
        Attraction rollerCoaster  = new Attraction("Американские горки", "10:00 - 20:00", 1000);
        Attraction bumperCars = new Attraction("Автодром", "10:00 - 20:00", 500);

         System.out.println(ferrisWheel);
         System.out.println(rollerCoaster);
         System.out.println(bumperCars);
     }



    @Override
    public String toString() {
        return '"' + parkName + '"';
    }

}
