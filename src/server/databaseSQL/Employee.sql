SET SEARCH_PATH = sep2;

CREATE TABLE Employee (
  firstName              VARCHAR(25),
  secondName             VARCHAR(25),
  familyName             VARCHAR(25),
  cpr                    CPR_DOMAIN PRIMARY KEY,
  dateOfBirth            DATE,
  address                VARCHAR(25),
  postcode               VARCHAR(10),
  city                   VARCHAR(25),
  mobile                 CHAR(8),
  landline               CHAR(8),
  email                  VARCHAR,
  konto                  CHAR(4),
  regNumber              CHAR(10),
  licencePlate           VARCHAR,
  preferredCommunication VARCHAR DEFAULT 'Mobile' CHECK (preferredCommunication IN ('Mobile', 'Home', 'Email')),
  moreInfo               VARCHAR


);

DROP TABLE Employee;

DELETE FROM Employee;
