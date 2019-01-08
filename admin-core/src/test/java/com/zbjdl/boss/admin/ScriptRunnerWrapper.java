package com.zbjdl.boss.admin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;

/**
 * <p>本类负责DAO部分的单元测试中，在测试开时装入测试数据。使用ibatis的ScriptRunner来执行初始化数据脚本，并向测试数据库写入数据。</p>
 * 本类被com.zbjdl.boss.admin.BaseDaoTest使用
 * @version:
 */
public class ScriptRunnerWrapper {
	
	private DataSource dataSource;

	private ScriptRunner scriptRunner;
	
	private List<String> scripts;
	
	private boolean autoCommit = false;
	
	private boolean stopOnError = false;
	
	public void initTestData() throws IOException, SQLException {
		Connection conn = dataSource.getConnection();
		scriptRunner = new ScriptRunner(conn);
		scriptRunner.setAutoCommit(autoCommit);
		scriptRunner.setStopOnError(stopOnError);
		scriptRunner.setSendFullScript(false);
		for (String script : scripts) {
			Reader initDataReader = new InputStreamReader(ScriptRunnerWrapper.class.getResourceAsStream(script), "UTF-8");
			scriptRunner.runScript(initDataReader);
		}
	}
	
	public void release() {
		scriptRunner.closeConnection();
	}
	
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public void setStopOnError(boolean stopOnError) {
		this.stopOnError = stopOnError;
	}

	public void setScripts(List<String> scripts) {
		this.scripts = scripts;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
