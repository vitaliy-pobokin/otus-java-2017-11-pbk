package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.dbService.DbServiceEndpoint;
import org.examples.pbk.otus.l161homework.dbService.SessionFactoryProvider;
import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MessageSystemContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class DbServiceRunner {
    public static void main(String[] args) {
        Address msAddress = null;
        Address dbAddress = null;
        Address frontAddress = null;
        try {
            msAddress = new Address(InetAddress.getByName("127.0.0.1"), 5050);
            dbAddress = new Address(InetAddress.getByName("127.0.0.1"), 5051);
            frontAddress = new Address(InetAddress.getByName("127.0.0.1"), 5052);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        MessageSystemContext context = new MessageSystemContext(msAddress);
        context.setDbAddress(dbAddress);
        context.setFrontendAddress(frontAddress);
        DbServiceEndpoint dbEndpoint = new DbServiceEndpoint(context);
        SessionFactoryProvider.init();
        dbEndpoint.init();
    }
}
