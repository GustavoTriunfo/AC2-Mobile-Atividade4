package com.example.myapplication.model;

public class Endereco {
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // localidade é equivalente a "cidade"
    private String uf; // Unidade Federativa (estado)

    // Getter e Setter para logradouro
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    // Getter e Setter para complemento
    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    // Getter e Setter para bairro
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    // Getter e Setter para localidade (cidade)
    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    // Getter e Setter para uf (estado)
    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
