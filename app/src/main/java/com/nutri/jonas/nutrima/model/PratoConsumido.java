package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class PratoConsumido
{

    public final static String TABLE_NAME = "PratoConsumido";

    public final static String ID = "id";
    public final static String PRATO_ID = "pratoId";
    public final static String DATA_CONSUMO = "dataConsumo";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRATO_ID + " INTEGER NOT NULL, " +
                    DATA_CONSUMO + " TEXT NOT NULL );";

    public long id;
    public long pratoId;
    public String dataConsumo;

    public PratoConsumido() {}

    public PratoConsumido( HashMap<String,String> pratoConsumidoHashMap )
    {

        this.id = Long.parseLong( pratoConsumidoHashMap.get( ID ) );
        this.pratoId = Long.parseLong( pratoConsumidoHashMap.get( PRATO_ID ) );
        this.dataConsumo = pratoConsumidoHashMap.get( DATA_CONSUMO );

    }

    public ContentValues toContentValues()
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put( ID, id );
        contentValues.put( PRATO_ID, pratoId );
        contentValues.put( DATA_CONSUMO, dataConsumo );

        return contentValues;

	}

}