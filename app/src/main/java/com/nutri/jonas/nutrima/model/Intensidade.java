package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import com.nutri.jonas.nutrima.util.JSONHandler;

import org.json.JSONObject;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Intensidade
{

    public final static String TABLE_NAME = "Intensidade";

    public final static String ID = "id";
    public final static String INTENSIDADE = "intensidade";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INTENSIDADE + " TEXT NOT NULL );";

    public long id;
    public String intensidade;

    public Intensidade() {}

    public Intensidade( HashMap<String, String> intensidadeHashMap )
    {

        this.id = Long.parseLong( intensidadeHashMap.get( ID ) );
        this.intensidade = intensidadeHashMap.get( INTENSIDADE );

    }

    public Intensidade( JSONObject jsonObject )
    {

        this.id = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, ID ) );
        this.intensidade = JSONHandler.getStringFromJSONObject( jsonObject, INTENSIDADE );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( INTENSIDADE, intensidade );

        return contentValues;

    }

}