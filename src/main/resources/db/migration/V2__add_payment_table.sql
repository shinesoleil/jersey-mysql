CREATE TABLE PAYMENT (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PAY_TYPE VARCHAR(100) NOT NULL,
  AMOUNT FLOAT NOT NULL,
  ORDER_ID VARCHAR(100) NOT NULL,

  FOREIGN KEY (ORDER_ID)
    REFERENCES ORDER_FORM(ID)
)