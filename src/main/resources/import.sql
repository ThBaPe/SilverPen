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

INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (1,	'Java for Web', 			'SilverPenMaster', 'Hugo Simpson',				'FFM', '15',  '2017-01-02 09:00:00', 'Creation', '2017-01-02 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (2,	'UI - UX', 					'SilverPenMaster', 'Abraham Simpson',			'FFM', '15',  '2017-01-03 09:00:00', 'Creation', '2017-01-03 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (3,	'Angular JS 2.0', 			'SilverPenMaster', 'Üter Zörker',				'FFM', '15',  '2017-01-04 09:00:00', 'Creation', '2017-01-04 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (4,	'Product Owner', 			'SilverPenMaster', 'Jeremy Freedman',			'FFM', '15',  '2017-01-05 09:00:00', 'Creation', '2017-01-05 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (5,	'Visual Thinking', 			'SilverPenMaster', 'El Barto',					'FFM', '15',  '2017-01-06 09:00:00', 'Creation', '2017-01-06 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (6,	'Graphical Recording', 		'SilverPenMaster', 'Adil Hoxha',				'FFM', '15',  '2017-01-09 09:00:00', 'Creation', '2017-01-09 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (7,	'Retailbanking', 			'SilverPenMaster', 'Kearney Zzyzwicz',			'FFM', '15',  '2017-01-11 09:00:00', 'Creation', '2017-01-11 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (8,	'Moderation technischer', 	'SilverPenMaster', 'Luke Perry',				'FFM', '15',  '2017-01-12 09:00:00', 'Creation', '2017-01-12 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (9,	'Java 1.3', 				'SilverPenMaster', 'Rabbi Hyman Krustofsky',	'FFM', '15',  '2017-01-13 09:00:00', 'Creation', '2017-01-13 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (10,	'Java 1.4', 				'SilverPenMaster', 'Sherri Mackleberry',		'FFM', '15',  '2017-01-16 09:00:00', 'Creation', '2017-01-16 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (11,	'JEE 5', 					'SilverPenMaster', 'Terri Mackleberry',			'FFM', '15',  '2017-01-17 09:00:00', 'Creation', '2017-01-17 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')
INSERT INTO WORKSHOP (id, title, organizer,tutor,location, maxParticipants,  start, status, stop, description) VALUES (12,	'JEE 7', 					'SilverPenMaster', 'Mr. Mackleberry ',			'FFM', '15',  '2017-01-18 09:00:00', 'Creation', '2017-01-18 17:00:00', 'Die im März 2014 veröffentliche Java-Version wird inzwischen...')

INSERT INTO WORKSHOP_USER (id, Role, State, users_email) VALUES (1, 'PARTICIPANT', 'REQUESTED', 'tb@silverpen.de');
INSERT INTO WORKSHOP_USER (id, Role, State, users_email) VALUES (2, 'PARTICIPANT', 'REQUESTED', 'tb@silverpen.de');
INSERT INTO WORKSHOP_USER (id, Role, State, users_email) VALUES (3, 'PARTICIPANT', 'REQUESTED', 'tb@silverpen.de');
INSERT INTO WORKSHOP_USER (id, Role, State, users_email) VALUES (4, 'PARTICIPANT', 'REQUESTED', 'tb@silverpen.de');
INSERT INTO WORKSHOP_USER (id, Role, State, users_email) VALUES (5, 'PARTICIPANT', 'REQUESTED', 'tb@silverpen.de');

INSERT INTO WORKSHOP_WORKSHOP_USER (Workshop_id, participant_id) VALUES (1,1);
INSERT INTO WORKSHOP_WORKSHOP_USER (Workshop_id, participant_id) VALUES (3,2);
INSERT INTO WORKSHOP_WORKSHOP_USER (Workshop_id, participant_id) VALUES (7,3);
INSERT INTO WORKSHOP_WORKSHOP_USER (Workshop_id, participant_id) VALUES (10,4);
INSERT INTO WORKSHOP_WORKSHOP_USER (Workshop_id, participant_id) VALUES (12,5);
