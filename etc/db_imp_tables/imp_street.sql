DROP TABLE test.imp_street;

CREATE TABLE test.imp_street
(
  street_name character varying(200) -- Улица
)
WITH (
  OIDS=FALSE
);
ALTER TABLE test.imp_street
  OWNER TO disp;
COMMENT ON COLUMN test.imp_street.street_name IS 'Улица';


-- после загрузки из imp_street.csv убрать пустые (в тексте для глаза как разделители)
delete from test.imp_street where street_name is null;