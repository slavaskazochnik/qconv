-- Generated by Oracle SQL Developer Data Modeler 4.0.3.853
--   at:        2015-03-10 19:49:58 BRT
--   site:      Oracle9i
--   type:      Oracle9i




CREATE TABLE APP_ROLES
  (
    ID_ROLE    INTEGER NOT NULL ,
    ROLE_NAME  VARCHAR2 (30) NOT NULL ,
    ROLE_DESCR VARCHAR2 (200)
  )
  LOGGING ;
COMMENT ON TABLE APP_ROLES
IS
  '���� ����������' ;
  COMMENT ON COLUMN APP_ROLES.ID_ROLE
IS
  'PK' ;
  COMMENT ON COLUMN APP_ROLES.ROLE_NAME
IS
  '��� ����' ;
  COMMENT ON COLUMN APP_ROLES.ROLE_DESCR
IS
  '�������� ������������, ���� ����' ;
  CREATE INDEX APP_ROLES_ROLE_NAME_UN ON APP_ROLES
    ( ROLE_NAME ASC
    ) ;
  ALTER TABLE APP_ROLES ADD CONSTRAINT APP_ROLES_PK PRIMARY KEY ( ID_ROLE ) ;

CREATE TABLE AUTOS
  (
    ID_AUTO      INTEGER NOT NULL ,
    ID_CAR       INTEGER NOT NULL ,
    ID_DRIVER    INTEGER NOT NULL ,
    R_WAITING    SMALLINT ,
    R_ROUTE      SMALLINT ,
    POSITION_LAT SMALLINT ,
    POSITION_LNG SMALLINT ,
    SIGN_ACTIVE  INTEGER ,
    D_CREATE     DATE
  )
  LOGGING ;
COMMENT ON TABLE AUTOS
IS
  '������ ����������� ������� � ���������� �������' ;
  COMMENT ON COLUMN AUTOS.ID_AUTO
IS
  'PK' ;
  COMMENT ON COLUMN AUTOS.ID_CAR
IS
  'FK cars' ;
  COMMENT ON COLUMN AUTOS.ID_DRIVER
IS
  'FK drivers' ;
  COMMENT ON COLUMN AUTOS.R_WAITING
IS
  '������ ��������, ��' ;
  COMMENT ON COLUMN AUTOS.R_ROUTE
IS
  '������ ���������� ������, ��' ;
  COMMENT ON COLUMN AUTOS.POSITION_LAT
IS
  '������� ��������� ���� (������)' ;
  COMMENT ON COLUMN AUTOS.POSITION_LNG
IS
  '������� ��������� ���� (�������)' ;
  COMMENT ON COLUMN AUTOS.SIGN_ACTIVE
IS
  '������� ������� � ���������� ������' ;
  COMMENT ON COLUMN AUTOS.D_CREATE
IS
  '���� �������� ������' ;
  CREATE INDEX AUTOS_PK ON AUTOS
    ( ID_AUTO ASC
    ) ;
  CREATE INDEX AUTOS_CAR_DRIVER_UK ON AUTOS
    ( ID_CAR ASC , ID_DRIVER ASC
    ) ;
  ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_PK PRIMARY KEY ( ID_AUTO ) ;

CREATE TABLE CARS
  (
    ID_CAR      INTEGER CONSTRAINT NNC_cars_id_car NOT NULL ,
    CAR_REG_NUM VARCHAR2 (10) ,
    SITS_QUAN   INTEGER ,
    ID_CAR_TYPE INTEGER NOT NULL ,
    SIGN_ACTIVE INTEGER ,
    D_CREATE    DATE
  )
  LOGGING ;
COMMENT ON TABLE CARS
IS
  '���������� ������������ �������' ;
  COMMENT ON COLUMN CARS.ID_CAR
IS
  'PK' ;
  COMMENT ON COLUMN CARS.CAR_REG_NUM
IS
  '��������������� �����' ;
  COMMENT ON COLUMN CARS.SITS_QUAN
IS
  '���������� ������������ ����' ;
  COMMENT ON COLUMN CARS.ID_CAR_TYPE
IS
  '��� ����' ;
  COMMENT ON COLUMN CARS.SIGN_ACTIVE
IS
  '������� �������������� ����' ;
  COMMENT ON COLUMN CARS.D_CREATE
IS
  '���� �������� ������' ;
  CREATE INDEX CARS_UK ON CARS
    ( CAR_REG_NUM ASC
    ) ;
  ALTER TABLE CARS ADD CONSTRAINT CARS_PK PRIMARY KEY ( ID_CAR ) ;

CREATE TABLE CAR_TYPES
  (
    ID_CAR_TYPE   INTEGER NOT NULL ,
    CAR_TYPE_NAME VARCHAR2 (30)
  )
  LOGGING ;
COMMENT ON TABLE CAR_TYPES
IS
  '���� ����' ;
  COMMENT ON COLUMN CAR_TYPES.ID_CAR_TYPE
IS
  'PK' ;
  COMMENT ON COLUMN CAR_TYPES.CAR_TYPE_NAME
IS
  '�������� ����' ;
  ALTER TABLE CAR_TYPES ADD CONSTRAINT CAR_TYPES_PK PRIMARY KEY ( ID_CAR_TYPE ) ;

CREATE TABLE CITIES
  (
    ID_CITY   INTEGER NOT NULL ,
    CITY_NAME VARCHAR2 (50)
  )
  LOGGING ;
COMMENT ON TABLE CITIES
IS
  '���������� ��������� �������' ;
  COMMENT ON COLUMN CITIES.ID_CITY
IS
  'PK' ;
  COMMENT ON COLUMN CITIES.CITY_NAME
IS
  '�������� ���������� ������' ;
  ALTER TABLE CITIES ADD CONSTRAINT CITIES_PK PRIMARY KEY ( ID_CITY ) ;

CREATE TABLE CUSTOMERS
  (
    ID_CUSTOMER         INTEGER CONSTRAINT NNC_DRIVERSv1_ID_DRIVER NOT NULL ,
    AVG_CUSTOMER_RATING INTEGER ,
    SIGN_ACTIVE         INTEGER ,
    D_CREATE            DATE
  )
  LOGGING ;
COMMENT ON TABLE CUSTOMERS
IS
  '���������� ����������' ;
  COMMENT ON COLUMN CUSTOMERS.ID_CUSTOMER
IS
  'PK' ;
  COMMENT ON COLUMN CUSTOMERS.AVG_CUSTOMER_RATING
IS
  '������� ������ ���������' ;
  COMMENT ON COLUMN CUSTOMERS.SIGN_ACTIVE
IS
  '������� ���������� �������� � �������' ;
  COMMENT ON COLUMN CUSTOMERS.D_CREATE
IS
  '���� �������� ������' ;
  ALTER TABLE CUSTOMERS ADD CONSTRAINT CUSTOMERS_PK PRIMARY KEY ( ID_CUSTOMER ) ;

CREATE TABLE DRIVERS
  (
    ID_DRIVER         INTEGER NOT NULL ,
    AVG_DRIVER_RATING INTEGER ,
    SIGN_ACTIVE       INTEGER ,
    D_CREATE          DATE
  )
  LOGGING ;
COMMENT ON TABLE DRIVERS
IS
  '���������� ���������' ;
  COMMENT ON COLUMN DRIVERS.ID_DRIVER
IS
  'PK' ;
  COMMENT ON COLUMN DRIVERS.AVG_DRIVER_RATING
IS
  '������� ������ ��������' ;
  COMMENT ON COLUMN DRIVERS.SIGN_ACTIVE
IS
  '������� ���������� �������� � �������' ;
  COMMENT ON COLUMN DRIVERS.D_CREATE
IS
  '���� �������� ������' ;
  ALTER TABLE DRIVERS ADD CONSTRAINT DRIVERS_PK PRIMARY KEY ( ID_DRIVER ) ;

CREATE TABLE ORDERS
  (
    ID_ORDER        INTEGER NOT NULL ,
    ID_CUSTOMER     INTEGER NOT NULL ,
    ID_ROUTE        INTEGER NOT NULL ,
    ID_AUTO         INTEGER ,
    CURRENT_STATE   INTEGER NOT NULL ,
    ID_RESULT       INTEGER NOT NULL ,
    ROUTE_LENGTH    INTEGER ,
    ORDER_PRICE     INTEGER ,
    CUSTOMER_RATING INTEGER ,
    DRIVER_RATING   INTEGER ,
    D_CREATE        DATE NOT NULL
  )
  LOGGING ;
COMMENT ON TABLE ORDERS
IS
  '������' ;
  COMMENT ON COLUMN ORDERS.ID_ORDER
IS
  'PK' ;
  COMMENT ON COLUMN ORDERS.ID_CUSTOMER
IS
  '��������' ;
  COMMENT ON COLUMN ORDERS.ID_ROUTE
IS
  '������� ����������' ;
  COMMENT ON COLUMN ORDERS.ID_AUTO
IS
  '����������� ������' ;
  COMMENT ON COLUMN ORDERS.CURRENT_STATE
IS
  '��������� ������: �����, ������, �������� ���������, ��������' ;
  COMMENT ON COLUMN ORDERS.ID_RESULT
IS
  '���������: ����� ������, ����� ��������, ����� �������' ;
  COMMENT ON COLUMN ORDERS.ROUTE_LENGTH
IS
  '����� ��������' ;
  COMMENT ON COLUMN ORDERS.ORDER_PRICE
IS
  '��������� ������' ;
  COMMENT ON COLUMN ORDERS.CUSTOMER_RATING
IS
  '������ ���������' ;
  COMMENT ON COLUMN ORDERS.DRIVER_RATING
IS
  '������ ��������' ;
  COMMENT ON COLUMN ORDERS.D_CREATE
IS
  '���� �������� ������' ;
  ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_PK PRIMARY KEY ( ID_ORDER ) ;

CREATE TABLE ORDERS_TIMETABLE
  (
    ID_TIMETABLE INTEGER NOT NULL ,
    ID_ORDER     INTEGER NOT NULL ,
    ID_STATE     INTEGER NOT NULL ,
    D_CREATE     DATE
  )
  LOGGING ;
COMMENT ON TABLE ORDERS_TIMETABLE
IS
  '������ ���������� ������' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.ID_TIMETABLE
IS
  'PK' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.ID_ORDER
IS
  'ID ������' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.ID_STATE
IS
  '��������� ������' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.D_CREATE
IS
  '���� ������������ ���������' ;
  ALTER TABLE ORDERS_TIMETABLE ADD CONSTRAINT ORADERS_TIMETABLE_PK PRIMARY KEY ( ID_TIMETABLE ) ;

CREATE TABLE ORDER_RESULTS
  (
    ID_RESULT   INTEGER NOT NULL ,
    RESULT_NAME VARCHAR2 (50)
  )
  LOGGING ;
COMMENT ON TABLE ORDER_RESULTS
IS
  '��������� ���������� ������' ;
  COMMENT ON COLUMN ORDER_RESULTS.ID_RESULT
IS
  'PK' ;
  COMMENT ON COLUMN ORDER_RESULTS.RESULT_NAME
IS
  '������������' ;
  ALTER TABLE ORDER_RESULTS ADD CONSTRAINT ORDER_RESULTS_PK PRIMARY KEY ( ID_RESULT ) ;

CREATE TABLE ORDER_STATES
  (
    ID_STATE    INTEGER NOT NULL ,
    STATE_NAME  VARCHAR2 (30) ,
    STATE_DESCR VARCHAR2 (100)
  )
  LOGGING ;
COMMENT ON TABLE ORDER_STATES
IS
  '��������� ������' ;
  COMMENT ON COLUMN ORDER_STATES.ID_STATE
IS
  'PK' ;
  COMMENT ON COLUMN ORDER_STATES.STATE_NAME
IS
  '�������� ��������� ������' ;
  COMMENT ON COLUMN ORDER_STATES.STATE_DESCR
IS
  '��������� ��������' ;
  ALTER TABLE ORDER_STATES ADD CONSTRAINT ORDER_STATES_PK PRIMARY KEY ( ID_STATE ) ;

CREATE TABLE PERSONS
  (
    ID_PERSON  INTEGER NOT NULL ,
    FIRST_NAME VARCHAR2 (30) NOT NULL ,
    LAST_NAME  VARCHAR2 (30) ,
    TEL_NUM    VARCHAR2 (30) NOT NULL ,
    D_CREATE   DATE
  )
  LOGGING ;
COMMENT ON TABLE PERSONS
IS
  '���������� ������' ;
  COMMENT ON COLUMN PERSONS.ID_PERSON
IS
  'PK' ;
  COMMENT ON COLUMN PERSONS.FIRST_NAME
IS
  '���' ;
  COMMENT ON COLUMN PERSONS.LAST_NAME
IS
  '�������' ;
  COMMENT ON COLUMN PERSONS.TEL_NUM
IS
  '���������� �������' ;
  COMMENT ON COLUMN PERSONS.D_CREATE
IS
  '���� �������� ������' ;
  ALTER TABLE PERSONS ADD CONSTRAINT PERSONS_PK PRIMARY KEY ( ID_PERSON ) ;

CREATE TABLE POINTS
  (
    ID_POINT  INTEGER NOT NULL ,
    ID_STREET INTEGER NOT NULL ,
    HOUSE_NUM VARCHAR2 (10) ,
    POINT_LAT SMALLINT ,
    POINT_LNG SMALLINT
  )
  LOGGING ;
COMMENT ON TABLE POINTS
IS
  '���������� ������� ����������' ;
  COMMENT ON COLUMN POINTS.ID_POINT
IS
  'PK' ;
  COMMENT ON COLUMN POINTS.ID_STREET
IS
  'FK �����' ;
  COMMENT ON COLUMN POINTS.HOUSE_NUM
IS
  '����� ����/�������' ;
  COMMENT ON COLUMN POINTS.POINT_LAT
IS
  '��������� �� �����: ������ [-90, 90]' ;
  COMMENT ON COLUMN POINTS.POINT_LNG
IS
  '��������� �� �����: ������� [-180, 180]' ;
  ALTER TABLE POINTS ADD CONSTRAINT POINTS_PK PRIMARY KEY ( ID_POINT ) ;

CREATE TABLE PRICES
  (
    ID_PRICE    INTEGER NOT NULL ,
    ID_CAR_TYPE INTEGER NOT NULL ,
    COST_BEFORE INTEGER ,
    COST_KM     INTEGER ,
    D_BEGIN     DATE ,
    D_END       DATE ,
    D_CREATE    DATE
  )
  LOGGING ;
COMMENT ON TABLE PRICES
IS
  '���� �� ������' ;
  COMMENT ON COLUMN PRICES.ID_PRICE
IS
  'PK' ;
  COMMENT ON COLUMN PRICES.ID_CAR_TYPE
IS
  '��� ����' ;
  COMMENT ON COLUMN PRICES.COST_BEFORE
IS
  '���� �������' ;
  COMMENT ON COLUMN PRICES.COST_KM
IS
  '���� �� 1 ��' ;
  COMMENT ON COLUMN PRICES.D_BEGIN
IS
  '��������� �' ;
  COMMENT ON COLUMN PRICES.D_END
IS
  '��������� ��' ;
  COMMENT ON COLUMN PRICES.D_CREATE
IS
  '���� ��������' ;
  ALTER TABLE PRICES ADD CONSTRAINT PRICES_PK PRIMARY KEY ( ID_PRICE ) ;

CREATE TABLE ROUTES
  (
    ID_ROUTE     INTEGER NOT NULL ,
    ID_SRC_POINT INTEGER NOT NULL ,
    ID_DST_POINT INTEGER NOT NULL ,
    EST_LENGTH   INTEGER ,
    FACT_LENGTH  INTEGER ,
    EST_TIME     INTEGER
  )
  LOGGING ;
COMMENT ON TABLE ROUTES
IS
  '������� ����������' ;
  COMMENT ON COLUMN ROUTES.ID_ROUTE
IS
  'PK' ;
  COMMENT ON COLUMN ROUTES.ID_SRC_POINT
IS
  '��������� �����' ;
  COMMENT ON COLUMN ROUTES.ID_DST_POINT
IS
  '�������� �����' ;
  COMMENT ON COLUMN ROUTES.EST_LENGTH
IS
  '����������� ����� ����' ;
  COMMENT ON COLUMN ROUTES.FACT_LENGTH
IS
  '����������� ����� ����' ;
  COMMENT ON COLUMN ROUTES.EST_TIME
IS
  '����������� ����� (�����)' ;
  ALTER TABLE ROUTES ADD CONSTRAINT ROUTES_PK PRIMARY KEY ( ID_ROUTE ) ;

CREATE TABLE STREETS
  (
    ID_STREET   INTEGER NOT NULL ,
    STREET_NAME VARCHAR2 (50) ,
    ID_CITY     INTEGER NOT NULL
  )
  LOGGING ;
COMMENT ON TABLE STREETS
IS
  '���������� ����' ;
  COMMENT ON COLUMN STREETS.ID_STREET
IS
  'PK' ;
  COMMENT ON COLUMN STREETS.STREET_NAME
IS
  '�������� �����' ;
  COMMENT ON COLUMN STREETS.ID_CITY
IS
  'FK ��������� �����' ;
  ALTER TABLE STREETS ADD CONSTRAINT STREETS_PK PRIMARY KEY ( ID_STREET ) ;

CREATE TABLE USER_ACCOUNTS
  (
    ID_ACCOUNT    INTEGER NOT NULL ,
    USER_NAME     VARCHAR2 (30) NOT NULL ,
    USER_PASSWORD VARCHAR2 (30) NOT NULL ,
    USER_EMAIL    VARCHAR2 (50) ,
    USER_ROLE     INTEGER NOT NULL ,
    USER_PERSONE  INTEGER NOT NULL ,
    SIGN_ACTIVE   INTEGER ,
    D_CREATE      DATE
  )
  LOGGING ;
COMMENT ON TABLE USER_ACCOUNTS
IS
  '���������� ���������' ;
  COMMENT ON COLUMN USER_ACCOUNTS.ID_ACCOUNT
IS
  'PK' ;
  COMMENT ON COLUMN USER_ACCOUNTS.USER_NAME
IS
  '����� ������������' ;
  COMMENT ON COLUMN USER_ACCOUNTS.USER_PASSWORD
IS
  '������' ;
  COMMENT ON COLUMN USER_ACCOUNTS.USER_EMAIL
IS
  '���������� email' ;
  COMMENT ON COLUMN USER_ACCOUNTS.USER_ROLE
IS
  '���� ������������' ;
  COMMENT ON COLUMN USER_ACCOUNTS.USER_PERSONE
IS
  'ID �������' ;
  COMMENT ON COLUMN USER_ACCOUNTS.SIGN_ACTIVE
IS
  '������� ����������' ;
  COMMENT ON COLUMN USER_ACCOUNTS.D_CREATE
IS
  '���� ��������' ;
  ALTER TABLE USER_ACCOUNTS ADD CONSTRAINT USER_ACCOUNTS_PK PRIMARY KEY ( ID_ACCOUNT ) ;

ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_CARS_FK FOREIGN KEY ( ID_CAR ) REFERENCES CARS ( ID_CAR ) ;

ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_DRIVERS_FK FOREIGN KEY ( ID_DRIVER ) REFERENCES DRIVERS ( ID_DRIVER ) ;

ALTER TABLE CARS ADD CONSTRAINT CARS_CAR_TYPES_FK FOREIGN KEY ( ID_CAR_TYPE ) REFERENCES CAR_TYPES ( ID_CAR_TYPE ) ;

ALTER TABLE CUSTOMERS ADD CONSTRAINT CUSTOMERS_PERSONS_FK FOREIGN KEY ( ID_CUSTOMER ) REFERENCES PERSONS ( ID_PERSON ) ;

ALTER TABLE DRIVERS ADD CONSTRAINT DRIVERS_PERSONS_FK FOREIGN KEY ( ID_DRIVER ) REFERENCES PERSONS ( ID_PERSON ) ;

ALTER TABLE ORDERS_TIMETABLE ADD CONSTRAINT ORADERS_TIMETABLE_ORDERS_FK FOREIGN KEY ( ID_ORDER ) REFERENCES ORDERS ( ID_ORDER ) ;

ALTER TABLE ORDERS_TIMETABLE ADD CONSTRAINT ORADERS_TIMETABLE_STATES_FK FOREIGN KEY ( ID_STATE ) REFERENCES ORDER_STATES ( ID_STATE ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_AUTOS_FK FOREIGN KEY ( ID_AUTO ) REFERENCES AUTOS ( ID_AUTO ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_CUSTOMERS_FK FOREIGN KEY ( ID_CUSTOMER ) REFERENCES CUSTOMERS ( ID_CUSTOMER ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ORDER_RESULTS_FK FOREIGN KEY ( ID_RESULT ) REFERENCES ORDER_RESULTS ( ID_RESULT ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ORDER_STATES_FK FOREIGN KEY ( CURRENT_STATE ) REFERENCES ORDER_STATES ( ID_STATE ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ROUTES_FK FOREIGN KEY ( ID_ROUTE ) REFERENCES ROUTES ( ID_ROUTE ) ;

ALTER TABLE POINTS ADD CONSTRAINT POINTS_STREETS_FK FOREIGN KEY ( ID_STREET ) REFERENCES STREETS ( ID_STREET ) ;

ALTER TABLE PRICES ADD CONSTRAINT PRICES_CAR_TYPES_FK FOREIGN KEY ( ID_CAR_TYPE ) REFERENCES CAR_TYPES ( ID_CAR_TYPE ) ;

ALTER TABLE ROUTES ADD CONSTRAINT ROUTES_POINTS_DST_FK FOREIGN KEY ( ID_DST_POINT ) REFERENCES POINTS ( ID_POINT ) ;

ALTER TABLE ROUTES ADD CONSTRAINT ROUTES_POINTS_SRC_FK FOREIGN KEY ( ID_SRC_POINT ) REFERENCES POINTS ( ID_POINT ) ;

ALTER TABLE STREETS ADD CONSTRAINT STREETS_CITIES_FK FOREIGN KEY ( ID_CITY ) REFERENCES CITIES ( ID_CITY ) ;

ALTER TABLE USER_ACCOUNTS ADD CONSTRAINT USER_ACCOUNTS_APP_ROLES_FK FOREIGN KEY ( USER_ROLE ) REFERENCES APP_ROLES ( ID_ROLE ) ;

ALTER TABLE USER_ACCOUNTS ADD CONSTRAINT USER_ACCOUNTS_PERSONS_FK FOREIGN KEY ( USER_PERSONE ) REFERENCES PERSONS ( ID_PERSON ) ;


-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            17
-- CREATE INDEX                             4
-- ALTER TABLE                             36
-- CREATE VIEW                              0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
