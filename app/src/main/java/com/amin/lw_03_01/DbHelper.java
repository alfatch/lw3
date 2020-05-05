package com.amin.lw_03_01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper
{
	public static String TableName 	= "students";
	public static String ColumnRid 	= "_id";
	public static String ColumnFIO 	= "fio";
	public static String ColumnDate = "date";

	private static int _version 	= 1;
	public boolean IsNew 			= false;

	public DbHelper(Context context)
	{
		super(context, "myDB3", null, _version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		IsNew = true;
		App.Log("Создание таблицы. Start");
		db.execSQL("create table students ("
				+ DbHelper.ColumnRid + " integer primary key autoincrement,"
				+ DbHelper.ColumnFIO + " text(500) not null,"
				+ DbHelper.ColumnDate + " text(100) not null"+ ");");
		App.Log("Создание таблицы. End");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if (newVersion != oldVersion)
		{
			App.Log("Удаление таблицы. Start");
			//	обновление
			db.execSQL("drop table if exists students");
			App.Log("Удаление таблицы. End");
			onCreate(db);
		}
	}
}
