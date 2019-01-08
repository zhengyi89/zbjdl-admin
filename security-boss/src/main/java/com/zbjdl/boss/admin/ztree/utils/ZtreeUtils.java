package com.zbjdl.boss.admin.ztree.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.ztree.vo.ZtreeVO;


public class ZtreeUtils {

	/**
	 * @描述 构建Json数据
	 * @param selectFunctionUnit
	 * @param functionUnitListQuery
	 * @param disabled
	 * @param dependenceAndRefMap
	 * @return
	 */
	public static List<Map<String, Object>> buildJson(
			List<FunctionDTO> selectFunction,
			List<FunctionDTO> functionListQuery, Boolean disabled,
			Map<Long, Long[]>[] dependenceAndRefMap) {

		if (selectFunction == null || selectFunction.isEmpty()) {
			return null;
		}

		List<Map<String, Object>> zTreeJsonList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < selectFunction.size(); i++) {
			Map<Long, Long[]> dependenceMap = new HashMap<Long, Long[]>();
			Map<Long, Long[]> refMap = new HashMap<Long, Long[]>();
			if (dependenceAndRefMap != null && dependenceAndRefMap.length != 0) {
				// 取出该功能依赖的节点
				Long[] dependence = dependenceAndRefMap[0].get(selectFunction
						.get(i).getFunctionId());
				dependenceMap.put(selectFunction.get(i).getFunctionId(),
						dependence);
				// 取出该功能依赖节点的映射
				Long[] ref = dependenceAndRefMap[1].get(selectFunction.get(i)
						.getFunctionId());
				refMap.put(selectFunction.get(i).getFunctionId(), ref);
			}
			// 构建selectFunction中的所有功能
			if (functionListQuery == null || functionListQuery.isEmpty()) {
				// 构建Json对象
				Map<String, Object> zTreeJson = toTreeJson(selectFunction
						.get(i).getFunctionId(), selectFunction.get(i)
						.getPreFunctionId(), selectFunction.get(i)
						.getFunctionName(), false, disabled, dependenceMap,
						refMap);
				zTreeJsonList.add(zTreeJson);
			} else {
				// 构建selectFunction中的所有功能,在functionListQuery中存在checked
				// 为true否则false
				Boolean flag = false;
				for (FunctionDTO dto : functionListQuery) {
					if (dto.getFunctionId().equals(
							selectFunction.get(i).getFunctionId())) {
						Map<String, Object> zTreeJson = toTreeJson(
								selectFunction.get(i).getFunctionId(),
								selectFunction.get(i).getPreFunctionId(),
								selectFunction.get(i).getFunctionName(), true,
								disabled, dependenceMap, refMap);
						zTreeJsonList.add(zTreeJson);
						flag = true;
						break;
					}
				}
				if (!flag) {
					Map<String, Object> zTreeJson = toTreeJson(selectFunction
							.get(i).getFunctionId(), selectFunction.get(i)
							.getPreFunctionId(), selectFunction.get(i)
							.getFunctionName(), false, disabled, dependenceMap,
							refMap);
					zTreeJsonList.add(zTreeJson);
				}
			}

			if (i + 1 == selectFunction.size()) {
				break;
			}
		}
		// 构建JsonString
		return zTreeJsonList;

	}

	public static ZtreeVO toCreateJson(Long id, Long pId, String name,
			Boolean checked, Boolean chkDisabled, Map<Long, Long[]> dependence,
			Map<Long, Long[]> ref) {
		ZtreeVO zTreeJson = new ZtreeVO();
		zTreeJson.setChecked(checked);
		zTreeJson.setChkDisabled(chkDisabled);
		zTreeJson.setId(id);
		zTreeJson.setName(name);
		zTreeJson.setpId(pId);
		zTreeJson.setDependency(dependence);
		zTreeJson.setRef(ref);
		return zTreeJson;
	}

	public static Map<String, Object> toTreeJson(Long id, Long pId,
			String name, Boolean checked, Boolean chkDisabled,
			Map<Long, Long[]> dependence, Map<Long, Long[]> ref) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pId", pId);
		map.put("checked", checked);
		map.put("chkDisabled", chkDisabled);
		map.put("dependence", dependence);
		map.put("ref", ref);
		map.put("name", name);
		return map;
	}

	public static List<ZtreeVO> jsonMapToZtreeVOArray(
			List<Map<String, Object>> mapList) {

		List<ZtreeVO> list = new ArrayList<ZtreeVO>();

		for (Map<String, Object> map : mapList) {
			ZtreeVO ztreeVO = toCreateJson((Long) map.get("id"),
					(Long) map.get("pId"), (String) map.get("name"),
					(Boolean) map.get("checked"),
					(Boolean) map.get("chkDisabled"),
					new HashMap<Long, Long[]>(), new HashMap<Long, Long[]>());
			list.add(ztreeVO);
		}
		return list;
	}

	/**
	 * @描述 把所选节点转化成为Long[]
	 * @param node
	 * @return
	 */
	public static Long[] getLongArrayByStringNum(String node) {
		Long[] nodesLongs = null;
		String[] nodes = null;

		if (node == null || "".equals(node)) {
			return nodesLongs;
		}

		// if (node.indexOf(",") > 0) {
		nodes = node.split(",");
		// }

		if (nodes != null) {
			nodesLongs = new Long[nodes.length];
		}

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] == null || "".equals(nodes[i])) {
				continue;
			}
			nodesLongs[i] = Long.parseLong(nodes[i].trim());
		}
		return nodesLongs;

	}

}
