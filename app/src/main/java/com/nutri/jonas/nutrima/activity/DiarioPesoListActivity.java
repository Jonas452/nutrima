package com.nutri.jonas.nutrima.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.adapter.DiarioPesoAdapter;
import com.nutri.jonas.nutrima.controller.DiarioPesoController;
import com.nutri.jonas.nutrima.model.DiarioPeso;

import java.util.ArrayList;

public class DiarioPesoListActivity extends AppCompatActivity
{

    private ListView pesagensListView;
    private TextView semDadosPesoListTextView;

    private DiarioPesoController diarioPesoController;

    private long pesoId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_peso_list);

        diarioPesoController = new DiarioPesoController( getApplicationContext() );

        pesagensListView = (ListView) findViewById( R.id.pesagensListView );
        semDadosPesoListTextView = (TextView) findViewById( R.id.semDadosPesoListTextView );

        pesagensListView.setOnItemClickListener( pesagensListViewOnItemClickListener );
        pesagensListView.setOnItemLongClickListener(persagensListOnItemLongClickListener);

    }

    @Override
    protected void onResume()
    {

        super.onResume();

        new CarregarDiarioPeso().execute();

    }

    AdapterView.OnItemClickListener pesagensListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3)
        {

            Intent intent = new Intent( DiarioPesoListActivity.this, DiarioPesoDetalheActivity.class );

            intent.putExtra( "pesoId", Long.parseLong( ( (TextView) v.findViewById( R.id.idPesoAdaptertextView ) ).getText().toString() ) );
            intent.putExtra( "isEditingScreen", true );

            startActivity( intent );

        }

    };

    AdapterView.OnItemLongClickListener persagensListOnItemLongClickListener = new AdapterView.OnItemLongClickListener()
    {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {

            pesoId = Long.parseLong( ( (TextView) view.findViewById( R.id.idPesoAdaptertextView ) ).getText().toString() );

            AlertDialog.Builder builder = new AlertDialog.Builder( DiarioPesoListActivity.this );

            builder.setTitle( "AVISO" );
            builder.setMessage( "Tem certeza que deseja EXCLUIR esse dado?" );
            builder.setPositiveButton("Sim", dialogClickListener);
            builder.setNegativeButton("Não", dialogClickListener);

            builder.show();

            return false;
        }


    };

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {

            switch( which )
            {
                case DialogInterface.BUTTON_POSITIVE:

                    boolean deleted = diarioPesoController.deleteDiarioPeso( pesoId );

                    if( deleted )
                    {

                        Toast.makeText( DiarioPesoListActivity.this, "Dados excluídos com sucesso.", Toast.LENGTH_LONG).show();

                        new CarregarDiarioPeso().execute();

                    }else
                    {

                        Toast.makeText( DiarioPesoListActivity.this, "Ocorreu um error ao excluír os dados.", Toast.LENGTH_LONG).show();

                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch( item.getItemId() )
        {

            case R.id.action_add:

                Intent intent = new Intent( DiarioPesoListActivity.this, DiarioPesoDetalheActivity.class );

                startActivity( intent );

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class CarregarDiarioPeso extends AsyncTask< Long, Void, ArrayList<DiarioPeso> >
    {

        @Override
        protected ArrayList<DiarioPeso> doInBackground( Long... params )
        {

            return diarioPesoController.getAllDiarioPeso();

        }

        @Override
        protected void onPostExecute( ArrayList<DiarioPeso> result )
        {

            super.onPostExecute( result );

            if( result != null && result.size() > 0 )
            {

                //Não existe dados.
                semDadosPesoListTextView.setVisibility( View.GONE );

                //Instancia o adapter.
                DiarioPesoAdapter pesoAdapter = new DiarioPesoAdapter( DiarioPesoListActivity.this, R.layout.peso_adapter, result );

                //Set o adapter.
                pesagensListView.setAdapter( pesoAdapter );

            }else
            {

                //Não existe dados.
                semDadosPesoListTextView.setVisibility( View.VISIBLE );

                //Set o adapter.
                pesagensListView.setAdapter( null );

            }

        }

    }

}