package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Prato;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class PratoController extends Controller
{

    public PratoController( Context context )
    {

        super( context, Prato.TABLE_NAME, Prato.ID );

    }

    public boolean insertPrato( Prato prato )
    {

        return insert(prato.toContentValues());

    }

    public boolean updatePrato( Prato prato )
    {

        return update(prato.toContentValues());

    }

    public boolean deletePrato( long id )
    {

        Prato prato = getPratoById( id );
        prato.ativo = 0;

        return update( prato.toContentValues() );

    }



    public ArrayList<Prato> getAllPrato()
    {

        return fromArrayListHashMapToArrayListPrato( selectAll( null, null ) );

    }

    public Prato getPratoById( long id )
    {

        return new Prato( selectById( id ) );

    }

    private ArrayList<Prato> fromArrayListHashMapToArrayListPrato( ArrayList<HashMap<String,String>> arrayListHashMap )
    {

        ArrayList<Prato> arrayList = new ArrayList<Prato>();

        for( int i = 0; i < arrayListHashMap.size(); i++ )
        {

            arrayList.add( new Prato( arrayListHashMap.get( i ) ) ) ;

        }

        return arrayList;

    }

}