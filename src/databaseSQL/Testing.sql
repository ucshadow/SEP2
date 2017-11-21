--Employee
SELECT count(firstname)
FROM Employee;

SELECT *
FROM Employee;

SELECT firstname
FROM Employee
WHERE cpr = '1203523615';

--Use testing


SELECT *
FROM UserLogIn;

INSERT INTO UserLogIn VALUES ('Nikolay', '0123456789', '123456789A', 'Admin');
INSERT INTO UserLogIn VALUES ('Nikoasdsss', '1234567891', 'Abdfjkgbsjdfkj', 'Admin');
INSERT INTO UserLogIn VALUES ('sdfgfffffff', '1234567821', 'asdasdsAdasd', 'Admin');
INSERT INTO UserLogIn VALUES ('Niksdfgasdsdfosss', '1234567893', 'sdfgsdgf', 'Admin');
INSERT INTO UserLogIn VALUES ('sdfgdsfgdsfg', '1234567894', 'asdasdasdaZ', 'Admin');
INSERT INTO UserLogIn VALUES ('sdfgsdfgsdfg', '1234567895', 'dgsfgsdfg', 'Admin');
INSERT INTO UserLogIn VALUES ('sdfgdsfgsdgfdfgd', '1234567896', 'asdasdasH', 'Admin');
INSERT INTO UserLogIn VALUES ('sdfgfdgdd', '1234567897', 'asdasdasdM', 'Admin');


SELECT Password
FROM UserLogIn
WHERE Username = 'sdfgfffffff';