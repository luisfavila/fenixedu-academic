
alter table PROFESSORSHIP add column KEY_TEACHING_INQUIRY int(11);
alter table PROFESSORSHIP add index (KEY_TEACHING_INQUIRY);
-- alter table TEACHING_INQUIRY add column GLOBAL_CLASSIFICATION_OF_THIS_C_U int(11);
alter table TEACHING_INQUIRY add column STRONG_POINTS_OF_C_U_TEACHING_PROCESS text;
alter table TEACHING_INQUIRY add column TEACHING_LANGUAGE text;
alter table TEACHING_INQUIRY add column WEAK_POINTS_OF_C_U_TEACHING_PROCESS text;

