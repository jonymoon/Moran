package com.geekband.jonymoon.moran.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jonly on 2015/11/12 0012.
 */
public class StreamUtil {
    public static byte[] readInputStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len=0;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer,0,len);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}
