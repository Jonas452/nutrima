package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.PratoOpcao;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class PratoOpcaoController extends Controller
{

    public PratoOpcaoController( Context context )
    {

        super( context, PratoOpcao.TABLE_NAME, PratoOpcao.ID );

    }

    public boolean insertPratoOpcao( PratoOpcao pratoOpcao )
    {

        return insert( pratoOpcao.toContentValues() );

    }

    public PratoOpcao getPratoOpcaoById( long id )
    {

        return new PratoOpcao( selectById( id ) );

    }

}