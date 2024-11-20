package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.MockApiService;
import com.example.myapplication.model.Aluno;
import com.example.myapplication.api.ViaCepService;
import com.example.myapplication.model.Endereco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {

    private EditText etRa, etNome, etCep, etLogradouro, etComplemento, etBairro, etCidade, etUf;
    private Button btnSalvar, btnBuscarCep;

    private MockApiService mockApiService;
    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializar campos
        etRa = findViewById(R.id.etRa);
        etNome = findViewById(R.id.etNome);
        etCep = findViewById(R.id.etCep);
        etLogradouro = findViewById(R.id.etLogradouro);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUf = findViewById(R.id.etUf);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnBuscarCep = findViewById(R.id.btnBuscarCep);

        // Configurar Retrofit para ViaCEP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        viaCepService = retrofit.create(ViaCepService.class);

        // Configurar Retrofit para MockAPI
        retrofit = new Retrofit.Builder()
                .baseUrl("https://673d08f74db5a341d833cd5a.mockapi.io/alunos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mockApiService = retrofit.create(MockApiService.class);

        // Buscar CEP
        btnBuscarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = etCep.getText().toString();
                viaCepService.getEndereco(cep).enqueue(new Callback<Endereco>() {
                    @Override
                    public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                        if (response.isSuccessful()) {
                            Endereco endereco = response.body();
                            if (endereco != null) {
                                etLogradouro.setText(endereco.getLogradouro());
                                etComplemento.setText(endereco.getComplemento());
                                etBairro.setText(endereco.getBairro());
                                etCidade.setText(endereco.getLocalidade());
                                etUf.setText(endereco.getUf());
                            } else {
                                Toast.makeText(CadastroActivity.this, "Endereço não encontrado!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CadastroActivity.this, "CEP não encontrado!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Endereco> call, Throwable t) {
                        Toast.makeText(CadastroActivity.this, "Erro ao buscar CEP!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Salvar aluno
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {

                    String complementoValue = etComplemento.getText().toString();
                    if (complementoValue.isEmpty()) {
                        complementoValue = "Não informado";
                    }

                    Aluno aluno = new Aluno(
                            Integer.parseInt(etRa.getText().toString()),
                            etNome.getText().toString(),
                            etCep.getText().toString(),
                            etLogradouro.getText().toString(),
                            complementoValue,
                            etBairro.getText().toString(),
                            etCidade.getText().toString(),
                            etUf.getText().toString()
                    );

                    new AlertDialog.Builder(CadastroActivity.this)
                            .setTitle("Aluno Criado")
                            .setMessage(aluno.toString()) // Exibe o conteúdo do objeto
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss()) // Botão para fechar
                            .setCancelable(false) // Impede fechar clicando fora do diálogo
                            .show();

                    mockApiService.salvarAluno(aluno).enqueue(new Callback<Aluno>() {
                        @Override
                        public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Aluno alunoSalvo = response.body();
                                Toast.makeText(CadastroActivity.this,
                                        "Aluno salvo com sucesso! ID: " + alunoSalvo.getId(),
                                        Toast.LENGTH_SHORT).show();
                                limparCampos();
                            } else {
                                // Log para entender a resposta do servidor
                                int statusCode = response.code();
                                String errorMessage = response.message();
                                Toast.makeText(CadastroActivity.this,
                                        "Erro ao salvar aluno! Código: " + statusCode + " Mensagem: " + errorMessage,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Aluno> call, Throwable t) {
                            // Log para erro de conexão ou outros problemas
                            t.printStackTrace();
                            Toast.makeText(CadastroActivity.this,
                                    "Erro na conexão ao salvar aluno: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    private boolean validarCampos() {
        if (etRa.getText().toString().isEmpty() || etNome.getText().toString().isEmpty() || etCep.getText().toString().isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limparCampos() {
        etRa.setText("");
        etNome.setText("");
        etCep.setText("");
        etLogradouro.setText("");
        etComplemento.setText("");
        etBairro.setText("");
        etCidade.setText("");
        etUf.setText("");
    }
}
