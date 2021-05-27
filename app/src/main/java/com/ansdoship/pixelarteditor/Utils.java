package com.ansdoship.pixelarteditor;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.EnvironmentCompat;

import com.ansdoship.pixelarteditor.app.ApplicationUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;

public final class Utils {

    // Calculate linear distance of two fingers
    public static double spacing(@NonNull MotionEvent event) {
        if(event.getPointerCount() >= 2) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return Math.pow(x * x + y * y, 0.5);
        }
        return 0;
    }

    public static <T> List<T> removeNullElements (@NonNull List <T> list) {
        list.removeAll(Collections.singleton(null));
        return list;
    }

    public static <T> int getNonNullElementsCount (@NonNull List <T> list) {
        return removeNullElements(list).size();
    }

    @Nullable
    public static String getCachePath () {
        Context context = ApplicationUtils.getApplicationContext();
        if (isExternalStorageMounted()) {
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir == null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/Android/data/" + context.getPackageName() + "/cache");
                    if (dir.mkdirs()) {
                        return dir.getAbsolutePath();
                    }
                }
            }
            else {
                return externalCacheDir.getAbsolutePath();
            }
        }
        else {
            return context.getCacheDir().getAbsolutePath();
        }
        return null;
    }

    @Nullable
    public static String getFilesPath (@NonNull String type) {
        Context context = ApplicationUtils.getApplicationContext();
        if (!type.equals("")) {
            type = "/" + type;
        }
        if (isExternalStorageMounted()) {
            File externalFilesDir = context.getExternalFilesDir(type);
            if (externalFilesDir == null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/Android/data/" + context.getPackageName() + "/files" + type);
                    if (dir.mkdirs()) {
                        return dir.getAbsolutePath();
                    }
                }
            }
            else {
                return externalFilesDir.getAbsolutePath();
            }
        }
        else {
            File dir = new File(context.getFilesDir().getAbsolutePath() + type);
            if (dir.mkdirs()) {
                return dir.getAbsolutePath();
            }
        }
        return null;
    }

    public static boolean isExternalStorageMounted() {
        return EnvironmentCompat.getStorageState(Environment.getExternalStorageDirectory()).
                equals(Environment.MEDIA_MOUNTED);
    }

}
