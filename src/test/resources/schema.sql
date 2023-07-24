CREATE TABLE IF NOT EXISTS patient (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255),
  password VARCHAR(255),
  id_Card_No VARCHAR(255),
  first_Name VARCHAR(255),
  last_Name VARCHAR(255),
  phone_Number VARCHAR(255),
  birthday DATE
);

INSERT INTO patient (email, password, id_Card_No, first_Name, last_Name, phone_Number, birthday)
VALUES ('misiakasia@gmail.com', '01234','0604', 'Jan', 'Kowalski', '459349206', '1969-02-09');

INSERT INTO patient (email, password, id_Card_No, first_Name, last_Name, phone_Number, birthday)
VALUES ('walezgwinta@gmail.com', '5467','0468', 'Waldek', 'Kumpel', '749271948', '1978-06-10');

INSERT INTO patient (email, password, id_Card_No, first_Name, last_Name, phone_Number, birthday)
VALUES ('wisimulacha@gmail.com', '6756','9372', 'Abdul', 'Wisimulacha', '850375923', '1999-12-12');

INSERT INTO patient (email, password, id_Card_No, first_Name, last_Name, phone_Number, birthday)
VALUES ('javamaster@gmail.com', '3949','0950', 'Mati', 'Forni', '934201038', '1994-07-07');

INSERT INTO patient (email, password, id_Card_No, first_Name, last_Name, phone_Number, birthday)
VALUES ('zjaranakinga@gmail.com', '3939','0730', 'Kinga', 'Bongo', '40043255', '2004-01-01');

INSERT INTO patient (email, password, id_Card_No, first_Name, last_Name, phone_Number, birthday)
VALUES ('walinos@gmail.com', '93005','0489', 'Kacper', 'Torba', '482948992', '1998-08-08');

INSERT INTO patient (email, password, id_Card_No, first_Name, last_Name, phone_Number, birthday)
VALUES ('puzon69@gmail.com', '012343243','0483', 'Cezary', 'Puzonista', '24324235', '1979-03-06');

CREATE TABLE IF NOT EXISTS visit (
  id INT AUTO_INCREMENT PRIMARY KEY,
  visit_Start_Date TIMESTAMP,
  visit_End_Date TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES patient(id)
);

INSERT INTO visit (visit_Start_Date, visit_End_Date, patient_id)
VALUES ('2024-07-20T17:30:00', '2024-07-20T18:30:00', null);
INSERT INTO visit (visit_Start_Date, visit_End_Date, patient_id)
VALUES ('2024-07-20T18:30:00', '2024-07-20T19:30:00', null);
INSERT INTO visit (visit_Start_Date, visit_End_Date, patient_id)
VALUES ('2024-07-20T19:30:00', '2024-07-20T20:30:00', null);
INSERT INTO visit (visit_Start_Date, visit_End_Date, patient_id)
VALUES ('2024-07-20T20:30:00', '2024-07-20T21:30:00', null);

CREATE TABLE IF NOT EXISTS facility (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  city VARCHAR(255),
  post_Code VARCHAR(255),
  street VARCHAR(255),
  street_Number VARCHAR(255)
);

INSERT INTO facility (name, city, post_Code, street, street_Number)
VALUES ('Palcowka lodz', 'LODZ', '99-999', 'sieniekwicza', '20b');
INSERT INTO facility (name, city, post_Code, street, street_Number)
VALUES ('Palcowka kato', 'katowice', '96-999', 'sieniekwicza', '19b');
INSERT INTO facility (name, city, post_Code, street, street_Number)
VALUES ('Palcowka gdansk', 'gdansk', '94-999', 'sieniekwicza', '18b');
INSERT INTO facility (name, city, post_Code, street, street_Number)
VALUES ('Palcowka radom', 'radom', '93-999', 'sieniekwicza', '17b');
INSERT INTO facility (name, city, post_Code, street, street_Number)
VALUES ('Palcowka krakow', 'krakow', '92-999', 'sieniekwicza', '16b');

CREATE TABLE IF NOT EXISTS doctor (
  id INT AUTO_INCREMENT PRIMARY KEY,
  specialization VARCHAR(255),
  first_Name VARCHAR(255),
  last_Name VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255)
);

INSERT INTO doctor(specialization, first_Name, last_Name, email, password)
VALUES ('SURGEON', 'Jaca', 'Kowal', 'jj@lol.pl', 'sdfsdd2');
INSERT INTO doctor(specialization, first_Name, last_Name, email, password)
VALUES ('NEUROLOGIST', 'Marek', 'Sagan', 'aa@lol.pl', 'sdfsdd2');
INSERT INTO doctor(specialization, first_Name, last_Name, email, password)
VALUES ('DERMATOLOGIST', 'Mariusz', 'Pawło', 'mp@lol.pl', 'sdfsdd2');
INSERT INTO doctor(specialization, first_Name, last_Name, email, password)
VALUES ('SURGEON', 'Mateusz', 'Swiatek', 'ms@lol.pl', 'sdfsdd2');
INSERT INTO doctor(specialization, first_Name, last_Name, email, password)
VALUES ('CARDIOLOGIST', 'Przemek', 'Słupek', 'ps@lol.pl', 'sdfsdd2');