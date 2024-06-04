package com.transacaofinanceira.infrastructure.repository;

import com.transacaofinanceira.domain.ContaSaldo;

public interface ContaRepository {
    ContaSaldo findById(long conta);
    void atualizar(ContaSaldo contaSaldo);
}