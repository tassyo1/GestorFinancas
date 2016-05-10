package DAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by tassyosantana on 05/05/16.
 */
public class Banco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "gerencia_financas.db";

    private static final int VERSAO = 1;

    private static final String TABLE_FREQUENCIA = "CREATE TABLE frequencias (id integer primary key "
            +"autoincrement, descricao text)";

    private static final String TABLE_CATEGORIA = "CREATE TABLE categorias (id integer primary key "
            + "autoincrement, nome text, valor real, data_agendada text, tipo text, frequencia_id integer, " +
            "FOREIGN KEY(frequencia_id) REFERENCES frequencias(id) );";

    private static final String TABLE_MOVIMENTO = "CREATE TABLE movimentos (id integer primary key "
            +"autoincrement, data_lancamento date, saldo_atual real, categoria_id integer, " +
            "FOREIGN KEY(categoria_id) REFERENCES categorias(id) );";

    //Nao foi possivel usar array para inserir com loop, ocorria falta de memoria
    private static final String EVENTUAL = " INSERT INTO frequencias (descricao) VALUES ('eventual'); ";
    private static final String MENSAL =    " INSERT INTO frequencias (descricao) VALUES ('mensal');  ";
    private static final String BIMESTRAL = " INSERT INTO frequencias (descricao) VALUES ('bimestral'); ";
    private static final String TRIMESTRAL = " INSERT INTO frequencias (descricao) VALUES ('trimestral');";
    private static final String SEMESTRAL = " INSERT INTO frequencias (descricao) VALUES ('semestral'); ";
    private static final String ANUAL = " INSERT INTO frequencias (descricao) VALUES ('anual');";


    public Banco(Context context)
    {
        super(context,NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORIA);
        db.execSQL(TABLE_FREQUENCIA);
        db.execSQL(TABLE_MOVIMENTO);
        db.execSQL(EVENTUAL);
        db.execSQL(MENSAL);
        db.execSQL(BIMESTRAL);
        db.execSQL(TRIMESTRAL);
        db.execSQL(SEMESTRAL);
        db.execSQL(ANUAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FREQUENCIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIMENTO);

        // create new tables
        onCreate(db);
    }
}
