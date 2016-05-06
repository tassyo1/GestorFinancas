package DAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tassyosantana on 05/05/16.
 */
public class Banco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "gerencia_financas.db";

    private static final String TABLE_CATEGORIA = "CREATE TABLE categorias (id integer primary key "
            + "autoincrement, nome text, valor real, data_agendada text, tipo text, frequencia_id integer)";

    private static final String TABLE_FREQUENCIA = "CREATE TABLE frequencias (id integer primary key "
            +"autoincrement, descricao text)";

    private static final String TABLE_MOVIMENTO = "CREATE TABLE movimentos (id integer primary key "
            +"autoincrement, data_lacamento text, saldo_atual real, categoria_id integer)";

    private static final String INSERT_FREQUENCIA =
            "INSERT INTO frequencias (descricao) VALUES ('enventual'); "+
            "INSERT INTO frequencias (descricao) VALUES ('mensal'); "+
            "INSERT INTO frequencias (descricao) VALUES ('bimestral'); "+
            "INSERT INTO frequencias (descricao) VALUES ('trimestral'); "+
            "INSERT INTO frequencias (descricao) VALUES ('semestral'); "+
            "INSERT INTO frequencias (descricao) VALUES ('anual');";

    public Banco(Context context)
    {
        super(context,NOME_BANCO,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORIA);
        db.execSQL(TABLE_FREQUENCIA);
        db.execSQL(TABLE_MOVIMENTO);
        db.execSQL(INSERT_FREQUENCIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
