package com.example.imgurupload;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.List;

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

    public static void createGalleryIntent(Activity activity, int type) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");

        PackageManager packageManager = activity.getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            Intent destIntent = Intent.createChooser(intent, activity.getString(R.string.select_photos));
            if (intent.resolveActivity(packageManager) != null) {
                activity.startActivityForResult(destIntent, 500);
            }
        } else {
            Toast.makeText(activity, R.string.not_install_picture_viewer, Toast.LENGTH_SHORT).show();
        }
    }
}
