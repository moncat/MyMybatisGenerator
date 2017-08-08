package com.github.moncat.common.dao;

import java.util.LinkedHashMap;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class DefaultMapFactory extends DefaultObjectFactory {

	private static final long serialVersionUID = 665746939666922500L;

	protected Class<?> resolveInterface(Class<?> type) {
		Class<?> classToCreate;
		//可以添加多个Map子接口或接口实现类
		if (type == LinkedHashMap.class) {
			classToCreate = LinkedHashMap.class;
		} else {
			classToCreate = type;
		}
		return classToCreate;
	}
}

