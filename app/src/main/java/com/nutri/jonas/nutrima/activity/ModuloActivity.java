package com.nutri.jonas.nutrima.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.controller.AlimentoController;
import com.nutri.jonas.nutrima.controller.AtividadeFisicaController;
import com.nutri.jonas.nutrima.controller.IntensidadeController;
import com.nutri.jonas.nutrima.controller.TipoDietaController;
import com.nutri.jonas.nutrima.controller.TipoQuantidadeController;
import com.nutri.jonas.nutrima.controller.TipoRefeicaoController;
import com.nutri.jonas.nutrima.model.Alimento;
import com.nutri.jonas.nutrima.model.AtividadeFisica;
import com.nutri.jonas.nutrima.model.Intensidade;
import com.nutri.jonas.nutrima.model.TipoDieta;
import com.nutri.jonas.nutrima.model.TipoQuantidade;
import com.nutri.jonas.nutrima.model.TipoRefeicao;
import com.nutri.jonas.nutrima.util.JSONHandler;
import com.nutri.jonas.nutrima.util.Util;
import com.nutri.jonas.nutrima.util.UtilPreference;
import com.nutri.jonas.nutrima.util.UtilWebservice;
import com.nutri.jonas.nutrima.webservice.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ModuloActivity extends AppCompatActivity {

    private static final String SINC_LOG_WEBSERVICE = "SincLogWebservice";
    private static final String MAIN_IMPORT_WEBSERVICE = "MainImportWebservice";

    private ImageButton refeicaoImageButton,
            pratosImageButton,
            diarioPesoImageButton,
            diarioAlturaImageButton,
            diarioAtividadeImageButton,
            graficoEvolucaoImageButton;

    private long pacienteId;

    private boolean preparingApp = false;
    private boolean appReady = false;

    private AtividadeFisicaController atividadeFisicaController;
    private IntensidadeController intensidadeController;
    private TipoDietaController tipoDietaController;
    private TipoRefeicaoController tipoRefeicaoController;
    private AlimentoController alimentoController;
    private TipoQuantidadeController tipoQuantidadeController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo);

        atividadeFisicaController = new AtividadeFisicaController( getApplicationContext() );
        intensidadeController = new IntensidadeController( getApplicationContext() );
        tipoDietaController = new TipoDietaController( getApplicationContext() );
        tipoRefeicaoController = new TipoRefeicaoController( getApplicationContext() );
        alimentoController = new AlimentoController( getApplicationContext() );
        tipoQuantidadeController = new TipoQuantidadeController( getApplicationContext() );

        refeicaoImageButton = (ImageButton) findViewById( R.id.refeicaoImageButton );
        pratosImageButton = (ImageButton) findViewById( R.id.pratosImageButton );
        diarioPesoImageButton = (ImageButton) findViewById( R.id.diarioPesoImageButton );
        diarioAlturaImageButton = (ImageButton) findViewById( R.id.diarioAlturaImageButton );
        diarioAtividadeImageButton = (ImageButton) findViewById( R.id.diarioAtividadeImageButton );
        graficoEvolucaoImageButton = (ImageButton) findViewById( R.id.graficoEvolucaoImageButton );

        refeicaoImageButton.setOnClickListener( refeicaoImageButtonOnClickListener );
        pratosImageButton.setOnClickListener( pratosImageButtonOnClickListener );
        diarioPesoImageButton.setOnClickListener( diarioPesoImageButtonOnClickListener );
        diarioAlturaImageButton.setOnClickListener( diarioAlturaImageButtonOnClickListener );
        diarioAtividadeImageButton.setOnClickListener( diarioAtividadeImageButtonOnClickListener );
        graficoEvolucaoImageButton.setOnClickListener( graficoEvolucaoImageButtonOnClickListener );

    }

    @Override
    protected void onResume()
    {

        super.onResume();

        //Instancia as preferencias
        SharedPreferences preferenciaDeUsuario = getSharedPreferences( UtilPreference.PACIENTE_LOGADO_PREF_NAME, MODE_PRIVATE );

        pacienteId = preferenciaDeUsuario.getLong( "id", 0 );

        //Chama o webservice para preparar o aplicativo.
        if( Util.testConnection( getApplicationContext() ) && !preparingApp && !appReady )
        {

            preparingApp = true;
            new PrepareAppAsyncTask().execute();

        }

    }

    private class PrepareAppAsyncTask extends AsyncTask< Void, Integer, Boolean >
    {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();

            dialog = new ProgressDialog( ModuloActivity.this );
            dialog.getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
            dialog.setMessage("Preparando o aplicativo, por favor, aguarde.");
            dialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
            dialog.setProgress( 1 );
            dialog.setCancelable(false);
            dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
            dialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params)
        {

            ServiceHandler serviceHandler = new ServiceHandler();

            HashMap<String, String> parameters = new HashMap<String, String>();

            parameters.put( "action", "1" ); //Action to consult data
            parameters.put( "paciente_id", String.valueOf( pacienteId ) );
            parameters.put( "numeroserie", Settings.Secure.getString( getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID ) );

            JSONHandler jsonHandlerSinc =  new JSONHandler( serviceHandler.makeServiceCall( UtilWebservice.getWebserviceURL( SINC_LOG_WEBSERVICE, UtilWebservice.SUPER_GAMES_SCHOOLS_URL ), ServiceHandler.POST, parameters ) );

            int successSinc = jsonHandlerSinc.getInt( UtilWebservice.SUCCESS_TAG );
            String serverTimestamp = jsonHandlerSinc.getString( "server_time" );

            boolean wentWrong = false;

            int sincId = 0;

            if( successSinc == 1 || successSinc == 2 )
            {

                int progress = 0;

                parameters = new HashMap<String, String>();

                parameters.put( "action", "1" ); //Action to consult data
                parameters.put( "paciente_id", String.valueOf( pacienteId ) );

                if( successSinc == 1 )
                {

                    parameters.put("numeroserie", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));

                    JSONArray sincLogJSONArray = jsonHandlerSinc.getJSONArray("sinc_log");

                    for (int i = 0; i < sincLogJSONArray.length(); i++)
                    {

                        try
                        {

                            JSONObject mainImportJSONObject = sincLogJSONArray.getJSONObject(i);

                            sincId = mainImportJSONObject.getInt( "id" );

                        } catch (JSONException e)
                        {

                            e.printStackTrace();

                        }

                    }

                }

                JSONHandler jsonHandlerMainImport =  new JSONHandler( serviceHandler.makeServiceCall( UtilWebservice.getWebserviceURL( MAIN_IMPORT_WEBSERVICE, UtilWebservice.SUPER_GAMES_SCHOOLS_URL ), ServiceHandler.POST, parameters ) );

                int successMainImport = jsonHandlerMainImport.getInt( UtilWebservice.SUCCESS_TAG );

                if( successMainImport == 1 )
                {

                    JSONArray mainImportJSONArray = jsonHandlerMainImport.getJSONArray( "imports_qtde" );

                    int qtdeAtividadeFisica = 0,
                        qtdeIntensidade = 0,
                        qtdeTipoDieta = 0,
                        qtdeTipoRefeicao = 0,
                        qtdeAlimiento = 0,
                        qtdeTipoQuantidade = 0;

                    for( int i = 0; i < mainImportJSONArray.length(); i++ )
                    {

                        try
                        {

                            JSONObject mainImportJSONObject = mainImportJSONArray.getJSONObject( i );


                            qtdeAtividadeFisica = JSONHandler.getIntFromJSONObject( mainImportJSONObject, "qtde_atividadefisica" );
                            qtdeIntensidade = JSONHandler.getIntFromJSONObject( mainImportJSONObject, "qtde_intensidade" );
                            qtdeTipoDieta = JSONHandler.getIntFromJSONObject( mainImportJSONObject, "qtde_tipodieta" );
                            qtdeTipoRefeicao = JSONHandler.getIntFromJSONObject( mainImportJSONObject, "qtde_tiporefeicao" );
                            qtdeAlimiento = JSONHandler.getIntFromJSONObject( mainImportJSONObject, "qtde_alimiento" );
                            qtdeTipoQuantidade = JSONHandler.getIntFromJSONObject( mainImportJSONObject, "qtde_tipoquantidade" );

                        }catch( JSONException e )
                        {

                            wentWrong = true;
                            e.printStackTrace();

                        }

                    }
                    //Seta o máximo.
                    publishProgress( progress, (qtdeAtividadeFisica + qtdeIntensidade  + qtdeTipoDieta + qtdeTipoRefeicao + qtdeAlimiento + qtdeTipoQuantidade) );

                    if( qtdeAtividadeFisica > 0 )
                    {

                        JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "atividade_fisica" );

                        for( int i = 0; i < dataJSONArray.length(); i++ )
                        {

                            try
                            {
                                if( !atividadeFisicaController.insertAtividadeFisicaWithId(  new AtividadeFisica(  dataJSONArray.getJSONObject( i ) ) ) )
                                    wentWrong = true;

                            }catch( JSONException e )
                            {

                                wentWrong = true;
                                e.printStackTrace();

                            }

                            publishProgress( ++progress );

                        }

                    }

                    if( qtdeIntensidade > 0 )
                    {

                        JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "intensidade" );

                        for( int i = 0; i < dataJSONArray.length(); i++ )
                        {

                            try
                            {

                                if( !intensidadeController.insertIntensidadeWithId(  new Intensidade(  dataJSONArray.getJSONObject( i ) ) ) )
                                    wentWrong = true;

                            }catch( JSONException e )
                            {

                                wentWrong = true;
                                e.printStackTrace();

                            }

                            publishProgress( ++progress );

                        }

                    }

                    if( qtdeTipoDieta > 0 )
                    {

                        JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "tipodieta" );

                        for( int i = 0; i < dataJSONArray.length(); i++ )
                        {

                            try
                            {

                                if( !tipoDietaController.insertTipoDietaWithId(  new TipoDieta(  dataJSONArray.getJSONObject( i ) ) ) )
                                    wentWrong = true;

                            }catch( JSONException e )
                            {

                                wentWrong = true;
                                e.printStackTrace();

                            }

                            publishProgress( ++progress );

                        }

                    }

                    if( qtdeTipoRefeicao > 0 )
                    {

                        JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "tiporefeicao" );

                        for( int i = 0; i < dataJSONArray.length(); i++ )
                        {

                            try
                            {

                                if( !tipoRefeicaoController.insertTipoRefeicaoWithId(  new TipoRefeicao(  dataJSONArray.getJSONObject( i ) ) ) )
                                    wentWrong = true;

                            }catch( JSONException e )
                            {

                                wentWrong = true;
                                e.printStackTrace();

                            }

                            publishProgress( ++progress );

                        }

                    }

                    if( qtdeAlimiento > 0 )
                    {

                        JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "alimento" );

                        for( int i = 0; i < dataJSONArray.length(); i++ )
                        {

                            try
                            {

                                if( !alimentoController.insertAlimentoWithId(  new Alimento(  dataJSONArray.getJSONObject( i ) ) ) )
                                    wentWrong = true;

                            }catch( JSONException e )
                            {

                                wentWrong = true;
                                e.printStackTrace();

                            }

                            publishProgress( ++progress );

                        }

                    }

                    if( qtdeTipoQuantidade > 0 )
                    {

                        JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "tipoquantidade" );

                        for( int i = 0; i < dataJSONArray.length(); i++ )
                        {

                            try
                            {

                                if( !tipoQuantidadeController.insertTipoQuantidadeWithId(  new TipoQuantidade(  dataJSONArray.getJSONObject( i ) ) ) )
                                    wentWrong = true;

                            }catch( JSONException e )
                            {

                                wentWrong = true;
                                e.printStackTrace();

                            }

                            publishProgress( ++progress );

                        }

                    }

                }else if( successMainImport == 0 || successMainImport == 2 )
                {

                    wentWrong = true;

                }

                if( !wentWrong )
                {

                    parameters = new HashMap<String, String>();

                    if( successSinc == 2 )
                    {

                        parameters.put("action", "2"); //Action to insert data
                        parameters.put( "numeroserie", Settings.Secure.getString( getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID ) );
                        parameters.put( "paciente_id", String.valueOf( pacienteId ) );

                    }else if( successSinc == 1)
                    {

                        parameters.put("action", "3"); //Action to update data
                        parameters.put("sinc_log_id", sincId + "" );

                    }

                    parameters.put( "date_sinc", serverTimestamp );

                    jsonHandlerSinc =  new JSONHandler( serviceHandler.makeServiceCall( UtilWebservice.getWebserviceURL( SINC_LOG_WEBSERVICE, UtilWebservice.SUPER_GAMES_SCHOOLS_URL ), ServiceHandler.POST, parameters ) );

                    successMainImport = jsonHandlerSinc.getInt( UtilWebservice.SUCCESS_TAG );

                    if( successMainImport == 0 )
                        wentWrong = true;

                }

            }

            return wentWrong;

        }

        @Override
        protected void onProgressUpdate( Integer... result )
        {

            //Se o dialogo estiver mostrando
            if( dialog.isShowing() )
            {

                //Testa o primeiro parametro passado.
                if( result[0] != null )
                {

                    //Passa como progresso dialog.
                    dialog.setProgress( result[0] );

                }

                //Testa o segundo parametro passado.
                if( result.length > 1 )
                {

                    //Passa como max do dialog.
                    dialog.setMax( result[1] );

                }

            }

        }

        @Override
        protected void onPostExecute( Boolean wentWrong )
        {

            super.onPostExecute( wentWrong );

            if( dialog.isShowing() )
            {

                dialog.dismiss();

            }

            if( wentWrong )
            {

                Toast.makeText(ModuloActivity.this, "Ocorreu um erro ao tentar importar os dados.", Toast.LENGTH_LONG).show();

            }else
            {

                Toast.makeText( ModuloActivity.this, "Dados importados com sucesso!", Toast.LENGTH_LONG ).show();
                appReady = true;

            }


        }


    }

    //----------------------------------------------------------------------------------------------
    //On Clicks Listeners

    View.OnClickListener refeicaoImageButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent( ModuloActivity.this,RefeicaoListActivity.class );

            startActivity( intent );

        }

    };

    View.OnClickListener pratosImageButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent( ModuloActivity.this, PratosListActivity.class );

            startActivity( intent );

        }

    };

    View.OnClickListener diarioPesoImageButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent( ModuloActivity.this, DiarioPesoListActivity.class );

            startActivity( intent );

        }

    };

    View.OnClickListener diarioAlturaImageButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent( ModuloActivity.this, DiarioAlturaListActivity.class );

            startActivity( intent );

        }

    };

    View.OnClickListener diarioAtividadeImageButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent( ModuloActivity.this, DiarioAtividadeListActivity.class );

            startActivity( intent );

        }

    };

    View.OnClickListener graficoEvolucaoImageButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent( ModuloActivity.this, GraficoEvolucaoActivity.class );

            startActivity( intent );

        }

    };
    //----------------------------------------------------------------------------------------------

}