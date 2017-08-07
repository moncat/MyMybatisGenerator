package com.github.moncat.common.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.github.moncat.common.generator.constant.GenerateConstant;


public class FieldCommentPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
	    IntrospectedTable introspectedTable) {
		topLevelClass.addAnnotation("@Data");
		topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper=true)");
		topLevelClass.addAnnotation("@ToString(callSuper=true)");
		topLevelClass.addImportedType("lombok.Data");
		topLevelClass.addImportedType("lombok.EqualsAndHashCode");
		topLevelClass.addImportedType("lombok.ToString");
		topLevelClass.addImportedType(GenerateConstant.BASEENTITY);
		topLevelClass.setSuperClass(GenerateConstant.BASEENTITY);
		topLevelClass.addImportedType("lombok.ToString");
		
	    return true;
	}
	
	//不生成get set方法  使用 lombok 生成
	@Override
	public boolean modelGetterMethodGenerated(Method method,
            TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable,
            Plugin.ModelClassType modelClassType) {
        return false;
    }
	@Override
	public boolean modelSetterMethodGenerated(Method method,
            TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable,
            Plugin.ModelClassType modelClassType) {
        return false;
    }
	
	@Override
	public boolean modelFieldGenerated(Field field,
			TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable,
			Plugin.ModelClassType modelClassType) {
		generateFiledComment(field,introspectedColumn);
		
		String actualColumnName = introspectedColumn.getActualColumnName();
		for (String excludeField : GenerateConstant.EXCLUDE_FIELD) {
			if(excludeField.equals(actualColumnName)){
				return false;
			}
		}
		
		return true;
	}
	
	private void generateFiledComment(Field field,IntrospectedColumn introspectedColumn){
		field.addJavaDocLine("/** "+introspectedColumn.getRemarks()+" */");
	}

}
