package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.DiarioAtividade;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class DiarioAtividadeController extends Controller
{

    public DiarioAtividadeController( Context context )
    {

        super( context, DiarioAtividade.TABLE_NAME, DiarioAtividade.ID );

    }

    public boolean insertDiarioAtividade( DiarioAtividade diarioAtividade )
    {

        return insert( diarioAtividade.toContentValues( false ) );

    }

    public boolean updateDiarioAtividade(DiarioAtividade diarioAtividade )
    {

        return update( diarioAtividade.toContentValues( true ) );

    }

    public boolean deleteDiarioAtividade( long id )
    {

        return delete(id);

    }

    public ArrayList<DiarioAtividade> getAllDiarioAtividade()
    {

        return fromArrayListHashMapToArrayListDiarioAtividade( selectAll( null, null ) );

    }

    public DiarioAtividade getDiarioAtividadeById( long id )
    {

        return new DiarioAtividade( selectById( id ) ) ;

    }

    private ArrayList<DiarioAtividade> fromArrayListHashMapToArrayListDiarioAtividade( ArrayList<HashMap<String,String>> arrayListHashMap )
    {

        ArrayList<DiarioAtividade> arrayList = null;

        if( arrayListHashMap != null )
        {

            arrayList = new ArrayList<DiarioAtividade>();

            for (int i = 0; i < arrayListHashMap.size(); i++) {

                arrayList.add(new DiarioAtividade(arrayListHashMap.get(i)));

            }

        }

        return arrayList;

    }

}