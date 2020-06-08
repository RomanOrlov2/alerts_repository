INSERT INTO users (user_id, username, password)
VALUES (1, 'user', 'password');
INSERT INTO users (user_id, username, password)
VALUES (2, 'admin','password');
INSERT INTO users (user_id, username, password)
VALUES (3, 'mary','password');

INSERT INTO roles (role_id, role)
VALUES (1, 'USER');
INSERT INTO roles (role_id, role)
VALUES (2, 'ADMIN');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role (user_id, role_id)
VALUES (2, 2);
INSERT INTO user_role (user_id, role_id)
VALUES (3, 1);