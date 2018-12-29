-- Create user data types section -------------------------------------------------

CREATE TYPE "t_udrzba"
AS OBJECT (
  km Number,
  cena Number,
  od Date,
  do Date,
  popis Varchar2(40)
)
/

create type "udrzba" as table of "t_udrzba"
/
-- Create sequences section -------------------------------------------------
--region
CREATE SEQUENCE "seq_pk_vozidla"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE "seq_pk_osoba"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE "seq_pk_vypozicky"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE "seq_pk_udrzba"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE "seq_pk_cennik"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE "seq_pk_vozidlo_cennik"
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/

CREATE SEQUENCE "seq_pk_faktury"
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 20
/
--endregion
-- Create tables section -------------------------------------------------

-- Table vozidla

CREATE TABLE "vozidla"(
  "id" Integer NOT NULL,
  "id_cennika" Integer,
  "spz" Char(20 ) NOT NULL,
  "znacka" Char(20 ) NOT NULL,
  "typ" Char(20 ) NOT NULL,
  "fotka" Blob,
  "udrzby" "udrzba",
  "datum_vyradenia" Date
) NESTED TABLE "udrzby" STORE AS udrzby_tab;

/


-- Create indexes for table vozidla

CREATE INDEX "IX_Relationship16" ON "vozidla" ("id_cennika")
/

-- Add keys for table vozidla

ALTER TABLE "vozidla" ADD CONSTRAINT "id" PRIMARY KEY ("id")
/

ALTER TABLE "vozidla" ADD CONSTRAINT "spz" UNIQUE ("spz")
/

-- Table Zakaznik

CREATE TABLE "Zakaznik"(
  "id" Char(11 ) NOT NULL,
  "id" Char(10 ) NOT NULL
)
/

-- Add keys for table Zakaznik

ALTER TABLE "Zakaznik" ADD CONSTRAINT "PK_Zakaznik" PRIMARY KEY ("id","id")
/

-- Table vypozicky

CREATE TABLE "vypozicky"(
  "id" Integer NOT NULL,
  "id_vozidla" Integer,
  "id_zakaznika" Char(10 ),
  "od" Date NOT NULL,
  "do" Date NOT NULL
)
/

-- Create indexes for table vypozicky

CREATE INDEX "IX_Relationship4" ON "vypozicky" ("id_vozidla")
/

CREATE INDEX "IX_Relationship21" ON "vypozicky" ("id_zakaznika")
/

-- Add keys for table vypozicky

ALTER TABLE "vypozicky" ADD CONSTRAINT "PK_vypozicky" PRIMARY KEY ("id")
/

-- Table udrzba

CREATE TABLE "udrzba"(
  "id" Integer NOT NULL,
  "popis" Char(20 ) NOT NULL,
  "udaje" "t_udrzba" NOT NULL,
  "cena" Real NOT NULL
)
/

-- Add keys for table udrzba

ALTER TABLE "udrzba" ADD CONSTRAINT "PK_udrzba" PRIMARY KEY ("id")
/

-- Table udrzba_vozidla

CREATE TABLE "udrzba_vozidla"(
  "id_vozidla" Integer NOT NULL,
  "id_udrzba" Integer NOT NULL
)
/

-- Add keys for table udrzba_vozidla

ALTER TABLE "udrzba_vozidla" ADD CONSTRAINT "PK_udrzba_vozidla" PRIMARY KEY ("id_vozidla","id_udrzba")
/

-- Table cennik

CREATE TABLE "cennik"(
  "id" Integer NOT NULL,
  "cena_den" Real NOT NULL,
  "poplatok" Real NOT NULL,
  "platny_od" Date DEFAULT sysdate NOT NULL,
  "platny_do" Date NOT NULL
)
/

-- Add keys for table cennik

ALTER TABLE "cennik" ADD CONSTRAINT "PK_cennik" PRIMARY KEY ("id")
/

-- Table vozidlo_cennik

CREATE TABLE "vozidlo_cennik"(
  "id_vozidla" Integer NOT NULL,
  "id_cennika" Integer NOT NULL,
  "id" Integer NOT NULL
)
/

-- Add keys for table vozidlo_cennik

ALTER TABLE "vozidlo_cennik" ADD CONSTRAINT "PK_vozidlo_cennik" PRIMARY KEY ("id_vozidla","id_cennika","id")
/

-- Table faktura

CREATE TABLE "faktura"(
  "id" Integer NOT NULL,
  "id_faktury" Integer,
  "suma" Real NOT NULL,
  "vystavena" Date DEFAULT sysdate NOT NULL,
  "zaplatena" Date
)
/

-- Create indexes for table faktura

CREATE INDEX "IX_Relationship17" ON "faktura" ("id_faktury")
/

-- Add keys for table faktura

ALTER TABLE "faktura" ADD CONSTRAINT "PK_faktura" PRIMARY KEY ("id")
/

-- Table vypozicka_faktura

CREATE TABLE "vypozicka_faktura"(
  "id_vypozicky" Integer NOT NULL,
  "id_faktury" Integer NOT NULL
)
/

-- Add keys for table vypozicka_faktura

ALTER TABLE "vypozicka_faktura" ADD CONSTRAINT "PK_vypozicka_faktura" PRIMARY KEY ("id_vypozicky","id_faktury")
/

-- Table Osoba

CREATE TABLE "Osoba"(
  "rod_cislo" Char(10 ) NOT NULL,
  "meno" Char(20 ) NOT NULL,
  "priezvisko" Char(20 ) NOT NULL
)
/

-- Add keys for table Osoba

ALTER TABLE "Osoba" ADD CONSTRAINT "PK_Osoba" PRIMARY KEY ("rod_cislo")
/

-- Table Firma

CREATE TABLE "Firma"(
  "ico" Char(10 ) NOT NULL,
  "nazov" Varchar2(30 ) NOT NULL
)
/

-- Add keys for table Firma

ALTER TABLE "Firma" ADD CONSTRAINT "PK_Firma" PRIMARY KEY ("ico")
/

-- Table zakaznik

CREATE TABLE "zakaznik"(
  "id" Char(10 ) NOT NULL,
  "kontakt" Char(20 ) NOT NULL
)
/

-- Add keys for table zakaznik

ALTER TABLE "zakaznik" ADD CONSTRAINT "PK_zakaznik" PRIMARY KEY ("id")
/

-- Trigger for sequence seq_pk_vozidla for column id in table vozidla ---------
CREATE OR REPLACE TRIGGER "ts_vozidla_seq_pk_vozidla" BEFORE INSERT
ON "vozidla" FOR EACH ROW
BEGIN
  :new."id" := "seq_pk_vozidla".nextval;
END;
/
CREATE OR REPLACE TRIGGER "tsu_vozidla_seq_pk_vozidla" AFTER UPDATE OF "id"
ON "vozidla" FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column "id" in table "vozidla" as it uses sequence.');
END;
/

-- Trigger for sequence seq_pk_osoba for column id in table Zakaznik ---------
CREATE OR REPLACE TRIGGER "ts_Zakaznik_seq_pk_osoba" BEFORE INSERT
ON "Zakaznik" FOR EACH ROW
BEGIN
  :new."id" := "seq_pk_osoba".nextval;
END;
/
CREATE OR REPLACE TRIGGER "tsu_Zakaznik_seq_pk_osoba" AFTER UPDATE OF "id"
ON "Zakaznik" FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column "id" in table "Zakaznik" as it uses sequence.');
END;
/

-- Trigger for sequence seq_pk_vypozicky for column id in table vypozicky ---------
CREATE OR REPLACE TRIGGER "ts_vypozicky_seq_pk_vypozicky" BEFORE INSERT
ON "vypozicky" FOR EACH ROW
BEGIN
  :new."id" := "seq_pk_vypozicky".nextval;
END;
/
CREATE OR REPLACE TRIGGER "tsu_vypozicky_seq_pk_vypozicky" AFTER UPDATE OF "id"
ON "vypozicky" FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column "id" in table "vypozicky" as it uses sequence.');
END;
/

-- Trigger for sequence seq_pk_udrzba for column id in table udrzba ---------
CREATE OR REPLACE TRIGGER "ts_udrzba_seq_pk_udrzba" BEFORE INSERT
ON "udrzba" FOR EACH ROW
BEGIN
  :new."id" := "seq_pk_udrzba".nextval;
END;
/
CREATE OR REPLACE TRIGGER "tsu_udrzba_seq_pk_udrzba" AFTER UPDATE OF "id"
ON "udrzba" FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column "id" in table "udrzba" as it uses sequence.');
END;
/

-- Trigger for sequence seq_pk_cennik for column id in table cennik ---------
CREATE OR REPLACE TRIGGER "ts_cennik_seq_pk_cennik" BEFORE INSERT
ON "cennik" FOR EACH ROW
BEGIN
  :new."id" := "seq_pk_cennik".nextval;
END;
/
CREATE OR REPLACE TRIGGER "tsu_cennik_seq_pk_cennik" AFTER UPDATE OF "id"
ON "cennik" FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column "id" in table "cennik" as it uses sequence.');
END;
/

-- Trigger for sequence seq_pk_vozidlo_cennik for column id in table vozidlo_cennik ---------
CREATE OR REPLACE TRIGGER "ts_vozidlo_cennik_seq_pk_voz_2" BEFORE INSERT
ON "vozidlo_cennik" FOR EACH ROW
BEGIN
  :new."id" := "seq_pk_vozidlo_cennik".nextval;
END;
/
CREATE OR REPLACE TRIGGER "tsu_vozidlo_cennik_seq_pk_vo_2" AFTER UPDATE OF "id"
ON "vozidlo_cennik" FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column "id" in table "vozidlo_cennik" as it uses sequence.');
END;
/

-- Trigger for sequence seq_pk_faktury for column id in table faktura ---------
CREATE OR REPLACE TRIGGER "ts_faktura_seq_pk_faktury" BEFORE INSERT
ON "faktura" FOR EACH ROW
BEGIN
  :new."id" := "seq_pk_faktury".nextval;
END;
/
CREATE OR REPLACE TRIGGER "tsu_faktura_seq_pk_faktury" AFTER UPDATE OF "id"
ON "faktura" FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column "id" in table "faktura" as it uses sequence.');
END;
/


-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE "vypozicky" ADD CONSTRAINT "je pozicane" FOREIGN KEY ("id_vozidla") REFERENCES "vozidla" ("id")
/


ALTER TABLE "udrzba_vozidla" ADD CONSTRAINT "Relationship7" FOREIGN KEY ("id_vozidla") REFERENCES "vozidla" ("id")
/


ALTER TABLE "udrzba_vozidla" ADD CONSTRAINT "Relationship8" FOREIGN KEY ("id_udrzba") REFERENCES "udrzba" ("id")
/


ALTER TABLE "vozidlo_cennik" ADD CONSTRAINT "vozidlo ma cennik" FOREIGN KEY ("id_vozidla") REFERENCES "vozidla" ("id")
/


ALTER TABLE "vozidlo_cennik" ADD CONSTRAINT "vozidlo ma cenniky" FOREIGN KEY ("id_cennika") REFERENCES "cennik" ("id")
/


ALTER TABLE "vypozicka_faktura" ADD CONSTRAINT "vypozicka ma fakturu" FOREIGN KEY ("id_vypozicky") REFERENCES "vypozicky" ("id")
/


ALTER TABLE "vypozicka_faktura" ADD CONSTRAINT "vypozicku fakturujem" FOREIGN KEY ("id_faktury") REFERENCES "faktura" ("id")
/


ALTER TABLE "vozidla" ADD CONSTRAINT "Relationship16" FOREIGN KEY ("id_cennika") REFERENCES "cennik" ("id")
/


ALTER TABLE "faktura" ADD CONSTRAINT "Relationship17" FOREIGN KEY ("id_faktury") REFERENCES "vypozicky" ("id")
/


ALTER TABLE "Zakaznik" ADD CONSTRAINT "Relationship19" FOREIGN KEY ("id") REFERENCES "Osoba" ("rod_cislo")
/


ALTER TABLE "vypozicky" ADD CONSTRAINT "Relationship21" FOREIGN KEY ("id_zakaznika") REFERENCES "zakaznik" ("id")
/


ALTER TABLE "Osoba" ADD CONSTRAINT "Relationship22" FOREIGN KEY ("rod_cislo") REFERENCES "zakaznik" ("id")
/


ALTER TABLE "Firma" ADD CONSTRAINT "Relationship23" FOREIGN KEY ("ico") REFERENCES "zakaznik" ("id")
/