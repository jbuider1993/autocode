package com.brain.ca;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.MathTool;
import com.brain.ca.config.Config;
import com.brain.ca.template.TempPackage;
import com.brain.ca.template.VelocityGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
	public static void main(String[] args) {
		//String configFile = "C:/AutoTemplates/SpringBoot/templatev1/init_Spring.example.vm.json";
		processCommdLine(args);
	    //genreateCode(configFile);
	}
	
	public static void generateCodeByTemplateFile(String configFile) {
		ObjectMapper mapper = new ObjectMapper();

		TempPackage tempPackage = null;
		try {
			tempPackage = mapper.readValue(new File(configFile), TempPackage.class);
			generateCodeByPackage(tempPackage, null);			
		} catch(JsonParseException e) {
			e.printStackTrace();
		} catch(JsonMappingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void genreateCode(String templateConfigFile) {
		Map<String, Object> context = new HashMap<>();
		ObjectMapper mapperObj = new ObjectMapper();
		context.put("GenUtils", com.brain.ca.template.GenUtils.class);
		context.put("JDBCUtil", com.brain.ca.database.JDBCUtil.class);
		context.put("StringUtils", org.apache.commons.lang3.StringUtils.class);
		context.put("LogOut", com.brain.ca.output.LogOut.class);
		context.put("mapperObj", mapperObj);
		context.put("math",new MathTool()); 
		context.put("dollar", "$");
		context.put("d", "$");
		context.put("pound", "#");
		context.put("p", "#");
		context.put("newline", "\n");	
		generateCodeByTemplateFile(templateConfigFile, context);
	}
	
	public static void generateCodeByTemplateFile(String configFile,  Map<String, Object> context) {
		ObjectMapper mapper = new ObjectMapper();

		TempPackage tempPackage = null;
		try {
			tempPackage = mapper.readValue(new File(configFile), TempPackage.class);			
			generateCodeByPackage(tempPackage, context);				
			
		} catch(JsonParseException e) {
			e.printStackTrace();
		} catch(JsonMappingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
		
	public static void generateCodeByPackage(TempPackage tempPackage, Map<String, Object> context) {		
		String tempPath;
		String targetPath;		
		Map<String, Object> pubContext = tempPackage.getContext();
		tempPath = tempPackage.getTemplateBase();
		targetPath = tempPackage.getTargetBase();
		if (pubContext != null && context != null) {
			pubContext.putAll(context);	
		} else if (context != null) {
			pubContext = context;
		}
		if (tempPackage.getTemplateTargets() != null) {
			for (Map.Entry < String, String > tempTarget: tempPackage.getTemplateTargets().entrySet()) {
				String tempFile = margeFilePath(tempPath, tempTarget.getKey()); 
				String targetFile = margeFilePath(targetPath, tempTarget.getValue());
				generateSourceCode(pubContext, margeFilePath(tempPath, tempTarget.getKey()), targetFile);
				if (StringUtils.endsWith(targetFile, Config.suffixOfConfig)) {
					generateCodeByTemplateFile(targetFile, pubContext);
				}
			}
		}
		if ( tempPackage.getPackages()  != null ) {
			for (TempPackage subPackage : tempPackage.getPackages()) {
				generateCodeByPackage(subPackage, pubContext);
			}
		}				
	}
	

	public static void generateSourceCode(Map < String, Object > context, String templateFile, String targetFile) {
		VelocityGenerator generator = new VelocityGenerator();
		VelocityContext velocityContext = new VelocityContext(context);
		generator.evaluateTempack(velocityContext, templateFile, targetFile);
	}

	
	public static void loadJson(String fileName, String templateFile, String targetFile) {
		ObjectMapper mapper = new ObjectMapper();
		Map < String, Object > configProperty = null;
		try {
			configProperty = mapper.readValue(new File(fileName), Map.class);
		} catch(JsonParseException e) {
			e.printStackTrace();
		} catch(JsonMappingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		generateSourceCode(configProperty, templateFile, targetFile);
	}
	
	private static String margeFilePath(String path, String fileName) {
		return path + "/" + fileName;
	}
	
		
	public static String getPath(String basePath, List < String > parameters) {
		String returnValue = "/" + basePath;
		for (String item: parameters) {
			returnValue = returnValue + String.format("/{%s}", item);
		}

		return returnValue;
	}

	public static String getMethod(String method, List < String > parameters) {
		String returnValue = "method(%s)";
		String params = "";
		for (String item: parameters) {
			if (params.isEmpty()) {
				params = String.format("@PathParam(\"%s\") String %s", item, item);
			} else {
				params = params + ", " + String.format("@PathParam(\"%s\") String %s", item, item);
			}
		}
		return String.format(returnValue, params);
	}

	public static String getMethodWithParameters(String method, List < String > parameters) {
		String returnValue = "method(%s)";
		String params = "";
		for (String item: parameters) {
			if (params.isEmpty()) {
				params = String.format("String %s", item, item);
			} else {
				params = params + ", " + String.format("String %s", item, item);
			}
		}
		return String.format(returnValue, params);
	}

	public static String getParametersList(List < String > parameters) {
		String params = "";
		for (String item: parameters) {
			if (params.isEmpty()) {
				params = String.format("%s", item, item);
			} else {
				params = params + ", " + String.format(" %s", item, item);
			}
		}
		return params;
	}
		
	private static void processCommdLine(String[] args) {    
		String dislplayMessage = "";
	    String helpComm = "-help";
	    String foldComm = "-path";
	    String outPath = "./";
	    
		String currentFile = "init_Angular.vm.json";
	       		
		if (args.length > 0) {
			   if (args[0].equalsIgnoreCase(helpComm)) {
				   dislplayMessage = "\n" + 
			                         "              -help  list commandlines \n" +  
			                         "              -path  source files and output directory \n" + 
			                         "               init txt files, service request files include path\n";	
			   } else if (args[0].equalsIgnoreCase(foldComm)) {
				   String currentPath = outPath;
				   if (args.length == 2) {
					   currentPath = args[1] + "\\";
				   }			   
				   dislplayMessage = "\n done! \n";
			   } else {	
				   currentFile = args[0];
				   System.out.println("==> " + currentFile);
				   genreateCode(currentFile);				   
				   dislplayMessage = "\n done! \n";
			   }
			} else {
				dislplayMessage = "\n Need more args, ie :  \n" + 
			
									"\n" + 
									"              -help  list commandlines \n" +  
									"              -path  source files and output directory \n" + 
									"               init txt files, service request files include path\n";				
				
			}		
		System.out.println(dislplayMessage);
	}
}

