set SEARCH_PATH =sep2;
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
DROP TABLE workingschedule;
SELECT *
FROM workingschedule;
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '24/11/2017', '08:00', '18:00');
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '25/11/2017', '08:00', '18:00');
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '26/11/2017', '08:00', '18:00');
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '27/11/2017', '08:00', '18:00');
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '28/11/2017', '08:00', '18:00');
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '29/11/2017', '08:00', '18:00');
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '30/11/2017', '08:00', '18:00');
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('d001', '1234567890', '1/12/2017', '08:00', '18:00');

SELECT *
FROM workingschedule
WHERE employecpr = '1234567890' AND workingday >= '27/11/2017' AND workingday <= '3/12/2017';
SELECT *
FROM userlogin where cpr = '1234567890';

SELECT *
FROM department;

INSERT INTO department VALUES ('d001', 'Ice', 'London', '0123456789', '0123456789');
INSERT INTO department VALUES ('d002', 'Free', 'London', '3535353535', '0123456789');
INSERT INTO department VALUES ('d003', 'Taken', 'London', '0987654321', '9876543210');
INSERT INTO department VALUES ('d002', 'Free', 'London', '3535353535', '0876541329');
INSERT INTO department VALUES ('d001', 'Ice', 'London', '0123456789', '3232323232');


SELECT dno FROM department where demployee ='0123456789';



SELECT firstname,familyname,mobile,email,username from employee where cpr is DISTINCT FROM '2234567890';
SELECT * FROM  employee;
SELECT * FROM wageperhour;
INSERT INTO wageperhour VALUES ('1234567890','121.00');
SELECT * from userlogin;
DELETE from userlogin where cpr = '1234567890';