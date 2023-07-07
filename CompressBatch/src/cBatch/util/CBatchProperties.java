package cBatch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Properties used by CompressBatch project
 * 
 * @author DAnomaly
 */
public class CBatchProperties extends Properties{

	/**
	 * CompressBatch Project's Porperties UID
	 */
	private static final long serialVersionUID = -4775347108291575371L;

	public CBatchProperties() throws IOException {
		this.load(new FileInputStream(new File("project.properties")));
	}
	
}
