package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.AtividadeFisica;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class AtividadeFisicaController extends Controller
{

    public AtividadeFisicaController( Context context )
    {

        super( context, AtividadeFisica.TABLE_NAME, AtividadeFisica.ID );

    }

    public boolean insertAtividadeFisica( AtividadeFisica atividadeFisica )
    {

        return insert( atividadeFisica.toContentValues( false ) );

    }

    public boolean insertAtividadeFisicaWithId( AtividadeFisica atividadeFisica )
    {

        return insert( atividadeFisica.toContentValues( true ) );

    }

    public boolean updateAtividadeFisica( AtividadeFisica atividadeFisica )
    {

        return update( atividadeFisica.toContentValues( true ) );
        
    }

    public ArrayList<AtividadeFisica> getAllAtividadeFisica()
    {

        return fromArrayListHashMapToArrayListAtividadeFisica( selectAll( null, null ) );

    }

    public AtividadeFisica getAtividadeFisicaById( long id )
    {

        return new AtividadeFisica( selectById( id ) );

    }

    private ArrayList<AtividadeFisica> fromArrayListHashMapToArrayListAtividadeFisica( ArrayList<HashMap<String,String>> arrayListHashMap )
    {
        ArrayList<AtividadeFisica> arrayList = null;

        if( arrayListHashMap != null ) {

            arrayList = new ArrayList<AtividadeFisica>();

            for (int i = 0; i < arrayListHashMap.size(); i++) {

                arrayList.add(new AtividadeFisica(arrayListHashMap.get(i)));

            }

        }

        return arrayList;

    }

}