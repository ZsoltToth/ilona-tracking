create table if not exists TrackingUserRoles(
	userid varchar(20) not null,
    userrole varchar(20) not null,
    constraint PK_TrackingUserRoles primary key(userid, userrole),
    constraint FK_TrackingUserRolesUserid foreign key(userid) references TrackingUsers(userid)
    on delete cascade
);