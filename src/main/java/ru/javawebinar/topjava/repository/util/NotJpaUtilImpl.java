package ru.javawebinar.topjava.repository.util;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.util.JpaUtil;


public class NotJpaUtilImpl implements JpaUtil {

    @Override
    public void clear2ndLevelHibernateCache() {
    }
}
