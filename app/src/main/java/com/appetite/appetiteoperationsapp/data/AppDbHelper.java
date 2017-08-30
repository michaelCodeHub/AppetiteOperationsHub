/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appetite.appetiteoperationsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.appetite.appetiteoperationsapp.Database.AppContract;

import java.io.File;

/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class AppDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = AppDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "appetiteoperations.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link AppDbHelper}.
     *
     * @param context of the app
     */
    public AppDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
		super(context, Environment.getExternalStorageDirectory()
				+ File.separator + "appetiteoperations"
				+ File.separator + "database", null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
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

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}