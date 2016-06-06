PRAGMA foreign_keys = ON;

CREATE TABLE "cities" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,
    "name" VARCHAR NOT NULL UNIQUE
);

CREATE TABLE "training_group" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE ,
    "city_id" INTEGER NOT NULL REFERENCES cities,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "karateka" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,
    "group_id" INTEGER NOT NULL REFERENCES training_group,
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

CREATE TABLE IF NOT EXISTS "user" (
    "id" INTEGER PRIMARY KEY NOT NULL UNIQUE,
    "login" VARCHAR NOT NULL,
    "pass" VARCHAR NOT NULL,
    "name" VARCHAR NOT NULL,
    "surname" VARCHAR NOT NULL,
    "email" VARCHAR NOT NULL,
    "role" VARCHAR NOT NULL,
    "default_city_id" INTEGER NOT NULL REFERENCES cities,
    "stamp" DATETIME DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "events" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,
    "city_id" INTEGER NOT NULL REFERENCES cities,
    "name" VARCHAR NOT NULL,
    "date" DATETIME NOT NULL,
    "type" VARCHAR NOT NULL
);

CREATE TABLE "presence" (
    "karateka_id" INTEGER NOT NULL REFERENCES karateka,
    "start" DATETIME NOT NULL DEFAULT CURRENT_DATE,
    "period" VARCHAR NOT NULL DEFAULT DAY,
    "count" INTEGER NOT NULL DEFAULT 1,
    "type" VARCHAR NOT NULL DEFAULT BASIC
);
CREATE UNIQUE INDEX presence_day ON presence("karateka_id", "start", "type");
