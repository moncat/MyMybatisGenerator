package com.github.moncat.common.generator.model;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import com.github.moncat.common.generator.constant.GenerateConstant;

public class DaoImplModelGenerator extends AbstractJavaGenerator {
	public DaoImplModelGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		CommentGenerator commentGenerator = context.getCommentGenerator();

		String domainObjectName = introspectedTable.getFullyQualifiedTable()
				.getDomainObjectName();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(
				introspectedTable.getBaseRecordType()
						.replace("entity", "dao")
						.replace(domainObjectName, "impl." + domainObjectName)
						+ "DaoImpl");
		TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        
        StringBuilder rootInterface = new StringBuilder();
        rootInterface.append(GenerateConstant.BASEDAO);
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
            FullyQualifiedJavaType implType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType().replace("entity", "dao")+"Dao");

            topLevelClass.setSuperClass(superClass);
            topLevelClass.addSuperInterface(implType);
            topLevelClass.addImportedType(superClass);
            topLevelClass.addImportedType(domainObjectNameType);
            topLevelClass.addImportedType(implType);
        }
        topLevelClass.addAnnotation("@Repository");
        topLevelClass.addImportedType("org.springframework.stereotype.Repository");
        
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(topLevelClass);

		return answer;
	}
}
