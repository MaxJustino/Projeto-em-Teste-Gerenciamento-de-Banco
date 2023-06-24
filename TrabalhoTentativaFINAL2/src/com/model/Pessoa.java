package com.model;

public class Pessoa {
    private String nome; // Atributo que armazena o nome da pessoa
    private String cpf; // Atributo que armazena o CPF da pessoa

    public Pessoa(String nome, String cpf) {
        this.nome = nome; // Inicializa o atributo nome com o valor passado como par창metro
        this.cpf = cpf; // Inicializa o atributo cpf com o valor passado como par창metro
    }

    public Pessoa(int numero, String titular, String cpf2, int senha, int saldo, int tipoConta) {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
        return nome; // Retorna o valor do atributo nome
    }

    public void setNome(String nome) {
        this.nome = nome; // Define o valor do atributo nome com o valor passado como par창metro
    }

    public String getCpf() {
        return cpf; // Retorna o valor do atributo cpf
    }

    public void setCpf(String cpf) {
        this.cpf = cpf; // Define o valor do atributo cpf com o valor passado como par창metro
    }

	public void cadastrarPessoa(Pessoa pessoa) {
		// TODO Auto-generated method stub
		
	}
}