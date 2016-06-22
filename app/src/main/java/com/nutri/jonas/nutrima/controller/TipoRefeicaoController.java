package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.TipoRefeicao;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class TipoRefeicaoController extends Controller
{

    public TipoRefeicaoController( Context context )
    {

        super( context, TipoRefeicao.TABLE_NAME, TipoRefeicao.ID );

    }

    public boolean insertTipoRefeicao( TipoRefeicao tipoRefeicao )
    {

        return insert( tipoRefeicao.toContentValues( false ) );

    }

    public boolean insertTipoRefeicaoWithId( TipoRefeicao tipoRefeicao )
    {

        return insert( tipoRefeicao.toContentValues( true ) );

    }

    public TipoRefeicao getTipoRefeicaoById( long id )
    {

        return new TipoRefeicao( selectById( id ) );

    }

}
