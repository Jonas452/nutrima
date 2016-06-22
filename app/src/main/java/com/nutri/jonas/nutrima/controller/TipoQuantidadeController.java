package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.TipoQuantidade;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class TipoQuantidadeController extends Controller
{

    public TipoQuantidadeController( Context context )
    {

        super( context, TipoQuantidade.TABLE_NAME, TipoQuantidade.ID );

    }

    public boolean insertTipoQuantidade( TipoQuantidade tipoQuantidade )
    {

        return insert( tipoQuantidade.toContentValues( false ) );

    }

    public boolean insertTipoQuantidadeWithId( TipoQuantidade tipoQuantidade )
    {

        return insert( tipoQuantidade.toContentValues( true ) );

    }

    public TipoQuantidade getTipoQuantidadeById( long id )
    {

        return new TipoQuantidade( selectById( id ) );

    }

}