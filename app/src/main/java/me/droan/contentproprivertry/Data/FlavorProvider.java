package me.droan.contentproprivertry.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by drone on 07/04/16.
 */
public class FlavorProvider extends ContentProvider {
  private static final String TAG = "FlavorProvider";
  //codes for uri matcher
  private static final int FLAVOR = 211;
  private static final int FLAVOR_WITH_ID = 497;
  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private FlavorDBHelper openDBHelper;

  private static UriMatcher buildUriMatcher() {
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = FlavorContract.CONTENT_AUTHORITY;
    matcher.addURI(authority, FlavorContract.FlavorTable.TABLE_NAME, FLAVOR);
    matcher.addURI(authority, FlavorContract.FlavorTable.TABLE_NAME + "/#", FLAVOR_WITH_ID);
    return matcher;
  }

  @Override public boolean onCreate() {
    openDBHelper = new FlavorDBHelper(getContext());
    return true;
  }

  @Nullable @Override public String getType(Uri uri) {
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case FLAVOR:
        return FlavorContract.FlavorTable.CONTENT_DIR_TYPE;
      case FLAVOR_WITH_ID:
        return FlavorContract.FlavorTable.CONTENT_ITEM_TYPE;
      default:
        throw new UnsupportedOperationException("Unknown Uri: " + uri);
    }
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
      // All Flavors selected
      case FLAVOR: {
        retCursor = openDBHelper.getReadableDatabase()
            .query(FlavorContract.FlavorTable.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
        return retCursor;
      }
      // Individual flavor based on Id selected
      case FLAVOR_WITH_ID: {
        retCursor = openDBHelper.getReadableDatabase()
            .query(FlavorContract.FlavorTable.TABLE_NAME, projection,
                FlavorContract.FlavorTable._ID + " = ?",
                new String[] { String.valueOf(ContentUris.parseId(uri)) }, null, null, sortOrder);
        return retCursor;
      }
      default: {
        // By default, we assume a bad URI
        throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
    }
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues values) {
    return null;
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override public int bulkInsert(Uri uri, ContentValues[] values) {

    final SQLiteDatabase db = openDBHelper.getWritableDatabase();

    final int match = sUriMatcher.match(uri);

    switch (match) {
      case FLAVOR:
        // allows for multiple transactions
        db.beginTransaction();

        // keep track of successful inserts
        int numInserted = 0;
        try {
          for (ContentValues value : values) {
            if (value == null) {
              throw new IllegalArgumentException("Cannot have null content values");
            }
            long _id = -1;
            try {
              _id = db.insertOrThrow(FlavorContract.FlavorTable.TABLE_NAME, null, value);
            } catch (SQLiteConstraintException e) {
              Log.w(TAG, "Attempting to insert "
                  +
                  value.getAsString(FlavorContract.FlavorTable.COL_VERSION_NAME)
                  + " but value is already in database.");
            }
            if (_id != -1) {
              numInserted++;
            }
          }
          if (numInserted > 0) {
            // If no errors, declare a successful transaction.
            // database will not populate if this is not called
            db.setTransactionSuccessful();
          }
        } finally {
          // all transactions occur at once
          db.endTransaction();
        }
        if (numInserted > 0) {
          // if there was successful insertion, notify the content resolver that there
          // was a change
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return numInserted;
      default:
        return super.bulkInsert(uri, values);
    }
  }
}
