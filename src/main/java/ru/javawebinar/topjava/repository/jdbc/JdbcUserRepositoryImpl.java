package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.lang.ref.SoftReference;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {
    private static final String ADD_ROLES = "INSERT INTO user_roles (user_id, role) VALUES(?,?)";
    private static final String DEL_ROLES = "DELETE FROM user_roles WHERE user_id=? AND role=?";





    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            changeRoles(ADD_ROLES, newKey.intValue(), user.getRoles());
            user.setId(newKey.intValue());
        } else {

            User oldUser = get(user.getId());
            if (oldUser != null) {
                Set<Role> oldRoles = oldUser.getRoles().size() == 0 ? Collections.emptySet() : EnumSet.copyOf(oldUser.getRoles());
                oldRoles.removeAll(user.getRoles());

                Set<Role> newRoles = user.getRoles().size() == 0 ? Collections.emptySet() : EnumSet.copyOf(user.getRoles());
                newRoles.removeAll(oldUser.getRoles());

                changeRoles(DEL_ROLES, user.getId(), oldRoles);
                changeRoles(ADD_ROLES, user.getId(), newRoles);
                // План 2 и его варианты  - удалить все роли и вставить заново, понравился меньше


                namedParameterJdbcTemplate.update(
                        "UPDATE users SET name=:name, email=:email, password=:password, " +
                                "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);

            }
        }
        return user;
    }

    private void changeRoles(String operation, int id, Set<Role> roles) {
        jdbcTemplate.batchUpdate(operation, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String role = roles.toArray()[i].toString();
                ps.setInt(1, id);
                ps.setString(2, role);
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }


    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {

        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id = r.user_id WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {

        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id = r.user_id WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }


    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id = r.user_id ORDER BY name, email", ROW_MAPPER);
        return users;
    }


    public List<User> getAllVer2() {
        List<User> rowMapperUsers = new ArrayList<>();
        RowMapper<User> myROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
            if (rowNum == 0 || rowMapperUsers.get(rowMapperUsers.size() - 1).getId() != resultSet.getInt("id")) {
                User user = new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"),
                        resultSet.getString("password"), resultSet.getInt("calories_per_day"), resultSet.getBoolean("enabled"),
                        resultSet.getString("role") == null ? Collections.emptySet() : EnumSet.of(Role.valueOf(resultSet.getString("role"))));
                rowMapperUsers.add(user);
            } else {
                rowMapperUsers.get(rowMapperUsers.size() - 1).addRole(Role.valueOf(resultSet.getString("role")));
            }
            return null;
        };
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id = r.user_id ORDER BY name, email", myROW_MAPPER);
        return rowMapperUsers;
    }

/*
    private List<User> shrink(List<User> users) {
        User workUser = null;
        for (Iterator it = users.iterator(); it.hasNext(); ) {
            User curUser = (User) it.next();
            if (workUser == null || !workUser.getId().equals(curUser.getId())) {
                workUser = curUser;
            } else {
                workUser.addRole(curUser.getRoles());
                it.remove();
            }
        }
        return users;
    }
*/
}
