package org.examples.pbk.otus.l11homework;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class Encryption {

    public static void main(String[] args) {
        Map<String, String> unhashedUsernamePasswordPairs = generateUsernamePasswordPairs(10);
        printUsernamePasswordPairs(unhashedUsernamePasswordPairs);
        Map<String, String> hashedUsernamePasswordPairs = hashPasswords(unhashedUsernamePasswordPairs);
        printUsernamePasswordPairs(hashedUsernamePasswordPairs);
    }

    private static Map<String, String> generateUsernamePasswordPairs(int quantity) {
        Map<String, String> map = new HashMap<>(quantity);
        for (int i = 0; i < quantity; i++) {
            String username = RandomStringUtils.randomAlphabetic(4, 9);
            String password = RandomStringUtils.randomAlphanumeric(6, 13);
            map.put(username, password);
        }
        return map;
    }

    private static Map<String, String> hashPasswords(Map<String, String> unhashedUsernamePasswordPairs) {
        Map<String, String> map = new HashMap<>(unhashedUsernamePasswordPairs.size());
        for (Map.Entry<String, String> entry : unhashedUsernamePasswordPairs.entrySet()) {
            String hashedPassword = BCrypt.hashpw(entry.getValue(), BCrypt.gensalt());
            map.put(entry.getKey(), hashedPassword);
        }
        return map;
    }

    private static void printUsernamePasswordPairs(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
