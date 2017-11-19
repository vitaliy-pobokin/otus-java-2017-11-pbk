package org.examples.pbk.otus.l11homework;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class Encryption {

    public static void main(String[] args) {
        Map<String, String> unencryptedUsernamePasswordPairs = generateUsernamePasswordPairs(10);
        printUsernamePasswordPairs(unencryptedUsernamePasswordPairs);
        Map<String, String> encryptedUsernamePasswordPairs = encryptPasswords(unencryptedUsernamePasswordPairs);
        printUsernamePasswordPairs(encryptedUsernamePasswordPairs);
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

    private static Map<String, String> encryptPasswords(Map<String, String> unencryptedUsernamePasswordPairs) {
        Map<String, String> map = new HashMap<>(unencryptedUsernamePasswordPairs.size());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (Map.Entry<String, String> entry : unencryptedUsernamePasswordPairs.entrySet()) {
            String encodedPassword = encoder.encode(entry.getValue());
            map.put(entry.getKey(), encodedPassword);
        }
        return map;
    }

    private static void printUsernamePasswordPairs(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
