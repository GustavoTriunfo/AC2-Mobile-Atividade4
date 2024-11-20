package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.AlunoAdapter;
import com.example.myapplication.api.MockApiService;
import com.example.myapplication.model.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListagemActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlunoAdapter adapter;
    private TextView tvSemRegistros;  // TextView para a mensagem

    private MockApiService mockApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvSemRegistros = findViewById(R.id.tvSemRegistros);  // Inicializa a TextView

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://673d08f74db5a341d833cd5a.mockapi.io/alunos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mockApiService = retrofit.create(MockApiService.class);

        carregarAlunos();
    }

    private void carregarAlunos() {
        mockApiService.getAlunos().enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful()) {
                    List<Aluno> alunos = response.body();

                    if (alunos != null && !alunos.isEmpty()) {
                        // Se há alunos, configura o RecyclerView
                        adapter = new AlunoAdapter(alunos);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);  // Exibe o RecyclerView
                        tvSemRegistros.setVisibility(View.GONE);  // Esconde a mensagem
                    } else {
                        // Se não há alunos, exibe a mensagem "Sem registros"
                        recyclerView.setVisibility(View.GONE);  // Esconde o RecyclerView
                        tvSemRegistros.setVisibility(View.VISIBLE);  // Exibe a mensagem
                    }
                } else {
                    Toast.makeText(ListagemActivity.this, "Erro ao carregar alunos!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListagemActivity.this, "Falha na conexão!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
