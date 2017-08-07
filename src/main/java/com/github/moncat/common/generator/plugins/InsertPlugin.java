package com.github.moncat.common.generator.plugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import com.github.moncat.common.generator.constant.GenerateConstant;

public class InsertPlugin extends PluginAdapter {
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	/**
	 * 不生成insertSelective
	 */
	@Override
	public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	/**
	 * 给insert生成主键
	 * @param element
	 * @param introspectedTable
	 */
	public boolean sqlMapInsertElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		addPrimaryKey(element, introspectedTable);
		return true;
	}

	private void addPrimaryKey(XmlElement element,IntrospectedTable introspectedTable) {
		
		String primaryColumnName = null;
		
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
        	//主键
			IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
			
//	        element.addAttribute(new Attribute("useGeneratedKeys","true")); //$NON-NLS-1$
//	        element.addAttribute(new Attribute("keyColumn",MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn))); //$NON-NLS-1$
//	        element.addAttribute(new Attribute("keyProperty",primaryKeyColumn.getJavaProperty())); //$NON-NLS-1$
			primaryColumnName = primaryKeyColumn.getActualColumnName();
        }
        
        if(primaryColumnName == null){
        	try {
				throw new Exception("has no primary key, check your table please!");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        //移除原有记录
        element.getElements().removeAll(element.getElements());
        
        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$

        valuesClause.append("values ("); //$NON-NLS-1$

        List<String> valuesClauses = new ArrayList<String>();
        //去除主键
        //Iterator<IntrospectedColumn> iter = introspectedTable.getNonPrimaryKeyColumns()
        //加上主键 2017年7月19日
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
                .iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.isIdentity()) {
                // cannot set values on identity fields
                continue;
            }
            String actualColumnName = introspectedColumn.getActualColumnName();

            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            //判断是否为主键，为主键则自定义生成规则
            if(primaryColumnName.equals(actualColumnName)){
            	valuesClause.append("${@"+GenerateConstant.NEXTID_CLASS+"@"+GenerateConstant.NEXTID_METHOD+"()}");
            }else{
            	valuesClause.append(MyBatis3FormattingUtilities
            			.getParameterClause(introspectedColumn));
            }
            if (iter.hasNext()) {
                insertClause.append(", "); //$NON-NLS-1$
                valuesClause.append(", "); //$NON-NLS-1$
            }

            if (valuesClause.length() > 80) {
            	element.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        element.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
        	element.addElement(new TextElement(clause));
        }
	}
}
