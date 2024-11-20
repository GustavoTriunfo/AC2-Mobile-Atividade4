package com.example.myapplication.api;

import com.example.myapplication.model.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MockApiService {
    @POST("alunos")
    Call<Aluno> salvarAluno(@Body Aluno aluno);

    @GET("alunos")
    Call<List<Aluno>> getAlunos();
}
