package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.TipoDieta;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class TipoDietaController extends Controller
{

    public TipoDietaController( Context context )
    {

        super( context, TipoDieta.TABLE_NAME, TipoDieta.ID );

    }

    public boolean insertTipoDieta( TipoDieta tipoDieta )
    {

        return insert( tipoDieta.toContentValues( false ) );

    }

    public boolean insertTipoDietaWithId( TipoDieta tipoDieta )
    {

        return insert( tipoDieta.toContentValues( true ) );

    }

    public TipoDieta getTipoDietaById( long id )
    {

        return new TipoDieta( selectById( id ) );

    }

}
