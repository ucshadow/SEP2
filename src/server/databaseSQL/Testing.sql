SELECT *
FROM employee;
SELECT *
FROM userlogin;
INSERT INTO UserLogIn VALUES ('nikolays', '0123656789', '123456789A', 'Admin');
INSERT INTO UserLogIn VALUES ('Nikoasdsss', '1234567891', 'Abdfjkgbsjdfkj', 'Admin');

SELECT *
FROM UserLogIn;
SELECT *
FROM Employee;

UPDATE employee
SET passEmp = 'AssHole2'
WHERE cpr = '0123656789';

DELETE FROM userlogin
WHERE cpr = '0123656789';

SELECT *
FROM department;
SELECT *
FROM department
WHERE dno = 'D003';
INSERT INTO department VALUES ('d001', 'Ice', 'London', '0123456789', '0123456789');
SET SEARCH_PATH = sep2;


UPDATE department
SET dno = 'D001'
WHERE dno = 'D003';