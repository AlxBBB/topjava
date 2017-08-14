package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.NULL_MEALS;
import static ru.javawebinar.topjava.UserTestData.*;


@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest{

    @Test
    public void testGetWithMeals() throws Exception {
        User user = getService().getWithMeal(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MEALS, user.getMeals());

        // Для теста завел пользователя без еды
        user = getService().getWithMeal(NOMEAL_ID);
        MATCHER.assertEquals(NOMEAL, user);
        MealTestData.MATCHER.assertCollectionEquals(NULL_MEALS, user.getMeals());
    }

}
