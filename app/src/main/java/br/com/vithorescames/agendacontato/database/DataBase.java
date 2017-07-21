package br.com.vithorescames.agendacontato.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vithor Escames on 19/07/2017.
 */

public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context) { //esse método é chamado no mainactivity
        super(context, "agenda", null, 1); //chamando  construtor da superclasse (SQLiteOpenHelper)
    }//precisa desse construtor pra funcionar

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CONTATO ( ");
        sqlBuilder.append("_id                INTEGER       NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME               VARCHAR (200), ");
        sqlBuilder.append("TELEFONE           VARCHAR (14), ");
        sqlBuilder.append("TIPOTELEFONE       VARCHAR (1), ");
        sqlBuilder.append("EMAIL              VARCHAR (255), ");
        sqlBuilder.append("TIPOEMAIL          VARCHAR (1), ");
        sqlBuilder.append("ENDERECO           VARCHAR (255), ");
        sqlBuilder.append("TIPOENDERECO       VARCHAR (1), ");
        sqlBuilder.append("DATASESPECIAIS     DATE, ");
        sqlBuilder.append("TIPODATASESPECIAIS VARCHAR (1), ");
        sqlBuilder.append("GRUPOS             VARCHAR (255) ");
        sqlBuilder.append("); ");

        db.execSQL(sqlBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
