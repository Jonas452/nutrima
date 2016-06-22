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
import com.nutri.jonas.nutrima.adapter.DiarioAtividadeAdapter;
import com.nutri.jonas.nutrima.controller.DiarioAtividadeController;
import com.nutri.jonas.nutrima.model.DiarioAtividade;

import java.util.ArrayList;

public class DiarioAtividadeListActivity extends AppCompatActivity
{

    private ListView atividadesListView;
    private TextView semDadosAtividadeListTextView;

    private DiarioAtividadeController diarioAtividadeController;

    private long diarioAtividadeId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_atividade_list);

        diarioAtividadeController = new DiarioAtividadeController( getApplicationContext() );

        atividadesListView = (ListView) findViewById( R.id.atividadesListView );
        semDadosAtividadeListTextView = (TextView) findViewById( R.id.semDadosAtividadeListTextView );

        atividadesListView.setOnItemClickListener(atividadeListViewOnItemClickListener);
        atividadesListView.setOnItemLongClickListener(atividadeListOnItemLongClickListener);

    }

    @Override
    protected void onResume()
    {

        super.onResume();

        new CarregarDiarioAtividade().execute();

    }

    AdapterView.OnItemClickListener atividadeListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3)
        {

            Intent intent = new Intent( DiarioAtividadeListActivity.this, DiarioAtividadeDetalheActivity.class );

            intent.putExtra( "diarioAtividadeId", Long.parseLong( ( (TextView) v.findViewById( R.id.idAtividadeAdapterTextView ) ).getText().toString() ) );
            intent.putExtra( "isEditingScreen", true );

            startActivity( intent );

        }

    };

    AdapterView.OnItemLongClickListener atividadeListOnItemLongClickListener = new AdapterView.OnItemLongClickListener()
    {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {

            diarioAtividadeId = Long.parseLong( ( (TextView) view.findViewById( R.id.idAtividadeAdapterTextView ) ).getText().toString() );

            AlertDialog.Builder builder = new AlertDialog.Builder( DiarioAtividadeListActivity.this );

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

                    boolean deleted = diarioAtividadeController.deleteDiarioAtividade( diarioAtividadeId );

                    if( deleted )
                    {

                        Toast.makeText( DiarioAtividadeListActivity.this, "Dados excluídos com sucesso.", Toast.LENGTH_LONG ).show();

                        new CarregarDiarioAtividade().execute();

                    }else
                    {

                        Toast.makeText( DiarioAtividadeListActivity.this, "Ocorreu um error ao excluír os dados.", Toast.LENGTH_LONG ).show();

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

                Intent intent = new Intent( DiarioAtividadeListActivity.this, DiarioAtividadeDetalheActivity.class );

                startActivity( intent );

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class CarregarDiarioAtividade extends AsyncTask< Long, Void, ArrayList<DiarioAtividade> >
    {

        @Override
        protected ArrayList<DiarioAtividade> doInBackground( Long... params )
        {

            return diarioAtividadeController.getAllDiarioAtividade();

        }

        @Override
        protected void onPostExecute( ArrayList<DiarioAtividade> result )
        {

            super.onPostExecute( result );

            if( result != null && result.size() > 0 )
            {

                //Não existe dados.
                semDadosAtividadeListTextView.setVisibility(View.GONE);

                //Instancia o adapter.
                DiarioAtividadeAdapter atividadeAdapter = new DiarioAtividadeAdapter( DiarioAtividadeListActivity.this, R.layout.atividade_adapter, result );

                //Set o adapter.
                atividadesListView.setAdapter( atividadeAdapter );

            }else
            {

                //Não existe dados.
                semDadosAtividadeListTextView.setVisibility(View.VISIBLE);

                //Set o adapter.
                atividadesListView.setAdapter(null);

            }

        }

    }

}