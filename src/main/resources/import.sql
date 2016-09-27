insert into USER (email, password, username) values ('ak@silverpen.de', '53d199c78682a09cbaf6357c4bd1373b', 'Adrian')
insert into USER (email, password, username) values ('bb@silverpen.de', 'a211c2bfb1985e26bef175b2d4a0e609', 'Bjoern')
insert into USER (email, password, username) values ('je@silverpen.de', '014b4d5c3bd4bf5901a1849b3bad7bfd', 'Jonas')
insert into USER (email, password, username) values ('js@silverpen.de', '343645b62c534a1c58e963dfb5c32e46', 'Joachim')
insert into USER (email, password, username) values ('pm@silverpen.de', 'fa5b362289ae84b14259a86c746de106', 'Polihron')
insert into USER (email, password, username) values ('tb@silverpen.de', '4a9244c80f9ff1c2b55bb29b5dd75bc5', 'Thomas')
INSERT INTO USER (email, password, username) VALUES ('bjoern.baumgarten@pentasys.de', '50b37fd90bf7065731686ecf4f70ae3d', 'Björn')

insert into ROLE (id, rolename) values (1,'User')
insert into ROLE (id, rolename) values (2,'Admin')

insert into ROLE_USER (roles_id, users_email) values (1,'ak@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'bb@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'je@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'js@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'pm@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (2,'tb@silverpen.de')
INSERT INTO ROLE_USER (roles_id, users_email) VALUES (2,'bjoern.baumgarten@pentasys.de')

INSERT INTO PROJECT (projectnumber, name) VALUES ('TTL0003.INT', 'Internes Projekt')
INSERT INTO PROJECT (projectnumber, name) VALUES ('TTL0003.WTB', 'Weiterbildung')
INSERT INTO PROJECT (projectnumber, name) VALUES ('TTL0005.INT', 'Internes Projekt')
INSERT INTO PROJECT (projectnumber, name) VALUES ('TTL0005.WTB', 'Weiterbildung')

INSERT INTO PROJECT_USER (projects_id, users_email) VALUES (1,'bjoern.baumgarten@pentasys.de')
INSERT INTO PROJECT_USER (projects_id, users_email) VALUES (2,'bjoern.baumgarten@pentasys.de')
INSERT INTO PROJECT_USER (projects_id, users_email) VALUES (3,'tb@silverpen.de')
INSERT INTO PROJECT_USER (projects_id, users_email) VALUES (4,'tb@silverpen.de')
INSERT INTO PROJECT_USER (projects_id, users_email) VALUES (3,'ak@silverpen.de')
INSERT INTO PROJECT_USER (projects_id, users_email) VALUES (4,'ak@silverpen.de')


INSERT INTO USER (email, password, username) VALUES ('link@silverpen.de', '40258e72cf4267aa486cf479c5a1f252', 'Link')
INSERT INTO USERCONSTRAINT (id,pinDate,type,user_email) VALUES ('45f5b6b0-dc7e-4f48-8d62-4e898bc3d094','2016-08-22 16:17:03','LOGIN_CONFIRMATION','link@silverpen.de')
insert into ROLE_USER (roles_id, users_email) values (1,'link@silverpen.de')

INSERT INTO WORKSHOP (id, description, location, maxParticipants, organizer, start, status, stop, title, tutor) VALUES (1,'Die Geschichte', 'Springfield', '30', 'homer j. Simpson', '2017-01-01 00:00:00', 'Creation', '2017-01-01 00:00:00', 'Duff-Drink', 'SilverPenMaster')

