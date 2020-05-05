package com.amin.lw_03_01;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class App extends Application
{
	public static final String LogTag = "_DEBUG_";
	public static final String ErrorTag = "LogTag";
	public static final int[] Colors = new int[2];

	public static DbHelper Db;

	public App()
	{
		Colors[0] = Color.parseColor("#FFFFFFFF");
		Colors[1] = Color.parseColor("#CCCCCCCC");

		Db = new DbHelper(this);
	}

	public static void Log(String msg)
	{
		Log.d(LogTag, msg);
	}
	public static void Error(String message)
	{
		Log.e(LogTag, message);
	}

	public static String GetCurrentTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}}
