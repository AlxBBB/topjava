package ru.javawebinar.topjava.web.Validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Locale;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void validate(Object o, Errors errors) {
        try {
            Integer iD = null;
            User baseUser = null;
            if (o instanceof User) {
                iD = ((User) o).getId();
                baseUser = userService.getByEmail(((User) o).getEmail());

            } else if (o instanceof UserTo) {
                iD = ((UserTo) o).getId();
                baseUser = userService.getByEmail(((UserTo) o).getEmail());
            }

            if (iD == null || !baseUser.getId().equals(iD)) {
                errors.rejectValue("email", "valid.dublicateEmail", messageSource.getMessage("valid.dublicateEmail",null, LocaleContextHolder.getLocale()));
            }

        } catch (NotFoundException e) {
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass) || UserTo.class.equals(aClass);
    }


}
