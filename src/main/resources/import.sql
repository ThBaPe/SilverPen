insert into USER (email, password, username) values ('ak@silverpen.de', '53d199c78682a09cbaf6357c4bd1373b', 'Adrian')
insert into USER (email, password, username) values ('bb@silverpen.de', 'a211c2bfb1985e26bef175b2d4a0e609', 'Björn')
insert into USER (email, password, username) values ('je@silverpen.de', '014b4d5c3bd4bf5901a1849b3bad7bfd', 'Jonas')
insert into USER (email, password, username) values ('js@silverpen.de', '343645b62c534a1c58e963dfb5c32e46', 'Joachim')
insert into USER (email, password, username) values ('pm@silverpen.de', 'fa5b362289ae84b14259a86c746de106', 'Polihron')
insert into USER (email, password, username) values ('tb@silverpen.de', '4a9244c80f9ff1c2b55bb29b5dd75bc5', 'Thomas')

insert into ROLE (id, rolename) values (1,'User')
insert into ROLE (id, rolename) values (1,'Admin')

insert into ROLE_USER (roles_id, users_email) values (1,'ak@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'bb@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'je@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'js@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'pm@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'tb@silverpen.de')


