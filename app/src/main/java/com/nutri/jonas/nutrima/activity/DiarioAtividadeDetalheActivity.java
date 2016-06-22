package com.nutri.jonas.nutrima.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.adapter.AtividadeFisicaSpinnerAdapter;
import com.nutri.jonas.nutrima.adapter.IntensidadeSpinnerAdapter;
import com.nutri.jonas.nutrima.controller.AtividadeFisicaController;
import com.nutri.jonas.nutrima.controller.DiarioAtividadeController;
import com.nutri.jonas.nutrima.controller.IntensidadeController;
import com.nutri.jonas.nutrima.model.AtividadeFisica;
import com.nutri.jonas.nutrima.model.DiarioAtividade;
import com.nutri.jonas.nutrima.model.Intensidade;
import com.nutri.jonas.nutrima.util.Util;

import java.util.ArrayList;

public class DiarioAtividadeDetalheActivity extends AppCompatActivity
{

    private TextView idAtividadeDetalheTextView,
            atividadeAtividadeDetalheTextView,
            intensidadeAtividadeDetalheTextView,
            dataRealizacaoAtividadeDetalheTextView,
            duracaoAtividadeDetalheTextView;

    private EditText dataRealizacaoAtividadeDetalheEditText,
            duracaoAtividadeDetalheEditText;

    private Spinner atividadeAtividadeDetalheSpinner,
            intensidadeAtividadeDetalheSpinner;

    DiarioAtividadeController diarioAtividadeController;
    AtividadeFisicaController atividadeFisicaController;
    IntensidadeController intensidadeController;

    private Bundle extras;

    public boolean isEditingScreen = false;

    private long atividadeFisicaId;
    private long intensidadeId;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_atividade_detalhe);

        diarioAtividadeController = new DiarioAtividadeController( getApplicationContext() );
        atividadeFisicaController = new AtividadeFisicaController( getApplicationContext() );
        intensidadeController = new IntensidadeController( getApplicationContext() );

        idAtividadeDetalheTextView = (TextView) findViewById( R.id.idAtividadeDetalheTextView );
        atividadeAtividadeDetalheTextView = (TextView) findViewById( R.id.atividadeAtividadeDetalheTextView );
        intensidadeAtividadeDetalheTextView = (TextView) findViewById( R.id.intensidadeAtividadeDetalheTextView );
        dataRealizacaoAtividadeDetalheTextView = (TextView) findViewById( R.id.dataRealizacaoAtividadeDetalheTextView );
        duracaoAtividadeDetalheTextView = (TextView) findViewById( R.id.duracaoAtividadeDetalheTextView );

        dataRealizacaoAtividadeDetalheEditText = (EditText) findViewById( R.id.dataRealizacaoAtividadeDetalheEditText );
        duracaoAtividadeDetalheEditText = (EditText) findViewById( R.id.duracaoAtividadeDetalheEditText );

        atividadeAtividadeDetalheSpinner = (Spinner) findViewById( R.id.atividadeAtividadeDetalheSpinner );
        intensidadeAtividadeDetalheSpinner = (Spinner) findViewById( R.id.intensidadeAtividadeDetalheSpinner );

        atividadeAtividadeDetalheSpinner.setOnItemSelectedListener( atividadeAtividadeDetalheSpinnerOnItemSelectedListener );
        intensidadeAtividadeDetalheSpinner.setOnItemSelectedListener( intensidadeAtividadeDetalheSpinnerOnItemSelectedListener );

        prepareAtividadeFisicaSpinner();
        prepareIntensidadeSpinner();

        if( getIntent().getExtras() != null )
        {

            extras = getIntent().getExtras();

            isEditingScreen = extras.getBoolean( "isEditingScreen" );

            if( isEditingScreen )
                loadDiarioAtividade( diarioAtividadeController.getDiarioAtividadeById( extras.getLong( "diarioAtividadeId" ) ) );

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.confirm_menu, menu );
        return true;

    }

    AdapterView.OnItemSelectedListener atividadeAtividadeDetalheSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {

        @Override
        public void onItemSelected( AdapterView<?> arg0, View v, int arg2, long arg3)
        {

            atividadeFisicaId = Long.parseLong( ( ( TextView ) v.findViewById( R.id.idTextView ) ).getText().toString() );

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) { }

    };

    AdapterView.OnItemSelectedListener intensidadeAtividadeDetalheSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener()
    {

        @Override
        public void onItemSelected( AdapterView<?> arg0, View v, int arg2, long arg3)
        {

            intensidadeId = Long.parseLong( ( ( TextView ) v.findViewById( R.id.idTextView ) ).getText().toString() );

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) { }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch( item.getItemId() )
        {

            case R.id.action_confirm:

                if( wereFieldsFilled() )
                {

                    resetColorsFieldToBlack();

                    DiarioAtividade diarioAtividade = new DiarioAtividade();

                    if( isEditingScreen )
                        diarioAtividade.id  = Long.parseLong( idAtividadeDetalheTextView.getText().toString() );

                    diarioAtividade.atividadeFisicaId = atividadeFisicaId;
                    diarioAtividade.atividadeIntensidadeId  = intensidadeId;
                    diarioAtividade.dataRealizada = dataRealizacaoAtividadeDetalheEditText.getText().toString();
                    diarioAtividade.duracao = Integer.parseInt( duracaoAtividadeDetalheEditText.getText().toString() );
                    //TODO salvar paciente id.

                    new insertDiarioAtividade().execute(diarioAtividade);

                }else
                {

                    String title = "AVISO";
                    String msg = "Preencha os campos obrigatórios.";

                    Util.alertDialog( DiarioAtividadeDetalheActivity.this, title, msg );

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class insertDiarioAtividade extends AsyncTask< DiarioAtividade, Void, Boolean >
    {

        @Override
        protected Boolean doInBackground( DiarioAtividade... diarioAtividade )
        {

            if( isEditingScreen )
                return diarioAtividadeController.updateDiarioAtividade( diarioAtividade[0] );
            else
                return diarioAtividadeController.insertDiarioAtividade( diarioAtividade[0] );

        }

        @Override
        protected void onPostExecute( Boolean savedWithSucess )
        {

            super.onPostExecute( savedWithSucess );

            if( savedWithSucess )
            {

                Toast.makeText(getApplicationContext(), "Realizado com sucesso.", Toast.LENGTH_LONG).show();
                DiarioAtividadeDetalheActivity.this.finish();

            }else
            {

                Toast.makeText( getApplicationContext(), "Um erro ocorreu, tente novamente.", Toast.LENGTH_LONG ).show();

            }

        }

    }

    private boolean wereFieldsFilled() {

        boolean fieldsFilled = true;

        if( atividadeAtividadeDetalheSpinner.getSelectedItemPosition() == 0 )
        {

            atividadeAtividadeDetalheTextView.setTextColor(Color.RED);
            fieldsFilled = false;

        }

        if( intensidadeAtividadeDetalheSpinner.getSelectedItemPosition() == 0 )
        {

            intensidadeAtividadeDetalheTextView.setTextColor( Color.RED );
            fieldsFilled = false;

        }

        if( Util.isNullOrWhiteSpace( dataRealizacaoAtividadeDetalheEditText.getText().toString() ) )
        {

            dataRealizacaoAtividadeDetalheTextView.setTextColor( Color.RED );
            fieldsFilled = false;

        }

        if( Util.isNullOrWhiteSpace( duracaoAtividadeDetalheEditText.getText().toString( ) ) )
        {

            duracaoAtividadeDetalheTextView.setTextColor( Color.RED );
            fieldsFilled = false;

        }

        return fieldsFilled;

    }

    private void resetColorsFieldToBlack()
    {

        atividadeAtividadeDetalheTextView.setTextColor( Color.BLACK );
        intensidadeAtividadeDetalheTextView.setTextColor(Color.BLACK);
        dataRealizacaoAtividadeDetalheTextView.setTextColor( Color.BLACK );
        duracaoAtividadeDetalheTextView.setTextColor(Color.BLACK);

    }

    private void loadDiarioAtividade( DiarioAtividade diarioAtividade )
    {

        String nomeAtividadeFisica = atividadeFisicaController.getAtividadeFisicaById( diarioAtividade.atividadeFisicaId ).nome;
        if( nomeAtividadeFisica != null & !nomeAtividadeFisica.equals( "null" ) )
        {

            atividadeAtividadeDetalheSpinner.setSelection( ( ( AtividadeFisicaSpinnerAdapter ) atividadeAtividadeDetalheSpinner.getAdapter() ).getPosition( nomeAtividadeFisica ) );

        }

        String nomeIntensidade = intensidadeController.getIntensidadeById(diarioAtividade.atividadeIntensidadeId ).intensidade;
        if( nomeIntensidade != null & !nomeIntensidade.equals( "null" ) )
        {

            intensidadeAtividadeDetalheSpinner.setSelection( ( ( IntensidadeSpinnerAdapter ) intensidadeAtividadeDetalheSpinner.getAdapter() ).getPosition( nomeIntensidade ) );

        }

        idAtividadeDetalheTextView.setText( String.valueOf( diarioAtividade.id) );
        dataRealizacaoAtividadeDetalheEditText.setText( String.valueOf( diarioAtividade.dataRealizada ) );
        duracaoAtividadeDetalheEditText.setText( String.valueOf( diarioAtividade.duracao ) );

    }

    private void prepareAtividadeFisicaSpinner()
    {

        ArrayList<AtividadeFisica> atividadeFisicaArrayList = atividadeFisicaController.getAllAtividadeFisica();

        AtividadeFisica tempAtividadeFisica = new AtividadeFisica();

        tempAtividadeFisica.id =  0;
        tempAtividadeFisica.nome = "Selecione uma Atividade";

        atividadeFisicaArrayList.add(0, tempAtividadeFisica);

        AtividadeFisicaSpinnerAdapter atividadeFisicaSpinnerAdapter = new AtividadeFisicaSpinnerAdapter( DiarioAtividadeDetalheActivity.this, R.layout.spinner_items_adapter, atividadeFisicaArrayList );

        atividadeAtividadeDetalheSpinner.setAdapter(atividadeFisicaSpinnerAdapter);

    }

    private void prepareIntensidadeSpinner()
    {

        ArrayList<Intensidade> intensidadeArrayList = intensidadeController.getAllIntensidade();

        Intensidade tempIntensidade = new Intensidade();

        tempIntensidade.id = 0;
        tempIntensidade.intensidade = "Selecione uma Intensidade";

        intensidadeArrayList.add(0, tempIntensidade );

        IntensidadeSpinnerAdapter atividadeFisicaSpinnerAdapter = new IntensidadeSpinnerAdapter( DiarioAtividadeDetalheActivity.this, R.layout.spinner_items_adapter, intensidadeArrayList );

        intensidadeAtividadeDetalheSpinner.setAdapter( atividadeFisicaSpinnerAdapter );

    }

}