
select count(*) from sys_dept;
select * from sys_dept limit 8, 11;


select count(*) from nginx_access;
select * from nginx_access limit 3, 11;

show tables;



 select * from QRTZ_LOCKS;
 
 
 DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE IF NOT EXISTS `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;