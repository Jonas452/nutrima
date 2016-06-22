package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class PratoOpcao
{

    public final static String TABLE_NAME = "PratoOpcao";

    public final static String ID = "id";
    public final static String PRATO_ID = "pratoId";
    public final static String REFEICAO_ALIMENTO_ID = "refeicaoAlimentoId";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRATO_ID + " INTEGER NOT NULL, " +
                    REFEICAO_ALIMENTO_ID + " INTEGER NOT NULL );";

    public long id;
    public long pratoId;
    public long refeicaoAlimentoId;

    public PratoOpcao() {}

    public PratoOpcao( HashMap<String, String> pratoOpcaoHashMap )
    {

        this.id = Long.parseLong( pratoOpcaoHashMap.get( ID ) );
        this.pratoId = Long.parseLong( pratoOpcaoHashMap.get( PRATO_ID ) );
        this.refeicaoAlimentoId = Long.parseLong( pratoOpcaoHashMap.get( REFEICAO_ALIMENTO_ID ) );

    }

    public ContentValues toContentValues()
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put( ID, id );
        contentValues.put( PRATO_ID, pratoId );
        contentValues.put( REFEICAO_ALIMENTO_ID, refeicaoAlimentoId );

        return contentValues;

    }

}