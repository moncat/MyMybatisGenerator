package com.github.moncat.common.generator.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import com.github.moncat.common.generator.model.ServiceImplModelGenerator;
import com.github.moncat.common.generator.model.ServiceModelGenerator;

public class ServicePlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();

		AbstractJavaGenerator serviceGenerator = new ServiceModelGenerator();
		serviceGenerator.setContext(context);
		serviceGenerator.setIntrospectedTable(introspectedTable);
		List<CompilationUnit> serviceCompilationUnits2 = serviceGenerator.getCompilationUnits();
		for (CompilationUnit compilationUnit : serviceCompilationUnits2) {
			GeneratedJavaFile gjf = new GeneratedJavaFile(
					compilationUnit,
					context.getJavaModelGeneratorConfiguration().getTargetProject(),
					context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
					context.getJavaFormatter());
			list.add(gjf);
		}
		
		AbstractJavaGenerator serviceImplGenerator = new ServiceImplModelGenerator();
		serviceImplGenerator.setContext(context);
		serviceImplGenerator.setIntrospectedTable(introspectedTable);
		List<CompilationUnit> serviceImplCompilationUnits2 = serviceImplGenerator.getCompilationUnits();
		for (CompilationUnit compilationUnit : serviceImplCompilationUnits2) {
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
