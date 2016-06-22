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
import com.nutri.jonas.nutrima.controller.DiarioPesoController;
import com.nutri.jonas.nutrima.model.DiarioPeso;
import com.nutri.jonas.nutrima.util.Util;

import java.text.DecimalFormat;

public class DiarioPesoDetalheActivity extends AppCompatActivity
{

    private TextView idPesoDetalheTextView,
            pesoPesoDetalheTextView,
            dataPesoDetalheTextView,
            observacaoPesoDetalheTextView;

    private EditText pesoPesoDetalheEditText,
            dataPesoDetalheEditText,
            observacaoPesoDetalheEditText;

    private DiarioPesoController diarioPesoController;

    private Bundle extras;

    public boolean isEditingScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_peso_detalhe);

        diarioPesoController = new DiarioPesoController( getApplicationContext() );

        pesoPesoDetalheEditText = (EditText) findViewById( R.id.pesoPesoDetalheEditText );
        dataPesoDetalheEditText = (EditText) findViewById( R.id.dataPesoDetalheEditText );
        observacaoPesoDetalheEditText = (EditText) findViewById( R.id.observacaoPesoDetalheEditText );

        idPesoDetalheTextView = (TextView) findViewById( R.id.idPesoDetalheTextView );
        pesoPesoDetalheTextView = (TextView) findViewById( R.id.pesoPesoDetalheTextView );
        dataPesoDetalheTextView = (TextView) findViewById( R.id.dataPesoDetalheTextView );
        observacaoPesoDetalheTextView = (TextView) findViewById( R.id.observacaoPesoDetalheTextView );

        if( getIntent().getExtras() != null )
        {

            extras = getIntent().getExtras();

            isEditingScreen = extras.getBoolean( "isEditingScreen" );

            if( isEditingScreen )
                loadDiarioPeso( diarioPesoController.getDiarioPesoById( extras.getLong( "pesoId" ) ) );

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

                    DiarioPeso diarioPeso = new DiarioPeso();

                    if( isEditingScreen )
                        diarioPeso.id = Long.parseLong( idPesoDetalheTextView.getText().toString() );

                    diarioPeso.peso = Float.parseFloat( pesoPesoDetalheEditText.getText().toString() );
                    diarioPeso.dataPesagem = dataPesoDetalheEditText.getText().toString();
                    diarioPeso.imc = diarioPeso.peso / ( 1.90 * 2 );
                    diarioPeso.observacao = observacaoPesoDetalheEditText.getText().toString();

                    new insertDiarioPeso().execute( diarioPeso );

                }else
                {

                    String title = "AVISO";
                    String msg = "Preencha os campos obrigatórios.";

                    Util.alertDialog( DiarioPesoDetalheActivity.this, title, msg );

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class insertDiarioPeso extends AsyncTask< DiarioPeso, Void, Boolean >
    {

        @Override
        protected Boolean doInBackground( DiarioPeso... diarioPeso )
        {

            if( isEditingScreen )
                return diarioPesoController.updateDiarioPeso( diarioPeso[0] );
            else
                return diarioPesoController.insertDiarioPeso( diarioPeso[0] );

        }

        @Override
        protected void onPostExecute( Boolean savedWithSucess )
        {

            super.onPostExecute(savedWithSucess);

            if( savedWithSucess )
            {

                Toast.makeText( getApplicationContext(), "Realizado com sucesso.", Toast.LENGTH_LONG ).show();
                DiarioPesoDetalheActivity.this.finish();

            }else
            {

                Toast.makeText( getApplicationContext(), "Um erro ocorreu, tente novamente.", Toast.LENGTH_LONG ).show();

            }

        }

    }

    private boolean wereFieldsFilled()
    {

        boolean fieldsFilled = true;

        if( Util.isNullOrWhiteSpace( pesoPesoDetalheEditText.getText().toString( ) ) )
        {

            pesoPesoDetalheTextView.setTextColor( Color.RED );
            fieldsFilled = false;

        }

        if( Util.isNullOrWhiteSpace( dataPesoDetalheEditText.getText().toString( ) ) )
        {

            dataPesoDetalheTextView.setTextColor( Color.RED );
            fieldsFilled = false;

        }

        return fieldsFilled;

    }

    private void resetColorsFieldToBlack()
    {

        pesoPesoDetalheTextView.setTextColor( Color.BLACK );
        dataPesoDetalheTextView.setTextColor( Color.BLACK );

    }

    private void loadDiarioPeso( DiarioPeso diarioPeso )
    {

        idPesoDetalheTextView.setText( String.valueOf( diarioPeso.id ) );
        pesoPesoDetalheEditText.setText( String.valueOf( diarioPeso.peso ) );
        dataPesoDetalheEditText.setText( diarioPeso.dataPesagem );
        observacaoPesoDetalheEditText.setText( diarioPeso.observacao );

    }

}