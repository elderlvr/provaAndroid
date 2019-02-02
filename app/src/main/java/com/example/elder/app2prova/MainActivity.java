package com.example.elder.app2prova;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Tarefa tarefaEditada = null;
    RecyclerView recyclerView;
    TarefaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent i = getIntent();
        if(((Intent) i).hasExtra("tarefa")){
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);

            tarefaEditada = (Tarefa) i.getSerializableExtra("tarefa");
            Log.d("  Tarefaeditada = ",tarefaEditada.getId()  +" - "+tarefaEditada.getDescricao()
                    +" - "+tarefaEditada.getCategoria()+" "+tarefaEditada.getPrioridade()+" - "+tarefaEditada.getStatus());

            // puxando os dados para serem editados.
            EditText txtDesc = (EditText)findViewById(R.id.txtNome);
            Spinner spinner = (Spinner)findViewById(R.id.spnCategorias);
            CheckBox cbResolvido = (CheckBox) findViewById(R.id.cbStatus);

            txtDesc.setText(tarefaEditada.getDescricao());
            spinner.setSelection(getIndex(spinner, tarefaEditada.getCategoria()));
            cbResolvido.setChecked(tarefaEditada.getStatus());

            RadioButton rb1 = (RadioButton) findViewById(R.id.rb01);
            RadioButton rb2 = (RadioButton) findViewById(R.id.rb02);
            RadioButton rb3 = (RadioButton) findViewById(R.id.rb03);
            RadioButton rb4 = (RadioButton) findViewById(R.id.rb04);
            RadioButton rb5 = (RadioButton) findViewById(R.id.rb05);

            rb1.setSelected(false);
            rb2.setSelected(false);
            rb3.setSelected(false);
            rb4.setSelected(false);
            rb5.setSelected(false);

            //rb3.setSelected(true);

            /*switch (tarefaEditada.getPrioridade()){

                case 1:
                    rb1.setSelected(true);
                    Log.d("  Tarefaeditada1 = ",tarefaEditada.getPrioridade()+"");
                    //break;
                case 2:
                    rb2.setSelected(true);
                    Log.d("  Tarefaeditada2 = ",tarefaEditada.getPrioridade()+"");
                    //break;
                case 3:
                    rb3.setSelected(true);
                    Log.d("  Tarefaeditada3 = ",tarefaEditada.getPrioridade()+"");
                    //break;
                case 4:
                    rb4.setSelected(true);
                    //break;
                case 5:
                    rb5.setSelected(true);
                    //break;
            }*/
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            //carregando os campos
            //EditText editId = (EditText)findViewById(R.id.txtNome);
            EditText txtNome = (EditText)findViewById(R.id.txtNome);
            Spinner spCategoria = (Spinner) findViewById(R.id.spnCategorias);
            RadioGroup rgPriorid = (RadioGroup) findViewById(R.id.rgPrioridade);
            CheckBox cbStatus = findViewById(R.id.cbStatus);

            //pegando os valores
            String desc = txtNome.getText().toString();
            String cat = spCategoria.getSelectedItem().toString();

            int prio = 1;
            switch (rgPriorid.getCheckedRadioButtonId()){
                case R.id.rb01:
                    prio = 1;
                    break;
                case R.id.rb02:
                    prio = 2;
                    break;
                case R.id.rb03:
                    prio = 3;
                    break;
                case R.id.rb04:
                    prio = 4;
                    break;
                case R.id.rb05:
                    prio = 5;
                    break;
            }

            boolean status  = cbStatus.isChecked();

            //salvando os dados
            TarefaDAO dao = new TarefaDAO(getBaseContext());
            boolean sucesso;// = dao.salvar( desc, cat, prio, status);//id
        if(tarefaEditada != null)
            sucesso = dao.salvar(tarefaEditada.getId(), desc, cat, prio, status);
        else
            sucesso = dao.salvar(desc, cat, prio, status);
        if(sucesso){
            Tarefa tarefa = dao.retornaUltimo();
            if(tarefaEditada != null) {

            }else
                adapter.adicionar(tarefa);
                //limpa os campos
                //editId.setText("");
                txtNome.setText("");
                spCategoria.setTag(0);
                rgPriorid.setSelected(false);
                cbStatus.setChecked(false);

                Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }else{
                Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
                }
        });





        configuraRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getIndex(Spinner sp, String myString){
        int index = 0;
        for (int i=0; i<sp.getCount(); i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    private void configuraRecycler(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLista);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TarefaDAO dao = new TarefaDAO(this);
        adapter = new TarefaAdapter(dao.retornaTodos());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    

}
