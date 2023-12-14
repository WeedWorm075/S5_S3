CREATE VIEW vue_lieu_categorie AS
SELECT 
    lc.idLieu_categorie,
    cl.idCategorieLieu,
    cl.nomCategorieLieu,
    l.idLieu,
    l.nomLieu
FROM lieu_categorie lc
JOIN categorieLieu cl ON lc.idCategorieLieu = cl.idCategorieLieu
JOIN lieu l ON lc.idLieu = l.idLieu;

CREATE VIEW vue_activite_bouquet AS
SELECT 
    ab.idActivite_bouquet,
    a.idActivite,
    a.nomActivite,
    b.idBouquet,
    b.nomBouquet
FROM activite_bouquet ab
JOIN activite a ON ab.idActivite = a.idActivite
JOIN bouquet b ON ab.idBouquet = b.idBouquet;

CREATE VIEW vue_voyage_activite AS
SELECT 
    ab.idVoyage_activite,
    a.idActivite,
    a.nomActivite,
    b.idVoyage,
    b.depart,
    b.arrive
FROM voyage_activite ab
JOIN activite a ON ab.idActivite = a.idActivite
JOIN voyage b ON ab.idVoyage = b.idVoyage;
