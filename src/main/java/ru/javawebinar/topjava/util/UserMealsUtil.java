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
        List<UserMealWithExceed> mealWithExceedList=new ArrayList<UserMealWithExceed>();
        Map<LocalDate,Boolean> exceed=new HashMap<LocalDate,Boolean>();
        
        mealList.stream().collect(Collectors.groupingBy(m->m.getDateTime().toLocalDate(),Collectors.summingInt(UserMeal::getCalories)))
                .forEach((gDate,sumCl)-> exceed.put(gDate, sumCl > caloriesPerDay));

        mealList.stream().filter(m->TimeUtil.isBetween(m.getDateTime().toLocalTime(),startTime,endTime))
                .forEach(u->mealWithExceedList.add(new UserMealWithExceed(u.getDateTime(),u.getDescription(),u.getCalories(),exceed.get(u.getDateTime().toLocalDate()))));

        return mealWithExceedList;
    }
    public static List<UserMealWithExceed>  getFilteredWithExceededV2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceedList=new ArrayList<UserMealWithExceed>();
        Map<LocalDate,Integer> exceed=new HashMap<LocalDate,Integer>();
        for (UserMeal userMeal:mealList) {
             exceed.put(userMeal.getDateTime().toLocalDate(),
                   exceed.getOrDefault(userMeal.getDateTime().toLocalDate(),0)+userMeal.getCalories());
         }
        for (UserMeal userMeal:mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime)) {
                mealWithExceedList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(),
                                       userMeal.getCalories(), exceed.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
            }
        }
        return mealWithExceedList;
    }

}
