CREATE TABLE administrador (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  senha VARCHAR(20) NOT NULL,
  cpf VARCHAR(11) NOT NULL
);


CREATE TABLE conta (
  numero INT PRIMARY KEY,
  titular VARCHAR(50),
  cpf VARCHAR(11),
  senha VARCHAR(20),
  saldo DECIMAL(10, 2),
  tipoConta INT
);

CREATE TABLE pessoa (
  cpf VARCHAR(11) PRIMARY KEY,
  titular VARCHAR(50),
  numero INT,
  INDEX idx_pessoa_cpf (cpf)
);

CREATE TABLE conta_corrente (
   numero INT PRIMARY KEY,
  senha VARCHAR(6),
  saldo DECIMAL(10, 2),
  cpf VARCHAR(11),
  cheque_especial DECIMAL(10, 2) DEFAULT 500,
  FOREIGN KEY (numero) REFERENCES conta(numero),
  FOREIGN KEY (cpf) REFERENCES pessoa(cpf)
);


CREATE TABLE conta_poupanca (
  numero INT PRIMARY KEY,
  senha VARCHAR(6),
  saldo DECIMAL(10, 2),
  cpf VARCHAR(11),
  taxa  DECIMAL(10, 2),
  FOREIGN KEY (numero) REFERENCES conta(numero),
  FOREIGN KEY (cpf) REFERENCES pessoa(cpf)
);

DELIMITER $$
CREATE TRIGGER conta_trigger
AFTER INSERT ON conta
FOR EACH ROW
BEGIN
  INSERT INTO pessoa (cpf, titular, numero)
  VALUES (NEW.cpf, NEW.titular, NEW.numero);

  IF NEW.tipoConta = 1 THEN
    INSERT INTO conta_corrente (numero, senha, saldo, cpf)
    VALUES (NEW.numero, NEW.senha, NEW.saldo, NEW.cpf);
  ELSEIF NEW.tipoConta = 2 THEN
    INSERT INTO conta_poupanca (numero, senha, saldo, cpf)
    VALUES (NEW.numero, NEW.senha, NEW.saldo, NEW.cpf);
  END IF;
END $$
DELIMITER ;


ALTER TABLE conta_corrente
DROP FOREIGN KEY conta_corrente_ibfk_1;

ALTER TABLE conta_corrente
ADD CONSTRAINT conta_corrente_ibfk_1
FOREIGN KEY (numero)
REFERENCES conta(numero)
ON DELETE CASCADE;


