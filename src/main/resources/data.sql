INSERT INTO generation(id, sequence)
VALUES (1, 1),
       (2, 2)
;

INSERT INTO course(id, name)
VALUES (1, 'BE'),
       (2, 'FE')
;

-- user 비밀번호 : 00000000
-- admin 비밀번호 : admin123
INSERT INTO user(id, email, name, role, status, course_id, generation_id, password, authority)
VALUES (1, 'user@gmail.com', 'seunghun', 'STUDENT', 'JOIN', 1, 1, '$2a$10$HwWY2SAHd1rpoKQMFYntseC5eFpgKgRWXd6aLpY5mXc2LERilA2T2', 'USER'),
       (2, 'admin@naver.com', 'myungok', 'MANAGER', 'JOIN', 2, 2, '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 'ADMIN')
;