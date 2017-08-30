package com.appetite.appetiteoperationsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;


public class VerificationDBOpenHelper extends SQLiteOpenHelper
{
	public VerificationDBOpenHelper(Context context) {
//		super(context, context.getFilesDir() + "/database/lsvideos", null, 1);
		super(context, Environment.getExternalStorageDirectory()
				+ File.separator + "appetiteoperations"
				+ File.separator + "database", null, 1);
}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE " + AppContract.UserEntry.TABLE_NAME + "(" + AppContract.UserEntry.ID
				+ " INTEGER PRIMARY KEY   AUTOINCREMENT,"+ AppContract.UserEntry.SNO
				+ " INTEGER ,"+ AppContract.UserEntry.USERID
				+ " TEXT ,"+ AppContract.UserEntry.TYPE
				+ " INT ,"+ AppContract.UserEntry.NAME
				+ " TEXT  , UNIQUE("+AppContract.UserEntry.USERID
				+"))");

		db.execSQL("CREATE TABLE " + AppContract.ClientsEntry.TABLE_NAME + "(" + AppContract.ClientsEntry.ID
				+ " INTEGER PRIMARY KEY   AUTOINCREMENT,"+ AppContract.ClientsEntry.SNO
				+ " INTEGER ,"+ AppContract.ClientsEntry.CLIENTID
				+ " TEXT ,"+ AppContract.ClientsEntry.NAME
				+ " TEXT ,"+ AppContract.ClientsEntry.ADDRESS
				+ " TEXT ,"+ AppContract.ClientsEntry.NUMBER
				+ " TEXT  , UNIQUE("+AppContract.ClientsEntry.CLIENTID
				+"))");

		db.execSQL("CREATE TABLE " + AppContract.TaskEntry.TABLE_NAME + "(" + AppContract.TaskEntry.ID
				+ " INTEGER PRIMARY KEY   AUTOINCREMENT,"+ AppContract.TaskEntry.SNO
				+ " INTEGER ,"+ AppContract.TaskEntry.TASKID
				+ " TEXT ,"+ AppContract.TaskEntry.CLIENTID
				+ " TEXT ,"+ AppContract.TaskEntry.NAME
				+ " TEXT ,"+ AppContract.TaskEntry.CLIENT_DEADLINE
				+ " TEXT ,"+ AppContract.TaskEntry.DESCRIPTION
				+ " TEXT ,"+ AppContract.TaskEntry.STATUS
				+ " INTEGER  , UNIQUE("+AppContract.TaskEntry.TASKID
				+"))");

		db.execSQL("CREATE TABLE " + AppContract.TaskProgressEntry.TABLE_NAME + "(" + AppContract.TaskProgressEntry.ID
				+ " INTEGER PRIMARY KEY   AUTOINCREMENT,"+ AppContract.TaskProgressEntry.SNO
				+ " INTEGER ,"+ AppContract.TaskProgressEntry.TASKID
				+ " TEXT ,"+ AppContract.TaskProgressEntry.STATUS
				+ " TEXT ,"+ AppContract.TaskProgressEntry.DEADLINE
				+ " TEXT ,"+ AppContract.TaskProgressEntry.EMPLOYEE
				+ " TEXT ,"+ AppContract.TaskProgressEntry.START_TIME
				+ " TEXT ,"+ AppContract.TaskProgressEntry.END_TIME
				+ " TEXT  , UNIQUE("+AppContract.TaskProgressEntry.TASKID
				+"))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}