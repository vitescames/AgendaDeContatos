package br.com.vithorescames.agendacontato;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.vithorescames.agendacontato.database.DataBase;
import br.com.vithorescames.agendacontato.database.RepositorioContato;
import br.com.vithorescames.agendacontato.database.entidades.Contato;

public class ActCadContatos extends AppCompatActivity {

    private EditText endereco;
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText datas;
    private EditText grupos;

    private Spinner spnEmail;
    private Spinner spnEndereco;
    private Spinner spnDatas;
    private Spinner spnTelefone;

    private ArrayAdapter<String> adpEmail;
    private ArrayAdapter<String> adpTel;
    private ArrayAdapter<String> adpEnd;
    private ArrayAdapter<String> adpDatas;

    private RepositorioContato repositorioContato;
    private DataBase db;
    private SQLiteDatabase conexao;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_contatos);

        endereco = (EditText)findViewById(R.id.edtEnd);
        nome = (EditText)findViewById(R.id.edtNome);
        email = (EditText)findViewById(R.id.edtEmail);
        telefone = (EditText)findViewById(R.id.edtTel);
        datas = (EditText)findViewById(R.id.edtDatas);
        grupos = (EditText)findViewById(R.id.edtGrupos);

        spnEmail = (Spinner)findViewById(R.id.spnEmail);
        spnEndereco = (Spinner)findViewById(R.id.spnEnd);
        spnTelefone = (Spinner)findViewById(R.id.spnTel);
        spnDatas = (Spinner)findViewById(R.id.spnDatas);

        adpEmail = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpDatas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpDatas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpEnd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpTel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpTel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpEmail.add("Casa");
        adpEmail.add("Trabalho");
        adpEmail.add("Outros");

        adpTel.add("Principal");
        adpTel.add("Casa");
        adpTel.add("Celular");
        adpTel.add("Pager");
        adpTel.add("Fax trabalho");
        adpTel.add("Fax casa");
        adpTel.add("Outros");

        adpEnd.add("Casa");
        adpEnd.add("Trabalho");
        adpEnd.add("Outros");

        adpDatas.add("Aniversário");
        adpDatas.add("Data comemorativa");
        adpDatas.add("Outros");

        spnEmail.setAdapter(adpEmail);
        spnDatas.setAdapter(adpDatas);
        spnTelefone.setAdapter(adpTel);
        spnEndereco.setAdapter(adpEnd);

        //datas.setOnFocusChangeListener(new ExibeDataListener());

        //OU

        ExibeDataListener listener = new ExibeDataListener();
        datas.setOnFocusChangeListener(listener);

        contato = new Contato();

        try {

            db = new DataBase(this);
            conexao = db.getWritableDatabase();

            repositorioContato = new RepositorioContato(conexao);

        }catch (Exception ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //METODO PARA MOSTRAR O MENU NESSE ACTIVITY

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_contato, menu); //ASSOCIAR O ARQUIVO DO MENU

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //METODO PARA AS AÇÕES EXECUTADAS PARA CADA ITEM DO MENU

        switch (item.getItemId()){
            case R.id.salvar:
                if(contato == null) {
                    setDados();
                }
                else {

                }
                finish();
            break;

            case R.id.excluir:

            break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDados(){

        try {
            contato.setNome(nome.getText().toString());
            contato.setEndereco(endereco.getText().toString());
            contato.setGrupos(grupos.getText().toString());
            contato.setTelefone(telefone.getText().toString());
            contato.setEmail(email.getText().toString());

            contato.setTipoDatas(String.valueOf(spnDatas.getSelectedItem()));
            contato.setTipoEmail(String.valueOf(spnEmail.getSelectedItem()));
            contato.setTipoEndereco(String.valueOf(spnEndereco.getSelectedItem()));
            contato.setTipoTelefone(String.valueOf(spnTelefone.getSelectedItem()));

            repositorioContato.inserirContato(contato); //joga o o objeto pro metodo inserirContato da classe em questão
            //inserindo no banco de dados. Depois, o método buscaContatos busca essas informações e joga no arrayadapter

        } catch (Exception e){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao inserir os dados: " + e.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    private void exibeData(){

        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(calendar.YEAR); //pega o ano mes e dia atual
        int mes = calendar.get(calendar.MONTH);
        int dia = calendar.get(calendar.DAY_OF_MONTH);

        DatePickerDialog date = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);//o datepicker sera exibido
        //assim, primeiro parametro sendo a actvityr em questão, segundo sera instancia da classe OnDateSetListener
        date.show(); //mostra o datepicker
    }

    private class ExibeDataListener implements View.OnFocusChangeListener{ //listener para quando um campo tiver foco

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(hasFocus) { //se o elemento que estiver associado a esse evento tiver foco, faz isso
                exibeData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{

        //classe sendo utilizada no datepicker

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth); //seta no objeto calendar o ano mes e dia selecionados no datepicker

            Date date = calendar.getTime(); //o objeto date será a data do calendar

            DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM); //parametro q define como será o formato (medium)
            //caso queria só os números, deve-se usar SHORT
            datas.setText(formato.format(date)); //seta no edit text do datas a data em string e apresentavel

            contato.setDatasEspeciais(date); //seta na datasespeciais do banco de dados do contato o objeto date
            //nao pode setar o datas pq ele é string, o datas é só pra preencher o edit text pro usário ver
        }
    }
}
