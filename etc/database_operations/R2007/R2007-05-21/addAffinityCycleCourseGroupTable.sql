CREATE TABLE AFFINITY_CYCLE_COURSE_GROUP (
  ID_INTERNAL int(11) unsigned NOT NULL auto_increment,
  KEY_CYCLE_COURSE_GROUP_SOURCE_AFFINITIES int(11) unsigned NOT NULL,
  KEY_CYCLE_COURSE_GROUP_DESTINATION_AFFINITIES int(11) unsigned NOT NULL,   
  PRIMARY KEY  (ID_INTERNAL),
  KEY (KEY_CYCLE_COURSE_GROUP_SOURCE_AFFINITIES),
  KEY (KEY_CYCLE_COURSE_GROUP_DESTINATION_AFFINITIES)
) ENGINE=InnoDB;