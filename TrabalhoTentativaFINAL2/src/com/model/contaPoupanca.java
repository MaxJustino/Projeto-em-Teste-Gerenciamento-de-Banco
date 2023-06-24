package com.model;

public class contaPoupanca extends Conta {
    private float taxa;

    // Construtor da classe para inicializar os atributos da conta poupança
    public contaPoupanca(String numero, String titular, double saldo, float taxa) {
        super(numero, titular, saldo);
        this.taxa = taxa;
    }

    // Método para realizar um depósito na conta poupança
    public void realizaDeposito(float valor) {
        float valorComTaxa = valor / (1 + taxa);
        double novoSaldo = getSaldo() + valorComTaxa;
        setSaldo(novoSaldo);
        System.out.println("Depósito realizado com sucesso.");
        taxa += 0.005f;
        System.out.println("Taxa de juros atualizada: " + taxa);
    }

	public double getTaxa() {
		// TODO Auto-generated method stub
		return 0;
	}

    // Outros métodos e funcionalidades da classe
}
