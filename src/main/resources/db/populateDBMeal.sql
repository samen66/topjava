DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_seq_meal RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(date_time, description,calories, user_id)
VALUES ('2023-03-05 13:15:00.000000', 'lunch', 700, 100000),
       ('2023-03-05 16:15:00.000000', 'uzhen', 500, 100000),
       ('2023-03-06 13:15:00.000000', 'lunch', 700, 100000),
       ('2023-03-04 14:15:00.000000', 'after lunch', 700, 100001);


