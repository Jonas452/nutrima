package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.DiarioAltura;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class DiarioAlturaController extends Controller
{

    public DiarioAlturaController( Context context )
    {

        super( context, DiarioAltura.TABLE_NAME, DiarioAltura.ID );

    }

    public boolean insertDiarioAltura( DiarioAltura diarioAltura )
    {

        return insert( diarioAltura.toContentValues( false ) );

    }

    public boolean updateDiarioAltura( DiarioAltura diarioAltura )
    {

        return update(diarioAltura.toContentValues( true ) );

    }

    public boolean deleteDiarioAltura( long id )
    {

        return delete(id);

    }

    public ArrayList<DiarioAltura> getAllDiarioAltura()
    {

        return fromArrayListHashMapToArrayListDiarioAltura( selectAll( null, null ) );

    }

    public DiarioAltura getDiarioAlturaById( long id )
    {

        return new DiarioAltura( selectById( id ) );

    }

    private ArrayList<DiarioAltura> fromArrayListHashMapToArrayListDiarioAltura( ArrayList<HashMap<String,String> > arrayListHashMap )
    {

        ArrayList<DiarioAltura> arrayList = null;

        if( arrayListHashMap != null )
        {

            arrayList = new ArrayList<DiarioAltura>();

            for (int i = 0; i < arrayListHashMap.size(); i++) {

                arrayList.add(new DiarioAltura(arrayListHashMap.get(i)));

            }

        }

        return arrayList;

    }

}