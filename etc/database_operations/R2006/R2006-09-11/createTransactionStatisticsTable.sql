CREATE TABLE `TRANSACTION_STATISTICS` (
  `SERVER` varchar(255) NOT NULL default '',
  `NUM_REPORT` int(11) NOT NULL,
  `NUM_READS` int(11) NOT NULL,
  `NUM_WRITES` int(11) NOT NULL,
  `NUM_ABORTS` int(11) NOT NULL,
  `NUM_CONFLICTS` int(11) NOT NULL,
  `SECONDS` int(11) NOT NULL,
  `STATS_WHEN` timestamp NOT NULL default CURRENT_TIMESTAMP
) ENGINE=MYISAM;