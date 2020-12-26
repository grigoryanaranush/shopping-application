INSERT INTO role (name)
SELECT 'ADMIN'
FROM dual
WHERE
    NOT EXISTS (SELECT 'ADMIN' FROM role WHERE name = 'ADMIN');

INSERT INTO role (name)
SELECT 'USER'
FROM dual
WHERE
    NOT EXISTS (SELECT 'USER' FROM role WHERE name = 'USER');

-- password is password
INSERT INTO user (first_name, last_name, username, password, is_blocked)
SELECT 'Admin', 'Admin', 'admin@admin.com', '$2a$10$wYoXOBorGBboW.sjF9HjVunm0DqbPTfplL51O6Iaj03JSIvBurYJO', false
FROM dual
WHERE
    NOT EXISTS (SELECT 'Admin' FROM user
                WHERE first_name = 'Admin'
                  and last_name = 'Admin'
                  and username = 'admin@admin.com'
                  and password = '$2a$10$wYoXOBorGBboW.sjF9HjVunm0DqbPTfplL51O6Iaj03JSIvBurYJO'
                  and is_blocked = false);


INSERT INTO user_roles (user_id, role_id)
SELECT 1, 1
FROM dual
WHERE
    NOT EXISTS (SELECT 'USER' FROM user_roles WHERE user_id = 1 and role_id = 1);