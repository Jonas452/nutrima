package com.nutri.jonas.nutrima.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.nutri.jonas.nutrima.database.DatabaseConnector;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Controller
{

    private DatabaseConnector databaseConnector;

    private String tableName;
    private String primaryKeyName;

    public Controller() {}

    /*
    METHOD = Controller;
    SUMMAY = The constructor of the class;

    PARAMETERS

    context = The context used to create the connection with the database;
    tableName = The table name that actions will occur;
    primaryKeyName = The primary key name that is going to be used in delete, update and select fucntions;
    */
    public Controller( Context context, String tableName, String primaryKeyName )
    {

        databaseConnector = new DatabaseConnector( context );

        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;

    }

    /*
    METHOD = insert;
    SUMMAY = Insert the values into the table attached to the controller;

    PARAMETERS

    contentValues = The values that are going to be inserted;
    */
    protected boolean insert( ContentValues contentValues )
    {

        long insertedSuccessfully;

        databaseConnector.open();

        insertedSuccessfully = databaseConnector.getDatabase().insert(
                tableName,
                null,
                contentValues);

        databaseConnector.close();

        if( insertedSuccessfully >= 0 )
            return true;
        else
            return false;

    }

    /*
    METHOD = update;
    SUMMAY = Update the values into the table attached to the controller;

    PARAMETERS

    contentValues = The values that are going to be updated;
    */
    protected boolean update( ContentValues contentValues  )
    {

        long updatedSuccessfully;

        databaseConnector.open();

        updatedSuccessfully = databaseConnector.getDatabase().update(
                tableName,
                contentValues,
                primaryKeyName + " = " + contentValues.getAsString(primaryKeyName),
                null);

        databaseConnector.close();

        if( updatedSuccessfully >= 0 )
            return true;
        else
            return false;

    }

    /*
    METHOD = delete;
    SUMMAY = Delete the specific values from the table attached to the controller;

    PARAMETERS

    primaryKeyValue = The table data id that should be deleted;
    */
    protected boolean delete( long primaryKeyValue )
    {

        long deletedSuccessfully;

        databaseConnector.open();

        deletedSuccessfully = databaseConnector.getDatabase().delete(
                tableName,
                primaryKeyName + " = " + primaryKeyValue,
                null);

        databaseConnector.close();

        if( deletedSuccessfully > 0 )
            return true;
        else
            return false;

    }

    /*
    METHOD = selectAll;
    SUMMAY = Select all the data from the table attached to the controller;

    PARAMETERS
    Doesn't apply;
    */
    protected ArrayList< HashMap<String, String> > selectAll( String whereClause, String orderBy )
    {

        databaseConnector.open();

        Cursor cursor = databaseConnector.getDatabase().query(
                tableName,
                null,
                whereClause,
                null,
                null,
                null,
                orderBy );

        ArrayList< HashMap<String, String> > arrayListHashMap = cursorToArrayListOfHashMaps( cursor );

        databaseConnector.close();

        return arrayListHashMap;

    }

    /*
    METHOD = selectById;
    SUMMAY = Select one data from the table attached to the controller;

    PARAMETERS
    primaryKeyValue = The table data id that should be retrived;
    */
    protected HashMap<String, String> selectById( long primaryKeyValue )
    {

        databaseConnector.open();

        Cursor cursor = databaseConnector.getDatabase().query(
                tableName,
                null,
                primaryKeyName + " = " + primaryKeyValue,
                null,
                null,
                null,
                null );

        if( cursor != null && cursor.moveToFirst() )
        {

            HashMap<String, String> hashMap = cursorToHashMap( cursor );

            databaseConnector.close();

            return hashMap;

        }

        return null;

    }

    /*
    METHOD = cursorToArrayListOfHashMaps;
    SUMMAY = Transform a cursor into a arraylist of hashmaps;

    PARAMETERS
    cursor = The curso that is going to be transformed;
    */
    private ArrayList< HashMap<String, String> > cursorToArrayListOfHashMaps( Cursor cursor )
    {

        if( cursor != null && cursor.moveToFirst() )
        {

            ArrayList< HashMap<String, String> > arrayListOfHashMaps = new ArrayList< HashMap<String, String> >();

            for( int i = 0; i < cursor.getCount(); i++ )
            {

                cursor.moveToPosition( i );
                arrayListOfHashMaps.add( cursorToHashMap( cursor ) );

            }

            return arrayListOfHashMaps;

        }else
        {

            return null;

        }

    }

    /*
    METHOD = cursorToHashMap;
    SUMMAY = Transform a specific opsition of the cursor into a hashmap;

    PARAMETERS
    cursor = The curso that is going to be transformed;
    */
    private HashMap< String, String> cursorToHashMap( Cursor cursor )
    {

        if( cursor != null )
        {

            Log.e( "CURSOR_DUMP", DatabaseUtils.dumpCursorToString( cursor ) );

            HashMap<String, String> hashMap = new HashMap<String, String>();

            for (int i = 0; i < cursor.getColumnCount(); i++)
            {

                Log.e("CURSOR", "Key: " + cursor.getColumnName(i) + " - Value: " + cursor.getString(i) );

                hashMap.put( cursor.getColumnName(i), cursor.getString(i) );

            }

            return hashMap;

        }else
        {

            return null;

        }

    }

}