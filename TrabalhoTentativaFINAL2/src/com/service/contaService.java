package com.service;


import com.model.Conta;
import com.model.contaCorrente;
import com.model.contaPoupanca;
import com.model.Pessoa;

public class contaService {
    private Pessoa pessoa;
    private contaCorrente contaCorrente;
    private contaPoupanca contaPoupanca;

    public contaService() {
        this.pessoa = new Pessoa(null, null);
        this.contaCorrente = new contaCorrente(null, null, 0, 0);
        this.contaPoupanca = new contaPoupanca(null, null, 0, 0);
    }

    public void cadastrarConta(Conta conta) {
        if (conta instanceof com.model.contaCorrente) {
            contaCorrente.cadastrarConta((com.model.contaCorrente) conta);
        } else if (conta instanceof com.model.contaPoupanca) {
            contaPoupanca.cadastrarConta((contaPoupanca) conta);
        }
    }



    public void cadastrarPessoa(Pessoa pessoa) {
        pessoa.cadastrarPessoa(pessoa);
    }
}