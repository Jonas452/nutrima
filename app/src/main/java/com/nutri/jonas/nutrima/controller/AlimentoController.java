package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Alimento;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class AlimentoController extends Controller
{

    public AlimentoController( Context context )
    {

        super( context, Alimento.TABLE_NAME, Alimento.ID );

    }

    public boolean insertAlimento( Alimento alimento )
    {

        return insert( alimento.toContentValues( false ) );

    }

    public boolean insertAlimentoWithId( Alimento alimento )
    {

        return insert( alimento.toContentValues( true ) );

    }

    public boolean updateAlimento( Alimento alimento )
    {

        return update( alimento.toContentValues( true ) );

    }

    public Alimento getAlimentoById( long id )
    {

        return new Alimento( selectById( id ) );

    }

}