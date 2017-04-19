package dimon.notes_new;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 12345 on 12.04.2017.
 */

public class add_note extends Activity implements View.OnClickListener  {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_note);
//    }
//}
    Button bSave;
    EditText etNewNote;

    DBConnector mDBConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        bSave = (Button) findViewById(R.id.bSave);
        etNewNote = (EditText) findViewById(R.id.etNewNote);

        bSave.setOnClickListener(this);
        mDBConnector = new DBConnector(this);

    }

    //должно сохранять заметку в базу данных
    @Override
    public void onClick(View v) {
        String note = etNewNote.getText().toString();

        SQLiteDatabase database = mDBConnector.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        switch (v.getId()){
            case R.id.bSave:
                Cursor cursor = database.query(DBConnector.TABLE_NAME, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBConnector.COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(DBConnector.COLUMN_NOTE);
                } else
                   Log.d("mLog","0 rows");
                contentValues.put(DBConnector.COLUMN_NOTE, note);
                database.insert(DBConnector.TABLE_NAME, null, contentValues);
                startActivity(new Intent(this, MainActivity.class));

                break;
        }
        mDBConnector.close();
    }
}