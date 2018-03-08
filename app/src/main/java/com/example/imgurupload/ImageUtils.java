package com.example.imgurupload;

/**
 * Created by Jason on 2017/12/19.
 */

public class ImageUtils {

    public static final String SMALL = "s";
    public static final String BIG = "b";
    public static final String SMALL_THUMBNAIL = "t";
    public static final String MEDIUM = "m";
    public static final String LARGE = "l";
    public static final String HUGE = "h";

    private static final String JPG = ".jpg";
    private static final String PNG = ".png";

    public ImageUtils() {}

    public static String resize(String url, String suffix) {
        if(url.contains(JPG))
            return url.replace(JPG, suffix + JPG);
        else if(url.contains(PNG))
            return url.replace(PNG, suffix + PNG);
        else return url;
    }
}
