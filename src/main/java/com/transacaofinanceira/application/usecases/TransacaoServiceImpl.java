package com.transacaofinanceira.application.usecases;

import com.transacaofinanceira.application.exceptions.SaldoInsuficienteException;
import com.transacaofinanceira.application.service.TransacaoService;
import com.transacaofinanceira.domain.ContaSaldo;
import com.transacaofinanceira.domain.Transacao;
import com.transacaofinanceira.infrastructure.repository.ContaRepository;

public class TransacaoServiceImpl implements TransacaoService {
    private final ContaRepository contaRepository;

    public TransacaoServiceImpl(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public void executarTransacao(Transacao transacao) throws SaldoInsuficienteException {
        ContaSaldo contaOrigem = contaRepository.findById(transacao.getContaOrigem());
        ContaSaldo contaDestino = contaRepository.findById(transacao.getContaDestino());

        if (contaOrigem == null || contaDestino == null) {
            throw new IllegalArgumentException("Conta origem ou destino inválida");
        }

        if (contaOrigem.getSaldo() < transacao.getValor()) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta de origem");
        }

        contaOrigem.debitar(transacao.getValor());
        contaDestino.creditar(transacao.getValor());

        contaRepository.atualizar(contaOrigem);
        contaRepository.atualizar(contaDestino);

        System.out.println("Transação " + transacao.getCorrelationId() + " executada com sucesso. " +
                "Novo saldo da conta de origem: " + contaOrigem.getSaldo() + ", " +
                "Novo saldo da conta de destino: " + contaDestino.getSaldo());
    }
}