select count(*) over (), * from test.user_role;
select count(*) over (), * from test.user_account;

select count(*) over (), * from test.user_profile;
select count(*) over (), * from test.customer;
select count(*) over (), * from test.driver d;
select count(*) over (), * from test.car;
select count(*) over (), * from test.autos a;
select count(*) over (), * from test.price;
select count(*) over (), * from test.orders o;

select count(*) over (), * from test.city;
select count(*) over (), * from test.street;
select count(*) over (), * from test.point;

select * from test.autos a join test.car c on (a.car_id = c.id) where a.sign_active = 1 and c.car_type = 0;

select count(*) over (), * from test.route;
select d.* from test.orders o join test.autos a on (a.id = o.autos_id) join test.driver d on (d.id = a.driver_id);