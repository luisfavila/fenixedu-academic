insert into EXECUTION_PATH (KEY_FUNCTIONALITY, EXECUTION_PATH, KEY_ROOT_DOMAIN_OBJECT) select CONTENT.ID_INTERNAL, CONTENT.EXECUTION_PATH, 1 from CONTENT where CONTENT.OJB_CONCRETE_CLASS = 'net.sourceforge.fenixedu.domain.functionalities.Functionality';

update CONTENT C, EXECUTION_PATH EP SET C.KEY_EXECUTION_PATH_VALUE=EP.ID_INTERNAL WHERE EP.KEY_FUNCTIONALITY=C.ID_INTERNAL;