create if not exists database Ilona;

create user if not exists ilona identified by 'ilona';

grant all if exists on Ilona.* to 'ilona';