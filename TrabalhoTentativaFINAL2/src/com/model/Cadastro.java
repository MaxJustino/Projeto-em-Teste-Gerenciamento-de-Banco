package com.model;



	
public class Cadastro {

	
	Conta cadastrarPessoa(String titular, String cpf, int numero, int senha) {
	    // Lógica para criar a conta
	    Conta novaConta = new Conta(titular, cpf, numero, senha);
	    // Salve a conta no banco de dados ou realize outras operações necessárias
	    return novaConta;
	}

}