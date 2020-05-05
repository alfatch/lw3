package com.amin.lw_03_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity
{
	private SQLiteDatabase _db;
	private Cursor _userCursor;
	private SimpleCursorAdapter _userAdapter;
	private ListView _userList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		_userList = (ListView)this.findViewById(R.id.listId);
		App.Log("ListActivity.onCreate");
	}

	@Override
	protected void onResume()
	{
		App.Log("ListActivity.onResume.Start");
		super.onResume();

		_db = App.Db.getReadableDatabase();
		_userCursor =  _db.rawQuery(String.format("select %s as _id, %s, %s from %s",
				DbHelper.ColumnRid,
				DbHelper.ColumnFIO,
				DbHelper.ColumnDate,
				DbHelper.TableName), null);
		int col = _userCursor.getCount();
		App.Log("Всего " + col);
		String[] headers = new String[] {"_id", DbHelper.ColumnFIO, DbHelper.ColumnDate};

		_userAdapter = new SimpleCursorAdapter(this, R.layout.list_item,
				_userCursor, headers, new int[]{R.id.ridId, R.id.fioId, R.id.dateId }, 0);
		_userList.setAdapter(_userAdapter);
		App.Log("ListActivity.onResume.End");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		_db.close();
		_userCursor.close();
		App.Log("ListActivity.onDestroy");
	}
}
