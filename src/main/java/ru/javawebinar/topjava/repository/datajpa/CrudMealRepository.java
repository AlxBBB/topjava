package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    Meal save(Meal meal);

    @Override
    Meal findOne(Integer integer);



    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startdate, LocalDateTime endDate, int userId);


    //возможно fetch лишнее
    @Query("SELECT m FROM Meal m JOIN FETCH User u ON m.user.id=u.id WHERE m.id=:id and m.user.id=:user_id")
    Meal get(@Param("id") int id, @Param("user_id") int userId);


    @Query("SELECT u FROM User u WHERE u.id=:user_id")
    User getUser(@Param("user_id") int userId);

    //User findOne(Integer integer)


}
