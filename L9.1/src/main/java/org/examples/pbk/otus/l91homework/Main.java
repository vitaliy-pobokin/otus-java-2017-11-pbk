package org.examples.pbk.otus.l91homework;

import org.examples.pbk.otus.l91homework.dbservice.DBService;
import org.examples.pbk.otus.l91homework.dbservice.DBServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            UsersDataSet user = new UsersDataSet(1, "John Smith", 30);
            AddressDataSet address = new AddressDataSet();
            address.setId(1);
            address.setCountry("USA");
            address.setPostalCode("10021");
            address.setCity("New York");
            address.setStreetAddress("21 2nd Street");

            DBService dbService = new DBServiceImpl();
            dbService.createTable(UsersDataSet.class);
            dbService.createTable(AddressDataSet.class);
            dbService.save(user);
            dbService.save(address);
            System.out.println(dbService.load(1, UsersDataSet.class));
            System.out.println(dbService.load(1, AddressDataSet.class));
            dbService.deleteTable(UsersDataSet.class);
            dbService.deleteTable(AddressDataSet.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
