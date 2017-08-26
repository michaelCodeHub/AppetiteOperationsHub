package com.appetite.appetiteoperationsapp.data;

/**
 * Created by MikeJaison on 2/6/17.
 */
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * {@link ContentProvider} for Pets app.
 */
public class AppProvider extends ContentProvider {

    AppDbHelper appDbHelper;

    public static final int USERS = 100;
    public static final int USERS_ID = 101;

    public static final int CLIENTS = 102;
    public static final int CLIENTS_ID = 103;

    public static final int TASKS = 104;
    public static final int TASKS_ID = 105;

    public static final int TASK_PROGRESS = 106;
    public static final int TASK_PROGRESS_ID = 107;

    public static final int BOTH = 108;

    public static final String LOG_TAG = AppProvider.class.getSimpleName();

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static
    {
        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "users" , USERS);
        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "users/#" , USERS_ID);

        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "clients" , CLIENTS);
        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "clients/#" , CLIENTS_ID);

        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "tasks" , TASKS);
        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "tasks/#" , TASKS_ID);

        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "tasks_progress" , TASK_PROGRESS);
        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "tasks_progress/#" , TASK_PROGRESS_ID);

        sUriMatcher.addURI(AppContract.CONTENT_AUTHORITY , "both" , BOTH);
    }

    @Override
    public boolean onCreate() {
        appDbHelper = new AppDbHelper(getContext());

        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase sqLiteDatabase = appDbHelper.getReadableDatabase();

        Cursor cursor = null;

        int match = sUriMatcher.match(uri);

        switch (match)
        {
            case USERS:
                cursor = sqLiteDatabase.query(AppContract.UserEntry.TABLE_NAME , projection , selection , selectionArgs , null,null,sortOrder);
                break;

            case USERS_ID:
                selection = AppContract.UserEntry.ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(AppContract.UserEntry.TABLE_NAME , projection , selection , selectionArgs , null ,null, sortOrder);
                break;

            case CLIENTS:
                cursor = sqLiteDatabase.query(AppContract.ClientsEntry.TABLE_NAME , projection , selection , selectionArgs , null,null,sortOrder);
                break;

            case CLIENTS_ID:
                selection = AppContract.ClientsEntry.ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(AppContract.ClientsEntry.TABLE_NAME , projection , selection , selectionArgs , null ,null, sortOrder);
                break;

            case TASKS:
                cursor = sqLiteDatabase.query(AppContract.TaskEntry.TABLE_NAME , projection , selection , selectionArgs , null,null,sortOrder);
                break;

            case TASKS_ID:
                cursor = sqLiteDatabase.query(AppContract.TaskEntry.TABLE_NAME , projection , selection , selectionArgs , null ,null, sortOrder);
                break;

            case TASK_PROGRESS:
                cursor = sqLiteDatabase.query(AppContract.TaskProgressEntry.TABLE_NAME , projection , selection , selectionArgs , null,null,sortOrder);
                break;

            case TASK_PROGRESS_ID:
                cursor = sqLiteDatabase.query(AppContract.TaskProgressEntry.TABLE_NAME , projection , selection , selectionArgs , null ,null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI"+ uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = sUriMatcher.match(uri);

        Uri result;

        switch (match)
        {
            case USERS:
                result = insertUSERS(uri , contentValues);
                break;

            case CLIENTS:
                result =  insertCLIENTS(uri , contentValues);
                break;

            case TASKS:
                result =  insertTASKS(uri , contentValues);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI"+ uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return result;
    }

    private Uri insertUSERS(Uri uri , ContentValues contentValues)
    {

        SQLiteDatabase sqLiteDatabase = appDbHelper.getReadableDatabase();

        long ID = sqLiteDatabase.insert(AppContract.UserEntry.TABLE_NAME , null, contentValues);

        return ContentUris.withAppendedId(uri , ID);
    }

    private Uri insertCLIENTS(Uri uri , ContentValues contentValues)
    {

        SQLiteDatabase sqLiteDatabase = appDbHelper.getReadableDatabase();

        long ID = sqLiteDatabase.insert(AppContract.ClientsEntry.TABLE_NAME , null, contentValues);

        return ContentUris.withAppendedId(uri , ID);
    }

    private Uri insertTASKS(Uri uri , ContentValues contentValues)
    {

        SQLiteDatabase sqLiteDatabase = appDbHelper.getReadableDatabase();

        long ID = sqLiteDatabase.insert(AppContract.TaskEntry.TABLE_NAME , null, contentValues);

        return ContentUris.withAppendedId(uri , ID);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = appDbHelper.getReadableDatabase();

        int result = 0;

        int match = sUriMatcher.match(uri);

        switch (match)
        {
            case USERS:
                result = sqLiteDatabase.update(AppContract.UserEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            case USERS_ID:
                selection = AppContract.UserEntry.ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = sqLiteDatabase.update(AppContract.UserEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            case CLIENTS:
                result = sqLiteDatabase.update(AppContract.ClientsEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            case CLIENTS_ID:
                selection = AppContract.ClientsEntry.CLIENTID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = sqLiteDatabase.update(AppContract.ClientsEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            case TASKS:
                result = sqLiteDatabase.update(AppContract.TaskEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            case TASKS_ID:
                selection = AppContract.TaskEntry.TASKID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = sqLiteDatabase.update(AppContract.TaskEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            case TASK_PROGRESS:
                result = sqLiteDatabase.update(AppContract.TaskEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            case TASK_PROGRESS_ID:
                selection = AppContract.TaskEntry.TASKID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = sqLiteDatabase.update(AppContract.TaskEntry.TABLE_NAME, contentValues, selection , selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI"+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return result;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = appDbHelper.getReadableDatabase();

        int result = 0;

        int match = sUriMatcher.match(uri);

        switch (match)
        {
            case USERS:
                result = sqLiteDatabase.delete(AppContract.UserEntry.TABLE_NAME, selection , selectionArgs);
                break;

            case USERS_ID:
                selection = AppContract.UserEntry.ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = sqLiteDatabase.delete(AppContract.UserEntry.TABLE_NAME, selection , selectionArgs);
                break;

            case CLIENTS:
                result = sqLiteDatabase.delete(AppContract.ClientsEntry.TABLE_NAME, selection , selectionArgs);
                break;

            case CLIENTS_ID:
                selection = AppContract.ClientsEntry.CLIENTID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = sqLiteDatabase.delete(AppContract.ClientsEntry.TABLE_NAME, selection , selectionArgs);
                break;

            case TASKS:
                result = sqLiteDatabase.delete(AppContract.TaskEntry.TABLE_NAME, selection , selectionArgs);
                break;

            case TASKS_ID:
                selection = AppContract.TaskEntry.TASKID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                result = sqLiteDatabase.delete(AppContract.TaskEntry.TABLE_NAME, selection , selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI"+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }



    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
