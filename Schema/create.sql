CREATE TABLE IF NOT EXISTS "user" (
    "id" INTEGER PRIMARY KEY NOT NULL UNIQUE,
    "login" VARCHAR NOT NULL,
    "pass" VARCHAR NOT NULL,
    "name" VARCHAR NOT NULL,
    "surname" VARCHAR NOT NULL,
    "email" VARCHAR NOT NULL,
    "role" VARCHAR NOT NULL,
    "defaultCity" VARCHAR NOT NULL,
    "stamp" DATETIME DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "karateka" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,
    "group_id" INTEGER NOT NULL,
    "name" VARCHAR NOT NULL,
    "surname" VARCHAR NOT NULL,
    "email" VARCHAR NOT NULL,
    "telephone" VARCHAR,
    "address" VARCHAR,
    "city" VARCHAR,
    "rank_type" VARCHAR NOT NULL,
    "rank_level" VARCHAR NOT NULL,
    "signup" DATETIME NOT NULL DEFAULT CURRENT_DATE,
    "birthdate" DATETIME,
    "status" BOOL NOT NULL DEFAULT true
);

CREATE TABLE "training_group" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE ,
    "city_id" INTEGER NOT NULL ,
    "name" VARCHAR NOT NULL
);
