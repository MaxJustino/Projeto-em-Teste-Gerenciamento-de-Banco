package com.model;

public class contaPoupanca extends Conta {
    private static float taxa = 0.005f;

    // Construtor da classe para inicializar os atributos da conta poupança
    public contaPoupanca(String numero, String titular, double saldo, float taxa) {
        super(numero, titular, saldo);
        contaPoupanca.taxa = taxa;
    }

    // Método para realizar um depósito na conta poupança
    public static void realizaDeposito(float valor) {
        float valorComTaxa = valor / (1 + taxa);
        saldo += valorComTaxa;
        System.out.println("Depósito realizado com sucesso.");
        taxa += 0.005f;
        System.out.println("Taxa de juros atualizada: " + taxa);
    }
}