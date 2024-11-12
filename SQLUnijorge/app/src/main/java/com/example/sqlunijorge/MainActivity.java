package com.example.sqlunijorge;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private DatabaseHelper dbHelper;
    private ListView listViewContatos;
    private EditText editNome, editTelefone, editEmail, editEndereco, editDataNascimento;
    private Button btnAdicionar, btnAtualizar, btnExcluir;
    private ArrayAdapter<Contato> adapter;
    private List<Contato> listaContatos;
    private Contato contatoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        editNome = findViewById(R.id.editNome);
        editTelefone = findViewById(R.id.editTelefone);
        editEmail = findViewById(R.id.editEmail);
        editEndereco = findViewById(R.id.editEndereco);
        editDataNascimento = findViewById(R.id.editDataNascimento);
        listViewContatos = findViewById(R.id.listViewContatos);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnExcluir = findViewById(R.id.btnExcluir);

        carregarContatos();

        btnAdicionar.setOnClickListener(view -> adicionarContato());
        btnAtualizar.setOnClickListener(view -> atualizarContato());
        btnExcluir.setOnClickListener(view -> excluirContato());

        listViewContatos.setOnItemClickListener((parent, view, position, id) ->
        {
            contatoSelecionado = listaContatos.get(position);
            editNome.setText(contatoSelecionado.getNome());
            editTelefone.setText(contatoSelecionado.getTelefone());
            editEmail.setText(contatoSelecionado.getEmail());
            editEndereco.setText(contatoSelecionado.getEndereco());
            editDataNascimento.setText(contatoSelecionado.getDataNascimento());
        });
    }

    private void adicionarContato()
    {
        Contato contato = new Contato();
        contato.setNome(editNome.getText().toString());
        contato.setTelefone(editTelefone.getText().toString());
        contato.setEmail(editEmail.getText().toString());
        contato.setEndereco(editEndereco.getText().toString());
        contato.setDataNascimento(editDataNascimento.getText().toString());

        dbHelper.adicionarContato(contato);
        Toast.makeText(this, "Contato adicionado", Toast.LENGTH_SHORT).show();
        carregarContatos();
    }

    private void atualizarContato() {
        if (contatoSelecionado != null)
        {
            contatoSelecionado.setNome(editNome.getText().toString());
            contatoSelecionado.setTelefone(editTelefone.getText().toString());
            contatoSelecionado.setEmail(editEmail.getText().toString());
            contatoSelecionado.setEndereco(editEndereco.getText().toString());
            contatoSelecionado.setDataNascimento(editDataNascimento.getText().toString());

            dbHelper.atualizarContato(contatoSelecionado);
            Toast.makeText(this, "Contato atualizado", Toast.LENGTH_SHORT).show();
            carregarContatos();
        } else
        {
            Toast.makeText(this, "Selecione um contato para atualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirContato() {
        if (contatoSelecionado != null)
        {
            dbHelper.excluirContato(contatoSelecionado.getId());
            Toast.makeText(this, "Contato excluído", Toast.LENGTH_SHORT).show();
            carregarContatos();
        } else
        {
            Toast.makeText(this, "Selecione um contato para excluir", Toast.LENGTH_SHORT).show();
        }
    }

    private void carregarContatos()
    {
        listaContatos = dbHelper.listarContatos();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContatos);
        listViewContatos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}