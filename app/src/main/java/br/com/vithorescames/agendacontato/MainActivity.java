package br.com.vithorescames.agendacontato;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import br.com.vithorescames.agendacontato.database.DataBase;
import br.com.vithorescames.agendacontato.database.RepositorioContato;
import br.com.vithorescames.agendacontato.database.entidades.Contato;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btn;
    private EditText search;
    private ListView lst;
    private ArrayAdapter<Contato> adpContatos;
    private RepositorioContato repositorioContato;
    private DataBase db; //objeto da classe q eu criei
    private SQLiteDatabase conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //método executado na primeira execução do activity

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (EditText)findViewById(R.id.edtPesquisa);
        lst = (ListView)findViewById(R.id.lstContatos);

        btn = (ImageButton)findViewById(R.id.btnAdd);

        btn.setOnClickListener(this);

        try {

            db = new DataBase(this);
            conexao = db.getWritableDatabase(); //"conexao" sera a tabela pronta para ser editada

            repositorioContato = new RepositorioContato(conexao);

            //repositorioContato.testeInserirContatos();

            adpContatos = repositorioContato.buscaContatos(this);

            lst.setAdapter(adpContatos);


        }catch (Exception ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

    }

    public void onClick(View v){

        Intent it = new Intent(this, ActCadContatos.class);
        startActivityForResult(it, 0); //vai iniciar o activity em questão esperando algum resultado (se tiver varios activities,
        //mudar numeração do segundo parâmetro (requestCode))

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //quando o activity em questão (identificado pelo requestCode) for fechado (finish()), esse activity aqui será atualizado:

        if(requestCode == 0) { //se o activity secundário fechado tiver requestCode 0 (definido no onClick()), faz isso
            adpContatos = repositorioContato.buscaContatos(this);

            lst.setAdapter(adpContatos);
        }

        //o sistema de janelas do android funciona como uma pilha, quando vc está num activity e vai pra outro, o primeiro
        //"fica atrás"
    }
}
