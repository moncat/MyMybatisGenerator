package com.github.moncat.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * 基础Dao接口
 * @author cuiwei 
 * @author zyl
 * cuiwei, myfriend in  ORG Packaging Co., Ltd. 
 */
public interface BaseDao<T,ID extends Serializable>{
	
	void insert(T entity);

	int delete(T query);

	int deleteById(ID id);

	int updateById(T entity);

	int updateByIdSelective(T entity);

	void deleteByIdInBatch(Collection<ID> idCollection);

	void insertInBatch(Collection<T> entityCollection);

	void updateInBatch(Collection<T> entityCollection);
	
	T selectById(ID id);
	
	T selectById(ID id,String sqlId);
	
	<V extends T> V selectVoById(ID id);
	
	<V extends T> V selectVoById(ID id,String sqlId);

	<V extends T> V selectOne(T query);
	
	<V extends T> V selectOne(T query,String sqlId);
	
	long selectCount(T query);
	
	long selectCount(T query,String sqlId);
	
	<V extends T> List<V> selectList();
	
	<V extends T> List<V> selectList(T query);
	
	<V extends T> List<V> selectList(T query,String sqlId);
	
	<V extends T> List<V> selectList(T query,Sort sort);

	<V extends T> List<V> selectList(T query, Sort sort, String sqlId);

	<V extends T> Page<V> selectPageList(T query, Pageable pageable);
	
	<V extends T> Page<V> selectPageList(T query, Pageable pageable,Long total);
	
	<V extends T> Page<V> selectPageList(T query, Pageable pageable,String sqlId,String sqlIdCount);
	
	<K, V extends T> Map<K, V> selectMap(T query, String mapKey);

	<K, V extends T> Map<K, V> selectMap(T query, String mapKey,String sqlId);

	<K, V extends T> Map<K, V> selectMap(T query, String mapKey, Sort sort);

	<K, V extends T> Map<K, V> selectMap(T query, String mapKey, Sort sort,String sqlId);
	
	<K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable);

	<K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable,String sqlId);
	
	Object selectList(String sqlId,Object object);
}
