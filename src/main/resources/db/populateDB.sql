--DELETE FROM meals;
--DELETE FROM user_roles;
--DELETE FROM users;


TRUNCATE TABLE users CASCADE;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES (100000,'2017-07-30 10:00','Завтрак',500),
                                                                     (100000,'2017-07-30 13:00','Обед',1000),
                                                                     (100000,'2017-07-30 20:00','Ужин',500),
                                                                     (100000,'2017-07-31 10:00', 'Завтрак', 500),
                                                                     (100000,'2017-07-31 13:00', 'Обед', 1000),
                                                                     (100000,'2017-07-31 20:00', 'Ужин', 600),
                                                                     (100001,'2017-07-30 10:10', 'Завтрак', 550),
                                                                     (100001,'2017-07-30 13:10', 'Обед', 800),
                                                                     (100001,'2017-07-30 20:10', 'Ужин', 600);
