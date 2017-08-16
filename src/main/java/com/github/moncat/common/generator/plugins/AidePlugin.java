package com.github.moncat.common.generator.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import com.github.moncat.common.generator.model.QueryModelGenerator;
import com.github.moncat.common.generator.model.VoModelGenerator;

public class AidePlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
	


	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();

		AbstractJavaGenerator queryGenerator = new QueryModelGenerator();
		queryGenerator.setContext(context);
		queryGenerator.setIntrospectedTable(introspectedTable);
		List<CompilationUnit> queryCompilationUnits2 = queryGenerator.getCompilationUnits();
		for (CompilationUnit compilationUnit : queryCompilationUnits2) {
			GeneratedJavaFile gjf = new GeneratedJavaFile(
					compilationUnit,
					context.getJavaModelGeneratorConfiguration().getTargetProject(),
					context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
					context.getJavaFormatter());
			list.add(gjf);
		}
		
		AbstractJavaGenerator voGenerator = new VoModelGenerator();
		voGenerator.setContext(context);
		voGenerator.setIntrospectedTable(introspectedTable);
		List<CompilationUnit> voCompilationUnits2 = voGenerator.getCompilationUnits();
		for (CompilationUnit compilationUnit : voCompilationUnits2) {
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
