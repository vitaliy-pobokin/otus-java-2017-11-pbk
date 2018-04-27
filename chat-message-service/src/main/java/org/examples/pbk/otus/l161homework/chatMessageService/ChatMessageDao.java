package org.examples.pbk.otus.l161homework.chatMessageService;

import org.examples.pbk.otus.l161homework.entity.ChatMessage;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ChatMessageDao {

    private Session session;

    public List<ChatMessage> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<ChatMessage> criteria = builder.createQuery(ChatMessage.class);
        criteria.from(ChatMessage.class);
        return session.createQuery(criteria).getResultList();
    }

    public ChatMessage findById(long id) {
        return getSession().find(ChatMessage.class, id);
    }

    public void create(ChatMessage chatMessage) {
        getSession().persist(chatMessage);
    }

    public void update(ChatMessage chatMessage) {
        getSession().merge(chatMessage);
    }

    public void delete(long id) {
        getSession().remove(getSession().find(ChatMessage.class, id));
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
