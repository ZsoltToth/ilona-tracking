create table if not exists PasswordRecoveryTokens (
	userid varchar(20) not null,
    recoverytoken varchar(50) not null,
    validuntil timestamp(3) not null,
    constraint PK_PasswordRecoveryTokens primary key(userid, recoverytoken),
    constraint FK_PasswordRecoveryTokens_userid foreign key(userid) references trackingusers(userid)
    on delete cascade;
);