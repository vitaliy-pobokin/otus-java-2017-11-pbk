package org.examples.pbk.otus.l101homework;

import org.examples.pbk.otus.l101homework.dao.*;
import org.examples.pbk.otus.l101homework.dataset.AddressDataSet;
import org.examples.pbk.otus.l101homework.dataset.PhoneDataSet;
import org.examples.pbk.otus.l101homework.dataset.PhoneDataSetHibernate;
import org.examples.pbk.otus.l101homework.dataset.UsersDataSet;
import org.examples.pbk.otus.l101homework.dbservice.UsersDataSetHibernateDbService;

import java.util.Arrays;

public class HibernateDemo {
    public static void main(String[] args) {
        UsersDataSet user = new UsersDataSet(-1, "John Smith", 30);
        AddressDataSet address = new AddressDataSet();
        address.setCountry("USA");
        address.setPostalCode("10021");
        address.setCity("New York");
        address.setStreet("21 2nd Street");
        PhoneDataSetHibernate phone1 = new PhoneDataSetHibernate();
        phone1.setPhoneNumber("+1(234)567-89-00");
        phone1.setNumberType("mobile");
        phone1.setUser(user);
        PhoneDataSetHibernate phone2 = new PhoneDataSetHibernate();
        phone2.setPhoneNumber("+1(234)567-89-11");
        phone2.setNumberType("home");
        phone2.setUser(user);
        user.setAddress(address);
        user.setPhones(Arrays.asList(phone1, phone2));

        UsersDataSetHibernateDbService dbService = new UsersDataSetHibernateDbService(new UsersDataSetHibernateDao());
        dbService.create(user);
        System.out.println(dbService.read(user.getId()));

        PersistenceManager.getInstance().close();
    }
}
