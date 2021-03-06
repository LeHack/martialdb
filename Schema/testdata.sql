/* Foreign keys are enabled, thus order is important! */

/* Test City data */
INSERT INTO "cities" VALUES(1,'Kołobrzeg');
INSERT INTO "cities" VALUES(2,'Kraków');

/* Test Group data */
INSERT INTO "training_group" VALUES(1,1,'10 - 8 kyu');
INSERT INTO "training_group" VALUES(2,1,'7 - 5 kyu');
INSERT INTO "training_group" VALUES(3,2,'4 kyu - dan');

/* Test Karateka data */
INSERT INTO "karateka" VALUES(1,3,'Jan','Kowalski','jan@kowalski.com','123456789','ul. Nie tutaj 5','Gdzieśtamtowo','DAN','1','2016-05-28','2000-01-01','true');
INSERT INTO "karateka" VALUES(2,2,'Marian','Nowak','marian@nowak.pl','876546271','ul. Testowa','Testowo','KYU','5','2016-05-28','1989-05-15','true');
INSERT INTO "karateka" VALUES(3,2,'Walery','Nietutejszy','walery@nietutejszy.pl',NULL,NULL,NULL,'KYU','2','2005-09-28','1991-04-01','false');
INSERT INTO "karateka" VALUES(4,1,'Zdzisław','Nowy','zdzislaw@nowy.pl',NULL,NULL,NULL,'KYU','9','2016-03-25','1981-07-12','true');

/* Test user data */
INSERT INTO "user" VALUES(1,'john','$2a$10$oWsUXmZJ4k3Dhyie/Vaf.eNjAq05XnWp7IlkLWDxKd6LzsAc77lNu','John','Wayne','john@wayne.com','ADMIN',1,'2016-05-28 11:42:21');
INSERT INTO "user" VALUES(2,'clint','$2a$10$8GUeoIdpnLdpNFXMGk9o3Oz7jKNlmAKj.87GlWUBWAiKgLMGhmEjW','Clint','Eastwood','clint@eastwood.com','ADMIN',1,'2016-05-28 11:44:28');
INSERT INTO "user" VALUES(3,'gary','$2a$10$VBqtT8b8ZsTpNzq8K00i6.01IwYHNcoRv0h/tQx.0650EOD8u.uqm','Gary','Cooper','gary@cooper.com','USER',2,'2016-05-28 11:46:41');

/* Test events data */
INSERT INTO "events" VALUES(1,2,'14ty staż w Krakowie','2016-05-14','SEMINAR');
INSERT INTO "events" VALUES(2,1,'40ty obóz sportowy w Zakopanem','2016-06-18','CAMP');
INSERT INTO "events" VALUES(3,2,'Pokaz na konwencie kultury Japońskiej','2016-08-13','SHOW');

/* Test presence data */
INSERT INTO "presence" VALUES(1, 1,'2016-05-09','WEEK',5,'BASIC');
INSERT INTO "presence" ("karateka_id", "start") VALUES (1,'2016-06-04'),(2,'2016-06-04'),(3,'2016-06-04'),(4,'2016-06-04');
INSERT INTO "presence" ("karateka_id", "count", "type", "start") VALUES (1,2,'EXTRA','2016-06-05'),(3,4,'EXTRA','2016-06-05');
INSERT INTO "presence" ("karateka_id") VALUES (1);
