package com.model;


public class Conta {
    private static String numero;
    private String titular;
    private String senha;
    protected static float saldo;

    public Conta(String numero, String titular, double saldo) {
        Conta.numero = numero;
        this.titular = titular;
        Conta.saldo = (float) saldo;
    }

    public Conta(String titular2, String cpf2, int numero2, int senha2) {
		// TODO Auto-generated constructor stub
	}

	public static String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        Conta.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public static float getSaldo() {
        return saldo;
    }

    public static void setSaldo(double saldo) {
        Conta.saldo = (float) saldo;
    }

    public void consultarSaldo() {
        System.out.println("Saldo da conta " + numero + ": R$" + saldo);
    }

    public static void sacar(float valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$" + valor + " realizado na conta " + numero);
        } else {
            System.out.println("Saldo insuficiente na conta " + numero);
        }
    }

    public void depositar(double valor) {
    	
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

   

    public static Conta get(String cpf) {
        // TODO: Implemente o cÃ³digo para obter a conta do banco de dados com base no CPF
        return null;
    }

	public void sacar(double valor) {
		// TODO Auto-generated method stub
		
	}
}


