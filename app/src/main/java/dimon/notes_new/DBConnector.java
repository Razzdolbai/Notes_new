package dimon.notes_new;

/**
 * Created by 12345 on 18.04.2017.
 */
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Класс для создания БД
public class DBConnector extends SQLiteOpenHelper {

    // Данные базы данных и таблиц
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NAME = "MyNotes";

    // Название столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTE = "Note";

    // Номера столбцов
    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NOTE = 1;

    private SQLiteDatabase mDataBase;

    public DBConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//    public DBConnector(Context context) {
//        // открываем (или создаем и открываем) БД для записи и чтения
//        OpenHelper mOpenHelper = new OpenHelper(context);
//        mDataBase = mOpenHelper.getWritableDatabase();
//    }

    //метод удаления записи
    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    //
    public MyNotes select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[] { String.valueOf(id) }, null, null, COLUMN_NOTE);
        mCursor.moveToFirst();
        String note = mCursor.getString(NUM_COLUMN_NOTE);
        return new MyNotes(id, note);
    }
    //

    // Метод выборки всех записей
    public ArrayList<MyNotes> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, COLUMN_NOTE);

        ArrayList<MyNotes> arr = new ArrayList<MyNotes>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String note = mCursor.getString(NUM_COLUMN_NOTE);
                arr.add(new MyNotes(id, note));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    @Override
    //создаем таблицу MyNotes
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOTE + " TEXT); ");
    }

    @Override
    // удаляем таблицу и создаем ее заново
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }
}