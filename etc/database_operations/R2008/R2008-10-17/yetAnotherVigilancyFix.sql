update EVALUATION SET VIGILANTS_REPORT=0;
update VIGILANCY V, EVALUATION E SET E.VIGILANTS_REPORT=1 WHERE V.STATUS='ATTENDED' AND V.KEY_WRITTEN_EVALUATION=E.ID_INTERNAL;