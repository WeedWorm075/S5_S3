-- create database voyage;
-- \c

-- 1st part
-- ENTITE : ACTIVITE
create sequence sq_activite increment by 1 minvalue 1 START 1; 
create table activite(
    idActivite varchar default 'A'||nextval('sq_activite') PRIMARY KEY,
    nomActivite varchar(50)
);

-- ENTITE : BOUQUET
create sequence sq_bouquet increment by 1 minvalue 1 START 1; 
create table bouquet(
    idBouquet varchar default 'B'||nextval('sq_bouquet') PRIMARY KEY,
    nomBouquet varchar(50)
);

-- ASSOCIATION : ACTIVITE-BOUQUET
create sequence sq_activite_bouquet increment by 1 minvalue 1 START 1; 
create table activite_bouquet(
    idActivite_bouquet varchar default 'A_B'||nextval('sq_activite_bouquet') PRIMARY KEY, 
    idActivite varchar(10),
    idBouquet varchar(10),
    FOREIGN KEY (idActivite) REFERENCES activite(idActivite),
    FOREIGN KEY (idBouquet) REFERENCES bouquet(idBouquet)
);

-- ENTITE : CATEGORIE LIEU
create sequence sq_categorieLieu increment by 1 minvalue 1 START 1; 
create table categorieLieu(
    idCategorieLieu varchar default 'C'||nextval('sq_categorieLieu') PRIMARY KEY,
    nomCategorieLieu varchar(50)
);

-- ENTITE : LIEU
create sequence sq_lieu increment by 1 minvalue 1 START 1; 
create table lieu(
    idLieu varchar default 'L'||nextval('sq_lieu') PRIMARY KEY,
    nomLieu varchar(50)

);

-- ASSOCIATION : LIEU-CATEGORIE LIEU
create sequence sq_categorie_lieu increment by 1 minvalue 1 START 1; 
create table lieu_categorie(
    idLieu_categorie varchar default 'C_L'||nextval('sq_categorie_lieu') PRIMARY KEY,
    idCategorieLieu varchar(10),
    idLieu varchar(10),
    foreign key (idCategorieLieu) references categorieLieu(idCategorieLieu),
    foreign key (idLieu) references lieu(idLieu)

);

-- 2st part
alter table activite add column dateHDebut timestamp, dateHFin timestamp;
alter table activite add column dateHFin timestamp, dateHFin timestamp;

-- ENTITE : VOYAGE
create sequence sq_voyage increment by 1 minvalue 1 start 1;
create table voyage(
    idVoyage varchar default'V'||nextval('sq_voyage') PRIMARY KEY,
    idBouquet varchar,
    idLieu varchar,
    depart Date,
    arrive Date,
    FOREIGN KEY (idBouquet) REFERENCES bouquet(idBouquet),
    FOREIGN KEY (idLieu) REFERENCES lieu(idLieu)
);

-- ASSOCIATION : VOYAGE-ACTIVITE
create sequence sq_voyage_activite increment by 1 start 1;
create table voyage_activite(
    idVoyage_activite varchar 'V_A'||nextval('sq_voyage_activite') PRIMARY KEY,
    idVoyage varchar UNIQUE,
    idActivite varchar,
    FOREIGN KEY (idVoyage) REFERENCES voyage(idVoyage),
    FOREIGN KEY (idActivite) REFERENCES activite(idActivite)
);