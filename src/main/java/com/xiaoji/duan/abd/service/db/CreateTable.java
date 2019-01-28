package com.xiaoji.duan.abd.service.db;

public class CreateTable extends AbstractSql {

	public CreateTable() {
		initDdl();
	}

	private void initDdl() {
		// alter table abd_group add column VERIFYTYPE varchar(128) DEFAULT 'WEIXIN' after VERIFY;
		// alter table abd_groupsa add column VERIFYTYPE varchar(128) DEFAULT 'WEIXIN' after VERIFY;
		// alter table abd_group add column SETTINGS text DEFAULT NULL after VERIFYTYPE;
		// alter table abd_groupsa add column SETTINGS text DEFAULT NULL after VERIFYTYPE;
		ddl.add("" + 
				"CREATE TABLE IF NOT EXISTS `abd_group` (" +
				"		  `NAME` varchar(256) NOT NULL," +
				"		  `FULLNAME` varchar(256) DEFAULT NULL," +
				"		  `SUBDOMAIN` varchar(64) NOT NULL," +
				"		  `LOGO` varchar(1024) DEFAULT NULL," +
				"		  `REGISTER` varchar(200) NOT NULL," +
				"		  `VERIFY` varchar(32) DEFAULT 'AUTOVERIFY'," +
				"		  `VERIFYTYPE` varchar(128) DEFAULT 'WEIXIN'," +
				"		  `SETTINGS` text DEFAULT NULL," +
				"		  `CREATE_TIME` date DEFAULT NULL," +
				"		  PRIMARY KEY (`SUBDOMAIN`)" +
				"		) ENGINE=InnoDB DEFAULT CHARSET=utf8;" +
				"");

		ddl.add("" + 
				"CREATE TABLE IF NOT EXISTS `abd_groupuser` (" +
				"		  `GROUP_NAME` varchar(256) NOT NULL," +
				"		  `GROUP_FULLNAME` varchar(256) DEFAULT NULL," +
				"		  `GROUP_SUBDOMAIN` varchar(64) NOT NULL," +
				"		  `USER_NAME` varchar(256) NOT NULL," +
				"		  `USER_ID` varchar(128) NOT NULL," +
				"		  `USER_TYPE` varchar(32) DEFAULT 'NORMAL'," +
				"		  `USER_VERIFY` varchar(32) DEFAULT 'VERIFIED'," +
				"		  `CREATE_TIME` date DEFAULT NULL," +
				"		  PRIMARY KEY (`GROUP_SUBDOMAIN`, `USER_ID`)" +
				"		) ENGINE=InnoDB DEFAULT CHARSET=utf8;" +
				"");

		ddl.add("" + 
				"CREATE TABLE IF NOT EXISTS `abd_groupsa` (" +
				"		  `GROUP_NAME` varchar(256) NOT NULL," +
				"		  `GROUP_FULLNAME` varchar(256) DEFAULT NULL," +
				"		  `GROUP_SUBDOMAIN` varchar(64) NOT NULL," +
				"		  `SA_NAME` varchar(256) DEFAULT NULL," +
				"		  `SA_PREFIX` varchar(3) NOT NULL," +
				"		  `SA_AUTHORIZE` varchar(32) DEFAULT 'AUTHORIZED'," +
				"		  `VERIFY` varchar(32) DEFAULT 'AUTOVERIFY'," +
				"		  `VERIFYTYPE` varchar(128) DEFAULT 'WEIXIN'," +
				"		  `SETTINGS` text DEFAULT NULL," +
				"		  `CREATE_TIME` date DEFAULT NULL," +
				"		  PRIMARY KEY (`GROUP_SUBDOMAIN`, `SA_PREFIX`)" +
				"		) ENGINE=InnoDB DEFAULT CHARSET=utf8;" +
				"");

		ddl.add("" + 
				"CREATE TABLE IF NOT EXISTS `abd_groupsauser` (" +
				"		  `GROUP_NAME` varchar(256) NOT NULL," +
				"		  `GROUP_FULLNAME` varchar(256) DEFAULT NULL," +
				"		  `GROUP_SUBDOMAIN` varchar(64) NOT NULL," +
				"		  `SA_NAME` varchar(256) DEFAULT NULL," +
				"		  `SA_PREFIX` varchar(3) NOT NULL," +
				"		  `USER_NAME` varchar(256) NOT NULL," +
				"		  `USER_ID` varchar(128) NOT NULL," +
				"		  `USER_TYPE` varchar(32) DEFAULT 'NORMAL'," +
				"		  `USER_VERIFY` varchar(32) DEFAULT 'VERIFIED'," +
				"		  `CREATE_TIME` date DEFAULT NULL," +
				"		  PRIMARY KEY (`GROUP_SUBDOMAIN`, `SA_PREFIX`, `USER_ID`)" +
				"		) ENGINE=InnoDB DEFAULT CHARSET=utf8;" +
				"");

	}
}
