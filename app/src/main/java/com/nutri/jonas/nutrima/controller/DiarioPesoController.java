package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.DiarioPeso;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class DiarioPesoController extends Controller
{

    public DiarioPesoController( Context context )
    {

        super( context, DiarioPeso.TABLE_NAME, DiarioPeso.ID );

    }

    public boolean insertDiarioPeso( DiarioPeso diarioPeso )
    {

        return insert( diarioPeso.toContentValues( false ) );

    }

    public boolean insertDiarioPesoWithId( DiarioPeso diarioPeso )
    {

        return insert( diarioPeso.toContentValues( true ) );

    }

    public boolean updateDiarioPeso( DiarioPeso diarioPeso )
    {

        return update( diarioPeso.toContentValues( true ) );

    }

    public boolean deleteDiarioPeso( long id )
    {

        return delete( id );

    }

    public ArrayList<DiarioPeso> getAllDiarioPeso()
    {

        String orderBy = DiarioPeso.DATA_PESAGEM + " asc";
        return fromArrayListHashMapToArrayListDiarioPeso( selectAll( null, orderBy ) );

    }

    public DiarioPeso getDiarioPesoById( long id )
    {

        return new DiarioPeso( selectById( id ) ) ;

    }

    private ArrayList<DiarioPeso> fromArrayListHashMapToArrayListDiarioPeso( ArrayList<HashMap<String,String>> arrayListHashMap )
    {

        ArrayList<DiarioPeso> arrayList = null;

        if( arrayListHashMap != null )
        {

            arrayList = new ArrayList<DiarioPeso>();

            for( int i = 0; i < arrayListHashMap.size(); i++ )
            {

                arrayList.add( new DiarioPeso( arrayListHashMap.get( i ) ) ) ;

            }

        }

        return arrayList;

    }

}