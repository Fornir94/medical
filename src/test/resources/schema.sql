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
