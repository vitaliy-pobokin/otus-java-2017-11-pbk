package org.examples.pbk.otus.l131homework.dao;

import org.examples.pbk.otus.l131homework.dataset.AccountDataSet;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AccountDataSetHibernateDao extends GenericDataSetHibernateDao<AccountDataSet> {
    public AccountDataSet findByUsername(String username) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<AccountDataSet> criteria = builder.createQuery(AccountDataSet.class);
        Root<AccountDataSet> from = criteria.from(AccountDataSet.class);
        criteria.where(builder.equal(from.get("username"), username));
        Query<AccountDataSet> query = getSession().createQuery(criteria);
        return query.uniqueResult();
    }
}
