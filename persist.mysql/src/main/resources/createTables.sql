create table if not exists PasswordRecoveryTokens (
	userid varchar(20) not null,
    recoverytoken varchar(50) not null,
    validuntil timestamp(3) not null,
    constraint PK_PasswordRecoveryTokens primary key(userid, recoverytoken),
    constraint FK_PasswordRecoveryTokens_userid foreign key(userid) references trackingusers(userid)
    on delete cascade;
);

create table if not exists TrackingDevices(
	ownerid varchar(20) not null,
    deviceid varchar(50) not null,
    devicename varchar(50) not null,
    devicetype varchar(20) not null,
    devicetypename varchar(30) not null,
    constraint PK_TrackingDevices primary key(deviceid),
    constraint FK_TrackingDevicesOwnerid foreign key(ownerid) references TrackingUsers(userid)
    on delete cascade
);

create table if not exists TrackingLoginAttempts(
	userid varchar(20) not null,
    attempt timestamp not null,
    constraint PK_TrackingLoginAttempts primary key(userid,attempt),
    constraint FK_TrackingLoginAttemptsUserid foreign key(userid) references TrackingUsers(userid)
    on delete cascade
);

create table if not exists TrackingUserRoles(
	userid varchar(20) not null,
    userrole varchar(20) not null,
    constraint PK_TrackingUserRoles primary key(userid, userrole),
    constraint FK_TrackingUserRolesUserid foreign key(userid) references TrackingUsers(userid)
    on delete cascade
);

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

create table if not exists History(
	  id varchar(20) not null AUTO_INCREMENT,
    userid varchar (20),
    posId varchar(40),
    timestmp timestamp not null,
    foreign key (posId) references Position (posId),
    foreign key (userid) references TrackingUsers (userid),
    primary key (id)
);