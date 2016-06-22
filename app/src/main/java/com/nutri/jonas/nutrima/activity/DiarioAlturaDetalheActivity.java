package com.nutri.jonas.nutrima.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.controller.DiarioAlturaController;
import com.nutri.jonas.nutrima.controller.DiarioPesoController;
import com.nutri.jonas.nutrima.model.DiarioAltura;
import com.nutri.jonas.nutrima.model.DiarioPeso;
import com.nutri.jonas.nutrima.util.Util;

public class DiarioAlturaDetalheActivity extends AppCompatActivity
{

    private TextView
            idAlturaDetalhetextView,
            alturaAlturaDetalheTextView,
            dataAlturaDetalheTextView,
            observacaoAlturaDetalheTextView;

    private EditText
            alturaAlturaDetalheEditText,
            dataAlturaDetalheEditText,
            observacaoAlturaDetalheEditText;

    private DiarioAlturaController diarioAlturaController;

    private Bundle extras;

    public boolean isEditingScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diario_altura_detalhe );

        diarioAlturaController = new DiarioAlturaController( getApplicationContext() );

        alturaAlturaDetalheEditText = (EditText) findViewById( R.id.alturaAlturaDetalheEditText );
        dataAlturaDetalheEditText = (EditText) findViewById( R.id.dataAlturaDetalheEditText );
        observacaoAlturaDetalheEditText = (EditText) findViewById( R.id.observacaoAlturaDetalheEditText );

        idAlturaDetalhetextView = (TextView) findViewById( R.id.idAlturaDetalhetextView );
        alturaAlturaDetalheTextView = (TextView) findViewById( R.id.alturaAlturaDetalheTextView );
        dataAlturaDetalheTextView = (TextView) findViewById( R.id.dataAlturaDetalheTextView );
        observacaoAlturaDetalheTextView = (TextView) findViewById( R.id.observacaoAlturaDetalheTextView );

        if( getIntent().getExtras() != null )
        {

            extras = getIntent().getExtras();

            isEditingScreen = extras.getBoolean( "isEditingScreen" );

            if( isEditingScreen )
                loadDiarioAltura( diarioAlturaController.getDiarioAlturaById( extras.getLong( "alturaId" ) ) );

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.confirm_menu, menu );
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch( item.getItemId() )
        {

            case R.id.action_confirm:

                if( wereFieldsFilled() )
                {

                    resetColorsFieldToBlack();

                    DiarioAltura diarioAltura = new DiarioAltura();

                    if( isEditingScreen )
                        diarioAltura.id = Long.parseLong( idAlturaDetalhetextView.getText().toString() );

                    diarioAltura.altura =  Float.parseFloat( alturaAlturaDetalheEditText.getText().toString() );
                    diarioAltura.dataMedicao = dataAlturaDetalheEditText.getText().toString();
                    diarioAltura.observacao = observacaoAlturaDetalheEditText.getText().toString();
                    //TODO salvar paciente id.

                    new insertDiarioAltura().execute( diarioAltura );

                }else
                {

                    String title = "AVISO";
                    String msg = "Preencha os campos obrigatórios.";

                    Util.alertDialog(DiarioAlturaDetalheActivity.this, title, msg);

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class insertDiarioAltura extends AsyncTask< DiarioAltura, Void, Boolean >
    {

        @Override
        protected Boolean doInBackground( DiarioAltura... diarioAltura )
        {

            if( isEditingScreen )
                return diarioAlturaController.updateDiarioAltura(diarioAltura[0] );
            else
                return diarioAlturaController.insertDiarioAltura(diarioAltura[0] );

        }

        @Override
        protected void onPostExecute( Boolean savedWithSucess )
        {

            super.onPostExecute(savedWithSucess);

            if( savedWithSucess )
            {

                Toast.makeText(getApplicationContext(), "Realizado com sucesso.", Toast.LENGTH_LONG).show();
                DiarioAlturaDetalheActivity.this.finish();

            }else
            {

                Toast.makeText( getApplicationContext(), "Um erro ocorreu, tente novamente.", Toast.LENGTH_LONG ).show();

            }

        }

    }

    private boolean wereFieldsFilled()
    {

        boolean fieldsFilled = true;

        if( Util.isNullOrWhiteSpace( alturaAlturaDetalheEditText.getText().toString( ) ) )
        {

            alturaAlturaDetalheTextView.setTextColor( Color.RED );
            fieldsFilled = false;

        }

        if( Util.isNullOrWhiteSpace( alturaAlturaDetalheEditText.getText().toString( ) ) )
        {

            dataAlturaDetalheTextView.setTextColor( Color.RED );
            fieldsFilled = false;

        }

        return fieldsFilled;

    }

    private void resetColorsFieldToBlack()
    {

        alturaAlturaDetalheTextView.setTextColor( Color.BLACK );
        dataAlturaDetalheTextView.setTextColor( Color.BLACK );

    }

    private void loadDiarioAltura( DiarioAltura diarioAltura )
    {

        idAlturaDetalhetextView.setText(String.valueOf(diarioAltura.id ) );
        alturaAlturaDetalheEditText.setText( String.valueOf( diarioAltura.altura ) );
        dataAlturaDetalheEditText.setText( diarioAltura.dataMedicao );
        observacaoAlturaDetalheEditText.setText( diarioAltura.observacao );

    }

}