/*
 * Created on Mar 15, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package com.tripi.asp.msxml2;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Category;
/**
 * @author jhorner
 */
public class DOMURIResolver implements URIResolver {

	/** Debugging category. */
	Category DBG = Category.getInstance(DOMDocument.class);

	private String pathprefix;
	private String basefilename;
	
	public DOMURIResolver() {
	}

	public Source resolve(String href, String base) {
		
		StreamSource result = null;
		
		if (DBG.isDebugEnabled()) {
			DBG.debug("Base " + base);
			DBG.debug("HREF " + href);
			DBG.debug("Prefix " + this.pathprefix);
			DBG.debug("Basefilename " + this.basefilename);
		}
		
		// try just the filename, absolute path
		try {
			DBG.debug("Trying [" + href + "]");
			File file = new File(href);
			if ( file.exists() ) {
				result = new StreamSource(file);
			}
		}
		catch (Exception e) {
			// nothing
		}

		if ( result == null ) {
			// use the basefilename and try a relative path
			try {
				String pathbase = parseBasepath(this.getBasefilename());
				if (DBG.isDebugEnabled()) {
					DBG.debug("Trying [" + pathbase + href + "]");
				}
				File file = new File(pathbase + href);
				if ( file.exists() ) {
					result = new StreamSource(file);
				}
			}
			catch (Exception e) {
				// nothing
			}			
		}
		
		// last resort try the prefix
		if ( result == null ) {
			String path = pathprefix + href;
			if (DBG.isDebugEnabled()) {
				DBG.debug("Trying [" + path + "]");
			}
			File file = new File(path);
			if ( file.exists() ) {
				result = new StreamSource(file);
			}
		}
		
		// last resort try the prefix
		if ( result == null ) {
			String path = parseBasepath(pathprefix) + href;
			if (DBG.isDebugEnabled()) {
				DBG.debug("Trying [" + path + "]");
			}
			File file = new File(path);
			if ( file.exists() ) {
				result = new StreamSource(file);
			}
		}
		
		return result;
	}
	
	private String parseBasepath(String filename) {
		String result = "";
		
		// remove everything after the last path separator
		int index = filename.lastIndexOf(File.separatorChar);
		if ( index > -1 ) {
			result = filename.substring(0, index) + File.separator;
		}
		
		return result;
	}
	
	/**
	 * @return String
	 */
	public String getBasefilename() {
		return basefilename;
	}

	/**
	 * @return String
	 */
	public String getPathprefix() {
		return pathprefix;
	}

	/**
	 * Sets the basefilename.
	 * @param basefilename The basefilename to set
	 */
	public void setBasefilename(String basefilename) {
		this.basefilename = basefilename;
	}

	/**
	 * Sets the pathprefix.
	 * @param pathprefix The pathprefix to set
	 */
	public void setPathprefix(String pathprefix) {
		this.pathprefix = pathprefix;
	}

}

