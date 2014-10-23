-- some users
insert into accounts( uuid,  name,  editionCode,  maxUsers,  appDirectManaged, appDirectBaseUrl ) values ('2222-1111','anthony', 'free', '10', false, 'app.com');

insert into users( uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin, accountUuid ) values ('1111-1111','anthonyOpenIdUrl', 'anthony@dahanne.net','anthony', 'dahanne', 'h2l', 'IT','EST', false, '2222-1111');
insert into users( uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin, accountUuid ) values ('2222-1111','papitoOpenIdUrl', 'papito@dahanne.net','papito', 'soleil', 'h2l', 'IT','EST', false, '2222-1111');
insert into users( uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin, accountUuid ) values ('3333-1111','johnnyOpenIdUrl', 'johnny@dahanne.net','johnny', 'boy', 'h2l', 'IT','EST', false, '2222-1111');
insert into users( uuid,  openId,  email,  firstName,  lastName,  zipCode,  department,  timezone, admin, accountUuid ) values ('4444-1111','ouamOpenIdUrl', 'ouam@dahanne.net','ouam', 'pote', 'h2l', 'IT','EST', false, '2222-1111');

