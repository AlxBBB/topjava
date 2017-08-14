package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.Profiles;


@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest{

}
