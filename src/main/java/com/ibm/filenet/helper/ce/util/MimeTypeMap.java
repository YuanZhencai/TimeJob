package com.ibm.filenet.helper.ce.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MimeTypeMap {
    public static final String FOLDER = "folder";
    private static final HashMap<String, String> mimetypeMap = new HashMap();

    static {
        ResourceBundle mimeTypes = ResourceBundle.getBundle("MimeTypes");
        Enumeration enumeration = mimeTypes.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = mimeTypes.getString(key);
            mimetypeMap.put(key, value);
        }
    }

    public static String getMimetype(String extension) {
        Assert.notEmpty(extension, "extension is null");
        String mimetype = (String) mimetypeMap.get(extension.toLowerCase());
        if (mimetype == null) { throw new NullPointerException("Extension '" + extension
                + "' does not have a mime type specified. Please add" + " its mime type in MimeTypes.properties"); }
        return mimetype;
    }
}