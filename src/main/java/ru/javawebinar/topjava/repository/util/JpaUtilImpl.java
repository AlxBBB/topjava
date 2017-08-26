package ru.javawebinar.topjava.repository.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class JpaUtilImpl implements JpaUtil {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void clear2ndLevelHibernateCache() {
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }
}
