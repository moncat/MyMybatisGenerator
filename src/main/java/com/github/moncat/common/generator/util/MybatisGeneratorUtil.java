package com.github.moncat.common.generator.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGeneratorUtil {

	public static void main(String[] args) {
		ge();
	}
	public static void ge() {
		ge(true,true,true); 
	}
	public static void ge(Boolean servicePlugin,Boolean daoPlugin,Boolean aidePlugin) {
		try {
			System.out.println("start generator ...");
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
//			URL resource = MybatisGeneratorUtil.class.getResource("/generatorConfig.xml");
//			File configFile = new File(resource.getFile());
			InputStream configFile = MybatisGeneratorUtil.class.getClassLoader().getResourceAsStream("generatorConfig.xml");
			String str = convertStreamToString(configFile);
			if(!servicePlugin){
				str = str.replace("<plugin type=\"com.github.moncat.common.generator.plugins.ServicePlugin\"/>", "");
			}
			if(!daoPlugin){
				str = str.replace("<plugin type=\"com.github.moncat.common.generator.plugins.DaoPlugin\"/>", "");
			}
			if(!aidePlugin){
				str = str.replace("<plugin type=\"com.github.moncat.common.generator.plugins.AidePlugin\"/> ", "");
			}
			configFile = new ByteArrayInputStream(str.getBytes("UTF-8"));  
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			System.out.println("end generator!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String convertStreamToString(InputStream is) {      
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
     
        return sb.toString();      
     }
}