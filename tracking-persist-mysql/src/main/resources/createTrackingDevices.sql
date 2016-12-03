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