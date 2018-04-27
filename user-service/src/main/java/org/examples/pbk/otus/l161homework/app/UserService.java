package org.examples.pbk.otus.l161homework.app;

import org.examples.pbk.otus.l161homework.entity.User;
import org.examples.pbk.otus.l161homework.messageSystem.Service;

import java.util.List;

public interface UserService extends Service {
    void init();

    List<User> findAll();

    User findById(long id);

    User findByName(String username);

    void create(User user);

    void update(User user);

    void delete(long id);
}
