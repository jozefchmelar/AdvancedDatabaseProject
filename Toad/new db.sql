/*
created: 28.12.2018
modified: 31.12.2018
model: oracle 11g release 2
database: oracle 11g release 2
*/


-- create user data types section -------------------------------------------------

create type t_udrzba
as object (
  km number,
  cena number,
  od date,
  do date,
  popis varchar2(40)
)
/

create type udrzby
as table of t_udrzba
/

-- create sequences section -------------------------------------------------

create sequence seq_pk_vozidla
 increment by 1
 start with 1
 nomaxvalue
 nominvalue
 cache 20
/

create sequence seq_pk_vypozicky
 increment by 1
 start with 1
 nomaxvalue
 nominvalue
 cache 20
/

create sequence seq_pk_udrzba
 increment by 1
 start with 1
 nomaxvalue
 nominvalue
 cache 20
/

create sequence seq_pk_cennik
 increment by 1
 start with 1
 nomaxvalue
 nominvalue
 cache 20
/

create sequence seq_pk_faktury
 increment by 1
 start with 1
 nomaxvalue
 nominvalue
 cache 20
/

-- create tables section -------------------------------------------------

-- table vozidlo

create table vozidlo(
  id integer not null,
  id_cennika integer,
  spz char(20 ) not null,
  znacka char(20 ) not null,
  typ char(20 ) not null,
  fotka blob,
  udrzba udrzby,
  datum_vyradenia date
) NESTED TABLE udrzba STORE AS udrzba_tab;
/

-- create indexes for table vozidlo

create index ix_relationship16 on vozidlo (id_cennika)
/

-- add keys for table vozidlo

alter table vozidlo add constraint id primary key (id)
/

alter table vozidlo add constraint spz unique (spz)
/

-- table vypozicka

create table vypozicka(
  id integer not null,
  id_vozidla integer,
  id_zakaznika char(10 ),
  od date not null,
  do date not null
)
/

-- create indexes for table vypozicka

create index ix_relationship4 on vypozicka (id_vozidla)
/

create index ix_relationship21 on vypozicka (id_zakaznika)
/

-- add keys for table vypozicka

alter table vypozicka add constraint pk_vypozicka primary key (id)
/

-- table udrzba

create table udrzba(
  id integer not null,
  popis char(20 ) not null,
  udaje t_udrzba not null,
  cena real not null
)
/

-- add keys for table udrzba

alter table udrzba add constraint pk_udrzba primary key (id)
/

-- table udrzba_vozidla

create table udrzba_vozidla(
  id_vozidla integer not null,
  id_udrzba integer not null
)
/

-- add keys for table udrzba_vozidla

alter table udrzba_vozidla add constraint pk_udrzba_vozidla primary key (id_vozidla,id_udrzba)
/

-- table cennik

create table cennik(
  id integer not null,
  cena_den real not null,
  poplatok real not null,
  platny_od date default sysdate not null,
  platny_do date not null
)
/

-- add keys for table cennik

alter table cennik add constraint pk_cennik primary key (id)
/

-- table vozidlo_cennik

create table vozidlo_cennik(
  id integer not null
)
/

-- add keys for table vozidlo_cennik

alter table vozidlo_cennik add constraint pk_vozidlo_cennik primary key (id)
/

-- table faktura

create table faktura(
  id integer not null,
  id_vypozicky integer,
  suma real not null,
  vystavena date default sysdate not null,
  zaplatena date
)
/

-- create indexes for table faktura

create index ix_relationship17 on faktura (id_vypozicky)
/

-- add keys for table faktura

alter table faktura add constraint pk_faktura primary key (id)
/

-- table osoba

create table osoba(
  rod_cislo char(10 ) not null,
  meno char(20 ) not null,
  priezvisko char(20 ) not null
)
/

-- add keys for table osoba

alter table osoba add constraint pk_osoba primary key (rod_cislo)
/

-- table firma

create table firma(
  ico char(10 ) not null,
  nazov varchar2(30 ) not null
)
/

-- add keys for table firma

alter table firma add constraint pk_firma primary key (ico)
/

-- table zakaznik

create table zakaznik(
  id char(10 ) not null,
  kontakt char(20 ) not null
)
/

-- add keys for table zakaznik

alter table zakaznik add constraint pk_zakaznik primary key (id)
/

-- trigger for sequence seq_pk_vozidla for column id in table vozidlo ---------
create or replace trigger ts_vozidlo_seq_pk_vozidla before insert
on vozidlo for each row
begin
  :new.id := seq_pk_vozidla.nextval;
end;
/
create or replace trigger tsu_vozidlo_seq_pk_vozidla after update of id
on vozidlo for each row
begin
  raise_application_error(-20010,'cannot update column id in table vozidlo as it uses sequence.');
end;
/

-- trigger for sequence seq_pk_vypozicky for column id in table vypozicka ---------
create or replace trigger ts_vypozicka_seq_pk_vypozicky before insert
on vypozicka for each row
begin
  :new.id := seq_pk_vypozicky.nextval;
end;
/
create or replace trigger tsu_vypozicka_seq_pk_vypozicky after update of id
on vypozicka for each row
begin
  raise_application_error(-20010,'cannot update column id in table vypozicka as it uses sequence.');
end;
/

-- trigger for sequence seq_pk_udrzba for column id in table udrzba ---------
create or replace trigger ts_udrzba_seq_pk_udrzba before insert
on udrzba for each row
begin
  :new.id := seq_pk_udrzba.nextval;
end;
/
create or replace trigger tsu_udrzba_seq_pk_udrzba after update of id
on udrzba for each row
begin
  raise_application_error(-20010,'cannot update column id in table udrzba as it uses sequence.');
end;
/

-- trigger for sequence seq_pk_cennik for column id in table cennik ---------
create or replace trigger ts_cennik_seq_pk_cennik before insert
on cennik for each row
begin
  :new.id := seq_pk_cennik.nextval;
end;
/
create or replace trigger tsu_cennik_seq_pk_cennik after update of id
on cennik for each row
begin
  raise_application_error(-20010,'cannot update column id in table cennik as it uses sequence.');
end;
/

-- trigger for sequence seq_pk_faktury for column id in table faktura ---------
create or replace trigger ts_faktura_seq_pk_faktury before insert
on faktura for each row
begin
  :new.id := seq_pk_faktury.nextval;
end;
/
create or replace trigger tsu_faktura_seq_pk_faktury after update of id
on faktura for each row
begin
  raise_application_error(-20010,'cannot update column id in table faktura as it uses sequence.');
end;
/


-- create foreign keys (relationships) section ------------------------------------------------- 

alter table vypozicka add constraint je_pozicane foreign key (id_vozidla) references vozidlo (id)
/


alter table udrzba_vozidla add constraint relationship7 foreign key (id_vozidla) references vozidlo (id)
/


alter table udrzba_vozidla add constraint relationship8 foreign key (id_udrzba) references udrzba (id)
/


alter table vozidlo add constraint relationship16 foreign key (id_cennika) references cennik (id)
/


alter table faktura add constraint relationship17 foreign key (id_vypozicky) references vypozicka (id)
/


alter table vypozicka add constraint relationship21 foreign key (id_zakaznika) references zakaznik (id)
/


alter table osoba add constraint relationship22 foreign key (rod_cislo) references zakaznik (id)
/


alter table firma add constraint relationship23 foreign key (ico) references zakaznik (id)
/