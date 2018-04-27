package org.examples.pbk.otus.l161homework.userService;

import org.examples.pbk.otus.l161homework.entity.User;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao {

    private Session session;

    public List<User> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        return session.createQuery(criteria).getResultList();
    }

    public User findById(long id) {
        return getSession().find(User.class, id);
    }

    public User findByUsername(String username) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        criteria.where(builder.equal(from.get("username"), username));
        TypedQuery<User> query = getSession().createQuery(criteria);
        return query.getSingleResult();
    }

    public void create(User user) {
        getSession().persist(user);
    }

    public void update(User user) {
        getSession().merge(user);
    }

    public void delete(long id) {
        getSession().remove(getSession().find(User.class, id));
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private Session getSession() {
        if (session == null) {
            throw new RuntimeException("Session wasn't set");
        }
        return session;
    }
}
