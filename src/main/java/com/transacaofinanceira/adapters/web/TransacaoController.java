package com.transacaofinanceira.adapters.web;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.transacaofinanceira.application.exceptions.SaldoInsuficienteException;
import com.transacaofinanceira.application.service.TransacaoService;
import com.transacaofinanceira.domain.Transacao;

public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    public void processarTransacoes(List<Transacao> transacoes) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (Transacao transacao : transacoes) {
            executorService.submit(() -> {
                try {
                    transacaoService.executarTransacao(transacao);
                } catch (SaldoInsuficienteException e) {
                    System.err.println("Erro ao processar transação: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
    }
}