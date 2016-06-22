package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class DiarioPeso
{

    public final static String TABLE_NAME = "DiarioPeso";

    public final static String ID = "id";
    public final static String PACIENTE_ID = "pacienteId";
    public final static String PESO = "peso";
    public final static String OBSERVACAO = "observacao";
    public final static String IMC = "imc";
    public final static String DATA_PESAGEM = "dataPesagem";

    public long id;
    public long pacienteId;
    public float peso;
    public String observacao;
    public double imc;
    public String dataPesagem;

    public final static String CREATE_TABLE_SQL_STATEMENT =
        "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PACIENTE_ID + " INTEGER NOT NULL, " +
                PESO + " REAL NOT NULL, " +
                OBSERVACAO + " TEXT, " +
                IMC + " REAL NOT NULL, " +
                DATA_PESAGEM + " TEXT NOT NULL );";

    public DiarioPeso() {}

    public DiarioPeso( HashMap<String, String> diarioPesoHashMap )
    {

        this.id = Long.parseLong( diarioPesoHashMap.get( ID ) );
        this.pacienteId = Long.parseLong( diarioPesoHashMap.get(PACIENTE_ID ) );
        this.peso = Float.parseFloat(diarioPesoHashMap.get(PESO));
        this.observacao = diarioPesoHashMap.get( OBSERVACAO );
        this.imc = Float.parseFloat( diarioPesoHashMap.get( IMC ) );
        this.dataPesagem = diarioPesoHashMap.get( DATA_PESAGEM );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( PACIENTE_ID, pacienteId );
        contentValues.put( PESO, peso );
        contentValues.put( OBSERVACAO, observacao );
        contentValues.put( IMC, imc );
        contentValues.put( DATA_PESAGEM, dataPesagem );

        return contentValues;

    }

}