package pl.allegro.todo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class TodoDao {

	public static final String TAG = TodoDao.class.getSimpleName();

    /**
     * Nazwy kolumn w DB.
     */
	public static final String C_ID = "_id";
	public static final String C_CONTENT = "content";
	public static final String C_DONE = "done";
	public static final String C_USER_ID = "user_id";
    public static final String C_CREATED_AT = "created_at";
    public static final String C_UPDATED_AT = "updated_at";

    /**
     * Nazwa tabeli, w kt�rej przechowywane b�da obiekty {@link pl.allegro.todo.dao.Todo}
     */
	public static final String TABLE_NAME = "todos";

    /**
     * Nazwa pliku zawieraj�cego baz� danych SQLite.
     */
	public static final String DB_NAME = "todo.db";
    /**
     * Rewizja bazy danych. Warto�� t� nale�y zmieni� przy ka�dej modyfikacji struktury bazy danych.
     */
	public static final int DB_VERSION = 1;

	private Context mContext;
	private DBHelper mDBHelper;

	public TodoDao(Context context) {
		mContext = context;
		mDBHelper = new DBHelper();
	}

    /**
     * Metoda zapisuj�ca obiekt {@link Todo} do bazy. W przypadku konfliktu nadpisuje star� warto��.
     * @param todo
     */
	public void insertOrUpdate(Todo todo) {

        //pobranie bazy danych do zapisu
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
        //utworzenie s�ownika nazwa kolumny -> warto��
		ContentValues values = new ContentValues();
        values.put(C_ID, todo.objectId);
		values.put(C_CONTENT, todo.content);
		values.put(C_DONE, todo.done);
		values.put(C_CREATED_AT, todo.createdAt.getTime());
        values.put(C_UPDATED_AT, todo.updatedAt.getTime());
		values.put(C_USER_ID, todo.userId);

		db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

	}

    /**
     * Pobranie najnowszej daty utworzenia dla obiektu. Ka�dy obiekt nowszy, traktujemy jako nowy,
     * podczas aktualizacji.
     * @param userId
     * @return
     */
    public long getLatestCreatedAtTime(String userId) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        try {
            //SELECT max(created_at) FROM todos WHERE user_id=?
            Cursor cursor = db.query(
                    TABLE_NAME,
                    new String[] {String.format("max(%s)", C_CREATED_AT)},
                    String.format("%s=?", C_USER_ID), new String[]{userId}, null, null, null);
            try {
                //je�li jest jaki� wynik to zwr��, w przeciwnym wypadku najstarsza data mo�liwa
                return cursor.moveToNext() ? cursor.getLong(0) : Long.MIN_VALUE;
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

	public Cursor query(String userId, boolean sortAsc) {
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
        //SELECT * FROM todos WHERE user_id=? ORDER BY ASC/DESC
		return db.query(TABLE_NAME, null, String.format("%s=?", C_USER_ID),
                new String[]{userId}, null, null,
                String.format("%s %s", C_UPDATED_AT, sortAsc ? "ASC" : "DESC"));
	}

    public Todo getTodoById(String id) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        //SELECT * FROM todos WHERE _id=?
        Cursor cursor = db.query(TABLE_NAME, null, String.format("%s=?", C_ID),
                new String[]{id}, null, null, null);
        if(cursor.moveToNext()) {
            Todo todo = new Todo();

            todo.objectId = cursor.getString(cursor.getColumnIndex(C_ID));
            todo.content = cursor.getString(cursor.getColumnIndex(C_CONTENT));
            todo.done = cursor.getInt(cursor.getColumnIndex(C_DONE)) > 0;
            todo.userId = cursor.getString(cursor.getColumnIndex(C_USER_ID));
            todo.createdAt = new Date(cursor.getInt(cursor.getColumnIndex(C_CREATED_AT)));
            todo.updatedAt = new Date(cursor.getInt(cursor.getColumnIndex(C_UPDATED_AT)));
            return todo;

        }
        return null;
    }

    /**
     * Klasa pomocnicza zajmuj�ca si� tworzeniem, aktualizacj� i otwieraniem bazy danych.
     */
    class DBHelper extends SQLiteOpenHelper {

		public DBHelper() {
			super(mContext, DB_NAME, null, DB_VERSION);
		}

        /**
         * Metoda wywo�ywana, je�li baza nie istnieje.
         * @param db
         */
		@Override
		public void onCreate(SQLiteDatabase db) {

			String sql = String.format("CREATE TABLE %s " +
					"(%s TEXT PRIMARY KEY NOT NULL, %s TEXT, %s INT, %s INT, %s INT," +
					" %s TEXT)", TABLE_NAME, C_ID,
					C_CONTENT, C_DONE, C_CREATED_AT, C_UPDATED_AT, C_USER_ID);
			Log.d(TAG, "onCreate sql:" + sql);
			db.execSQL(sql);
		}

        /**
         * Metoda wywo�ywana, je�li w wyniku aktualizacji aplikacji zmieniona zosta�a te� wersja
         * bazy danych. Powinna zawiera� kod migracji pomi�dzy wersjami. Cz�sto po prostu kasuje
         * i tworzy now� struktur� bazy.
         * @param db
         * @param oldVersion
         * @param newVersion
         */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


			db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
			onCreate(db);
		}

	}


}
