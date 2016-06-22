package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Consultorio
{

    public final static String TABLE_NAME = "Consultorio";

    public final static String ID = "id";
    public final static String NOME = "nome";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOME + " TEXT NOT NULL );";

    public long id;
    public String nome;

    public Consultorio() {}

    public Consultorio( HashMap<String, String> consultorioHashMap )
    {

        this.id = Long.parseLong( consultorioHashMap.get( ID ) );
        this.nome = consultorioHashMap.get( NOME );

    }

    public ContentValues toContentValues()
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put( ID, id );
        contentValues.put( NOME, nome );

        return contentValues;

    }

}