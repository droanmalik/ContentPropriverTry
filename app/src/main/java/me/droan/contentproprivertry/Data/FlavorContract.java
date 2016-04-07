package me.droan.contentproprivertry.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by drone on 07/04/16.
 */
public class FlavorContract {
  public static final String CONTENT_AUTHORITY = "me.droan.contentproprivertry";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

  public static final class FlavorTable implements BaseColumns {
    public static final String TABLE_NAME = "flavor";

    public static final String _ID = "_id";
    public static final String COL_ICON = "icon";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_VERSION_NAME = "version_name";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

    public static final String CONTENT_DIR_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

    public static Uri buildFlavorURI(long id) {
      return ContentUris.withAppendedId(CONTENT_URI, id);
    }
  }
}
