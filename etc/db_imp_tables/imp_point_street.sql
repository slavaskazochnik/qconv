DROP TABLE test.imp_point_street;

CREATE TABLE test.imp_point_street
(
  point_name character varying(30), -- Номер дома
  street_name character varying(200) -- Улица
)
WITH (
  OIDS=FALSE
);
ALTER TABLE test.imp_point_street
  OWNER TO disp;
COMMENT ON COLUMN test.imp_point_street.point_name IS 'Номер дома';
COMMENT ON COLUMN test.imp_point_street.street_name IS 'Улица';

