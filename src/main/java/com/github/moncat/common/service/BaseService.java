package com.github.moncat.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * 基础的Service接口
 * @author zyl
 */
public interface BaseService<T,ID extends Serializable> {
	
	/**
	 * 添加对象,如果要添加的对象没有设置Id或者Id为空字符串或者是空格，则添加数据之前会调用 generateId()方法设置Id
	 */
	void add(T entity);
	
	/**
	 * 批量插入，如果为空列表则直接返回
	 */
	void addInBatch(Collection<T> entityCollection);

	/**
	 * 删除对象
	 */
	int delete(T query);

	/**
	 * 根据Id删除对象
	 */
	int deleteById(ID id);
	
	/**
	 * 根据id，批量删除记录，如果传入的列表为null或为空列表则直接返回
	 */
	void deleteByIdInBatch(Collection<ID> idCollection);

	/**
	 * 更新对象，对象必须设置ID
	 */
	int updateById(T entity);

	/**
	 * 更新对象中已设置的字段，未设置的字段不更新
	 */
	int updateByIdSelective(T entity);

	/**
	 * 批量更新，改方法根据实体ID更新已设置的字段，未设置的字段不更新
	 */
	void updateInBatch(Collection<T> entityCollection);
	
	/**
	 * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
	 */
	T queryById(ID id);
	
	/**
	 * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
	 */
	<V extends T> V queryVoById(ID id);
	
	/**
	 * 查询一个对象，如果返回的结果多于一个对象将会抛出TooManyResultsException
	 */
	<V extends T> V queryOne(T query);
	
	/**
	 * 查询一个对象按照排序返回第一个对象
	 */
	<V extends T> V queryOne(T query,Sort sort);

	/**
	 * 查询记录数
	 */
	long queryCount(T query);
	
	/**
	 * 查询对象列表 ,查询所有 
	 */
	<V extends T> List<V> queryList();
	/**
	 * 查询对象列表
	 */
	<V extends T> List<V> queryList(T query);
	
	/**
	 * 查询对象列表
	 */
	<V extends T> List<V> queryList(T query,Sort sort);
	
	/**
	 *<pre>查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有</pre>
	 */
	<V extends T> Page<V> queryPageList(T query, Pageable pageable);
	
	<V extends T> Page<V> queryPageList(T query, Pageable pageable,Long total);
	
	/**
	 *<pre>查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有</pre>
	 */
	<V extends T> Page<V> queryPageList(T query, Pageable pageable,String sqlId,String sqlIdCount);
	
	/**
	 * 根据结果集中的一列作为key，将结果集转换成Map
	 */
	<K, V extends T> Map<K, V> queryMap(T query, String mapKey);
	
	/**
	 * 根据结果集中的一列作为key，将结果集转换成Map
	 */
	<K, V extends T> Map<K, V> queryMap(T query, String mapKey,String sqlId);

	/**
	 * 根据结果集中的一列作为key，将结果集转换成LinkedhashMap
	 */
	<K, V extends T> Map<K, V> queryMap(T query, String mapKey, Sort sort);

	/**
	 * 根据结果集中的一列作为key，将结果集转换成LinkedhashMap
	 */
	<K, V extends T> Map<K, V> queryMap(T query, String mapKey, Sort sort,String sqlId);
	
	/**
	 * 根据结果集中的一列作为key，将结果集转换成Map
	 */
	<K, V extends T> Map<K, V> queryMap(T query, String mapKey, Pageable pageable);
	
	/**
	 * 根据自定义sqlId查询自定义对象
	 */
	Object queryList(String sqlId,Object object);
}
