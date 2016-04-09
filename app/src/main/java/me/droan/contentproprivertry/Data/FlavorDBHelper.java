package me.droan.contentproprivertry.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by drone on 07/04/16.
 */
public class FlavorDBHelper extends SQLiteOpenHelper {

  private static final String TAG = "FlavorDBHelper";

  private static final String DATABASE_NAME = "flavor.db";
  private static final int DATABASE_VERSION = 1;

  private String SQL_CREATE_FLAVOR_TABLE =
      "CREATE TABLE " + FlavorContract.FlavorTable.TABLE_NAME + " ( " +
          " " + FlavorContract.FlavorTable._ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
          " " + FlavorContract.FlavorTable.COL_ICON + " INTEGER NOT NULL," +
          " " + FlavorContract.FlavorTable.COL_DESCRIPTION + " TEXT NOT NULL," +
          " " + FlavorContract.FlavorTable.COL_VERSION_NAME + " TEXT NT NULL);";

  private String SQL_DROP_FLAVOR_TABLE =
      "DROP TABLE IF EXISTS " + FlavorContract.FlavorTable.TABLE_NAME + ";";

  public FlavorDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    Log.d(TAG, "FlavorDBHelper() called with: " + "context = [" + context + "]");
  }

  @Override public void onCreate(SQLiteDatabase db) {
    Log.d(TAG, "onCreate() called with: " + "db = [" + db + "]");
    db.execSQL(SQL_CREATE_FLAVOR_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    Log.d(TAG, "onUpgrade() called with: "
        + "db = ["
        + db.toString()
        + "], oldVersion = ["
        + oldVersion
        + "], newVersion = ["
        + newVersion
        + "]");
    db.execSQL(SQL_DROP_FLAVOR_TABLE);
    onCreate(db);
  }
}
