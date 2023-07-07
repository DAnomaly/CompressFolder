package cBatch;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import cBatch.util.CBatchProperties;
import cFolder.CompressFolder;

/**
 * Program Main Entry Point
 * 
 * @author DAnomaly
 */
public class Program {

	/**
	 * CompressBatch project's Properties
	 */
	private static Properties prop;

	/**
	 * Program Main Entry Point
	 */
	public static void main(String[] args) throws IOException {
		// Set Properties
		prop = new CBatchProperties();

		// Remove old file
		removeOldFile();

		// Generate ZIP file
		generateZipFile();
	}

	/**
	 * Remove old file
	 */
	private static void removeOldFile() {
		File saveFolder = new File(prop.getProperty("project.savefolder").toString());
		LocalDate now = LocalDate.now();
		short oldMonth = Short.parseShort(prop.getProperty("remove.old.month").toString());

		File[] zipFiles = saveFolder.listFiles();
		if (zipFiles.length > 0) {
			for (int i = 0; i < zipFiles.length; i++) {
				File zipFile = zipFiles[i];
				LocalDate modifiedDate = new Date(zipFile.lastModified()).toLocalDate();
				if (now.minusMonths(modifiedDate.getMonthValue()).getMonthValue() > oldMonth) {
					zipFile.delete();
				}
			}
		}
	}

	/**
	 * Generate ZIP file
	 */
	private static void generateZipFile() {
		File targetFolder = new File(prop.getProperty("project.targetfolder").toString());
		File saveFolder = new File(prop.getProperty("project.savefolder").toString());
		String nowStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());

		String filename = targetFolder.getName() + "_" + nowStr + ".zip";
		
		try {
			CompressFolder compress = new CompressFolder(targetFolder.getAbsolutePath(), new File(saveFolder, filename).getAbsolutePath());
			compress.compress();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
