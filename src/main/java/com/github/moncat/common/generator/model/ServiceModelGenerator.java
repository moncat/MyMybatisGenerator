package com.github.moncat.common.generator.model;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import com.github.moncat.common.generator.constant.GenerateConstant;

public class ServiceModelGenerator extends AbstractJavaGenerator{
	public ServiceModelGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType().replace("entity", "service")+"Service");
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        
        StringBuilder rootInterface = new StringBuilder();
        rootInterface.append(GenerateConstant.BASESERVICE);
        rootInterface.append("<");
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
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface.toString());
            FullyQualifiedJavaType domainObjectNameType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
            interfaze.addImportedType(domainObjectNameType);
        }


        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(interfaze);
        
        return answer;
    }
}
