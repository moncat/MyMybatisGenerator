package com.github.moncat.common.generator.plugins;

import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class SelectPlugin extends PluginAdapter {
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		
		

		//<select id="selectVoByPrimaryKey">
		XmlElement voByIdElements = getVoByIdElements(introspectedTable);
        context.getCommentGenerator().addComment(voByIdElements);
        document.getRootElement().addElement(voByIdElements);
        
        //<sql id="Query_Where_Clause">
        XmlElement queryWhereElements = getQueryWhereElements(introspectedTable);
        context.getCommentGenerator().addComment(queryWhereElements);
        document.getRootElement().addElement(queryWhereElements);
        
        //<select id="selectCount"
        XmlElement countElements = getCountElements(introspectedTable);
        context.getCommentGenerator().addComment(countElements);
        document.getRootElement().addElement(countElements);
        
        //<select id="select"
        XmlElement selectElements = getSelectElements(introspectedTable);
        context.getCommentGenerator().addComment(selectElements);
        document.getRootElement().addElement(selectElements);
        
        //<delete id="delete"
        XmlElement deleteElements = getDeleteElements(introspectedTable);
        context.getCommentGenerator().addComment(deleteElements);
        document.getRootElement().addElement(deleteElements);
        context.getCommentGenerator().addComment(deleteElements);
        
        document.getRootElement().addElement(new TextElement("<!--  ************************expand your SQL below the line***********************  -->"));

       //<resultMap id="VoResultMap"
  		XmlElement voResultMap = getVoResultMap(introspectedTable);
       context.getCommentGenerator().addComment(voResultMap);
       document.getRootElement().addElement(voResultMap);
              
      //<sql id="Vo_Column_List"
      XmlElement voColumn = getVoColumn(introspectedTable);
      context.getCommentGenerator().addComment(voColumn);
      document.getRootElement().addElement(voColumn);
        
      //<sql id="Vo_Where_Clause"
      XmlElement voWhereElements = getVoWhereElements(introspectedTable);
      context.getCommentGenerator().addComment(voWhereElements);
      document.getRootElement().addElement(voWhereElements);
      
    //<sql id="Table_Join_Clause"
      XmlElement tableJoinElements = getTableJoinElements(introspectedTable);
      context.getCommentGenerator().addComment(tableJoinElements);
      document.getRootElement().addElement(tableJoinElements);
      
        
        return true;
    }
	
	private XmlElement getVoResultMap(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("resultMap"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", "VoResultMap"));
        answer.addElement(new TextElement("<!--  <association property=\"beanName\" javaType=\"beanVoFullyQualifiedName\">  -->"));
        answer.addElement(new TextElement("<!--  <result column=\"dbField\" property=\"beanField\" jdbcType=\"dbFieldStyle\" /> -->"));
        answer.addElement(new TextElement("<!--  </association> -->"));
        String returnType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            returnType = introspectedTable.getRecordWithBLOBsType();
        } else {
            // table has BLOBs, but no BLOB class - BLOB fields must be
            // in the base class
            returnType = introspectedTable.getBaseRecordType();
        }
        
        //voType
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        returnType = returnType.replace(domainObjectName, "aide."+domainObjectName+"Vo");

        answer.addAttribute(new Attribute("type", returnType));

        answer.addAttribute(new Attribute("extends","BaseResultMap"));

        context.getCommentGenerator().addComment(answer);

        return answer;
    }
	
	private XmlElement getVoColumn(IntrospectedTable introspectedTable) {
		
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "Vo_Column_List"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = introspectedTable
                .getNonBLOBColumns().iterator();
        while (iter.hasNext()) {
        	sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
                    .next()));

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            answer.addElement(new TextElement(sb.toString()));
            sb.setLength(0);
        }

        if (sb.length() > 0) {
            answer.addElement((new TextElement(sb.toString())));
        }
        answer.addElement(new TextElement(" <!-- ,alias.field -->"));
        return answer;
    }
	
	private XmlElement getVoWhereElements(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", "Vo_Where_Clause"));
        
        answer.addElement(new TextElement("<!-- <if test=\"queryBeanField != null\" > -->"));
        answer.addElement(new TextElement("<!-- and dbField = #{queryBeanField,jdbcType=dbFieldStyle} -->"));
        answer.addElement(new TextElement("<!-- </if> -->"));
        
        return answer;
	}
	
	
	private XmlElement getQueryWhereElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "Query_Where_Clause")); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append("  and "); 
            sb.append("t."); 
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        
        //包含Vo
        XmlElement includeWhereElement = new XmlElement("include"); //$NON-NLS-1$
        includeWhereElement.addAttribute(new Attribute("refid", "Vo_Where_Clause"));
        dynamicElement.addElement(includeWhereElement);
        
        //sort
        XmlElement isShotNotNullElement = new XmlElement("if"); //$NON-NLS-1$
        isShotNotNullElement.addAttribute(new Attribute("test", "sorting != null"));
        sb.setLength(0);
        //order by ${sorting},foo_id desc
        sb.append("order by ${sorting}");
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
        	//主键
			IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
			sb.append(",t.");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
	        sb.append(" desc");
        }
        isShotNotNullElement.addElement(new TextElement(sb.toString()));
        answer.addElement(isShotNotNullElement);
        
        return answer;
    }
	
	private XmlElement getVoByIdElements(IntrospectedTable introspectedTable){
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$
		
		answer.addAttribute(new Attribute("id", "selectVoByPrimaryKey")); //$NON-NLS-1$
		answer.addAttribute(new Attribute("parameterType", "java.util.Map")); //$NON-NLS-1$
		answer.addAttribute(new Attribute("resultMap", "VoResultMap")); //$NON-NLS-1$
		
		context.getCommentGenerator().addComment(answer);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		answer.addElement(new TextElement(sb.toString()));
		XmlElement includeColumnElement = new XmlElement("include"); //$NON-NLS-1$
		includeColumnElement.addAttribute(new Attribute("refid", "Vo_Column_List"));
		answer.addElement(includeColumnElement);
		sb.setLength(0);
		sb.append("from ");
		sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		sb.append(" t");
		answer.addElement(new TextElement(sb.toString()));
		addTableJoinClause(answer);
		boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }
		
		return answer;
	}

	
	private XmlElement getCountElements(IntrospectedTable introspectedTable){
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "selectCount")); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", "java.util.Map")); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType", "java.lang.Long")); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
        	//主键
			IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
			sb.append("select count(t.");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
			sb.append(") from ");
        }else{
        	sb.append("select count(1) from ");
        }
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" t");
        answer.addElement(new TextElement(sb.toString()));
        addTableJoinClause(answer);
        XmlElement includeWhereElement = new XmlElement("include"); //$NON-NLS-1$
        includeWhereElement.addAttribute(new Attribute("refid", "Query_Where_Clause"));
        answer.addElement(includeWhereElement);

        return answer;
	}
	
	private XmlElement getSelectElements(IntrospectedTable introspectedTable){
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$
		
		answer.addAttribute(new Attribute("id", "select")); //$NON-NLS-1$
		answer.addAttribute(new Attribute("parameterType", "java.util.Map")); //$NON-NLS-1$
		answer.addAttribute(new Attribute("resultMap", "VoResultMap")); //$NON-NLS-1$
		
		context.getCommentGenerator().addComment(answer);
		
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		answer.addElement(new TextElement(sb.toString()));
		XmlElement includeColumnElement = new XmlElement("include"); //$NON-NLS-1$
		includeColumnElement.addAttribute(new Attribute("refid", "Vo_Column_List"));
		answer.addElement(includeColumnElement);
		sb.setLength(0);
		sb.append("from ");
		sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		sb.append(" t");
		answer.addElement(new TextElement(sb.toString()));
		addTableJoinClause(answer);
		XmlElement includeWhereElement = new XmlElement("include"); //$NON-NLS-1$
		includeWhereElement.addAttribute(new Attribute("refid", "Query_Where_Clause"));
		answer.addElement(includeWhereElement);
		
		return answer;
	}
	private XmlElement getDeleteElements(IntrospectedTable introspectedTable){
		XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$
		
		answer.addAttribute(new Attribute("id", "delete")); //$NON-NLS-1$
		answer.addAttribute(new Attribute("parameterType", "java.util.Map")); //$NON-NLS-1$
		
		context.getCommentGenerator().addComment(answer);
		
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		sb.append(" where ");
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
        	//主键
			IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
			sb.append(" in (");
			answer.addElement(new TextElement(sb.toString()));
			sb.setLength(0);
			sb.append("select t.");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
			sb.append(" from ( select * from ");			
			sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
			sb.append(") t");
			answer.addElement(new TextElement(sb.toString()));
			addTableJoinClause(answer);
			XmlElement includeWhereElement = new XmlElement("include"); //$NON-NLS-1$
			includeWhereElement.addAttribute(new Attribute("refid", "Query_Where_Clause"));
			answer.addElement(includeWhereElement);
			sb.setLength(0);
			sb.append(")");
			answer.addElement(new TextElement(sb.toString()));
        }else{
        	sb.append("1 = 2");
        	answer.addElement(new TextElement(sb.toString()));
    		sb.setLength(0);
        }
		
		return answer;
	}
	
	private XmlElement getTableJoinElements(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", "Table_Join_Clause"));
        answer.addElement(new TextElement("<!-- left join tableName tn on t.primaryKey=tn.foreignKey -->"));

        return answer;
	}
	
	
	private void addTableJoinClause(XmlElement answer) {
		//引用表之间的关联关系，例如左连接
		XmlElement includeJoinElement = new XmlElement("include"); //$NON-NLS-1$
		includeJoinElement.addAttribute(new Attribute("refid", "Table_Join_Clause"));
		answer.addElement(includeJoinElement);
	}
}
