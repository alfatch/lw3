package com.amin.lw_03_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	private Button _bListId;
	private Button _bAddId;
	private Button _bReplaceId;

	private Button 		_bInsertDataId;
	private Button 		_bCancelInserId;
	private EditText 	_teFio;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_bListId 	= (Button) findViewById(R.id.bListId);
		_bAddId 	= (Button) findViewById(R.id.bAddId);
		_bReplaceId = (Button) findViewById(R.id.bReplaceId);
		_bInsertDataId = (Button) findViewById(R.id.bInsertDataId);
		_bCancelInserId= (Button) findViewById(R.id.bCancelInserId);

		_teFio 		= (EditText) findViewById(R.id.teFioId);
		_bReplaceId = (Button) findViewById(R.id.bReplaceId);

		_bListId.setOnClickListener(this);
		_bAddId.setOnClickListener(this);
		_bReplaceId.setOnClickListener(this);
		_bInsertDataId.setOnClickListener(this);
		_bCancelInserId.setOnClickListener(this);

		hideFio();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.bListId:
				App.Log("Старт ListActivity. Start");
				Intent intent = new Intent(MainActivity.this, ListActivity.class);
				startActivity(intent);
				App.Log("Старт ListActivity. End");
				break;
			case R.id.bAddId:
				showFio();
				break;
			case R.id.bInsertDataId:
				insertFio(_teFio.getText().toString());
				hideFio();
				break;
			case R.id.bCancelInserId:
				hideFio();
				break;
			case R.id.bReplaceId:
				updateLastRecord("Иванов Иван Иванович");
				break;
		}
	}

	private void showFio()
	{
		_bAddId.setEnabled(false);
		_teFio.setVisibility(View.VISIBLE);
		_bInsertDataId.setVisibility(View.VISIBLE);
		_bCancelInserId.setVisibility(View.VISIBLE);
	}

	private void hideFio()
	{
		_bAddId.setEnabled(true);
		_teFio.setVisibility(View.INVISIBLE);
		_teFio.setText("");
		_bInsertDataId.setVisibility(View.INVISIBLE);
		_bCancelInserId.setVisibility(View.INVISIBLE);
	}

	private void insertFio(String fio)
	{
		SQLiteDatabase db = App.Db.getWritableDatabase();
		try
		{
			ContentValues cv = new ContentValues();
			cv.put(DbHelper.ColumnFIO, fio);
			cv.put(DbHelper.ColumnDate, App.GetCurrentTime());
			long rowID = db.insert(DbHelper.TableName, null, cv);

			App.Log("Добавлена запись id = " + rowID);
		}
		finally
		{
			db.close();
		}
	}

	private void updateLastRecord(String fio)
	{
		SQLiteDatabase db = App.Db.getWritableDatabase();
		try
		{
			Cursor cursor = db.query(App.Db.TableName, new String[] {String.format("MAX(%s) as rid",App.Db.ColumnRid)},null, null, null, null, null);
			cursor.moveToFirst();

			int lastId = cursor.getInt(0);
			ContentValues values = new ContentValues();
			values.put(App.Db.ColumnFIO, fio);
			values.put(App.Db.ColumnDate, App.GetCurrentTime());
			String where = App.Db.ColumnRid + "=" + lastId;

			db.update(App.Db.TableName, values, where , null);
		}
		finally
		{
			db.close();
		}
	}
}
