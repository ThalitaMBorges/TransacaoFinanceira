package com.transacaofinanceira.infrastructure.repository;

import java.util.Map;

import com.transacaofinanceira.domain.ContaSaldo;

public class ContaRepositoryImpl implements ContaRepository {
    private Map<Long, ContaSaldo> saldos;

    public ContaRepositoryImpl(Map<Long, ContaSaldo> saldos) {
    	this.saldos = saldos;
        saldos.put(938485762L, new ContaSaldo(938485762L, 180));
        saldos.put(347586970L, new ContaSaldo(347586970L, 1200));
        saldos.put(2147483649L, new ContaSaldo(2147483649L, 0));
        saldos.put(675869708L, new ContaSaldo(675869708L, 4900));
        saldos.put(238596054L, new ContaSaldo(238596054L, 478));
        saldos.put(573659065L, new ContaSaldo(573659065L, 787));
        saldos.put(210385733L, new ContaSaldo(210385733L, 10));
        saldos.put(674038564L, new ContaSaldo(674038564L, 400));
        saldos.put(563856300L, new ContaSaldo(563856300L, 1200));
    }

    @Override
    public ContaSaldo findById(long conta) {
        return saldos.getOrDefault(conta, null);
    }

    @Override
    public void atualizar(ContaSaldo contaSaldo) {
        saldos.put(contaSaldo.getConta(), contaSaldo);
    }
}