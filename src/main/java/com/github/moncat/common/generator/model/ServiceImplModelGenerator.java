package com.github.moncat.common.generator.model;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import com.github.moncat.common.generator.constant.GenerateConstant;

public class ServiceImplModelGenerator extends AbstractJavaGenerator {
	public ServiceImplModelGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		CommentGenerator commentGenerator = context.getCommentGenerator();

		String domainObjectName = introspectedTable.getFullyQualifiedTable()
				.getDomainObjectName();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(
				introspectedTable.getBaseRecordType()
						.replace("entity", "service")
						.replace(domainObjectName, "impl." + domainObjectName)
						+ "ServiceImpl");
		TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        
        StringBuilder rootInterface = new StringBuilder();
        rootInterface.append(GenerateConstant.BASESERVICE);
        rootInterface.append("Impl<");
        rootInterface.append(domainObjectName);
        rootInterface.append(",");
        
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
        	//主键
			IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
			rootInterface.append(primaryKeyColumn.getFullyQualifiedJavaType().getShortName());
        }else{
        	rootInterface.append(GenerateConstant.KET_TYPE);
        }
        rootInterface.append(">");
        
        if (stringHasValue(rootInterface.toString())) {
        	FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(rootInterface.toString());
            FullyQualifiedJavaType domainObjectNameType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            FullyQualifiedJavaType implType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType().replace("entity", "service")+"Service");
            FullyQualifiedJavaType daoClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType().replace("entity", "dao")+"Dao");

            topLevelClass.setSuperClass(superClass);
            topLevelClass.addSuperInterface(implType);
            topLevelClass.addImportedType(superClass);
            topLevelClass.addImportedType(domainObjectNameType);
            topLevelClass.addImportedType(implType);
            topLevelClass.addImportedType(daoClass);
        }
        topLevelClass.addAnnotation("@Service");
        topLevelClass.addImportedType("org.springframework.stereotype.Service");

        //Field方法
        addDaoField(topLevelClass);
        
        //getBaseDao方法
        addGetBaseDaoMethod(topLevelClass);
        
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(topLevelClass);

		return answer;
	}
	
	private void addGetBaseDaoMethod(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        //method.setConstructor(true);
        method.setName("getBaseDao");
        method.addAnnotation("@Override");
        StringBuilder sb = new StringBuilder();
        sb.append(GenerateConstant.BASEDAO);
        sb.append("<");
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        sb.append(domainObjectName);
        sb.append(",");
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
        	//主键
			IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
			sb.append(primaryKeyColumn.getFullyQualifiedJavaType().getShortName());
        }else{
        	sb.append("Integer");
        }
        sb.append(">");
        FullyQualifiedJavaType baseDao = new FullyQualifiedJavaType(sb.toString());
        topLevelClass.addImportedType(baseDao);
        method.setReturnType(baseDao);
        context.getCommentGenerator().addGeneralMethodComment(method,introspectedTable);
        sb.setLength(0);
        sb.append("return ");
        sb.append(getClassName(domainObjectName));
        sb.append("Dao");
        sb.append(";");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }
	
	private void addDaoField(TopLevelClass topLevelClass) {
		Field field = new Field();
		field.addAnnotation("@Resource");
		topLevelClass.addImportedType("javax.annotation.Resource");
		field.setVisibility(JavaVisibility.PRIVATE);
        FullyQualifiedJavaType daoClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType().replace("entity", "dao")+"Dao");
		field.setType(daoClass);
		String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
		field.setName(getClassName(domainObjectName)+"Dao");
		
		topLevelClass.addField(field);
	}
	
	private static String getClassName(String className){
		String first = className.substring(0, 1).toLowerCase();
		return first+className.substring(1);
	}
}
