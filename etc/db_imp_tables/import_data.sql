select i.street_name, count(*) from test.imp_street i group by i.street_name having count(*) > 1;
select p.point_name, (select s.street_name from test.imp_streets s where s.street_name = p.street_name)
  from test.imp_point_street p;
select * from test.imp_street s order by s.street_name;
delete from test.imp_street where street_name is null;
create table test.imp_streets as select street_name from test.imp_street i group by i.street_name;

select * from public.city;
insert into public.city(name) values ('Гродно');

select * from public.street s;
select s.street_name, (select min(c.id) from public.city c) as city_id from test.imp_streets s;
insert into public.street (name, city_id)
select s.street_name, (select min(c.id) from public.city c) as city_id from test.imp_streets s;

select * from public.point s;
insert into public.point (house_num, street_id)
select p.point_name, 
       (select s.id from public.street s where s.name = p.street_name) as street_id
  from test.imp_point_street p;
