create table if not exists TrackingUsers(
	userid varchar(20) not  null,
    username varchar(30) not null,
    email varchar(350) not null,
    password varchar(80) not null,
    enabled tinyint(1) not null,
    lastlogindate timestamp not null, -- ezt engedi?!
    credentialsvaliduntil timestamp default now() not null, -- nem engedi not nullal, csak így defaulttal
    nonlocked tinyint(1) not null,
    lockeduntil timestamp default now() not null, -- nem engedi not nullal, csak így defaulttal
    constraint PK_TrackingUsers primary key(userid)
);