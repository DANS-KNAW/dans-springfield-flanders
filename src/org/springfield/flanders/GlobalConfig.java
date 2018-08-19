/* 
* GlobalConfig.java
* 
* Copyright (c) 2014 Noterik B.V.
* 
* This file is part of flanders, related to the Noterik Springfield project.
*
* flanders is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* flanders is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with flanders.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.springfield.flanders;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class GlobalConfig {
	private static Logger log = Logger.getLogger(GlobalConfig.class);

	private static GlobalConfig instance = new GlobalConfig();
	private static String CONFIG_FILE = "config.xml";

	private String ffprobeScriptDir;
	private String rtmpdumpScriptDir;
	private String idtrawScriptDir;
	private String cineScriptDir;
	private Properties config; 

	/**
	 * Sole constructor
	 */
	private GlobalConfig(){}

	public static GlobalConfig instance(){
		return instance;
	}

	private void initConfig(String baseDir) {
		File file = new File(baseDir + File.separator + CONFIG_FILE);
		config = new Properties();
		try {
			config.loadFromXML(new BufferedInputStream(new FileInputStream(file)));
			log.debug("FLANDERS: loaded configuration from " + file);
		}
		catch (IOException e) {
			log.warn("Eating exception and continuing", e);
		}
	}

	public void initialize() {
		String baseDir = "/springfield/flanders";
		log.info("FLANDERS: Initializing, basedir: " + baseDir);

		String os = System.getProperty("os.name").toLowerCase();
		//running windows
		if(os.contains("windows")){
			ffprobeScriptDir = baseDir + File.separator + "scripts" + File.separator + "ffprobe_extract.bat";
			rtmpdumpScriptDir = baseDir + File.separator + "scripts" + File.separator + "rtmpd_extract.bat";
		}
		//running linux
		else{
			ffprobeScriptDir = baseDir + File.separator + "scripts" + File.separator + "ffprobe_extract.sh";
			rtmpdumpScriptDir = baseDir + File.separator + "scripts" + File.separator + "rtmpd_extract.sh";
			idtrawScriptDir = baseDir + File.separator  + "scripts" + File.separator + "idt_raw_extract.sh";
			cineScriptDir = baseDir + File.separator + "scripts" + File.separator + "cine_extract.sh";
		}
		initConfig(baseDir);
	}
	
	public String getFfprobeScriptDir(){
		return ffprobeScriptDir;
	}
	
	public String getFfprobePath() {
		return config.getProperty("ffprobe-path");
	}
	
	public String getRtmpdumpScriptDir(){
		return rtmpdumpScriptDir;
	}
	
	public String getRtmpdumpPath() {
		return config.getProperty("rtmpdump-path");
	}
	
	public String getIdtRawScriptDir() {
	    return idtrawScriptDir;
	}
	
	public String getIdtRawPath() {
	    return config.getProperty("idt_raw-path");
	}
	
	public String getCineScriptDir() {
	    return cineScriptDir;
	}

	public String getCinePath() {
	    return config.getProperty("cine-path");
	}
	
}