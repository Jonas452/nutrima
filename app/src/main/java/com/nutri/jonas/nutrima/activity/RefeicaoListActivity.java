package com.nutri.jonas.nutrima.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.adapter.RefeicaoAdapter;
import com.nutri.jonas.nutrima.controller.RefeicaoAlimentoController;
import com.nutri.jonas.nutrima.controller.RefeicaoController;
import com.nutri.jonas.nutrima.model.Refeicao;
import com.nutri.jonas.nutrima.model.RefeicaoAlimento;
import com.nutri.jonas.nutrima.util.JSONHandler;
import com.nutri.jonas.nutrima.util.Util;
import com.nutri.jonas.nutrima.util.UtilPreference;
import com.nutri.jonas.nutrima.util.UtilWebservice;
import com.nutri.jonas.nutrima.webservice.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RefeicaoListActivity extends AppCompatActivity
{

    private static final String SINC_LOG_WEBSERVICE = "SincLogWebservice";
    private static final String REFEICAO_IMPORT_WEBSERVICE = "RefeicaoImportWebservice";

    private boolean preparingDieta = false;
    private boolean dietaReady = false;

    private long pacienteId;
    private long dietaId;

    private RefeicaoController refeicaoController;
    private RefeicaoAlimentoController refeicaoAlimentoController;

    private ListView refeicoesListView;
    private TextView semDadosRefeicaoListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao_list);

        refeicaoController = new RefeicaoController( getApplicationContext() );
        refeicaoAlimentoController = new RefeicaoAlimentoController( getApplicationContext() );

        refeicoesListView = (ListView) findViewById( R.id.refeicoesListView );
        refeicoesListView.setOnItemClickListener( refeicoesListViewOnItemClickListener );

        semDadosRefeicaoListTextView = (TextView) findViewById( R.id.semDadosRefeicaoListTextView );

    }

    AdapterView.OnItemClickListener refeicoesListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3)
        {

            Intent intent = new Intent( RefeicaoListActivity.this, RefeicaoAlimentoListActivity.class );

            intent.putExtra( "refeicaoId", Long.parseLong( ( (TextView) v.findViewById( R.id.idRefeicaoAdapterTextView ) ).getText().toString() ) );

            startActivity( intent );

        }

    };

    @Override
    protected void onResume()
    {

        super.onResume();

        //Instancia as preferencias
        SharedPreferences preferenciaDeUsuario = getSharedPreferences( UtilPreference.PACIENTE_LOGADO_PREF_NAME, MODE_PRIVATE );

        pacienteId = preferenciaDeUsuario.getLong( "id", 0 );
        dietaId = preferenciaDeUsuario.getLong( "dieta_id", 0 );

        //Chama o webservice para preparar o aplicativo.
        if( Util.testConnection( getApplicationContext() ) && !preparingDieta && !dietaReady )
        {

            preparingDieta = true;
            new PrepareRefeicaoAsyncTask().execute();

        }

    }

    private class PrepareRefeicaoAsyncTask extends AsyncTask< Void, Integer, Boolean >
    {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();

            dialog = new ProgressDialog(RefeicaoListActivity.this);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            dialog.setMessage("Importando dieta, por favor, aguarde.");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(1);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params)
        {

            ServiceHandler serviceHandler = new ServiceHandler();

            HashMap<String, String> parameters = new HashMap<String, String>();

            parameters.put( "action", "5" ); //Action to consult data
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

                parameters.put("action", "1"); //Action to consult data
                parameters.put("paciente_id", String.valueOf(pacienteId));
                parameters.put("dieta_id", String.valueOf(dietaId));

                    if (successSinc == 1)
                    {

                        parameters.put("numeroserie", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));

                        JSONArray sincLogJSONArray = jsonHandlerSinc.getJSONArray("sinc_log");

                        for (int i = 0; i < sincLogJSONArray.length(); i++)
                        {

                            try {

                                JSONObject mainImportJSONObject = sincLogJSONArray.getJSONObject(i);

                                sincId = mainImportJSONObject.getInt("id");

                            } catch (JSONException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    JSONHandler jsonHandlerMainImport =  new JSONHandler( serviceHandler.makeServiceCall( UtilWebservice.getWebserviceURL( REFEICAO_IMPORT_WEBSERVICE, UtilWebservice.SUPER_GAMES_SCHOOLS_URL ), ServiceHandler.POST, parameters ) );

                    int successMainImport = jsonHandlerMainImport.getInt( UtilWebservice.SUCCESS_TAG );

                    if( successMainImport == 1 )
                    {

                        JSONArray mainImportJSONArray = jsonHandlerMainImport.getJSONArray( "imports_qtde" );

                        int qtdeRefeicao = 0, qtdeRefeicaoAlimento = 0;

                        for (int i = 0; i < mainImportJSONArray.length(); i++)
                        {

                            try
                            {

                                JSONObject mainImportJSONObject = mainImportJSONArray.getJSONObject(i);

                                qtdeRefeicao = JSONHandler.getIntFromJSONObject(mainImportJSONObject, "qtde_refeicao");
                                qtdeRefeicaoAlimento = JSONHandler.getIntFromJSONObject(mainImportJSONObject, "qtde_refeicao_alimento");

                            } catch (JSONException e) {

                                wentWrong = true;
                                e.printStackTrace();

                            }

                        }

                        //Seta o máximo.
                        publishProgress( progress, (qtdeRefeicao + qtdeRefeicaoAlimento) );

                        if( qtdeRefeicao > 0 )
                        {

                            JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "refeicao" );

                            for( int i = 0; i < dataJSONArray.length(); i++ )
                            {

                                try
                                {

                                    Refeicao refeicao = new Refeicao(  dataJSONArray.getJSONObject( i ) );

                                    if( refeicaoController.getRefeicaoById( refeicao.id ) == null )
                                        if( !refeicaoController.insertRefeicaoWithId(  refeicao ) )
                                            wentWrong = true;
                                    else
                                        if( !refeicaoController.updateRefeicao(  refeicao ) )
                                            wentWrong = true;


                                }catch( JSONException e )
                                {

                                    wentWrong = true;
                                    e.printStackTrace();

                                }

                                publishProgress( ++progress );

                            }

                        }

                        if( qtdeRefeicaoAlimento > 0 )
                        {

                            JSONArray dataJSONArray = jsonHandlerMainImport.getJSONArray( "refeicao_alimento" );

                            for( int i = 0; i < dataJSONArray.length(); i++ )
                            {

                                try
                                {

                                    RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento(  dataJSONArray.getJSONObject( i ) );

                                    if( refeicaoAlimentoController.getRefeicaoAlimentoById( refeicaoAlimento.id ) == null )
                                        if( !refeicaoAlimentoController.insertRefeicaoAlimentoWithId( refeicaoAlimento ) )
                                            wentWrong = true;
                                    else
                                        if( !refeicaoAlimentoController.updateRefeicaoAlimento( refeicaoAlimento ) )
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

                            parameters.put("action", "4"); //Action to insert data
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

                Toast.makeText( RefeicaoListActivity.this, "Ocorreu um erro ao tentar importar os dados.", Toast.LENGTH_LONG).show();

            }else
            {

                Toast.makeText( RefeicaoListActivity.this, "Dados importados com sucesso!", Toast.LENGTH_LONG ).show();
                dietaReady = true;
                new CarregarRefeicao().execute();

            }

        }

    }

    private class CarregarRefeicao extends AsyncTask< Long, Void, ArrayList<Refeicao> >
    {

        @Override
        protected ArrayList<Refeicao> doInBackground(Long... params )
        {

            return refeicaoController.getAllRefeicaoByDietaId( dietaId );

        }

        @Override
        protected void onPostExecute( ArrayList<Refeicao> result )
        {

            super.onPostExecute( result );

            if( result != null && result.size() > 0 )
            {

                //Não existe dados.
                semDadosRefeicaoListTextView.setVisibility( View.GONE );

                //Instancia o adapter.
                RefeicaoAdapter refeicaoAdapter = new RefeicaoAdapter( RefeicaoListActivity.this, R.layout.refeicao_adapter, result );

                //Set o adapter.
                refeicoesListView.setAdapter( refeicaoAdapter );

            }else
            {

                //Não existe dados.
                semDadosRefeicaoListTextView.setVisibility( View.VISIBLE );

                //Set o adapter.
                refeicoesListView.setAdapter( null );

            }

        }

    }

}
