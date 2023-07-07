package cFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class for compress Folder to Zip
 * 
 * @author DAnomaly
 */
public class CompressFolder {

	/**
	 * Compress folder
	 */
	private File targetFolder;
	
	/**
	 * Compressed ZIP file
	 */
	private File compressFile;

	/**
	 * Class for compress Folder to Zip
	 * 
	 * @param targetFolderPath Compress folder full path
	 * @param compressFilePath Compressed ZIP file full path
	 * @throws IOException targetFolderPath is not exists
	 */
	public CompressFolder(String targetFolderPath, String compressFilePath) throws IOException {
		this.targetFolder = new File(targetFolderPath);
		this.compressFile = new File(compressFilePath);

		if (!targetFolder.exists())
			throw new IOException("targetFolder is not exists. targetFolder: " + targetFolderPath);
	}

	
	/**
	 * Compress Folder and Save ZIP file
	 * 
	 * @return working result
	 */
	public boolean compress() {
		boolean result = false;
		
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compressFile))) {

			Files.walkFileTree(targetFolder.toPath(), new FileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// only copy files, no symbolic links
                    if (attrs.isSymbolicLink()) {
                        return FileVisitResult.CONTINUE;
                    }

                    try (FileInputStream fis = new FileInputStream(file.toFile())) {

                        Path targetFile = targetFolder.toPath().relativize(file);
                        zos.putNextEntry(new ZipEntry(targetFile.toString()));

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                        zos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.err.printf("Unable to zip : %s%n%s%n", file, exc);
                    return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					// System.out.println(dir.toUri().toString());
                    return FileVisitResult.CONTINUE;
				}
				
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
