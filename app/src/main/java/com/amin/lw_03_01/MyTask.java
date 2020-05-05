package com.amin.lw_03_01;

import android.os.AsyncTask;

public class MyTask extends AsyncTask<Void, Void, Void>
{
	public interface Listener
	{
		void onPreExecute();
		void onPostExecute(Void result);
		void doInBackground(Void... params);
	}

	private Listener _listner;

	public MyTask(Listener listner)
	{
		_listner = listner;
	}

	protected void onPreExecute()
	{
		super.onPreExecute();
		_listner.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		try
		{
			_listner.doInBackground(params);
		}
		catch(Exception e)
		{
			if (e.getMessage() != null)
				App.Error(e.getMessage());
			else
				App.Error(e.toString());
			throw e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
		_listner.onPostExecute(result);
	}
}
