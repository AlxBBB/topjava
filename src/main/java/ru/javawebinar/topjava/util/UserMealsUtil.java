package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);

        getFilteredWithExceededV2(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {


        Map<LocalDate,Integer> exceed=mealList.stream().collect(Collectors.groupingBy(m->m.getDateTime().toLocalDate(),Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream().filter(m->TimeUtil.isBetween(m.getDateTime().toLocalTime(),startTime,endTime))
                .map(u->new UserMealWithExceed(u.getDateTime(),u.getDescription(),u.getCalories(),exceed.get(u.getDateTime().toLocalDate())>caloriesPerDay))
                .collect(Collectors.toList());
    }
    public static List<UserMealWithExceed>  getFilteredWithExceededV2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

         Map<LocalDate,Integer> exceed=new HashMap<LocalDate,Integer>();
         mealList.forEach(meal->exceed.put(meal.getDateTime().toLocalDate(),
                 exceed.getOrDefault(meal.getDateTime().toLocalDate(),0)+meal.getCalories()));

         List<UserMealWithExceed> mealWithExceedList=new ArrayList<UserMealWithExceed>();
         mealList.forEach(meal->{if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime))
                                    {mealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                                            meal.getCalories(), exceed.get(meal.getDateTime().toLocalDate())>caloriesPerDay));}
         });

        return mealWithExceedList;
    }

}
