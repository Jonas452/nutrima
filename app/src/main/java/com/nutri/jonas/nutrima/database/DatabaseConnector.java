package com.nutri.jonas.nutrima.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnector
{

    private static final String DATABASE_NAME = "nutrima.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector( Context context )
    {

        databaseOpenHelper = new DatabaseOpenHelper( context, DATABASE_NAME, null, DATABASE_VERSION );

    }

    public void open() throws SQLException
    {

        database = databaseOpenHelper.getWritableDatabase();

    }

    public void close()
    {

        if( database != null )
        {

            database.close();

        }

    }

    public SQLiteDatabase getDatabase() { return this.database; }

}