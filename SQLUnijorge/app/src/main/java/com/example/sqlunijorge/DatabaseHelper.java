package com.example.sqlunijorge;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "contatos.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE contatos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "telefone TEXT," +
                "email TEXT," +
                "endereco TEXT," +
                "dataNascimento TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS contatos");
        onCreate(db);
    }

    public void adicionarContato(Contato contato)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        values.put("endereco", contato.getEndereco());
        values.put("dataNascimento", contato.getDataNascimento());
        db.insert("contatos", null, values);
        db.close();
    }

    public int atualizarContato(Contato contato)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("email", contato.getEmail());
        values.put("endereco", contato.getEndereco());
        values.put("dataNascimento", contato.getDataNascimento());

        return db.update("contatos", values, "id = ?", new String[]{String.valueOf(contato.getId())});
    }

    public void excluirContato(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contatos", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Contato> listarContatos()
    {
        List<Contato> contatos = new ArrayList<>();
        String selectQuery = "SELECT * FROM contatos";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndex("id")));
                contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                contato.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                contato.setDataNascimento(cursor.getString(cursor.getColumnIndex("dataNascimento")));
                contatos.add(contato);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contatos;
    }
}
