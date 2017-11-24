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