/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.department.action;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ModelDriven;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.department.vo.DepartmentVO;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.DepartmentStatusEnum;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.boss.admin.ztree.utils.ZtreeUtils;
import com.zbjdl.common.json.JSONUtils;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

/**
 * 
 * 类名称：EditDepartmentAction <br>
 * 类描述： 部门编辑Action<br>
 * 
 * @author：feng
 * @since：2011-7-27 上午10:10:42
 * @version:
 * 
 */
public class EditDepartmentAction extends EmployeeBossBaseAction implements
		ModelDriven<DepartmentVO> {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门VO对象
	 */
	private DepartmentVO departmentVO;

	/**
	 * 更新部门信息
	 */
	public String execute() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							departmentVO.getPassword());
			DepartmentDTO deptDTO = remoteService.getUserFacade()
					.queryDepartmentById(departmentVO.getDepartmentId());
			deptDTO.setDepartmentName(departmentVO.getDepartmentName());
			deptDTO.setDepartmentDesc(departmentVO.getDepartmentDesc());
			deptDTO.setDepartmentId(departmentVO.getDepartmentId());
			deptDTO.setPortal(departmentVO.isPortal());
			Long[] node = null;
			if (departmentVO.getFunctionIds() != null
					&& !"".equals(departmentVO.getFunctionIds())) {
				node = ZtreeUtils.getLongArrayByStringNum(departmentVO
						.getFunctionIds());
			}
			if (!passwordRightOrNot) {
				departmentVO.setErrMsg("密码错误!");
			} else {
				// 修改部门 以及 对应功能
				remoteService.getSecurityConfigFacade()
						.configDepartmentFunction(deptDTO, node);
			}
		} catch (Exception e) {
			handleException(e, departmentVO);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 描述： 添加部门
	 * 
	 * @return 跳转页面
	 */
	public String add() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							departmentVO.getPassword());
			DepartmentDTO departmentDTO = new DepartmentDTO();
			BeanUtils.copyProperties(departmentVO, departmentDTO);
			departmentDTO.setDepartmentStatus(DepartmentStatusEnum.ACTIVE);
			Long[] node = null;
			if (departmentVO.getFunctionIds() != null
					&& !"".equals(departmentVO.getFunctionIds())) {
				node = ZtreeUtils.getLongArrayByStringNum(departmentVO
						.getFunctionIds());
			}
			if (!passwordRightOrNot) {
				departmentVO.setErrMsg("密码错误!");
			} else {
				// 修改部门 以及 对应功能
				remoteService.getSecurityConfigFacade()
						.configDepartmentFunction(departmentDTO, node);
			}
		} catch (Exception e) {
			handleException(e, departmentVO);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 描述： 初始化部门编辑页面
	 * 
	 * @return 跳转页面
	 */
	public String init() {
		if ("edit".equals(departmentVO.getType())) {
			DepartmentDTO departmentDTO = remoteService.getUserFacade()
					.queryDepartmentById(departmentVO.getDepartmentId());
			BeanUtils.copyProperties(departmentDTO, departmentVO);
		}

		List<FunctionDTO> functionList = remoteService
				.getSecurityConfigFacade().queryDisplayFunction();
		List<FunctionDTO> departnmentFunctions = Lists.newArrayList();
		if (departmentVO.getDepartmentId() != null) {
			departnmentFunctions = remoteService.getSecurityConfigFacade()
					.queryFunctionByDepartment(departmentVO.getDepartmentId());
		}

		Map<Long, Long[]>[] dependenceAndRefMap = remoteService
				.getSecurityConfigFacade().getDependenceRelation();

		List<Map<String, Object>> zTreeJson = ZtreeUtils.buildJson(
				functionList, departnmentFunctions, false, dependenceAndRefMap);

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("zTreeJson", JSONUtils.toJsonString(zTreeJson));

		return SUCCESS;
	}

	/**
	 * 
	 * 描述：删除部门
	 * 
	 * @return 跳转页面
	 */
	public String delete() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			/*
			 * try {
			 * remoteService.getSecurityConfigFacade().deleteDepartment(
			 * departmentVO.getDepartmentId()); } catch (BaseException
			 * e) { throw new BaseException("当前部门存在人员，不能删除！"); }
			 */
			if (!passwordRightOrNot) {
				departmentVO.setErrMsg("密码错误!");
			} else {
				remoteService.getSecurityConfigFacade().deleteDepartment(
						departmentVO.getDepartmentId());
			}
		} catch (Exception e) {
			handleException(e, departmentVO);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 描述：验证部门名称是否唯一
	 * 
	 * @return 跳转页面
	 */
	public String validateDeptNameUnique() {

		String deptName = departmentVO.getDepartmentName();

		DepartmentDTO query = new DepartmentDTO();
		query.setDepartmentName(deptName);
		List<DepartmentDTO> result = remoteService.getUserFacade()
				.queryDepartments(query);
		departmentVO.setDeptNameUnique(result.isEmpty());
		return SUCCESS;
	}

	/**
	 * 
	 * 描述： 验证部门名称和编码是否唯一
	 * 
	 * @return 跳转页面
	 */
	public String validateDeptNameAndCodeUnique() {

		String deptName = departmentVO.getDepartmentName();
		String deptCode = departmentVO.getDepartmentCode();

		DepartmentDTO nameQuery = new DepartmentDTO();
		nameQuery.setDepartmentName(deptName);
		List<DepartmentDTO> nameResult = remoteService.getUserFacade()
				.queryDepartments(nameQuery);
		departmentVO.setDeptNameUnique(nameResult.isEmpty());

		DepartmentDTO codeQuery = new DepartmentDTO();
		codeQuery.setDepartmentCode(deptCode);
		List<DepartmentDTO> codeResult = remoteService.getUserFacade()
				.queryDepartments(codeQuery);
		departmentVO.setDeptCodeUnique(codeResult.isEmpty());
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public DepartmentVO getModel() {
		departmentVO = new DepartmentVO();
		return departmentVO;
	}

	/**
	 * departmentVO
	 * 
	 * @return the departmentVO
	 */
	public DepartmentVO getDepartmentVO() {
		return departmentVO;
	}

	/**
	 * 描述： 部门VO对象
	 * 
	 * @param departmentVO
	 *            the departmentVO to set
	 */
	public void setDepartmentVO(DepartmentVO departmentVO) {
		this.departmentVO = departmentVO;
	}

}
