package com.github.moncat.common.generator.constant;

import java.util.ArrayList;
import java.util.List;

public class GenerateConstant {

	//所需引用的BaseDao路径
	public static final String BASEDAO="com.github.moncat.common.dao.BaseDao";
	
	//所需引用的BaseService路径
	public static final String BASESERVICE="com.github.moncat.common.service.BaseService";
	
	//所需引用的BaseEntity路径
	public static final String BASEENTITY="com.github.moncat.common.entity.BaseEntity";
	
	//所需引用的id创建累类
	public static final String NEXTID_CLASS="com.github.moncat.common.generator.id.NextId";
	
	//所需引用的id创建累方法
	public static final String NEXTID_METHOD="getId";
	
	// 主键类型
	public static final String KET_TYPE="Long";
	
	//所需引用的字段中移除的字段
	@SuppressWarnings("serial")
	public static final List<String> EXCLUDE_FIELD = new ArrayList<String>(){
		{
			add("item_order");
			add("create_time");
			add("create_by");
			add("update_time");
			add("update_by");
			add("is_active");
			add("del_flg");
		}
	};
	
}

