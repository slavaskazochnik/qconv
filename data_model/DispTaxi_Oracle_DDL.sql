-- Generated by Oracle SQL Developer Data Modeler 4.0.3.853
--   at:        2015-02-28 17:57:30 BRT
--   site:      Oracle9i
--   type:      Oracle9i




CREATE TABLE AUTOS
  (
    ID_AUTO     INTEGER NOT NULL ,
    ID_CAR      INTEGER NOT NULL ,
    ID_DRIVER   INTEGER NOT NULL ,
    R_WAITING   SMALLINT ,
    R_ROUTE     SMALLINT ,
    SIGN_ACTIVE INTEGER ,
    D_CREATE    DATE
  )
  LOGGING ;
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
    SIGN_ACTIVE INTEGER ,
    D_CREATE    DATE
  )
  LOGGING ;
CREATE INDEX CARS_UK ON CARS
  ( CAR_REG_NUM ASC
  ) ;
ALTER TABLE CARS ADD CONSTRAINT CARS_PK PRIMARY KEY ( ID_CAR ) ;

CREATE TABLE CITIES
  (
    ID_CITY   INTEGER NOT NULL ,
    CITY_NAME VARCHAR2 (50)
  )
  LOGGING ;
ALTER TABLE CITIES ADD CONSTRAINT CITIES_PK PRIMARY KEY ( ID_CITY ) ;

CREATE TABLE DRIVERS
  (
    ID_DRIVER   INTEGER NOT NULL ,
    SIGN_ACTIVE INTEGER ,
    D_CREATE    DATE
  )
  LOGGING ;
ALTER TABLE DRIVERS ADD CONSTRAINT DRIVERS_PK PRIMARY KEY ( ID_DRIVER ) ;

CREATE TABLE ORDERS
  (
    ID_ORDER    INTEGER NOT NULL ,
    ID_CUSTOMER INTEGER NOT NULL ,
    ID_ROUTE    INTEGER NOT NULL ,
    ID_AUTO     INTEGER NOT NULL ,
    ID_STATE    INTEGER NOT NULL ,
    ID_RESULT   INTEGER NOT NULL ,
    D_CREATE    DATE NOT NULL
  )
  LOGGING ;
ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_PK PRIMARY KEY ( ID_ORDER ) ;

CREATE TABLE ORDER_RESULTS
  (
    ID_RESULT   INTEGER NOT NULL ,
    RESULT_NAME VARCHAR2 (50)
  )
  LOGGING ;
ALTER TABLE ORDER_RESULTS ADD CONSTRAINT ORDER_RESULTS_PK PRIMARY KEY ( ID_RESULT ) ;

CREATE TABLE ORDER_STATES
  (
    ID_STATE    INTEGER NOT NULL ,
    STATE_NAME  VARCHAR2 (30) ,
    STATE_DESCR VARCHAR2 (100)
  )
  LOGGING ;
ALTER TABLE ORDER_STATES ADD CONSTRAINT ORDER_STATES_PK PRIMARY KEY ( ID_STATE ) ;

CREATE TABLE PERSONS
  (
    ID_PERSON   INTEGER NOT NULL ,
    PERSON_NAME VARCHAR2 (30) ,
    FIRST_NAME  VARCHAR2 (30) ,
    LAST_NAME   VARCHAR2 (30) ,
    TEL_NUM     VARCHAR2 (30) ,
    D_CREATE    DATE
  )
  LOGGING ;
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
ALTER TABLE POINTS ADD CONSTRAINT POINTS_PK PRIMARY KEY ( ID_POINT ) ;

CREATE TABLE ROUTES
  (
    ID_ROUTE     INTEGER NOT NULL ,
    ID_SRC_POINT INTEGER NOT NULL ,
    ID_DST_POINT INTEGER NOT NULL
  )
  LOGGING ;
ALTER TABLE ROUTES ADD CONSTRAINT ROUTES_PK PRIMARY KEY ( ID_ROUTE ) ;

CREATE TABLE STREETS
  (
    ID_STREET   INTEGER NOT NULL ,
    STREET_NAME VARCHAR2 (50) ,
    ID_CITY     INTEGER NOT NULL
  )
  LOGGING ;
ALTER TABLE STREETS ADD CONSTRAINT STREETS_PK PRIMARY KEY ( ID_STREET ) ;

ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_CARS_FK FOREIGN KEY ( ID_CAR ) REFERENCES CARS ( ID_CAR ) ;

ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_DRIVERS_FK FOREIGN KEY ( ID_DRIVER ) REFERENCES DRIVERS ( ID_DRIVER ) ;

ALTER TABLE DRIVERS ADD CONSTRAINT DRIVERS_PERSONS_FK FOREIGN KEY ( ID_DRIVER ) REFERENCES PERSONS ( ID_PERSON ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_AUTOS_FK FOREIGN KEY ( ID_AUTO ) REFERENCES AUTOS ( ID_AUTO ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ORDER_RESULTS_FK FOREIGN KEY ( ID_RESULT ) REFERENCES ORDER_RESULTS ( ID_RESULT ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ORDER_STATES_FK FOREIGN KEY ( ID_STATE ) REFERENCES ORDER_STATES ( ID_STATE ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_PERSONS_FK FOREIGN KEY ( ID_CUSTOMER ) REFERENCES PERSONS ( ID_PERSON ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ROUTES_FK FOREIGN KEY ( ID_ROUTE ) REFERENCES ROUTES ( ID_ROUTE ) ;

ALTER TABLE POINTS ADD CONSTRAINT POINTS_STREETS_FK FOREIGN KEY ( ID_STREET ) REFERENCES STREETS ( ID_STREET ) ;

ALTER TABLE ROUTES ADD CONSTRAINT ROUTES_POINTS_DST_FK FOREIGN KEY ( ID_DST_POINT ) REFERENCES POINTS ( ID_POINT ) ;

ALTER TABLE ROUTES ADD CONSTRAINT ROUTES_POINTS_SRC_FK FOREIGN KEY ( ID_SRC_POINT ) REFERENCES POINTS ( ID_POINT ) ;

ALTER TABLE STREETS ADD CONSTRAINT STREETS_CITIES_FK FOREIGN KEY ( ID_CITY ) REFERENCES CITIES ( ID_CITY ) ;


-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            11
-- CREATE INDEX                             3
-- ALTER TABLE                             23
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
