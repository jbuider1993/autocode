package com.brain.ca.template;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

	public static void CopyFile(String sourceFile, String targetFile) {
		File autoCodeOut = new File(targetFile);
		autoCodeOut.getParentFile().mkdirs();        	
        Path sourceDirectory = Paths.get(sourceFile);	        
        Path targetDirectory = Paths.get(targetFile);
		try {
			Files.copy(sourceDirectory, targetDirectory,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
            System.out.println(String.format("Problem for copy file from %s to %", sourceFile, targetFile ));
		}		
	}

}
