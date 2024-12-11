package org.example.lesson_3;

public class Main {
    public static void main(String[] args) {
        Employee[] employees = new Employee[5];
        employees[0] = new Employee("Ivanov Ivan Ivanovich", "Engineer","ivan.ivanov@email.com", "+79031234567",
                50000, 28);
        employees[1] = new Employee("Petrov Petr Petrovich", "Manager", "petr.petrov@email.com", "+79519876543",
                65000, 35);
        employees[2] = new Employee("Sidorov Sidor Sidorovich", "Developer","sidor.sidorov@email.com", "+79104567890",
                70000, 42);
        employees[3] = new Employee("Smirnov Sergey Sergeyevich", "Designer","sergey.smirnov@email.com", "+79273210987",
                75000, 30);
        employees[4] = new Employee("Kuznetsov Alexey Alexeyevich", "Analyst","alexey.kuznetsov@email.com", "+79631592748",
                80000, 26);

        Employee.printEmployees(employees);


        Park park = new Park("Остров мечты");
        park.addAttraction("Колесо обозрения", "10:00 - 22:00", 750);
        park.addAttraction("Американские горки", "10:00 - 20:00", 1000);
        park.addAttraction("Автодром", "10:00 - 20:00", 500);
        System.out.println("Атракционы в парке " + park + ": ");
        park.printInfoAttractions();

    }
}
