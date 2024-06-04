package com.transacaofinanceira.application.service;

import com.transacaofinanceira.application.exceptions.SaldoInsuficienteException;
import com.transacaofinanceira.domain.Transacao;

public interface TransacaoService {
    void executarTransacao(Transacao transacao) throws SaldoInsuficienteException;
}