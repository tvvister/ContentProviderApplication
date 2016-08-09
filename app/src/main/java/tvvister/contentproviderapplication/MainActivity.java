package tvvister.contentproviderapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import tvvister.contentproviderapplication.component.SingerInfoProvider;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Uri uri = Uri.parse("content://" + SingerInfoProvider.AUTHORITY + "/" + SingerInfoProvider.ARTISTS);
        final Random random = new Random();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {




                        Thread.sleep(2000);
                        Cursor cursor = getContentResolver().query(uri, null, null,
                                null, null);
                        if (cursor.moveToFirst()){
                            int columnIndex = cursor.getColumnIndex(SingerInfoProvider.ARTIST_ID);
                            int id = cursor.getInt(columnIndex);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(SingerInfoProvider.ARTIST_NAME, "ggggggggg" + random.nextInt());
                            Uri artistUri = Uri.withAppendedPath(uri, String.valueOf(id));
                            getContentResolver().update(
                                artistUri,
                                contentValues,
                                null,
                                null);
                        }
                        cursor.close();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
