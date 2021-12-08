INSERT INTO permission(id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN')
;

INSERT INTO group_s(id, name)
VALUES (1, 'USER_GROUP'),
       (2, 'ADMIN_GROUP')
;

-- USER_GROUP (ROLE_USER)
-- ADMIN_GROUP (ROLE_USER, ROLE_ADMIN)
INSERT INTO group_permission(id, group_id, permission_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 2, 2)
;

INSERT INTO generation(id, sequence)
VALUES (1, 1),
       (2, 2)
;

INSERT INTO course(id, name)
VALUES (1, 'BE'),
       (2, 'FE')
;


-- user 비밀번호 : user123
-- admin 비밀번호 : admin123
INSERT INTO user(id, email, name, role, status, course_id, generation_id, password, group_id)
VALUES (1, 'user@gmail.com', 'seunghun', 'STUDENT', 'JOIN', 1, 1, '$2a$10$B32L76wyCEGqG/UVKPYk9uqZHCWb7k4ci98VTQ7l.dCEib/kzpKGe', 1),
       (2, 'admin@naver.com', 'myungok', 'MANAGER', 'JOIN', 2, 2, '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 2)
;