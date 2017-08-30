package com.appetite.appetiteoperationsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Database {

	private final Context context;
	SQLiteDatabase myDatabase;
	VerificationDBOpenHelper myVerificationHelper;

	public Database(Context ct)
	{
		context = ct;
	}

	public Database open() throws SQLException
	{
		myVerificationHelper = new VerificationDBOpenHelper(context);
		myDatabase = myVerificationHelper.getWritableDatabase();
		return this;
	}

	public void close() throws SQLException
	{
		if(myDatabase.isOpen())
		{
			myDatabase.close();
			myVerificationHelper.close();
		}
	}

	public void addUser(ContentValues cv)
	{
		String where = AppContract.UserEntry.USERID+"='" + cv.getAsString("id") +"'";
		Cursor c = myDatabase.query(AppContract.UserEntry.TABLE_NAME, null, where, null, null, null, null);

		if (c.moveToNext()) {
			myDatabase.update(AppContract.UserEntry.TABLE_NAME, cv, where, null);
		} else {
			myDatabase.insert(AppContract.UserEntry.TABLE_NAME, null, cv);
		}
	}

	public void addClient(ContentValues cv)
	{
		String where = AppContract.ClientsEntry.CLIENTID+"='" + cv.getAsString("id") +"'";
		Cursor c = myDatabase.query(AppContract.ClientsEntry.TABLE_NAME, null, where, null, null, null, null);

		if (c.moveToNext()) {
			myDatabase.update(AppContract.ClientsEntry.TABLE_NAME, cv, where, null);
		} else {
			myDatabase.insert(AppContract.ClientsEntry.TABLE_NAME, null, cv);
		}
	}

	public void addTask(ContentValues cv)
	{
		String where = AppContract.TaskEntry.TASKID+"='" + cv.getAsString("taskid") +"'";
		Cursor c = myDatabase.query(AppContract.TaskEntry.TABLE_NAME, null, where, null, null, null, null);

		if (c.moveToNext()) {
			myDatabase.update(AppContract.TaskEntry.TABLE_NAME, cv, where, null);
		} else {
			myDatabase.insert(AppContract.TaskEntry.TABLE_NAME, null, cv);
		}
	}

	public void addTaskProgress(ContentValues cv)
	{
		String where = AppContract.TaskProgressEntry.TASKID+"='" + cv.getAsString("taskid") +"'";
		Cursor c = myDatabase.query(AppContract.TaskProgressEntry.TABLE_NAME, null, where, null, null, null, null);

		if (c.moveToNext()) {
			myDatabase.update(AppContract.TaskProgressEntry.TABLE_NAME, cv, where, null);
		} else {
			myDatabase.insert(AppContract.TaskProgressEntry.TABLE_NAME, null, cv);
		}
	}

	public Cursor getNewTasks()
	{
		String query = "SELECT d1.* " +
				"FROM "+AppContract.TaskEntry.TABLE_NAME+" d1 " +
				"LEFT JOIN "+AppContract.TaskProgressEntry.TABLE_NAME+" d2 " +
				"ON d1.taskid = d2.taskid " +
				"WHERE d2.taskid IS NULL";
		Log.e("query",query);
		return myDatabase.rawQuery(query,null);
	}

	public Cursor getAssignedTasks()
	{
		String query = "SELECT * from "+AppContract.TaskProgressEntry.TABLE_NAME+" a LEFT JOIN "+AppContract.TaskEntry.TABLE_NAME+" b " +
				"ON a."+AppContract.TaskProgressEntry.STATUS+"=0 and a."+AppContract.TaskProgressEntry.TASKID+" = b."+AppContract.TaskEntry.TASKID;
		Log.e("query",query);
		return myDatabase.rawQuery(query,null);
	}

	public Cursor getCompletedTasks()
	{
		String query = "SELECT * from "+AppContract.TaskProgressEntry.TABLE_NAME+" a LEFT JOIN "+AppContract.TaskEntry.TABLE_NAME+" b " +
				"ON a."+AppContract.TaskProgressEntry.STATUS+"=2 and a."+AppContract.TaskProgressEntry.TASKID+" = b."+AppContract.TaskEntry.TASKID;
		Log.e("query",query);
		return myDatabase.rawQuery(query,null);
	}

	public String getClientName(String clientid)
	{
		String x="";
		Cursor c = myDatabase.rawQuery("select "+AppContract.ClientsEntry.NAME+" from "+AppContract.ClientsEntry.TABLE_NAME+" where "+AppContract.ClientsEntry.CLIENTID+"='"+clientid+"'",null);
		if(c.moveToNext()) {
			x = c.getString(0);
		}
		else {
			x = "The Client has no Name";
		}
		c.close();
		return x;
	}

	public Cursor getAllUsers()
	{
		String query = "SELECT * from "+AppContract.UserEntry.TABLE_NAME;
		Log.e("query",query);
		return myDatabase.rawQuery(query,null);
	}

	public void updateStatusInTaskTable(String taskid)
	{
		String where = AppContract.TaskEntry.TASKID+"='" + taskid+"'";
		ContentValues cv = new ContentValues();
		cv.put(AppContract.TaskEntry.STATUS, 1);
		myDatabase.update(AppContract.TaskEntry.TABLE_NAME, cv, where, null);
	}























	public String getID()
	{
		String x="";
		Cursor c = myDatabase.rawQuery("select max(sno) from videos",null);
		if(c.moveToNext()) {
			x = c.getString(0);
		}
		else {
			x = "0";
		}
		c.close();
		return x;
	}

	public String getID(String category)
	{
		String x="";
		Cursor c = myDatabase.rawQuery("select max(sno) from videos where genre='"+category+"'",null);
		if(c.moveToNext()) {
			x = c.getString(0);
		}
		else {
			x = "0";
		}
		c.close();
		return x;
	}

	public Cursor getData(String query)
	{
		return myDatabase.rawQuery(query,null);
	}

	public void addVideo(String sno, String movieID, String name, String url, String language, String genre, String description,
						 String duration, String thumbnail, String date, int views, int likes , String average)
	{
		String where = "movieID='" + movieID +"'";
		Cursor c = myDatabase.query("videos", null, where, null, null, null, null);

		if (c.moveToNext()) {
			ContentValues cv = new ContentValues();
			cv.put("sno", sno);
			cv.put("movieID", movieID);
			cv.put("name", name);
			cv.put("description", description);
			cv.put("url", url);
			cv.put("language", language);
			cv.put("genre", genre);
			cv.put("description", description);
			cv.put("duration", duration);
			cv.put("thumbnail", thumbnail);
			cv.put("date", date);
			cv.put("views", views);
			cv.put("likes", likes);
			cv.put("average", average);
			myDatabase.update("videos", cv, where, null);
		} else {
			ContentValues cv = new ContentValues();
			cv.put("sno", sno);
			cv.put("movieID", movieID);
			cv.put("name", name);
			cv.put("description", description);
			cv.put("url", url);
			cv.put("language", language);
			cv.put("genre", genre);
			cv.put("description", description);
			cv.put("duration", duration);
			cv.put("thumbnail", thumbnail);
			cv.put("date", date);
			cv.put("views", views);
			cv.put("likes", likes);
			cv.put("average", average);
			myDatabase.insert("videos", null, cv);
		}
	}

	public void addLatest(String sno, String movieID, String name, String url, String language, String genre, String description,
						  String duration, String thumbnail, String date, int views, int likes , String average)
	{
		String where = "movieID='" + movieID +"'";
		Cursor c = myDatabase.query("latest", null, where, null, null, null, null);

		if (c.moveToNext()) {
			ContentValues cv = new ContentValues();
			cv.put("sno", sno);
			cv.put("movieID", movieID);
			cv.put("name", name);
			cv.put("description", description);
			cv.put("url", url);
			cv.put("language", language);
			cv.put("genre", genre);
			cv.put("description", description);
			cv.put("duration", duration);
			cv.put("thumbnail", thumbnail);
			cv.put("date", date);
			cv.put("views", views);
			cv.put("likes", likes);
			cv.put("average", average);
			myDatabase.update("latest", cv, where, null);
		} else {
			ContentValues cv = new ContentValues();
			cv.put("sno", sno);
			cv.put("movieID", movieID);
			cv.put("name", name);
			cv.put("description", description);
			cv.put("url", url);
			cv.put("language", language);
			cv.put("genre", genre);
			cv.put("description", description);
			cv.put("duration", duration);
			cv.put("thumbnail", thumbnail);
			cv.put("date", date);
			cv.put("views", views);
			cv.put("likes", likes);
			cv.put("average", average);
			myDatabase.insert("latest", null, cv);
		}
	}

	public boolean isClap(String movieID)
	{
		Cursor c = myDatabase.rawQuery("select * from clap where movieID='"+movieID+"'" , null);
		if(c.moveToNext())
			return true;
		else
			return false;
	}

	public Cursor isIDinVideos(String movieID)
	{
		Cursor c = myDatabase.rawQuery("select name,description,url from videos where movieID='"+movieID+"'" , null);
		return c;
	}

	public Cursor isIDinLatest(String movieID)
	{
		Cursor c = myDatabase.rawQuery("select name,description,url from latest where movieID='"+movieID+"'" , null);
		return c;
	}

	public void removeClap(String movieID)
	{
		myDatabase.rawQuery("delete from clap where movieID='"+movieID+"'" , null);
	}

	public void addClap(String movieID)
	{
		String where = "movieID='" + movieID +"'";
		Cursor c = myDatabase.query("clap", null, where, null, null, null, null);

		if (c.moveToNext()) {
			ContentValues cv = new ContentValues();
			cv.put("movieID", movieID);
			myDatabase.update("clap", cv, where, null);
		} else {
			ContentValues cv = new ContentValues();
			cv.put("movieID", movieID);
			myDatabase.insert("clap", null, cv);
		}
	}

	public void addView(String movieID, String url, String language, String genre)
	{
		String where = "movieID='" + movieID +"'";
		Cursor c = myDatabase.query("view", null, where, null, null, null, null);

		if (c.moveToNext()) {
			ContentValues cv = new ContentValues();
			cv.put("movieID", movieID);
			cv.put("url", url);
			cv.put("language", language);
			cv.put("genre", genre);
			myDatabase.update("view", cv, where, null);
		} else {
			ContentValues cv = new ContentValues();
			cv.put("movieID", movieID);
			cv.put("url", url);
			cv.put("language", language);
			cv.put("genre", genre);
			myDatabase.insert("view", null, cv);
		}
	}

	public void delete(String tableName)
	{
		myDatabase.delete(tableName , null, null);
	}

	public SQLiteDatabase getDatabase() {
		return myDatabase;
	}
}

