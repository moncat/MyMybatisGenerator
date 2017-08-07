package com.github.moncat.common.generator.model;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

public class QueryModelGenerator extends AbstractJavaGenerator{
	public QueryModelGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        
        String domainObjectName = introspectedTable.getFullyQualifiedTable()
				.getDomainObjectName();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
				introspectedTable.getBaseRecordType()
						.replace(domainObjectName, "aide." + domainObjectName+"Query"));
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        
        if (stringHasValue(domainObjectName)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            topLevelClass.setSuperClass(fqjt);
            topLevelClass.addImportedType(fqjt);
        }

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(topLevelClass);
        
        return answer;
    }
}
