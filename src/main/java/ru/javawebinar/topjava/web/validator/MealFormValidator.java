package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

@Component
public class MealFormValidator implements Validator {

    @Autowired
    private MealService mealService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> aClass) {
        return Meal.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal meal = (Meal) target;
        List<Meal> list = mealService.getBetweenDateTimes(meal.getDateTime(), meal.getDateTime(), AuthorizedUser.id());
        if (list != null && list.size() > 0 && (meal.isNew() || !meal.getId().equals(list.get(0).getId()))) {
            errors.rejectValue("DateTime", "valid.dublicateDateTime", messageSource.getMessage("valid.dublicateDateTime", null, LocaleContextHolder.getLocale()));
        }
    }
}
