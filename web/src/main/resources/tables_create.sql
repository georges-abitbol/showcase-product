CREATE table accounts (
  uuid varchar(255) not null,
  name varchar(255) not null,
  editionCode varchar(255) not null,
  maxUsers int not null default 0,
  appDirectManaged boolean not null default FALSE ,
  appDirectBaseUrl varchar(255) not null,
  primary key (uuid)
);

CREATE table users (
  uuid varchar(255) not null,
  openid varchar(255) not null,
  email varchar(255) not null,
  firstName varchar(255) not null,
  lastName varchar(255) not null,
  zipCode varchar(255),
  department varchar(255),
  timezone varchar(255),
  admin boolean not null default false ,
  primary key (uuid),
  accountUuid varchar(255) not null,
  FOREIGN KEY (accountUuId) REFERENCES accounts(uuid) ON DELETE CASCADE
);
