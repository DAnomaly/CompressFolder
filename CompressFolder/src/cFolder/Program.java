package cFolder;

import java.io.IOException;

/**
 * Program Main Entry Point
 * 
 * @author DAnomaly
 */
public class Program {

	public static void main(String[] args) {

		// check parameter
		if (args.length < 2) {
			System.out.println("USE: java -jar CompressFolder.java [targetFolderPath] [compressedFilePath]");
			
			return;
		}

		try {
			new CompressFolder(args[0], args[1]).compress();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
