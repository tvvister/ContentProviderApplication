package tvvister.contentproviderapplication.component;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tvvister.contentproviderapplication.data.ISingerInfoProvider;
import tvvister.contentproviderapplication.data.ProviderCreator;
import tvvister.contentproviderapplication.data.SingerInfo;

/**
 * Created by Andrey on 06.08.2016.
 */
public class SingerInfoProvider extends ContentProvider {




    public final static String AUTHORITY = "tvvister.contentproviderapplication.component";
    public static final String ARTISTS = "artists";

    private static final UriMatcher sUriMatcher;
    private static final int CONTENT_TYPE = 1;
    private static final int SINGLE_ARTIST_TYPE = 2;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, ARTISTS, CONTENT_TYPE);
        sUriMatcher.addURI(AUTHORITY, ARTISTS + "/#", SINGLE_ARTIST_TYPE);
    }



    static final String DB_NAME = "artist_db";
    static final int DB_VERSION = 2;

    // Таблица


    // Поля
    public static final String ARTIST_ID = "_id";
    public static final String ARTIST_NAME = "name";
    static final String DESCRIPTION = "description";


    static final String DB_CREATE = "create table " + ARTISTS + "("
            + ARTIST_ID + " integer primary key, "
            + ARTIST_NAME + " text, " + DESCRIPTION + " text" + ");";
    private DBHelper dbHelper;
    private SQLiteDatabase database;



    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), ProviderCreator.createSingerInfoProvider());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder){
        database = dbHelper.getReadableDatabase();
        database = dbHelper.getWritableDatabase();
        String type = getType(uri);
        if (type.equals("table")) {
            Cursor cursor =  database.query(ARTISTS, projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        } else
        {
            Cursor cursor =  database.query(ARTISTS, projection, " " + ARTIST_ID + " = " + uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);
            return cursor;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CONTENT_TYPE:
                return "table";
            case SINGLE_ARTIST_TYPE:
                return "single";
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        String id = uri.getLastPathSegment();
        database.update(ARTISTS, contentValues, " " + ARTIST_ID + " = " + id, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return 1;
    }

    private class DBHelper extends SQLiteOpenHelper {
        @NonNull
        private final ISingerInfoProvider singerInfoProvider;

        public DBHelper(Context context, @NonNull ISingerInfoProvider singerInfoProvider) {
            super(context, DB_NAME, null, DB_VERSION);
            this.singerInfoProvider = singerInfoProvider;
//            final SQLiteDatabase writableDatabase = this.getWritableDatabase();
//
//            singerInfoProvider.getAllSingers(new Callback<SingerInfo[]>() {
//                @Override
//                public void success(SingerInfo[] singerInfos, Response response) {
//                    ContentValues cv = new ContentValues();
//                    for (SingerInfo singerInfo :  singerInfos) {
//                        cv.put(ARTIST_ID, singerInfo.getId());
//                        cv.put(ARTIST_NAME, singerInfo.getName());
//                        cv.put(DESCRIPTION, singerInfo.getDescription());
//                        writableDatabase.insert(ARTISTS, null, cv);
//                    }
//                }
//                @Override
//                public void failure(RetrofitError error) {
//
//                }
//            });
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            final SQLiteDatabase finalDb = db;
            singerInfoProvider.getAllSingers(new Callback<SingerInfo[]>() {
                @Override
                public void success(SingerInfo[] singerInfos, Response response) {
                    ContentValues cv = new ContentValues();
                    for (SingerInfo singerInfo :  singerInfos) {
                        cv.put(ARTIST_ID, singerInfo.getId());
                        cv.put(ARTIST_NAME, singerInfo.getName());
                        cv.put(DESCRIPTION, singerInfo.getDescription());
                        finalDb.insert(ARTISTS, null, cv);
                    }
                }
                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
