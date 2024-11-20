package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Aluno;
import com.example.myapplication.R;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> alunos;

    public AlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public AlunoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla o layout do item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlunoViewHolder holder, int position) {
        // Pega o aluno da lista
        Aluno aluno = alunos.get(position);

        // Preenche as TextViews com os dados do aluno
        holder.tvRa.setText("RA: " + aluno.getRa());
        holder.tvNome.setText("Nome: " + aluno.getNome());
        holder.tvEndereco.setText("Endereço: " + aluno.getLogradouro() + ", " + aluno.getBairro() + " - " + aluno.getCidade());

        // Preenche o campo de complemento (caso esteja informado)
        if (aluno.getComplemento() != null && !aluno.getComplemento().isEmpty()) {
            holder.tvComplemento.setText("Complemento: " + aluno.getComplemento());
        } else {
            holder.tvComplemento.setText("Complemento: Não informado");
        }
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {

        TextView tvRa, tvNome, tvEndereco, tvComplemento;

        public AlunoViewHolder(View itemView) {
            super(itemView);
            tvRa = itemView.findViewById(R.id.tvRa);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvEndereco = itemView.findViewById(R.id.tvEndereco);
            tvComplemento = itemView.findViewById(R.id.tvComplemento);
        }
    }
}

