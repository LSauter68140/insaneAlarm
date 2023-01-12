package fr.utt.if26.insanealarm.taskBackground.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;

public class FileManager {

    /**
     * Return file name from Uri given.
     *
     * @param context the context, cannot be null.
     * @param uri     uri request for file name, cannot be null
     * @return the corresponding display name for file defined in uri or null if error occurs.
     */
    @SuppressLint("Range")
    public static String getNameFromURI(@NonNull Context context, @NonNull Uri uri) {
        String result = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            try (Cursor c = context.getContentResolver().query(uri, null, null, null, null)) {
                c.moveToFirst();
                result = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            } catch (Exception e) {
                // error occurs
            }
        } else {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
    }
}
