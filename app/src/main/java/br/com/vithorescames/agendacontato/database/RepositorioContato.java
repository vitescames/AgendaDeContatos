package br.com.vithorescames.agendacontato.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.Date;

import br.com.vithorescames.agendacontato.database.entidades.Contato;

/**
 * Created by Vithor Escames on 19/07/2017.
 */

public class RepositorioContato {

    private SQLiteDatabase conexao;

    public RepositorioContato(SQLiteDatabase con){
        this.conexao = con;
    }

    public void inserirContato(Contato contato){

        ContentValues values = new ContentValues();
        values.put("TELEFONE", contato.getTelefone()); //coloca num objeto ContentValues dados
        values.put("TIPOTELEFONE", contato.getTipoTelefone());
        values.put("EMAIL", contato.getEmail());
        values.put("TIPOEMAIL", contato.getTipoEmail());
        values.put("NOME", contato.getNome());
        values.put("ENDERECO", contato.getEndereco());
        values.put("DATASESPECIAIS", contato.getDatasEspeciais().getTime());
        values.put("TIPODATASESPECIAIS", contato.getTipoDatas());
        values.put("GRUPOS", contato.getGrupos());
        values.put("TIPOENDERECO", contato.getTipoEndereco());

        conexao.insertOrThrow("CONTATO", null, values);

    }

    /*public void testeInserirContatos(){

        for(int i = 0; i < 3; i++) {
            ContentValues values = new ContentValues();
            values.put("TELEFONE", "424232"); //coloca num objeto ContentValues dados

            conexao.insertOrThrow("CONTATO", null, values);
        }
    }*/

    public ArrayAdapter<Contato> buscaContatos(Context context){

        ArrayAdapter<Contato> adpContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conexao.query("CONTATO", null, null, null, null, null, null);
        //Cursores são mecanismos que permitem que as linhas de uma tabela sejam manipuladas uma a uma.
        //Cursores são tipo interfaces com os dados
        //na linha 24, o cursor pegará as ocorrencias da tabela "CONTATO" criada na classe DataBase

        if(cursor.getCount() > 0){ //se o cursor tiver mais q uma linha (ocorrencia) de dados armazenados

            cursor.moveToFirst();

            while(cursor.moveToNext()) { //enquanto o cursor tiver linhas de dados (ocorrencias)

                Contato contato = new Contato();
                contato.setNome(cursor.getString(1));
                contato.setTelefone(cursor.getString(2)); //pega o valor da segunda coluna (telefone, conforme scriptado na
                //clase DataBase) e seta pro objeto contato (essa instancia é pro arrayadapter, pra aparecer na lista
                //ao chamar esse método, o cursor vai percorrer a tabela com os dados inseridos já (no metodo inserirContato)
                //e vai fazer com que cada linha do arrayadapter seja um objeto contato
                contato.setTipoTelefone(cursor.getString(3));
                contato.setEmail(cursor.getString(4));
                contato.setTipoEmail(cursor.getString(5));
                contato.setEndereco(cursor.getString(6));
                contato.setTipoEndereco(cursor.getString(7));
                contato.setDatasEspeciais(new Date(cursor.getLong(8))); //data é retornada em milisegundos
                contato.setTipoDatas(cursor.getString(9));
                contato.setGrupos(cursor.getString(10));
                adpContatos.add(contato); //e adiciona a linha no arrayadapter
            }
        }

        return adpContatos;
    }

}
