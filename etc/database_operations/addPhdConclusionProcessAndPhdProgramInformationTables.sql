create table `PHD_CONCLUSION_PROCESS` (`OID_PHD_PROCESS` bigint unsigned, `CONCLUSION_DATE` text, `OID` bigint unsigned, `OID_RESPONSIBLE` bigint unsigned, `VERSION` int(11), `WHEN_CREATED` timestamp NULL default NULL, `FINAL_GRADE` text, `STUDY_PLAN_ECTS_CREDITS` text, `THESIS_ECTS_CREDITS` text, `OID_ROOT_DOMAIN_OBJECT` bigint unsigned, `ID_INTERNAL` int(11) NOT NULL auto_increment, primary key (ID_INTERNAL), index (OID), index (OID_PHD_PROCESS), index (OID_RESPONSIBLE), index (OID_ROOT_DOMAIN_OBJECT)) ENGINE=InnoDB, character set latin1;

create table `PHD_PROGRAM_INFORMATION` (`MIN_STUDY_PLAN_ECTS_CREDITS` int(11), `OID` bigint unsigned, `MAX_STUDY_PLAN_ECTS_CREDITS` int(11), `MAX_THESIS_ECTS_CREDITS` int(11), `BEGIN_DATE` timestamp NULL default NULL, `OID_PHD_PROGRAM` bigint unsigned, `OID_ROOT_DOMAIN_OBJECT` bigint unsigned, `MIN_THESIS_ECTS_CREDITS` int(11), `ID_INTERNAL` int(11) NOT NULL auto_increment, primary key (ID_INTERNAL), index (OID), index (OID_PHD_PROGRAM), index (OID_ROOT_DOMAIN_OBJECT)) ENGINE=InnoDB, character set latin1;
