SET search_path TO 'public';
SET search_path TO 'test';

--
-- Drop all tables
--
DROP TABLE APP_ROLE CASCADE;
DROP TABLE AUTOS CASCADE;
DROP TABLE CAR CASCADE;
DROP TABLE CARS_TYPE CASCADE;
DROP TABLE CITY CASCADE;
DROP TABLE CUSTOMER CASCADE;
DROP TABLE DRIVER CASCADE;
DROP TABLE ORDERS CASCADE;
DROP TABLE ORDERS_RESULT CASCADE;
DROP TABLE ORDERS_STATE CASCADE;
DROP TABLE ORDERS_TIMETABLE CASCADE;
DROP TABLE POINT CASCADE;
DROP TABLE PRICE CASCADE;
DROP TABLE ROUTE CASCADE;
DROP TABLE STREET CASCADE;
DROP TABLE USER_ACCOUNT CASCADE;
DROP TABLE USER_PROFILE CASCADE;
DROP TABLE USER_ROLE CASCADE;

--
-- Create tables and constraints
--

CREATE TABLE APP_ROLE
  (
    ID SERIAL NOT NULL ,
    NAME  CHARACTER VARYING (30) NOT NULL ,
    DESCR CHARACTER VARYING (200)
  )
;
COMMENT ON TABLE APP_ROLE
IS
  'Роли приложения' ;
  COMMENT ON COLUMN APP_ROLE.ID
IS
  'PK' ;
  COMMENT ON COLUMN APP_ROLE.NAME
IS
  'Имя роли' ;
  COMMENT ON COLUMN APP_ROLE.DESCR
IS
  'Описание обязанностей, прав роли' ;
  ALTER TABLE APP_ROLE ADD CONSTRAINT APP_ROLE_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE APP_ROLE ADD CONSTRAINT APP_ROLE_UK UNIQUE ( NAME ) ;

CREATE TABLE AUTOS
  (
    ID SERIAL NOT NULL ,
    CAR_ID       INTEGER NOT NULL ,
    DRIVER_ID    INTEGER NOT NULL ,
    R_WAITING    SMALLINT ,
    R_ROUTE      SMALLINT ,
    POSITION_LAT SMALLINT ,
    POSITION_LNG SMALLINT ,
    SIGN_ACTIVE  INTEGER ,
    D_CREATE     TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE AUTOS
IS
  'Список автомобилей готовых к выполнению заказов' ;
  COMMENT ON COLUMN AUTOS.ID
IS
  'PK' ;
  COMMENT ON COLUMN AUTOS.CAR_ID
IS
  'FK cars' ;
  COMMENT ON COLUMN AUTOS.DRIVER_ID
IS
  'FK drivers' ;
  COMMENT ON COLUMN AUTOS.R_WAITING
IS
  'Радиус ожидания, км' ;
  COMMENT ON COLUMN AUTOS.R_ROUTE
IS
  'Радиус выполнения заказа, км' ;
  COMMENT ON COLUMN AUTOS.POSITION_LAT
IS
  'Текущее положение авто (широта)' ;
  COMMENT ON COLUMN AUTOS.POSITION_LNG
IS
  'Текущее положение авто (долгота)' ;
  COMMENT ON COLUMN AUTOS.SIGN_ACTIVE
IS
  'Признак участия в исполнении заказа' ;
  COMMENT ON COLUMN AUTOS.D_CREATE
IS
  'Дата создания записи' ;
  ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_UK UNIQUE ( CAR_ID , DRIVER_ID ) ;

CREATE TABLE CAR
  (
    ID SERIAL NOT NULL ,
    REG_NUM          CHARACTER VARYING (10) ,
    SEATS_QUAN       INTEGER NOT NULL ,
    CHILD_SEATS_QUAN INTEGER ,
    CARS_TYPE_ID     INTEGER NOT NULL ,
    SIGN_ACTIVE      INTEGER ,
    D_CREATE         TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE CAR
IS
  'Справочник транспортных средств' ;
  COMMENT ON COLUMN CAR.ID
IS
  'PK' ;
  COMMENT ON COLUMN CAR.REG_NUM
IS
  'Регистрационный номер' ;
  COMMENT ON COLUMN CAR.SEATS_QUAN
IS
  'Количество пассажирских мест' ;
  COMMENT ON COLUMN CAR.CHILD_SEATS_QUAN
IS
  'Кол-во детских сидений' ;
  COMMENT ON COLUMN CAR.CARS_TYPE_ID
IS
  'Тип авто' ;
  COMMENT ON COLUMN CAR.SIGN_ACTIVE
IS
  'Признак используемости авто' ;
  COMMENT ON COLUMN CAR.D_CREATE
IS
  'Дата создания записи' ;
  ALTER TABLE CAR ADD CONSTRAINT CAR_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE CAR ADD CONSTRAINT CAR_UK UNIQUE ( REG_NUM ) ;

CREATE TABLE CARS_TYPE
  ( ID SERIAL NOT NULL , NAME CHARACTER VARYING (30)
  );
COMMENT ON TABLE CARS_TYPE
IS
  'Типы авто' ;
  COMMENT ON COLUMN CARS_TYPE.ID
IS
  'PK' ;
  COMMENT ON COLUMN CARS_TYPE.NAME
IS
  'Название типа' ;
  ALTER TABLE CARS_TYPE ADD CONSTRAINT CARS_TYPE_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE CARS_TYPE ADD CONSTRAINT CARS_TYPE_UK UNIQUE ( NAME ) ;

CREATE TABLE CITY
  ( ID SERIAL NOT NULL , NAME CHARACTER VARYING (50)
  );
COMMENT ON TABLE CITY
IS
  'Справочник населённых пунктов' ;
  COMMENT ON COLUMN CITY.ID
IS
  'PK' ;
  COMMENT ON COLUMN CITY.NAME
IS
  'Название населённого пункта' ;
  ALTER TABLE CITY ADD CONSTRAINT CITIES_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE CITY ADD CONSTRAINT CITY_UK UNIQUE ( NAME ) ;

CREATE TABLE CUSTOMER
  (
    ID SERIAL NOT NULL ,
    AVG_RATING  INTEGER ,
    SIGN_ACTIVE INTEGER ,
    D_CREATE    TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE CUSTOMER
IS
  'Справочник заказчиков' ;
  COMMENT ON COLUMN CUSTOMER.ID
IS
  'PK' ;
  COMMENT ON COLUMN CUSTOMER.AVG_RATING
IS
  'Средняя оценка заказчику' ;
  COMMENT ON COLUMN CUSTOMER.SIGN_ACTIVE
IS
  'Признак активности водителя в системе' ;
  COMMENT ON COLUMN CUSTOMER.D_CREATE
IS
  'Дата создания записи' ;
  ALTER TABLE CUSTOMER ADD CONSTRAINT CUSTOMER_PK PRIMARY KEY ( ID ) ;

CREATE TABLE DRIVER
  (
    ID SERIAL NOT NULL ,
    AVG_RATING  INTEGER ,
    SIGN_ACTIVE INTEGER ,
    D_CREATE    TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE DRIVER
IS
  'Справочник водителей' ;
  COMMENT ON COLUMN DRIVER.ID
IS
  'PK' ;
  COMMENT ON COLUMN DRIVER.AVG_RATING
IS
  'Средняя оценка водителю' ;
  COMMENT ON COLUMN DRIVER.SIGN_ACTIVE
IS
  'Признак активности водителя в системе' ;
  COMMENT ON COLUMN DRIVER.D_CREATE
IS
  'Дата создания записи' ;
  ALTER TABLE DRIVER ADD CONSTRAINT DRIVER_PK PRIMARY KEY ( ID ) ;

CREATE TABLE ORDERS
  (
    ID SERIAL NOT NULL ,
    CUSTOMER_ID     INTEGER NOT NULL ,
    ROUTE_ID        INTEGER NOT NULL ,
    AUTOS_ID        INTEGER ,
    PRICE_ID        INTEGER NOT NULL ,
    ORDERS_STATE_ID INTEGER NOT NULL ,
    RESULT_ID       INTEGER NOT NULL ,
    ROUTE_LENGTH    INTEGER ,
    ORDER_PRICE     INTEGER ,
    CUSTOMER_RATING INTEGER ,
    DRIVER_RATING   INTEGER ,
    D_CREATE        TIMESTAMP WITHOUT TIME ZONE NOT NULL
  )
;
COMMENT ON TABLE ORDERS
IS
  'Заказы' ;
  COMMENT ON COLUMN ORDERS.ID
IS
  'PK' ;
  COMMENT ON COLUMN ORDERS.CUSTOMER_ID
IS
  'Заказчик' ;
  COMMENT ON COLUMN ORDERS.ROUTE_ID
IS
  'Маршрут следования' ;
  COMMENT ON COLUMN ORDERS.AUTOS_ID
IS
  'Исполнитель заказа' ;
  COMMENT ON COLUMN ORDERS.PRICE_ID
IS
  'Цены' ;
  COMMENT ON COLUMN ORDERS.ORDERS_STATE_ID
IS
  'Состояние заказа: новый, принят, ожидание пассажира, выполнен' ;
  COMMENT ON COLUMN ORDERS.RESULT_ID
IS
  'Результат: заказ принят, заказ выполнен, заказ отменён' ;
  COMMENT ON COLUMN ORDERS.ROUTE_LENGTH
IS
  'Длина маршрута' ;
  COMMENT ON COLUMN ORDERS.ORDER_PRICE
IS
  'Стоимость заказа' ;
  COMMENT ON COLUMN ORDERS.CUSTOMER_RATING
IS
  'Оценка заказчику' ;
  COMMENT ON COLUMN ORDERS.DRIVER_RATING
IS
  'Оценка водителю' ;
  COMMENT ON COLUMN ORDERS.D_CREATE
IS
  'Дата создания записи' ;
  ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_PK PRIMARY KEY ( ID ) ;

CREATE TABLE ORDERS_RESULT
  (
    ID SERIAL NOT NULL ,
    NAME  CHARACTER VARYING (50) NOT NULL ,
    DESCR CHARACTER VARYING (200)
  )
;
COMMENT ON TABLE ORDERS_RESULT
IS
  'Результат исполнения заказа' ;
  COMMENT ON COLUMN ORDERS_RESULT.ID
IS
  'PK' ;
  COMMENT ON COLUMN ORDERS_RESULT.NAME
IS
  'Наименование' ;
  COMMENT ON COLUMN ORDERS_RESULT.DESCR
IS
  'Подробное описание' ;
  ALTER TABLE ORDERS_RESULT ADD CONSTRAINT ORDER_RESULTS_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE ORDERS_RESULT ADD CONSTRAINT ORDERS_RESULT_UK UNIQUE ( NAME ) ;

CREATE TABLE ORDERS_STATE
  (
    ID SERIAL NOT NULL ,
    NAME  CHARACTER VARYING (30) ,
    DESCR CHARACTER VARYING (100)
  )
;
COMMENT ON TABLE ORDERS_STATE
IS
  'Состояния заказа' ;
  COMMENT ON COLUMN ORDERS_STATE.ID
IS
  'PK' ;
  COMMENT ON COLUMN ORDERS_STATE.NAME
IS
  'Описание состояния заказа' ;
  COMMENT ON COLUMN ORDERS_STATE.DESCR
IS
  'Подробное описание' ;
  ALTER TABLE ORDERS_STATE ADD CONSTRAINT ORDERS_STATE_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE ORDERS_STATE ADD CONSTRAINT ORDERS_STATE_UK UNIQUE ( NAME ) ;

CREATE TABLE ORDERS_TIMETABLE
  (
    ID SERIAL NOT NULL ,
    ORDERS_ID       INTEGER NOT NULL ,
    ORDERS_STATE_ID INTEGER NOT NULL ,
    D_CREATE        TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE ORDERS_TIMETABLE
IS
  'График выполнения заказа' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.ID
IS
  'PK' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.ORDERS_ID
IS
  'ID заказа' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.ORDERS_STATE_ID
IS
  'Состояние заказа' ;
  COMMENT ON COLUMN ORDERS_TIMETABLE.D_CREATE
IS
  'Дата проставления состояния' ;
  ALTER TABLE ORDERS_TIMETABLE ADD CONSTRAINT ORADERS_TIMETABLE_PK PRIMARY KEY ( ID ) ;

CREATE TABLE POINT
  (
    ID SERIAL NOT NULL ,
    STREET_ID INTEGER NOT NULL ,
    HOUSE_NUM CHARACTER VARYING (10) ,
    HOUSE_LOC INTEGER ,
    POINT_LAT SMALLINT ,
    POINT_LNG SMALLINT
  )
;
COMMENT ON TABLE POINT
IS
  'Справочник пунктов назначений' ;
  COMMENT ON COLUMN POINT.ID
IS
  'PK' ;
  COMMENT ON COLUMN POINT.STREET_ID
IS
  'FK Улица' ;
  COMMENT ON COLUMN POINT.HOUSE_NUM
IS
  'Номер дома/корпуса' ;
  COMMENT ON COLUMN POINT.HOUSE_LOC
IS
  'Номер подъезда' ;
  COMMENT ON COLUMN POINT.POINT_LAT
IS
  'Положение на карте: Широта [-90, 90]' ;
  COMMENT ON COLUMN POINT.POINT_LNG
IS
  'Положение на карте: Долгота [-180, 180]' ;
  ALTER TABLE POINT ADD CONSTRAINT POINTS_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE POINT ADD CONSTRAINT POINT_UK UNIQUE ( STREET_ID , HOUSE_NUM , HOUSE_LOC ) ;

CREATE TABLE PRICE
  (
    ID SERIAL NOT NULL ,
    CARS_TYPE_ID    INTEGER NOT NULL ,
    COST_BEFORE     INTEGER ,
    COST_KM         INTEGER ,
    COST_PER_MINUTE INTEGER ,
    D_BEGIN         TIMESTAMP WITHOUT TIME ZONE ,
    D_END           TIMESTAMP WITHOUT TIME ZONE ,
    D_CREATE        TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE PRICE
IS
  'Цены на услуги' ;
  COMMENT ON COLUMN PRICE.ID
IS
  'PK' ;
  COMMENT ON COLUMN PRICE.CARS_TYPE_ID
IS
  'Тип авто' ;
  COMMENT ON COLUMN PRICE.COST_BEFORE
IS
  'Цена посадки' ;
  COMMENT ON COLUMN PRICE.COST_KM
IS
  'Цена за 1 км' ;
  COMMENT ON COLUMN PRICE.COST_PER_MINUTE
IS
  'Цена ожидания за минуту' ;
  COMMENT ON COLUMN PRICE.D_BEGIN
IS
  'Действует с' ;
  COMMENT ON COLUMN PRICE.D_END
IS
  'Действует по' ;
  COMMENT ON COLUMN PRICE.D_CREATE
IS
  'Дата создания' ;
  ALTER TABLE PRICE ADD CONSTRAINT PRICE_PK PRIMARY KEY ( ID ) ;

CREATE TABLE ROUTE
  (
    ID SERIAL NOT NULL ,
    SRC_POINT_ID INTEGER NOT NULL ,
    DST_POINT_ID INTEGER NOT NULL ,
    EST_LENGTH   INTEGER ,
    FACT_LENGTH  INTEGER ,
    EST_TIME     INTEGER
  )
;
COMMENT ON TABLE ROUTE
IS
  'Маршрут следования' ;
  COMMENT ON COLUMN ROUTE.ID
IS
  'PK' ;
  COMMENT ON COLUMN ROUTE.SRC_POINT_ID
IS
  'Начальный пункт' ;
  COMMENT ON COLUMN ROUTE.DST_POINT_ID
IS
  'Конечный пункт' ;
  COMMENT ON COLUMN ROUTE.EST_LENGTH
IS
  'Планируемая Длина пути' ;
  COMMENT ON COLUMN ROUTE.FACT_LENGTH
IS
  'Фактическая Длина пути' ;
  COMMENT ON COLUMN ROUTE.EST_TIME
IS
  'Планируемое время (минут)' ;
  ALTER TABLE ROUTE ADD CONSTRAINT ROUTES_PK PRIMARY KEY ( ID ) ;

CREATE TABLE STREET
  (
    ID SERIAL NOT NULL ,
    NAME    CHARACTER VARYING (50) ,
    CITY_ID INTEGER NOT NULL
  )
;
COMMENT ON TABLE STREET
IS
  'Справочник улиц' ;
  COMMENT ON COLUMN STREET.ID
IS
  'PK' ;
  COMMENT ON COLUMN STREET.NAME
IS
  'Название улицы' ;
  COMMENT ON COLUMN STREET.CITY_ID
IS
  'FK Населённый пункт' ;
  ALTER TABLE STREET ADD CONSTRAINT STREET_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE STREET ADD CONSTRAINT STREET_UK UNIQUE ( CITY_ID , NAME ) ;

CREATE TABLE USER_ACCOUNT
  (
    ID SERIAL NOT NULL ,
    NAME     CHARACTER VARYING (30) NOT NULL ,
    PASSWORD CHARACTER VARYING (128) NOT NULL ,
    EMAIL    CHARACTER VARYING (255) NOT NULL ,
    D_CREATE   TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE USER_ACCOUNT
IS
  'Справочник аккаунтов' ;
  COMMENT ON COLUMN USER_ACCOUNT.ID
IS
  'PK' ;
  COMMENT ON COLUMN USER_ACCOUNT.NAME
IS
  'Логин пользователя' ;
  COMMENT ON COLUMN USER_ACCOUNT.PASSWORD
IS
  'Пароль (HASH)' ;
  COMMENT ON COLUMN USER_ACCOUNT.EMAIL
IS
  'Контактный email' ;
  COMMENT ON COLUMN USER_ACCOUNT.D_CREATE
IS
  'Дата создания' ;
  ALTER TABLE USER_ACCOUNT ADD CONSTRAINT USER_ACCOUNT_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE USER_ACCOUNT ADD CONSTRAINT USER_ACCOUNT_UK UNIQUE ( NAME , EMAIL ) ;

CREATE TABLE USER_PROFILE
  (
    ID SERIAL NOT NULL ,
    FIRST_NAME CHARACTER VARYING (30) NOT NULL ,
    LAST_NAME  CHARACTER VARYING (30) ,
    TEL_NUM    CHARACTER VARYING (30) NOT NULL ,
    D_CREATE   TIMESTAMP WITHOUT TIME ZONE
  )
;
COMMENT ON TABLE USER_PROFILE
IS
  'Справочник персон' ;
  COMMENT ON COLUMN USER_PROFILE.ID
IS
  'PK' ;
  COMMENT ON COLUMN USER_PROFILE.FIRST_NAME
IS
  'Имя' ;
  COMMENT ON COLUMN USER_PROFILE.LAST_NAME
IS
  'Фамилия' ;
  COMMENT ON COLUMN USER_PROFILE.TEL_NUM
IS
  'Контактный телефон' ;
  COMMENT ON COLUMN USER_PROFILE.D_CREATE
IS
  'Дата создания записи' ;
  ALTER TABLE USER_PROFILE ADD CONSTRAINT PERSON_PK PRIMARY KEY ( ID ) ;
  ALTER TABLE USER_PROFILE ADD CONSTRAINT USER_PROFILE_UK UNIQUE ( TEL_NUM ) ;

CREATE TABLE USER_ROLE
  (
    USER_ACCOUNT_ID INTEGER,
    USER_PROFILE_ID INTEGER NOT NULL ,
    APP_ROLE_ID     INTEGER NOT NULL
  )
;
COMMENT ON TABLE USER_ROLE
IS
  'Роли пользователя' ;
  COMMENT ON COLUMN USER_ROLE.USER_ACCOUNT_ID
IS
  'Аккаунт' ;
  COMMENT ON COLUMN USER_ROLE.USER_PROFILE_ID
IS
  'Пользователь' ;
  COMMENT ON COLUMN USER_ROLE.APP_ROLE_ID
IS
  'Роль' ;
  ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_UNIQ UNIQUE ( USER_PROFILE_ID , APP_ROLE_ID , USER_ACCOUNT_ID ) ;

ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_CAR_FK FOREIGN KEY ( CAR_ID ) REFERENCES CAR ( ID ) ;

ALTER TABLE AUTOS ADD CONSTRAINT AUTOS_DRIVER_FK FOREIGN KEY ( DRIVER_ID ) REFERENCES DRIVER ( ID ) ;

ALTER TABLE CAR ADD CONSTRAINT CAR_CARS_TYPE_FK FOREIGN KEY ( CARS_TYPE_ID ) REFERENCES CARS_TYPE ( ID ) ;

ALTER TABLE CUSTOMER ADD CONSTRAINT CUSTOMER_USER_PROFILE_FK FOREIGN KEY ( ID ) REFERENCES USER_PROFILE ( ID ) ;

ALTER TABLE DRIVER ADD CONSTRAINT DRIVER_USER_PROFILE_FK FOREIGN KEY ( ID ) REFERENCES USER_PROFILE ( ID ) ;

ALTER TABLE ORDERS_TIMETABLE ADD CONSTRAINT ORADERS_TIMETABLE_ORDER_FK FOREIGN KEY ( ORDERS_ID ) REFERENCES ORDERS ( ID ) ;

ALTER TABLE ORDERS_TIMETABLE ADD CONSTRAINT ORADERS_TIMETABLE_STATE_FK FOREIGN KEY ( ORDERS_STATE_ID ) REFERENCES ORDERS_STATE ( ID ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_AUTOS_FK FOREIGN KEY ( AUTOS_ID ) REFERENCES AUTOS ( ID ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_CUSTOMER_FK FOREIGN KEY ( CUSTOMER_ID ) REFERENCES CUSTOMER ( ID ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ORDERS_RESULT_FK FOREIGN KEY ( RESULT_ID ) REFERENCES ORDERS_RESULT ( ID ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ORDERS_STATE_FK FOREIGN KEY ( ORDERS_STATE_ID ) REFERENCES ORDERS_STATE ( ID ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_PRICE_FK FOREIGN KEY ( PRICE_ID ) REFERENCES PRICE ( ID ) ;

ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_ROUTE_FK FOREIGN KEY ( ROUTE_ID ) REFERENCES ROUTE ( ID ) ;

ALTER TABLE POINT ADD CONSTRAINT POINTS_STREETS_FK FOREIGN KEY ( STREET_ID ) REFERENCES STREET ( ID ) ;

ALTER TABLE PRICE ADD CONSTRAINT PRICE_CARS_TYPE_FK FOREIGN KEY ( CARS_TYPE_ID ) REFERENCES CARS_TYPE ( ID ) ;

ALTER TABLE ROUTE ADD CONSTRAINT ROUTE_POINT_DST_FK FOREIGN KEY ( DST_POINT_ID ) REFERENCES POINT ( ID ) ;

ALTER TABLE ROUTE ADD CONSTRAINT ROUTE_POINT_SRC_FK FOREIGN KEY ( SRC_POINT_ID ) REFERENCES POINT ( ID ) ;

ALTER TABLE STREET ADD CONSTRAINT STREET_CITY_FK FOREIGN KEY ( CITY_ID ) REFERENCES CITY ( ID ) ;

ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_APP_ROLE_FK FOREIGN KEY ( APP_ROLE_ID ) REFERENCES APP_ROLE ( ID ) ;

ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_USER_ACCOUNT_FK FOREIGN KEY ( USER_ACCOUNT_ID ) REFERENCES USER_ACCOUNT ( ID ) ;

ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_USER_PROFILE_FK FOREIGN KEY ( USER_PROFILE_ID ) REFERENCES USER_PROFILE ( ID ) ;


-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            18
-- ALTER TABLE                             50
