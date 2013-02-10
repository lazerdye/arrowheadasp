/**
 * ArrowHead ASP Server 
 * This is a source file for the ArrowHead ASP Server - an 100% Java
 * VBScript interpreter and ASP server.
 *
 * For more information, see http://www.tripi.com/arrowhead
 *
 * Copyright (C) 2002  Terence Haddock
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.tripi.asp.test;

import com.tripi.asp.parse.FileFactory;
import com.tripi.asp.*;
import jregex.*;
import org.apache.log4j.Category;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.Reader;

/**
 * This class is a factory to handle finding files on the local file
 * system. It is used by the other test routines.
 */
public class SimpleFileFactory implements FileFactory
{
    /** Debugging class */
    private static final Category DBG = Category.getInstance(SimpleFileFactory.class);

    /**
     * Resolve a relative file's location based on the given file.
     * @param baseFile base file to use.
     * @param relFile relative file to use.
     */
    public String resolveFile(String baseFilename, String relFile, boolean virtual)
        throws AspException
    {
        if (baseFilename == null) return relFile;
        File baseFile = new File(baseFilename);
        String path;
        String basePath = baseFile.getParent();
        if (basePath == null) basePath = "";
        if (basePath.length() > 0)
            return concatPath(basePath, relFile);
        return relFile;
    }

    /**
     * Internal utility function to concatenate two paths.
     * @param absPath Absolute path to start with
     * @param addPath Relative path to add
     */
    private static String concatPath(String absPath, String addPath)
        throws AspException
    {
        if (DBG.isDebugEnabled()) {
            DBG.debug("concatPath(" + absPath + "," + addPath + ")");
        }
        final Pattern pathSep = new Pattern("/|\\\\");
        String absPathStrings[] = pathSep.tokenizer(absPath).split();
        String addPathStrings[] = pathSep.tokenizer(addPath).split();
        String newPathStrings[] = new String[absPathStrings.length +
            addPathStrings.length];
        int absPathPos = absPathStrings.length - 1;
        int addPathPos = 0;
        int newPathPos = 0;
        for (int i = 0; i < absPathStrings.length; i++) {
            newPathStrings[i] = absPathStrings[i];
            newPathPos++;
        }
        if (DBG.isDebugEnabled()) DBG.debug("newPathPos: " + newPathPos);
        for (int i = 0; i < addPathStrings.length; i++) {
            if (addPathStrings[i].equalsIgnoreCase(".")) continue;
            if (addPathStrings[i].equalsIgnoreCase(".."))
            {
                if (newPathPos == 0) throw new AspException("Invalid path: " + absPath + File.pathSeparator + addPath);
                newPathPos--;
                continue;
            }
            newPathStrings[newPathPos++] = addPathStrings[i];
        }
        if (DBG.isDebugEnabled()) DBG.debug("newPathPos: " + newPathPos);
        StringBuffer buf = new StringBuffer();
        if (absPath.startsWith("/") || absPath.startsWith("\\"))
            buf.append(File.separatorChar);
        for (int i = 0; i < newPathPos; i++)
        {
            if (i != 0) buf.append(File.separatorChar);
            buf.append(newPathStrings[i]);
        }
        if (DBG.isDebugEnabled()) DBG.debug("Final path: " + buf.toString());
        return buf.toString();
    }

    /**
     * Get the file resource.
     * @param file file to obtain stream of
     * @return input stream of the specified file
     */
    public Reader getResource(String filename) throws AspException
    {
        try {
            return new FileReader(filename);
        } catch (IOException ex) {
            throw new AspNestedException(ex);
        }
    }
}
