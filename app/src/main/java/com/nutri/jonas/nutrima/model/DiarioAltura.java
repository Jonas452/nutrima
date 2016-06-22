package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class DiarioAltura
{
    public final static String TABLE_NAME = "DiarioAltura";

    public final static String ID = "id";
    public final static String PACIENTE_ID = "pacienteId";
    public final static String ALTURA = "altura";
    public final static String OBSERVACAO = "observacao";
    public final static String DATA_MEDICAO = "dataMedicao";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PACIENTE_ID + " INTEGER NOT NULL, " +
                    ALTURA + " REAL NOT NULL, " +
                    OBSERVACAO + " TEXT, " +
                    DATA_MEDICAO + " TEXT NOT NULL );";

    public long id;
    public long pacienteId;
    public float altura;
    public String observacao;
    public String dataMedicao;

    public DiarioAltura() {}

    public DiarioAltura( HashMap<String, String> diarioAlturaHashMap )
    {

        this.id = Long.parseLong( diarioAlturaHashMap.get( ID ) );
        this.pacienteId = Long.parseLong( diarioAlturaHashMap.get( PACIENTE_ID ) );
        this.altura = Float.parseFloat( diarioAlturaHashMap.get( ALTURA ) );
        this.observacao = diarioAlturaHashMap.get( OBSERVACAO );
        this.dataMedicao = diarioAlturaHashMap.get( DATA_MEDICAO );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( PACIENTE_ID, pacienteId );
        contentValues.put( ALTURA, altura );
        contentValues.put( OBSERVACAO, observacao );
        contentValues.put( DATA_MEDICAO, dataMedicao );

        return contentValues;

    }

}