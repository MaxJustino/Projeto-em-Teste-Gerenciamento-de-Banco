package com.model;



public class contaCorrente extends Conta {
    private static final float LIMITE_CHEQUE_ESPECIAL = 500.00f;
    private static double chequeEspecial;

    // Construtor da classe para inicializar os atributos da conta corrente
    public contaCorrente(String numero, String titular, double saldo, double chequeEspecial) {
        super(numero, titular, saldo);
        setChequeEspecial(chequeEspecial);
    }

    

    // Método para obter o valor do cheque especial
    public static double getChequeEspecial() {
        return chequeEspecial;
    }

    // Método para definir o valor do cheque especial
    public static void setChequeEspecial(double chequeEspecial) {
        contaCorrente.chequeEspecial = chequeEspecial;
    }

    // Método para realizar um saque na conta corrente
    @Override
    public void sacar(double valor) {
        if (valor <= (getSaldo() + chequeEspecial)) {
            setSaldo(getSaldo() - valor);
            System.out.println("Saque de R$" + valor + " realizado na conta corrente " + getNumero());
        } else {
            System.out.println("Saldo insuficiente na conta corrente " + getNumero());
        }
    }
    
    public static void sacarComChequeEspecial(double valor) {
        double saldoTotal = getSaldo() + chequeEspecial;
        if (valor <= saldoTotal || valor <= LIMITE_CHEQUE_ESPECIAL) {
            if (valor > getSaldo()) {
                double valorSaque = valor - getSaldo();
                setSaldo(0);
                chequeEspecial -= valorSaque;
                System.out.println("Saque de R$" + valor + " realizado na conta corrente " + getNumero() +
                        " utilizando o cheque especial.");
            } else {
                setSaldo(getSaldo() - valor);
                System.out.println("Saque de R$" + valor + " realizado na conta corrente " + getNumero());
            }
        } else {
            System.out.println("Valor do saque excede o saldo e o cheque especial disponíveis.");
        }
    }

    
    // Método para depositar um valor na conta corrente
    public void depositar(double valor) {
        setSaldo(getSaldo() + valor);
        System.out.println("Depósito de R$" + valor + " realizado na conta corrente " + getNumero());
    }



	public String getCpfTitular() {
		// TODO Auto-generated method stub
		return null;
	}



	


public void realizaSaque(float valor) {
        if (valor > getSaldo()) {
            System.out.println("Saldo insuficiente.");
        }
        else {
            saldo -= valor;
            System.out.println("Saque realizado com sucesso.");
        }
    }



public void cadastrarConta(contaCorrente conta) {
	// TODO Auto-generated method stub
	
}
}
