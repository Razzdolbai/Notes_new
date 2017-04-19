package dimon.notes_new;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    Context mContext;
    myListAdapter mAdapter;
    DBConnector mDBConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mDBConnector = new DBConnector (this);

        mListView = (ListView)findViewById(R.id.list);
        mAdapter = new myListAdapter(mContext, mDBConnector.selectAll());
        mListView.setAdapter(mAdapter);

        //регистрируем контекстное меню для listview
        registerForContextMenu(mListView);
    }
//копированный код

    //переход на новый экран при нажатии на кнопку
    public void add_note_scr(View v) {
        Intent intObj = new Intent(this, add_note.class);
        startActivity(intObj);
    }

    //создаём меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //закончили создавать меню

    //контекстное меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete:
                mDBConnector.delete (info.id);
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    //
    //Обновление списка
    private void updateList () {
        mAdapter.setArrayMyNotes(mDBConnector.selectAll());
        mAdapter.notifyDataSetChanged();
    }

    //
    class myListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private ArrayList<MyNotes> arrayMyNotes;

        public myListAdapter (Context ctx, ArrayList<MyNotes> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyNotes(arr);
        }

        public ArrayList<MyNotes> getArrayMyNotes() {
            return arrayMyNotes;
        }

        public void setArrayMyNotes(ArrayList<MyNotes> arrayMyNotes) {
            this.arrayMyNotes = arrayMyNotes;
        }

        public int getCount () {
            return arrayMyNotes.size();
        }

        public Object getItem (int position) {
            return position;
        }

        public long getItemId (int position) {
            MyNotes md = arrayMyNotes.get(position);
            if (md != null) {
                return md.getID();
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
//
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.add_note, null);
//
//            //TextView vTitle = (TextView)convertView.findViewById(R.id.Note);
//
//
            MyNotes md = arrayMyNotes.get(position);
//            //vTitle.setText(md.getTitle());
//
            return convertView;
        }

//        public void getArrayMyNotes(ArrayList<MyNotes> arrayMyNotes) {
//            this.arrayMyNotes = arrayMyNotes;
//        }
    } // end myAdapter
}
