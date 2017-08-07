package com.github.moncat.common.generator.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import com.github.moncat.common.generator.model.DaoImplModelGenerator;
import com.github.moncat.common.generator.model.DaoModelGenerator;

public class DaoPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();

		AbstractJavaGenerator daoGenerator = new DaoModelGenerator();
		daoGenerator.setContext(context);
		daoGenerator.setIntrospectedTable(introspectedTable);
		List<CompilationUnit> daoCompilationUnits2 = daoGenerator.getCompilationUnits();
		for (CompilationUnit compilationUnit : daoCompilationUnits2) {
			GeneratedJavaFile gjf = new GeneratedJavaFile(
					compilationUnit,
					context.getJavaModelGeneratorConfiguration().getTargetProject(),
					context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
					context.getJavaFormatter());
			list.add(gjf);
		}
		
		AbstractJavaGenerator daoImplGenerator = new DaoImplModelGenerator();
		daoImplGenerator.setContext(context);
		daoImplGenerator.setIntrospectedTable(introspectedTable);
		List<CompilationUnit> daoImplCompilationUnits2 = daoImplGenerator.getCompilationUnits();
		for (CompilationUnit compilationUnit : daoImplCompilationUnits2) {
			GeneratedJavaFile gjf = new GeneratedJavaFile(
					compilationUnit,
					context.getJavaModelGeneratorConfiguration().getTargetProject(),
					context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
					context.getJavaFormatter());
			list.add(gjf);
		}
		return list;
	}
}
