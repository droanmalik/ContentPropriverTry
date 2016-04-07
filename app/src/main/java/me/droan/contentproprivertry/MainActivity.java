package me.droan.contentproprivertry;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.droan.contentproprivertry.Data.FlavorContract;

public class MainActivity extends AppCompatActivity {

  private static final int CURSOR_LOADER_ID = 0;
  Flavor[] flavors = {
      new Flavor("Cupcake", "The first release of Android", R.drawable.cupcake), new Flavor("Donut",
      "The world's information is at your fingertips – "
          + "search the web, get driving directions... or just watch cat videos.",
      R.drawable.cupcake),
      new Flavor("Eclair", "Make your home screen just how you want it. Arrange apps " +
          "and widgets across multiple screens and in folders. Stunning live wallpapers " +
          "respond to your touch.", R.drawable.cupcake), new Flavor("Froyo",
      "Voice Typing lets you input text, and Voice Actions let "
          + "you control your phone, just by speaking.", R.drawable.cupcake),
      new Flavor("GingerBread", "New sensors make Android great for gaming - so "
          + "you can touch, tap, tilt, and play away.", R.drawable.cupcake), new Flavor("Honeycomb",
      "Optimized for tablets, this release opens up new " + "horizons wherever you are.",
      R.drawable.cupcake), new Flavor("Ice Cream Sandwich",
      "Android comes of age with a new, refined design. " + "Simple, beautiful and beyond smart.",
      R.drawable.cupcake), new Flavor("Jelly Bean",
      "Android is fast and smooth with buttery graphics. "
          + "With Google Now, you get just the right information at the right time.",
      R.drawable.cupcake), new Flavor("KitKat",
      "Smart, simple, and truly yours. A more polished design, "
          + "improved performance, and new features.", R.drawable.cupcake), new Flavor("Lollipop",
      "A sweet new take on Android. Get the smarts of Android on"
          + " screens big and small – with the right information at the right moment.",
      R.drawable.cupcake)
  };
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  @Override protected void onResume() {
    super.onResume();
    Cursor c = getContentResolver().query(FlavorContract.FlavorTable.CONTENT_URI,
        new String[] { FlavorContract.FlavorTable._ID }, null, null, null);
    if (c.getCount() == 0) {
      Toast.makeText(MainActivity.this, "NoData", Toast.LENGTH_SHORT).show();
      ContentValues[] flavorValuesArr = new ContentValues[flavors.length];
      // Loop through static array of Flavors, add each to an instance of ContentValues
      // in the array of ContentValues
      for (int i = 0; i < flavors.length; i++) {
        flavorValuesArr[i] = new ContentValues();
        flavorValuesArr[i].put(FlavorContract.FlavorTable.COL_ICON, flavors[i].image);
        flavorValuesArr[i].put(FlavorContract.FlavorTable.COL_VERSION_NAME, flavors[i].name);
        flavorValuesArr[i].put(FlavorContract.FlavorTable.COL_DESCRIPTION, flavors[i].description);
      }

      // bulkInsert our ContentValues array
      getContentResolver().bulkInsert(FlavorContract.FlavorTable.CONTENT_URI, flavorValuesArr);
    } else {
      int colmCount = c.getColumnCount();
      Toast.makeText(MainActivity.this, "" + colmCount, Toast.LENGTH_SHORT).show();
    }
  }
}
