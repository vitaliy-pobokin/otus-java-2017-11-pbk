package org.examples.pbk.otus.l101homework;

import oracle.jdbc.driver.OracleDriver;
import org.examples.pbk.otus.l101homework.dao.AddressDataSetMyOrmDao;
import org.examples.pbk.otus.l101homework.dao.PhoneDataSetMyOrmDao;
import org.examples.pbk.otus.l101homework.dao.UsersDataSetMyOrmDao;
import org.examples.pbk.otus.l101homework.dataset.AddressDataSet;
import org.examples.pbk.otus.l101homework.dataset.PhoneDataSet;
import org.examples.pbk.otus.l101homework.dataset.UsersDataSet;
import org.examples.pbk.otus.l101homework.dbservice.AddressDataSetMyOrmDbService;
import org.examples.pbk.otus.l101homework.dbservice.PhoneDataSetMyOrmDbService;
import org.examples.pbk.otus.l101homework.dbservice.UsersDataSetMyOrmDbService;
import org.examples.pbk.otus.l101homework.myorm.MyOrm;
import org.examples.pbk.otus.l101homework.myorm.OrmConfiguration;
import org.examples.pbk.otus.l101homework.myorm.OrmEntityManager;

public class MyOrmDemo {
    public static void main(String[] args) {
        UsersDataSet user = new UsersDataSet(1, "John Smith", 30);
        AddressDataSet address = new AddressDataSet();
        address.setId(1);
        address.setCountry("USA");
        address.setPostalCode("10021");
        address.setCity("New York");
        address.setStreet("21 2nd Street");
        PhoneDataSet phone = new PhoneDataSet();
        phone.setId(1);
        phone.setPhoneNumber("+1(234)567-89-00");
        phone.setNumberType("mobile");
        user.setAddress(address);
        user.setPhone(phone);

        OrmConfiguration configuration = new OrmConfiguration();
        configuration.addPersistenceClass(UsersDataSet.class);
        configuration.addPersistenceClass(AddressDataSet.class);
        configuration.addPersistenceClass(PhoneDataSet.class);
        configuration.setDriver(new OracleDriver());
        configuration.setConnectionUrl("jdbc:oracle:thin:@localhost:1521:XE");
        configuration.setConnectionUsername("SYS AS SYSDBA");
        configuration.setConnectionPassword("root");
        MyOrm orm = new MyOrm(configuration);

        OrmEntityManager entityManager = orm.getOrmEntityManager();
        UsersDataSetMyOrmDbService usersDbService = new UsersDataSetMyOrmDbService(new UsersDataSetMyOrmDao(entityManager));
        usersDbService.create(user);
        System.out.println(usersDbService.read(user.getId()));

        AddressDataSetMyOrmDbService addressDbService = new AddressDataSetMyOrmDbService(new AddressDataSetMyOrmDao(entityManager));
        System.out.println(addressDbService.read(address.getId()));

        PhoneDataSetMyOrmDbService phoneDbService = new PhoneDataSetMyOrmDbService(new PhoneDataSetMyOrmDao(entityManager));
        System.out.println(phoneDbService.read(phone.getId()));
    }
}
