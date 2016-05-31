/* Test user data */
INSERT INTO "user" VALUES(1,'john','$2a$10$oWsUXmZJ4k3Dhyie/Vaf.eNjAq05XnWp7IlkLWDxKd6LzsAc77lNu','John','Wayne','john@wayne.com','ADMIN','Utah','2016-05-28 11:42:21');
INSERT INTO "user" VALUES(2,'clint','$2a$10$8GUeoIdpnLdpNFXMGk9o3Oz7jKNlmAKj.87GlWUBWAiKgLMGhmEjW','Clint','Eastwood','clint@eastwood.com','ADMIN','Los Angeles','2016-05-28 11:44:28');
INSERT INTO "user" VALUES(3,'gary','$2a$10$VBqtT8b8ZsTpNzq8K00i6.01IwYHNcoRv0h/tQx.0650EOD8u.uqm','Gary','Cooper','gary@cooper.com','USER','Hadleyville','2016-05-28 11:46:41');

/* Test Karateka data */
INSERT INTO "karateka" VALUES(1,3,'Jan','Kowalski','jan@kowalski.com','123456789','ul. Nie tutaj 5','Gdzieśtamtowo','DAN','1','2016-05-28','2000-01-01','true');
INSERT INTO "karateka" VALUES(2,2,'Marian','Nowak','marian@nowak.pl','876546271','ul. Testowa','Testowo','KYU','5','2016-05-28','1989-05-15','true');
INSERT INTO "karateka" VALUES(3,2,'Walery','Nietutejszy','walery@nietutejszy.pl',NULL,NULL,NULL,'KYU','2','2005-09-28','1991-04-01','false');
INSERT INTO "karateka" VALUES(4,1,'Zdzisław','Nowy','zdzislaw@nowy.pl',NULL,NULL,NULL,'KYU','9','2016-03-25','1981-07-12','true');

/* Test Group data */
INSERT INTO "training_group" VALUES(1,1,'10 - 8 kyu');
INSERT INTO "training_group" VALUES(2,1,'7 - 5 kyu');
INSERT INTO "training_group" VALUES(3,2,'4 kyu - dan');
