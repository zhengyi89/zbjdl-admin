package com.zbjdl.boss.admin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import com.zbjdl.common.utils.cache.remote.RemoteCacheUtils;

public class StartupRunner implements CommandLineRunner {
	protected final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

	// @Autowired
	// private DataSource dataSource;
	@Override
	public void run(String... strings) throws Exception {
//		ConfigurationUtils.init();
		RemoteCacheUtils.init();
		// logger.info("### dataSource:"+dataSource);

	}
}