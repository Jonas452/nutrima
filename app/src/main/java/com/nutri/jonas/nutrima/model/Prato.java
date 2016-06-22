package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Prato
{

    public final static String TABLE_NAME = "Prato";

    public final static String ID = "id";
    public final static String NOME = "nome";
    public final static String HORARIO = "horario";
    public final static String ALARME = "alarme";
    public final static String FAVORITO = "favorito";
    public final static String NOTA = "nota";
    public final static String OBSERVACAO = "observacao";
    public final static String DATA_AVALIACAO = "dataAvaliacao";
    public final static String ATIVO = "ativo";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOME + " TEXT NOT NULL, " +
                    HORARIO + " TEXT NOT NULL, " +
                    ALARME + " INTEGER, " +
                    FAVORITO + " BLOB DEFAULT false, " +
                    NOTA + " INTEGER, " +
                    OBSERVACAO + " TEXT, " +
                    DATA_AVALIACAO + " TEXT, " +
                    ATIVO + " INTEGER NOT NULL DEFAULT 1 );";

    public long id;
    public String nome;
    public String horario;
    public int alarme;
    public boolean favorito;
    public int nota;
    public String observacao;
    public String dataAvaliacao;
    public int ativo;

    public Prato() {}

    public Prato( HashMap<String, String> pratoHashMap )
    {

        this.id = Long.parseLong(pratoHashMap.get(ID));
        this.nome = pratoHashMap.get(NOME);
        this.horario = pratoHashMap.get(HORARIO);
        this.alarme = Integer.parseInt( pratoHashMap.get( ALARME ) );
        this.favorito = Boolean.parseBoolean( pratoHashMap.get( FAVORITO ) );
        this.nota = Integer.parseInt( pratoHashMap.get( NOTA ) );
        this.observacao = pratoHashMap.get( OBSERVACAO );
        this.dataAvaliacao = pratoHashMap.get(DATA_AVALIACAO);
        this.ativo = Integer.parseInt( pratoHashMap.get( ATIVO ) );

    }

    public ContentValues toContentValues()
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put( ID, id );
        contentValues.put( NOME, nome );
        contentValues.put( HORARIO, horario );
        contentValues.put( ALARME, alarme );
        contentValues.put( FAVORITO, favorito );
        contentValues.put( NOTA, nota );
        contentValues.put( OBSERVACAO, observacao );
        contentValues.put( DATA_AVALIACAO, dataAvaliacao );
        contentValues.put( ATIVO, ativo );

        return contentValues;

    }

}