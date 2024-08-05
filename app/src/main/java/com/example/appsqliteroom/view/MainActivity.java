package com.example.appsqliteroom.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import com.example.appsqliteroom.R;
import com.example.appsqliteroom.dao.PessoaDao;
import com.example.appsqliteroom.database.Banco;
import com.example.appsqliteroom.entities.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private EditText edtNome, edtTelefone;
    private Button btnS, btnD, btnL;
    private Pessoa p;
    private PessoaDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setup();

        p = (Pessoa) getIntent().getSerializableExtra("dado");

        if (p != null)
        {
            edtNome.setText(p.getNome());
            edtTelefone.setText(p.getNome());
        }

        Banco db = Room.databaseBuilder(getApplicationContext(),
                Banco.class, "meu_banco")
                .allowMainThreadQueries()
                .build();

        dao = db.pessoaDao();
    }

    private void setup()
    {
        edtNome = findViewById(R.id.editTextNome);
        edtTelefone = findViewById(R.id.editTextTelefone);
        btnS = findViewById(R.id.buttonS);
        btnD = findViewById(R.id.buttonD);
        btnL = findViewById(R.id.buttonL);
    }

    public void clicar(View view)
    {
        if(view.getId() == R.id.buttonS) {
            if (p == null) {
                p = new Pessoa();
                p.setNome(edtNome.getText().toString());
                p.setTelefone(edtTelefone.getText().toString());

                long retorno = dao.adicionar(p);
                if (retorno != -1) {

                    Toast.makeText(MainActivity.this, "Salvo", Toast.LENGTH_SHORT).show();
                    p = null;
                    edtNome.setText("");
                    edtTelefone.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Não salvo", Toast.LENGTH_SHORT).show();
                }

            } else {
                p.setNome(edtNome.getText().toString());
                p.setTelefone(edtTelefone.getText().toString());
                int retorno = dao.atualizar(p);
                if (retorno != 0) {
                    Toast.makeText(MainActivity.this, "Salvo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Não salvo", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(view.getId() == R.id.buttonL) {
            ArrayList<Pessoa> dados = (ArrayList) dao.listar();
            if (dados != null) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("dados", dados);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Sem dados", Toast.LENGTH_SHORT).show();
            }

        }

        if(view.getId() == R.id.buttonD) {
            int retorno = dao.excluir(p);
            if (retorno > 0) {
                Toast.makeText(MainActivity.this, "Deletado", Toast.LENGTH_SHORT).show();
                p = null;
                edtNome.setText("");
                edtTelefone.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        }
    }
}