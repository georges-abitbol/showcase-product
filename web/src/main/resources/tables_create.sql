CREATE table accounts (
  uuid varchar(255) not null,
  name varchar(255) not null,
  editionCode varchar(255) not null,
  maxUsers int not null default 0,
  appDirectManaged boolean not null default 0,
  appDirectBaseUrl varchar(255) not null,
  id bigint NOT NULL AUTO_INCREMENT,
  primary key (id)
);

CREATE table users (
  uuid varchar(255) not null,
  openid varchar(255) not null,
  email varchar(255) not null,
  firstName varchar(255) not null,
  lastName varchar(255) not null,
  zipCode varchar(255) not null,
  department varchar(255) not null,
  timezone varchar(255) not null,
  admin boolean not null default 0,
  id bigint NOT NULL AUTO_INCREMENT,
  primary key (id),
  accountId bigint not null,
  FOREIGN KEY (accountId) REFERENCES accounts(id)
);
