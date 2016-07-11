create table PRODUCT (
  ID varchar(100) not null primary key,
  NAME varchar(100) not null,
  DESCRIPTION varchar(255) not null,
  PRICE float not null,
  RATING int not null
);

create table USER (
  ID varchar(100) not null primary key,
  NAME varchar(100) not null
);

create table ORDER_FORM (
  ID varchar(100) not null primary key,
  NAME varchar(100) not null,
  ADDRESS varchar(100) not null,
  PHONE varchar(100) not null,
  USER_ID varchar(100) not null,

  foreign key (USER_ID)
    references USER(ID)
);

create table ORDER_ITEM (
  ID int not null auto_increment,
  AMOUNT float not null,
  QUANTITY int not null,
  PRODUCT_ID varchar(100) not null,
  ORDER_ID varchar(100) not null,

  primary key (ID),

  foreign key (PRODUCT_ID)
    references PRODUCT(ID),

  foreign key (ORDER_ID)
    references ORDER_FORM(ID)
)
