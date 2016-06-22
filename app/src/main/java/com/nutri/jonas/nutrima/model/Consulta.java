package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Consulta
{

    public final static String TABLE_NAME = "Consulta";

    public final static String ID = "id";
    public final static String NUTRICIONISTA_ID = "nutricionistaId";
    public final static String PACIENTE_ID = "pacienteId";
    public final static String DATA_CONSULTA = "dataConsulta";
    public final static String HORARIO = "horario";
    public final static String OBSERVACAO = "observacao";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NUTRICIONISTA_ID + " INTEGER NOT NULL, " +
                    PACIENTE_ID + " INTEGER NOT NULL, " +
                    DATA_CONSULTA + " TEXT NOT NULL, " +
                    HORARIO + " TEXT NOT NULL, " +
                    OBSERVACAO + " TEXT );";

    public long id;
    public long nutricionistaId;
    public long pacienteId;
    public String dataConsulta;
    public String horario;
    public String observacao;

    public Consulta() {}

    public Consulta( HashMap<String, String> consultaHashMap )
    {

        this.id = Long.parseLong( consultaHashMap.get( ID ) );
        this.nutricionistaId = Long.parseLong( consultaHashMap.get( NUTRICIONISTA_ID ) );
        this.pacienteId = Long.parseLong( consultaHashMap.get( PACIENTE_ID ) );
        this.dataConsulta = consultaHashMap.get( DATA_CONSULTA );
        this.horario = consultaHashMap.get( HORARIO );
        this.observacao = consultaHashMap.get( OBSERVACAO );

    }

    public ContentValues toContentValues()
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put( ID, this.id );
        contentValues.put( NUTRICIONISTA_ID, this.nutricionistaId );
        contentValues.put( PACIENTE_ID, this.pacienteId );
        contentValues.put( DATA_CONSULTA, this.dataConsulta );
        contentValues.put( HORARIO, this.horario );
        contentValues.put( OBSERVACAO, this.observacao );

        return contentValues;

    }

}