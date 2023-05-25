CREATE TABLE IF NOT EXISTS Autonomia (
	id INTEGER PRIMARY KEY,
	nombre TEXT
);

INSERT INTO Autonomia (id, nombre) VALUES (1, 'Andalucía');
INSERT INTO Autonomia (id, nombre) VALUES (2, 'Aragón');
INSERT INTO Autonomia (id, nombre) VALUES (3, 'Asturias');
INSERT INTO Autonomia (id, nombre) VALUES (4, 'Illes Balears');
INSERT INTO Autonomia (id, nombre) VALUES (5, 'Cantabria');
INSERT INTO Autonomia (id, nombre) VALUES (6, 'Castilla-La Mancha');
INSERT INTO Autonomia (id, nombre) VALUES (7, 'Castilla y León');
INSERT INTO Autonomia (id, nombre) VALUES (8, 'Ceuta');
INSERT INTO Autonomia (id, nombre) VALUES (9, 'Extremadura');
INSERT INTO Autonomia (id, nombre) VALUES (10, 'La Rioja');
INSERT INTO Autonomia (id, nombre) VALUES (11, 'Madrid');
INSERT INTO Autonomia (id, nombre) VALUES (12, 'Melilla');
INSERT INTO Autonomia (id, nombre) VALUES (13, 'Murcia');
INSERT INTO Autonomia (id, nombre) VALUES (14, 'Navarra');
INSERT INTO Autonomia (id, nombre) VALUES (15, 'Valencia');

CREATE TABLE IF NOT EXISTS Aspirante (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
    dni TEXT,
    nombre TEXT
); 
CREATE INDEX idx_aspirante_dni ON Aspirante(dni);
CREATE INDEX idx_aspirante_nombre ON Aspirante(nombre);

CREATE TABLE IF NOT EXISTS Especialidad (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT,
	cuerpo INTEGER,
	especialidad INTEGER
); 

CREATE INDEX idx_especialidad_especialidad ON Especialidad(especialidad);

CREATE TABLE IF NOT EXISTS Participa (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idAspirante INTEGER,
	idEspecialidad INTEGER,
    notaNacional REAL,
    nota1 REAL,
    nota11 REAL,
    nota12 REAL,
    nota13 REAL,
    nota14 REAL,
    nota2 REAL,
    nota21 REAL,
    nota22 REAL,
    nota221 REAL,
    nota222 REAL,
    nota223 REAL,
    nota23 REAL,
    nota231 REAL,
    nota232 REAL,
    nota24 REAL,
    nota241 REAL,
    nota242 REAL,
    nota243 REAL,
    nota244 REAL,
    nota245 REAL,
    nota25 REAL,
    nota3 REAL,
    nota31 REAL,
    nota32 REAL,
    anos INTEGER,
    meses INTEGER,
    dias INTEGER,
    euskera CHAR(1) CHECK(euskera IN ('S', 'N')) DEFAULT 'N',
    asignado CHAR(1) CHECK(asignado IN ('S', 'N')) DEFAULT 'N',
	FOREIGN KEY (idAspirante) REFERENCES Aspirante(id),
	FOREIGN KEY (idEspecialidad) REFERENCES Especialidad(id)
);

CREATE INDEX idx_ordenacion_participa ON Participa (
    notaNacional, nota1, nota2, nota3, nota11, nota12, nota13, nota14, nota21, nota22, nota23, nota24, nota25,
    nota31, nota32, nota221, nota222, nota223, nota231, nota232, nota241, nota242, nota243, nota244, nota245,
    anos, meses, dias
);

CREATE INDEX idx_participa_idAspirante ON Participa (idAspirante);
CREATE INDEX idx_participa_idEspecialidad ON Participa (idEspecialidad);

CREATE TABLE IF NOT EXISTS Elije(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idParticipa INTEGER,
	idAutonomia INTEGER,
	orden INTEGER,
	turno INTEGER CHECK(turno >=1 AND turno <= 2) DEFAULT 1,
	rechazado  CHAR(1) CHECK(rechazado IN ('S', 'N')) DEFAULT 'N',
	FOREIGN KEY (idParticipa) REFERENCES Participa(id),
	FOREIGN KEY (idAutonomia) REFERENCES Autonomia(id)	
);
	
CREATE INDEX idx_elije_idParticipa ON Elije (idParticipa);
CREATE INDEX idx_elije_idAutonomia ON Elije (idAutonomia);

