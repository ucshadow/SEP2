SET SEARCH_PATH = sep2;
DROP SCHEMA sep2 CASCADE;
CREATE SCHEMA sep2;
DELETE FROM userlogin;
DELETE FROM workingschedule;
SELECT *
FROM workingschedule;
SELECT *
FROM userlogin;

INSERT INTO history (tablename, operation, details, timestamp) VALUES ('WordCHECK', 'False words', '', now());

REFRESH MATERIALIZED VIEW employeeinformation;



CREATE TABLE testing (
  that   INTEGER,
  number INTEGER
);
SELECT *
FROM wageperhour;
drop MATERIALIZED VIEW alluserswithwage;
REFRESH MATERIALIZED VIEW alluserswithwage;
SELECT * from alluserswithwage;
REFRESH MATERIALIZED VIEW usersbydepartment;

SELECT
  firstname,
  familyname,
  employecpr,
  dno
FROM usersbydepartment WHERE dno = '4229153';
SELECT DISTINCT(employecpr)
FROM workingschedule where dno = '4229153';
SELECT *
FROM city where postcode ='postcode';
SELECT *
FROM userlogin;
SELECT *
FROM workingschedule;




INSERT INTO department (dno, dname, dmanager, dpostcode, dstartdate) VALUES ('1234', '', '2345678901', '1234', now());
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('1234', '2345678901', to_date('20-1-2017', 'dd/mm/yyyy'), '08:00', '09:00');
DELETE FROM userlogin;
INSERT INTO testing (that, number) VALUES ('1', '0');
SELECT *
FROM testing;
UPDATE testing
SET number = number + 1
WHERE that = '1';
UPDATE testing
SET number = '0';
SELECT *
FROM employee;

SELECT *
FROM bankinfodk;

SELECT *
FROM communication;
SELECT *
FROM workingschedule;
SELECT *
FROM wageperhour;
SET SEARCH_PATH = sep2;
SELECT *
FROM department;
REFRESH MATERIALIZED VIEW allcolleagues;
SELECT *
FROM allcolleagues
WHERE cpr IS DISTINCT FROM '1234567890';
INSERT INTO wageperhour (employeecpr, wage) VALUES ('9988776655', '1234');

SELECT *
FROM department;
INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('3187044', '4334320890', '10/12/2017', '08:00', '19:00');
REFRESH MATERIALIZED VIEW workingcolleagues;
SELECT *
FROM workingcolleagues;
SELECT *
FROM test
WHERE cpr IS DISTINCT FROM '1234567890' AND dno = 'd001';

REFRESH MATERIALIZED VIEW employeeinformation;

SELECT *
FROM EmployeeInformation
WHERE cpr = '8701646897';
SET SEARCH_PATH = sep2;
SELECT *
FROM employee;
REFRESH MATERIALIZED VIEW usersbydepartment;
drop MATERIALIZED VIEW usersbydepartment;
SELECT
  cpr,
  firstname,
  familyname,
  dno
FROM usersbydepartment where cpr ='7213714538';
SELECT *
FROM workingschedule
WHERE employecpr = '7213714538';

INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
VALUES ('3115960', '7213714538', '07/12/2017', '08:00', '10:00');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('', 'Nikolay', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('2345678901', 'MomoLina', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('3456789012', 'Nikolay3', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('4567890123', 'Nikolay4', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('5678901234', 'Nikolay5', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('6789012345', 'Nikolay6', 'Password123', 'Admin');
INSERT INTO userlogin (cpr, username, pass, userrole) VALUES ('7890123456', 'Nikolay7', 'Password123', 'Admin');
UPDATE communication
SET preferredcommunication = 'mobile'
WHERE cpr = '1234657890';
DELETE FROM userlogin;

SELECT *
FROM userlogin;
SELECT count(id)
FROM history;
SELECT *
FROM city;
INSERT INTO city (postcode, city) VALUES ('1234', 'Admin');
UPDATE city
SET city = 'MyAss'
WHERE postcode = '1234';
INSERT INTO city (postcode, city) VALUES ('8700', 'Horsens');
INSERT INTO city (postcode, city) VALUES ('8732', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('9000', 'Horsens34');
DELETE FROM city
WHERE postcode = '9000';
INSERT INTO city (postcode, city) VALUES ('4321', 'Horsens');
INSERT INTO city (postcode, city) VALUES ('8736', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('9009', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('8709', 'Horsens');
INSERT INTO city (postcode, city) VALUES ('9999', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('9000', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('8700', 'Horsens');
INSERT INTO city (postcode, city) VALUES ('8732', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('9000', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('8700', 'Horsens');
INSERT INTO city (postcode, city) VALUES ('8732', 'Horsens34');
INSERT INTO city (postcode, city) VALUES ('9000', 'Horsens34');

-- SELECT *
-- FROM employee;
-- SELECT *
-- FROM userlogin;
-- INSERT INTO UserLogIn VALUES ('nikolays', '0123656789', '123456789A', 'Admin');
-- INSERT INTO UserLogIn VALUES ('Nikoasdsss', '1234567891', 'Abdfjkgbsjdfkj', 'Admin');
-- SELECT
--   picture,
--   firstname,
--   familyname,
--   mobile,
--   email
-- FROM employee
-- WHERE cpr IS DISTINCT FROM '1213421213';
-- SELECT *
-- FROM UserLogIn;
-- SELECT *
-- FROM Employee;
-- SELECT *
-- FROM department;
--
-- UPDATE department
-- SET dno = 'd001', dname = 'Ice', dlocation = 'London', dmanager = '1212121213'
-- WHERE dno = 'D001';
-- UPDATE employee
-- SET passEmp = 'AssHole2'
-- WHERE cpr = '0123656789';
--
-- DELETE FROM userlogin
-- WHERE cpr = '0123656789';
--
-- SELECT *
-- FROM department;
-- SELECT *
-- FROM department
-- WHERE dno = 'D003';
-- INSERT INTO department VALUES ('d001', 'Ice', 'London', '0123456789', '0123456789');
-- SET SEARCH_PATH = sep2;
--
--
-- UPDATE department
-- SET dno = 'D001'
-- WHERE dno = 'D003';
-- DROP TABLE workingschedule;
-- SELECT *
-- FROM workingschedule;
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '1234567890', '24/11/2017', '08:00', '18:00');
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '1234567890', '25/11/2017', '08:00', '18:00');
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '1234567890', '26/11/2017', '08:00', '18:00');
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '1234567890', '27/11/2017', '08:00', '18:00');
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '1234567890', '28/11/2017', '08:00', '18:00');
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '1234567890', '29/11/2017', '08:00', '18:00');
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '1234567890', '30/11/2017', '08:00', '18:00');
-- INSERT INTO workingschedule (dno, employecpr, workingday, starthours, endhours)
-- VALUES ('d001', '0987654310', '2/12/2017', '08:00', '18:00');
--
-- SELECT *
-- FROM workingschedule
-- WHERE employecpr = '1234567890' AND workingday >= '27/11/2017' AND workingday <= '3/12/2017';
-- SELECT *
-- FROM userlogin
-- WHERE cpr = '1234567890';
--
-- SELECT *
-- FROM department;
--
-- INSERT INTO department VALUES ('d001', 'Ice', 'London', '0123456789');
-- INSERT INTO department VALUES ('d002', 'Free', 'London', '3535353535');
-- INSERT INTO department VALUES ('d003', 'Taken', 'London', '0987654321');
--
--
-- SELECT DISTINCT dno
-- FROM workingschedule
-- WHERE employecpr = '1234567890';
--
-- SELECT *
-- FROM workingschedule;
-- DELETE FROM userlogin;
--
-- SELECT
--   firstname,
--   familyname,
--   mobile,
--   email,
--   username
-- FROM employee
-- WHERE cpr IS DISTINCT FROM '2234567890';
-- SELECT *
-- FROM employee;
-- SELECT *
-- FROM wageperhour;
-- INSERT INTO wageperhour VALUES ('1234567890', '121.00');
-- SELECT *
-- FROM userlogin;
-- DELETE FROM userlogin
-- WHERE cpr = '1234567890';
--
-- SELECT *
-- FROM workingschedule;
--
-- SELECT
--   picture,
--   firstname,
--   familyname,
--   mobile,
--   email
-- FROM employee
--   LEFT OUTER JOIN workingSchedule ON (employee.cpr = workingschedule.employecpr)
-- WHERE workingschedule.employecpr IS DISTINCT FROM '1234567890' AND workingSchedule.dno = 'd001';
-- DROP SCHEMA sep2;
-- DROP FUNCTION newemployee();
-- DROP FUNCTION addwage();
-- DROP FUNCTION emppassword();
-- DROP TABLE department;
--
-- DROP DOMAIN cpr_domain;
-- DROP DOMAIN dno_domain;
-- DROP TABLE userlogin;
-- DROP TABLE employee;
-- DROP TABLE workingschedule;
-- DROP TABLE wageperhour;
-- SET SEARCH_PATH = sep2