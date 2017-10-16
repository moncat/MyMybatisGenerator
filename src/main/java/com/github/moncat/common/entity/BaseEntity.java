package com.github.moncat.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BaseEntity  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 排序  */
	private Byte itemOrder;
	
	 /** 创建时间  */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人 */
    private Long createBy;

    /** 更新时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 更新人 */
    private Long updateBy;

    /** 是否有效  1有效  0失效 */
    private Byte isActive;
    
    /** 是否删除  1已删除  0未删除 */
    private Byte delFlg;
}
