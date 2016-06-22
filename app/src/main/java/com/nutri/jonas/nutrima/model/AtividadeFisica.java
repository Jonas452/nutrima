package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import com.nutri.jonas.nutrima.util.JSONHandler;

import org.json.JSONObject;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class AtividadeFisica
{

    public final static String TABLE_NAME = "AtividadeFisica";

    public final static String ID = "id";
    public final static String NOME = "nome";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOME + " TEXT NOT NULL );";

    public long id;
    public String nome;

    public AtividadeFisica() { }

    public AtividadeFisica( HashMap<String, String> atividadeFisicaHashMap )
    {

        this.id = Long.parseLong( atividadeFisicaHashMap.get( ID ) );
        this.nome = atividadeFisicaHashMap.get( NOME );

    }

    public AtividadeFisica( JSONObject jsonObject )
    {

        this.id = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, ID ) );
        this.nome = JSONHandler.getStringFromJSONObject( jsonObject, NOME );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( NOME, nome );

        return contentValues;

    }

}