SET SEARCH_PATH = sep2;

CREATE TABLE Employee (
  firstname              VARCHAR(25),
  secondname             VARCHAR(25),
  familyname             VARCHAR(25),
  cpr                    CPR_DOMAIN PRIMARY KEY,
  dateOfBirth            DATE,
  Address                VARCHAR(25),
  postcode               VARCHAR(10),
  city                   VARCHAR(25),
  mobile                 CHAR(8),
  landline               CHAR(8),
  email                  VARCHAR(100),
  konto                  CHAR(4),
  regNumber              CHAR(10),
  licencePlate           VARCHAR(7),
  preferredCommunication VARCHAR(10) DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Home', 'Email')),
  moreInfo               VARCHAR(100)


);

DROP TABLE Employee;
DROP DOMAIN cpr_Domain;
DELETE FROM Employee;
SELECT count(firstname)
FROM Employee;
SELECT *
FROM Employee;
SELECT firstname
FROM Employee
WHERE cpr = '1203523615';