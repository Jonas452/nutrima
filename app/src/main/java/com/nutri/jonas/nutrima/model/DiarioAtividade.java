package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class DiarioAtividade
{

    public final static String TABLE_NAME = "DiarioAtividade";

    public final static String ID = "id";
    public final static String ATIVIDADE_INTENSIDADE_ID = "atividadeIntensidadeId";
    public final static String ATIVIDADE_FISICA_ID = "atividadeFisicaId";
    public final static String DATA_REALIZADA = "dataRealizada";
    public final static String DURACAO = "duracao";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ATIVIDADE_INTENSIDADE_ID + " INTEGER NOT NULL, " +
                    ATIVIDADE_FISICA_ID + " INTEGER NOT NULL, " +
                    DATA_REALIZADA + " TEXT NOT NULL, " +
                    DURACAO + " INTEGER NOT NULL );";

    public long id;
    public long atividadeIntensidadeId;
    public long atividadeFisicaId;
    public String dataRealizada;
    public int duracao;

    public DiarioAtividade() {}

    public DiarioAtividade( HashMap<String, String> diarioAtividadeHashMap )
    {

        this.id = Long.parseLong( diarioAtividadeHashMap.get( ID ) );
        this.atividadeIntensidadeId = Long.parseLong( diarioAtividadeHashMap.get(ATIVIDADE_INTENSIDADE_ID ) );
        this.atividadeFisicaId = Long.parseLong( diarioAtividadeHashMap.get(ATIVIDADE_FISICA_ID) );
        this.dataRealizada = diarioAtividadeHashMap.get( DATA_REALIZADA );
        this.duracao = Integer.parseInt( diarioAtividadeHashMap.get( DURACAO ) );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( ATIVIDADE_INTENSIDADE_ID, atividadeIntensidadeId );
        contentValues.put(ATIVIDADE_FISICA_ID, atividadeFisicaId );
        contentValues.put( DATA_REALIZADA, dataRealizada );
        contentValues.put( DURACAO, duracao );

        return contentValues;

    }
	
}
