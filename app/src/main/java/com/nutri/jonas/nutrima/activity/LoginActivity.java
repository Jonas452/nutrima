package com.nutri.jonas.nutrima.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.controller.AtividadeFisicaController;
import com.nutri.jonas.nutrima.controller.DietaController;
import com.nutri.jonas.nutrima.controller.IntensidadeController;
import com.nutri.jonas.nutrima.controller.PacienteController;
import com.nutri.jonas.nutrima.model.AtividadeFisica;
import com.nutri.jonas.nutrima.model.Dieta;
import com.nutri.jonas.nutrima.model.Intensidade;
import com.nutri.jonas.nutrima.model.Paciente;
import com.nutri.jonas.nutrima.util.JSONHandler;
import com.nutri.jonas.nutrima.util.Util;
import com.nutri.jonas.nutrima.util.UtilPreference;
import com.nutri.jonas.nutrima.util.UtilWebservice;
import com.nutri.jonas.nutrima.webservice.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity
{

    private static final String LOGIN_WEBSERVICE = "LoginWebservice";
    private static final String DIETA_WEBSERVICE = "DietaWebservice";

    private Button autenticarButton;

    private EditText numeroTelefoneEditText;
    private TextView instrucaoTextView;

    AtividadeFisicaController atividadeFisicaController;;
    IntensidadeController intensidadeController;

    private long pacienteId;

    private PacienteController pacienteController;
    private DietaController dietaController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pacienteController = new PacienteController( getApplicationContext() );
        dietaController = new DietaController( getApplicationContext() );

        autenticarButton = (Button) findViewById( R.id.autenticarButton );

        autenticarButton.setOnClickListener(autenticarButtonOnClickListener);

        numeroTelefoneEditText = (EditText) findViewById( R.id.numeroTelefoneEditText );
        numeroTelefoneEditText.setText( "988384117" );

        instrucaoTextView = (TextView)  findViewById(R.id.instrucaoTextView);

        //testData();

        SharedPreferences preferenciaDeUsuario = getSharedPreferences( UtilPreference.PACIENTE_LOGADO_PREF_NAME, MODE_PRIVATE );

        if( preferenciaDeUsuario.getBoolean( UtilPreference.IS_PACIENTE_LOGADO_PREF, false ) ) {

            Intent modulosIntent = new Intent(LoginActivity.this, ModuloActivity.class);

            startActivity(modulosIntent);

            finish();

        }

    }

    View.OnClickListener autenticarButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            resetFieldsColor();


            if( fieldsFilled() )
            {

                if( Util.testConnection( getApplicationContext() ) )
                {

                    new VerificarNumero().execute();

                }else
                {

                    if( pacienteController.getPacienteByTelefone( numeroTelefoneEditText.getText().toString() ) == null )
                    {
                            String title = "AVISO";
                            String msg = "É necessário estar conectado a Internet para realizar a autenticação.";

                            Util.alertDialog(LoginActivity.this, title, msg);
                    }else
                    {

                        closeThisActivityAndOpenModuloActivity();

                    }

                }

            }else
            {

                String title = "AVISO";
                String msg = "Informe um número de telefone que foi préviamente cadastrado com o seu Nutricionista.";

                Util.alertDialog( LoginActivity.this, title, msg );

            }

        }

    };

    private class VerificarNumero extends AsyncTask< Void, Void, Integer >
    {

        private ProgressDialog dialog;

        private String telefone;

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();

            dialog = new ProgressDialog( LoginActivity.this );
            dialog.getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
            dialog.setMessage("Verificando, por favor, aguarde.");
            dialog.setCancelable(false);
            dialog.show();

            telefone = numeroTelefoneEditText.getText().toString();

        }

        @Override
        protected Integer doInBackground( Void... params )
        {

            ServiceHandler serviceHandler = new ServiceHandler();

            HashMap<String, String> parameters = new HashMap<String, String>();

            parameters.put( "telefone", telefone );

            JSONHandler jsonHandler =  new JSONHandler( serviceHandler.makeServiceCall( UtilWebservice.getWebserviceURL(LOGIN_WEBSERVICE, UtilWebservice.SUPER_GAMES_SCHOOLS_URL ), ServiceHandler.POST, parameters ) );

            int success = jsonHandler.getInt( UtilWebservice.SUCCESS_TAG );

            JSONArray pacienteJSONArray = jsonHandler.getJSONArray( "paciente" );

            if( success == 1 )
            {

                try
                {

                    Paciente paciente = new Paciente( pacienteJSONArray.getJSONObject(0) );

                    if( pacienteController.getPacienteById( paciente.id ) == null )
                        pacienteController.insertPaciente( paciente );
                    else
                        pacienteController.updatePaciente( paciente );

                    pacienteId = paciente.id;

                }catch( JSONException e )
                {

                    e.printStackTrace();

                    return 0;

                }

            }

            return success;

        }

        @Override
        protected void onPostExecute( Integer result )
        {

            super.onPostExecute( result );

            if( dialog.isShowing() )
            {

                dialog.dismiss();

            }

            String title = "AVISO";

            if( result == 1 )
            {

                new ImportarDieta().execute();

            }else if( result == 2 )
            {

                String msg = "O número de telefone informado não foi encontrado, por favor, verifique se está correto.";

                Util.alertDialog(LoginActivity.this, title, msg);

            }else if( result == 0 )
            {

                String msg = "Ocorreu um erro, por favor, tente novamente.";

                Util.alertDialog(LoginActivity.this, title, msg);

            }

        }

    }

    private class ImportarDieta extends AsyncTask< Void, Void, Boolean >
    {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {

            super.onPreExecute();

            dialog = new ProgressDialog( LoginActivity.this );
            dialog.getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
            dialog.setMessage("Preparando dieta, por favor, aguarde.");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Boolean doInBackground( Void... params )
        {

            ServiceHandler serviceHandler = new ServiceHandler();

            HashMap<String, String> parameters = new HashMap<String, String>();

            parameters.put( "action", "1" );
            parameters.put( "paciente_id", pacienteId + "" );

            JSONHandler jsonHandler =  new JSONHandler( serviceHandler.makeServiceCall( UtilWebservice.getWebserviceURL(DIETA_WEBSERVICE, UtilWebservice.SUPER_GAMES_SCHOOLS_URL ), ServiceHandler.POST, parameters ) );

            int success = jsonHandler.getInt( UtilWebservice.SUCCESS_TAG );

            if( success == 1 )
            {

                JSONArray dietaJSONArray = jsonHandler.getJSONArray( "dieta" );

                try
                {

                    Dieta dieta = new Dieta( dietaJSONArray.getJSONObject(0) );

                    if( dietaController.getDietaById( dieta.id ) == null )
                        dietaController.insertDietaWith( dieta );
                    else
                        dietaController.updateDieta( dieta );

                    //Instancia a nova preferencia.
                    SharedPreferences preferenciaDeUsuario = getSharedPreferences( UtilPreference.PACIENTE_LOGADO_PREF_NAME, MODE_PRIVATE );

                    //Salvar as preferencias.
                    SharedPreferences.Editor edit = preferenciaDeUsuario.edit();

                    //Salva o login.
                    edit.putLong( Paciente.ID, pacienteId );

                    edit.putBoolean( UtilPreference.IS_PACIENTE_LOGADO_PREF, true );

                    //Salva o login.
                    edit.putLong( "dieta_id", dieta.id );

                    //Salva a preferencia.
                    edit.commit();

                }catch( JSONException e )
                {

                    e.printStackTrace();

                    return false;

                }

                return true;

            }

            return false;

        }

        @Override
        protected void onPostExecute( Boolean result )
        {

            super.onPostExecute( result );

            if( dialog.isShowing() )
            {

                dialog.dismiss();

            }

            if( result )
            {

                closeThisActivityAndOpenModuloActivity();

            }else
            {

                String title = "AVISO";

                String msg = "Ocorreu um erro ao tentar importar sua Dieta.";

                Util.alertDialog(LoginActivity.this, title, msg);

            }

        }

    }

    private boolean fieldsFilled()
    {

        if( Util.isNullOrWhiteSpace( numeroTelefoneEditText.getText().toString() ) )
        {

            instrucaoTextView.setTextColor( Color.RED );
            return false;

        }else
            return true;

    }

    private void resetFieldsColor()
    {

        instrucaoTextView.setTextColor( Color.BLACK );

    }

    private void testData()
    {

        intensidadeController = new IntensidadeController( getApplicationContext() );

        Intensidade intensidade1 = new Intensidade();
        Intensidade intensidade2 = new Intensidade();
        Intensidade intensidade3 = new Intensidade();
        Intensidade intensidade4 = new Intensidade();

        intensidade1.intensidade = "Leve" ;
        intensidade2.intensidade = "Moderada";
        intensidade3.intensidade = "Alta";
        intensidade4.intensidade = "Muito Alta";

        intensidadeController.insertIntensidade( intensidade1 );
        intensidadeController.insertIntensidade( intensidade2 );
        intensidadeController.insertIntensidade( intensidade3 );
        intensidadeController.insertIntensidade( intensidade4 );

        atividadeFisicaController = new AtividadeFisicaController( getApplicationContext() );

        AtividadeFisica atividadeFisica1 = new AtividadeFisica();
        AtividadeFisica atividadeFisica2 = new AtividadeFisica();
        AtividadeFisica atividadeFisica3 = new AtividadeFisica();

        atividadeFisica1.nome = "Natação";
        atividadeFisica2.nome = "Corrida";
        atividadeFisica3.nome = "Bicicleta";

        atividadeFisicaController.insertAtividadeFisica( atividadeFisica1 );
        atividadeFisicaController.insertAtividadeFisica( atividadeFisica2 );
        atividadeFisicaController.insertAtividadeFisica( atividadeFisica3 );

    }

    private void closeThisActivityAndOpenModuloActivity()
    {

        Intent modulosIntent = new Intent( LoginActivity.this, ModuloActivity.class );

        startActivity( modulosIntent );

        finish();

    }

}