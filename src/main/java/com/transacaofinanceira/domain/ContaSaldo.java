package com.transacaofinanceira.domain;

public class ContaSaldo {
    private long conta;
    private double saldo;

    public ContaSaldo(long conta, double saldo) {
        this.conta = conta;
        this.saldo = saldo;
    }

    public long getConta() {
        return conta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void debitar(double valor) {
        this.saldo -= valor;
    }

    public void creditar(double valor) {
        this.saldo += valor;
    }
}