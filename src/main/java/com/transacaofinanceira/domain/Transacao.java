package com.transacaofinanceira.domain;

public class Transacao {
    private int correlationId;
    private String datetime;
    private long contaOrigem;
    private long contaDestino;
    private double valor;

    public Transacao(int correlationId, String datetime, long contaOrigem, long contaDestino, double valor) {
        this.correlationId = correlationId;
        this.datetime = datetime;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public String getDatetime() {
        return datetime;
    }

    public long getContaOrigem() {
        return contaOrigem;
    }

    public long getContaDestino() {
        return contaDestino;
    }

    public double getValor() {
        return valor;
    }
}