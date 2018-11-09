package com.github.moncat.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.github.moncat.common.dao.BaseDao;

/**
 * 基础Service服务接口实现类
 * @author zyl
 */
public abstract class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T,ID> {

	/**
	 * 获取基础数据库操作类
	 */
	protected abstract BaseDao<T,ID> getBaseDao();
	
	
	public void add(T entity) {
		getBaseDao().insert(entity);
	}
	
	
	public void addInBatch(Collection<T> entityCollection) {
		getBaseDao().insertInBatch(entityCollection);
	}

	
	public int delete(T query) {
		return getBaseDao().delete(query);
	}

	
	public int deleteById(ID id) {
		return getBaseDao().deleteById(id);
	}
	
	
	public void deleteByIdInBatch(Collection<ID> idCollection) {
		getBaseDao().deleteByIdInBatch(idCollection);
	}

	
	public int updateById(T entity) {
		return getBaseDao().updateById(entity);
	}

	
	public int updateByIdSelective(T entity) {
		return getBaseDao().updateByIdSelective(entity);
	}

	
	public void updateInBatch(Collection<T> entityCollection) {
		getBaseDao().updateInBatch(entityCollection);
	}
	
	
	public T queryById(ID id) {
		return getBaseDao().selectById(id);
	}
	
	
	public <V extends T> V queryVoById(ID id) {
		return getBaseDao().selectVoById(id);
	}
	
	
	public <V extends T> V queryOne(T query) {
		return getBaseDao().selectOne(query);
	}
	
	
	public <V extends T> V queryOne(T query,Sort sort) {
		Pageable pageable = new PageRequest(0,1,sort);
		Page<V> page = queryPageList(query,pageable);
		List<V> content = page.getContent();
		if(content != null &&  content.size() >0){
			return content.get(0);
		}else{
			return null;
		}
	}
	
	
	public long queryCount(T query) {
		return getBaseDao().selectCount(query);
	}
	
	
	public <V extends T> List<V> queryList() {
		return getBaseDao().selectList();
	}
	
	
	public <V extends T> List<V> queryList(T query) {
		return getBaseDao().selectList(query);
	}
	
	
	public <V extends T> List<V> queryList(T query,Sort sort) {
		return getBaseDao().selectList(query, sort);
	}
	
	
	public <V extends T> Page<V> queryPageList(T query, Pageable pageable) {
		return getBaseDao().selectPageList(query, pageable);
	}
	
	
	public <V extends T> Page<V> queryPageList(T query, Pageable pageable,Long total) {
		return getBaseDao().selectPageList(query, pageable,total);
	}
	
	
	public <V extends T> Page<V> queryPageList(T query, Pageable pageable, String sqlId,String sqlIdCount) {
		return getBaseDao().selectPageList(query, pageable,sqlId,sqlIdCount);
	}
	
	
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey) {
		return getBaseDao().selectMap(query, mapKey);
	}
	
	
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey,String sqlId){
		return getBaseDao().selectMap(query, mapKey, sqlId);
	};

	
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Sort sort){
		return getBaseDao().selectMap(query, mapKey, sort);
	};

	
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Sort sort,String sqlId){
		return getBaseDao().selectMap(query, mapKey, sort, sqlId);
	};
	
	
	
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Pageable pageable) {
		return getBaseDao().selectMap(query, mapKey, pageable);
	}
	
	
	public Object queryList(String sqlId, Object object) {
		return getBaseDao().selectList(sqlId,object);
	}
}
