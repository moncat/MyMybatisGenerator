package com.github.moncat.common.dao;

import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class ExpandMapResultHandler<K,V> implements ResultHandler{

	private final Map<K, V> mappedResults;
	  private final String mapKey;
	  private final ObjectFactory objectFactory;
	  private final ObjectWrapperFactory objectWrapperFactory;

	  /**
	   * ExpandResultHandler带参数构造方法
	   * @param mapKey map键
	   * @param objectFactory 对象创建工厂
	   * @param objectWrapperFactory 对象包装工厂
	   * @param clazz Map子接口或子类
	   */
	  @SuppressWarnings("unchecked")
	  public ExpandMapResultHandler(String mapKey, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory,Class<?> clazz) {
	    this.objectFactory = objectFactory;
	    this.objectWrapperFactory = objectWrapperFactory;
	    this.mappedResults = (Map<K, V>) objectFactory.create(clazz);
	    this.mapKey = mapKey;
	  }

	  @SuppressWarnings("unchecked")
	  public void handleResult(ResultContext context) {
		final V value = (V) context.getResultObject();
	    final MetaObject mo = MetaObject.forObject(value, objectFactory, objectWrapperFactory,null);
		final K key = (K) mo.getValue(mapKey);
	    mappedResults.put(key, value);
	  }

	  public Map<K, V> getMappedResults() {
	    return mappedResults;
	  }

}

