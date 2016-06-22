package com.nutri.jonas.nutrima.util;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

public class Util
{

    /*
	 * Verifica se a String é nula,
	 * ou se tem espaço vazio.
	 * Retorna TRUE se for nula ou com espaço vazio.
	 * Retorn FALSE se não for nula e não tiver espaço vazio.
	 */
    public static boolean isNullOrWhiteSpace( String text )
    {

        //Se a String não for nula.
        //Se a String tiver algum caracterer diferente de espaço.
        return text != null && text.trim().length() > 0 ? false : true;

    }

    //Caixa de dialogo com Ok
    public static void alertDialog( Context context, String title, String message )
    {

        //Cria o alerta.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( context );

        //Seta o titulo e a mensagem.
        alertDialogBuilder.setTitle( title );
        alertDialogBuilder.setMessage( message );

        //Buton de Ok.
        alertDialogBuilder.setNeutralButton( "Ok", new DialogInterface.OnClickListener()
        {

            public void onClick( DialogInterface dialog, int id ) { }

        });

        //Cria o alerta.
        AlertDialog alertDialog = alertDialogBuilder.create();

        //Mostra o alerta.
        alertDialog.show();

    }

    /*
	 * Verifica se existe uma conexão
	 * a Internet no aparelho.
	 */
    public static boolean testConnection( Context context )
    {

        boolean connected = false;

        ConnectivityManager connectivityManager = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if( activeNetwork != null )
        {

            if( activeNetwork.getType() == ConnectivityManager.TYPE_WIFI )
            {

                connected = true;

            }

            if( activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE )
            {

                connected = true;

            }

        }

        return connected;

    }

}