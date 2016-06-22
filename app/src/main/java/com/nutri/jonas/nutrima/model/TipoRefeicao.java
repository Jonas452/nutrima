package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import com.nutri.jonas.nutrima.util.JSONHandler;

import org.json.JSONObject;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class TipoRefeicao
{

    public final static String TABLE_NAME = "TipoRefeicao";

    public final static String ID = "id";
    public final static String TIPO = "tipo";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TIPO + " TEXT NOT NULL );";

    public long id;
    public String tipo;

    public TipoRefeicao() {}

    public TipoRefeicao( HashMap<String, String> tipoRefeicaoHashMap )
    {

        this.id = Long.parseLong( tipoRefeicaoHashMap.get( ID ) );
        this.tipo = tipoRefeicaoHashMap.get( TIPO );

    }

    public TipoRefeicao( JSONObject jsonObject )
    {

        this.id = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, ID ) );
        this.tipo = JSONHandler.getStringFromJSONObject( jsonObject, TIPO );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( TIPO, tipo );

        return contentValues;

    }

}