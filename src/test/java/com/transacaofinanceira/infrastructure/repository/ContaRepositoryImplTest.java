package com.transacaofinanceira.infrastructure.repository;

import com.transacaofinanceira.domain.ContaSaldo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

public class ContaRepositoryImplTest {

    private ContaRepository contaRepository;

    @BeforeEach
    public void setUp() {
        Map<Long, ContaSaldo> saldos = new HashMap<>();
        saldos.put(938485762L, new ContaSaldo(938485762L, 180));
        saldos.put(347586970L, new ContaSaldo(347586970L, 1200));
        saldos.put(2147483649L, new ContaSaldo(2147483649L, 0));
        saldos.put(675869708L, new ContaSaldo(675869708L, 4900));
        saldos.put(238596054L, new ContaSaldo(238596054L, 478));
        saldos.put(573659065L, new ContaSaldo(573659065L, 787));
        saldos.put(210385733L, new ContaSaldo(210385733L, 10));
        saldos.put(674038564L, new ContaSaldo(674038564L, 400));
        saldos.put(563856300L, new ContaSaldo(563856300L, 1200));

        contaRepository = new ContaRepositoryImpl(saldos);
    }

    @Test
    public void testFindByIdContaExistente() {
        ContaSaldo contaSaldo = contaRepository.findById(938485762L);
        assertEquals(180, contaSaldo.getSaldo());
    }

    @Test
    public void testFindByIdContaInexistente() {
        ContaSaldo contaSaldo = contaRepository.findById(999999999L);
        assertNull(contaSaldo);
    }
}