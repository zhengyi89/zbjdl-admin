package com.zbjdl.boss.admin;

import org.jmock.Mockery;

/**
 * <p>涉及业务逻辑的单元测试继承此类。本类有私有成员mockery，通过mocker来Mock被依赖的类</p>
 * @version:
 */
public abstract class BaseServiceTest {
	static {
		System.setProperty("mockrmi", "true");
		System.setProperty("mockconfig", "true");
	}
	
	protected Mockery mockery = new Mockery();
}
