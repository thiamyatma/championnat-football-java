USE championnat;
CREATE TABLE equipe (
    nom VARCHAR(50) PRIMARY KEY,
    victoires INT default 0,
    defaites INT default 0,
    nuls INT default 0,
    buts_marques INT default 0,
    buts_concedes INT default 0
);

CREATE TABLE match_jeu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    temperature DOUBLE,
    equipe_domicile VARCHAR(50) NOT NULL,
    equipe_exterieur VARCHAR(50) NOT NULL,
    score_domicile INT NOT NULL,
    score_exterieur INT NOT NULL,
    FOREIGN KEY (equipe_domicile) REFERENCES equipe(nom),
    FOREIGN KEY (equipe_exterieur) REFERENCES equipe(nom)
);

INSERT INTO Equipe (nom) VALUES ('Barcelone'), ('Real Madrid'), ('Paris Saint Germain'), ('Chelsea');


TRUNCATE TABLE match_jeu;
UPDATE equipe SET victoires = 0, defaites = 0, nuls = 0, buts_marques = 0, buts_concedes = 0;