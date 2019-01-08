
-- 部门表
delete from TBL_DEPARTMENT;
-- 用户状态变化表
delete from TBL_USERSTATUSUPDATERECORD;
-- 功能表
delete from TBL_FUNCTION;
-- 菜单表
delete from TBL_MENU;
delete from TBL_DEPARTMENTANDFUNCTIONRELATION;
delete from TBL_ROLEANDFUNCTIONRELATION;
delete from TBL_ROLE;
delete from TBL_USERANDROLERELATION;
--双重权限表
delete from TBL_WORKITEM;

insert into TBL_USER
		(
			userid,
			loginName,
			password,
			userStatus,
			isSuperAdmin,
			isAdmin,
			pwdErrorNums,
			primaryDepartmentId
		)
		values
		(
			0,
			'feng',
			'test123',
			'ACTIVE',
			'0',
			'0',
			0,
			0
		);

insert into TBL_USER(loginName,password,userStatus,isSuperAdmin,isAdmin,pwdErrorNums,primaryDepartmentId)
values('sysadmin','sysadmin','ACTIVE','1','0',0,1);

insert into TBL_DEPARTMENT
		(
			departmentid,
			departmentcode,
			departmentname,
			departmentDesc
		)
		values
		(
			0,
			'tech',
			'技术部',
			'创造性解决问题的部门',
			'ACTIVE'
		);

insert into TBL_DEPARTMENT(departmentcode,departmentname,departmentDesc,departmentstatus)
values('1','业管部门','业务管理','ACTIVE');
insert into TBL_DEPARTMENT(departmentcode,departmentname,departmentDesc,departmentstatus)
values('2','客服部门','业务管理','ACTIVE');
insert into TBL_DEPARTMENT(departmentcode,departmentname,departmentDesc,departmentstatus)
values('3','结算部门','业务管理','ACTIVE');
insert into TBL_DEPARTMENT(departmentcode,departmentname,departmentDesc,departmentstatus)
values('4','财务部门','业务管理','ACTIVE');
insert into TBL_DEPARTMENT(departmentcode,departmentname,departmentDesc,departmentstatus)
values('5','技术部门','业务管理','ACTIVE');
		
insert into TBL_USERSTATUSUPDATERECORD
		(
			recordId,
			userId, 
			preStatus, 
			currentStatus, 
			adminUserId, 
			updateReason,
			update_date,
			update_time
		)
		values
		(
			0,
			0,
			'CHECK_PENDING',
			'ACTIVE',
			-1,
			'测试',
			'2011-06-27',
			'16:00:00'
		);
		
--insert into TBL_FUNCTION values (0,'测试功能0','http://www.test.com',1,'0,1,2','ACTIVE');
--insert into TBL_MENU values (0,'测试菜单0',0,0,1,0);

insert into TBL_ROLE(roleName,description,roleStatus)
values('sysadmin','system admin','ACTIVE');
insert into TBL_FUNCTION(functionName,functionUrl,preFunctionId,deptIds,functionStatus)
values('系统设置','http://www.test.com',null,null,'ACTIVE');
insert into TBL_FUNCTION(functionName,functionUrl,preFunctionId,deptIds,functionStatus)
values('订单查询','http://www.test.com',null,null,'ACTIVE');
insert into TBL_FUNCTION(functionName,functionUrl,preFunctionId,deptIds,functionStatus)
values('主题变更','http://www.test.com',null,null,'ACTIVE');
insert into TBL_FUNCTION(functionName,functionUrl,preFunctionId,deptIds,functionStatus)
values('密码变更','http://www.test.com',null,null,'ACTIVE');
insert into TBL_FUNCTION(functionName,functionUrl,preFunctionId,deptIds,functionStatus)
values('结算管理','http://www.test.com',null,null,'ACTIVE');
insert into TBL_FUNCTION(functionName,functionUrl,preFunctionId,deptIds,functionStatus)
values('用户管理','http://www.test.com',null,null,'ACTIVE');

insert into TBL_MENU(menuName,functionId,sequence,parentId,levelNum,iconName)
values('主页',1,1,0,1,'http://localhost:8080/employee-boss/img/menu/ico1.gif');

insert into TBL_USERANDROLERELATION(userId,roleId)
values(1,1);
insert into TBL_ROLEANDFUNCTIONRELATION(roleId,functionId)
values(1,1);
insert into TBL_ROLEANDFUNCTIONRELATION(roleId,functionId)
values(1,2);
insert into TBL_ROLEANDFUNCTIONRELATION(roleId,functionId)
values(1,3);
insert into TBL_ROLEANDFUNCTIONRELATION(roleId,functionId)
values(1,4);
insert into TBL_ROLEANDFUNCTIONRELATION(roleId,functionId)
values(1,5);
insert into TBL_ROLEANDFUNCTIONRELATION(roleId,functionId)
values(1,6);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(6,1);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(6,3);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(6,5);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(7,2);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(7,4);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(7,3);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(8,6);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(9,2);
insert into TBL_DEPARTMENTANDFUNCTIONRELATION(departmentId,functionId)
values(10,1);

commit;