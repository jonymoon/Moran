package com.geekband.jonymoon.moran;

/**
 * Created by jony on 2015/10/21 0021.
 */
public class ApplicationContext extends android.app.Application {
    private static String baseUrl="http://moran.chinacloudapp.cn/moran/web";

    public String getUrl(String path) {
        return baseUrl+path;
    }
}
