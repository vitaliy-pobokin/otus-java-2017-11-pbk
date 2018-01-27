package org.examples.pbk.otus.l81homework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Address address = new Address();
        address.streetAddress = "21 2nd Street";
        address.city = "New York";
        address.state = "NY";
        address.postalCode = "10021";
        PhoneNumber home = new PhoneNumber();
        home.type = "home";
        home.number = "212 555-1234";
        PhoneNumber fax = new PhoneNumber();
        fax.type = "fax";
        fax.number = "646 555-4567";
        Employee employee1 = new Employee();
        employee1.firstName = "John";
        employee1.lastName = "Smith";
        employee1.age = 30;
        employee1.address = address;
        employee1.phoneNumbers = new PhoneNumber[2];
        employee1.phoneNumbers[0] = home;
        employee1.phoneNumbers[1] = fax;
        Employee employee2 = new Employee();
        employee2.firstName = "Sarah";
        employee2.lastName = "Smith";
        employee2.age = 25;
        employee2.address = address;
        employee2.phoneNumbers = null;

        List<Employee> employees = Arrays.asList(employee1, employee2);
        Gson gson = new GsonBuilder().serializeNulls().create();
        System.out.println(new JsonFramework().toJson(employees));
        System.out.println(gson.toJson(employees));
    }
}

class Employee {
    String firstName;
    String lastName;
    int age;
    Address address;
    PhoneNumber[] phoneNumbers;
}

class Address {
    String streetAddress;
    String city;
    String state;
    String postalCode;
}

class PhoneNumber {
    String type;
    String number;
}
