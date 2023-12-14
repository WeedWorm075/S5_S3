INSERT INTO activite (nomActivite) VALUES
('Randonnée'),
('Plongée'),
('Visite de musée');

INSERT INTO bouquet (nomBouquet) VALUES
('Aventure'),
('Détente'),
('Culture');

INSERT INTO activite_bouquet (idActivite, idBouquet) VALUES
('A1', 'B1'), -- Randonnée - Aventure
('A1', 'B2'), -- Randonnée - Aventure
('A2', 'B2'), -- Plongée - Détente
('A1', 'B3'), -- Visite de musée - Culture
('A2', 'B3'), -- Visite de musée - Culture
('A3', 'B3'); -- Visite de musée - Culture

INSERT INTO categorieLieu (nomCategorieLieu) VALUES
('Parc'),
('Plage'),
('Musée');

INSERT INTO lieu (nomLieu) VALUES
('Parc national'),
('Plage paradisiaque'),
('Musée d''art moderne');


INSERT INTO lieu_categorie (idCategorieLieu, idLieu) VALUES
('C1', 'L1'), -- Parc national - Parc
('C2', 'L2'), -- Plage paradisiaque - Plage
('C3', 'L3'); -- Musée d'art moderne - Musée


