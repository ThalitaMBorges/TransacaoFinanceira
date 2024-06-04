package com.transacaofinanceira.application.usecases;

import com.transacaofinanceira.application.exceptions.SaldoInsuficienteException;
import com.transacaofinanceira.application.usecases.TransacaoServiceImpl;
import com.transacaofinanceira.domain.ContaSaldo;
import com.transacaofinanceira.domain.Transacao;
import com.transacaofinanceira.infrastructure.repository.ContaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TransacaoServiceImplTest {

    @Mock
    private ContaRepository contaRepositoryMock;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecutarTransacao_SaldoSuficiente() throws SaldoInsuficienteException {
        long contaOrigem = 938485762L;
        long contaDestino = 2147483649L;
        double valor = 150.0;

        ContaSaldo origem = new ContaSaldo(contaOrigem, 300.0);
        ContaSaldo destino = new ContaSaldo(contaDestino, 0.0);

        Transacao transacao = new Transacao(1, "09/09/2023 14:15:00", contaOrigem, contaDestino, valor);

        when(contaRepositoryMock.findById(contaOrigem)).thenReturn(origem);
        when(contaRepositoryMock.findById(contaDestino)).thenReturn(destino);

        transacaoService.executarTransacao(transacao);

        assertEquals(150.0, origem.getSaldo());
        assertEquals(150.0, destino.getSaldo());

        verify(contaRepositoryMock, times(1)).atualizar(origem);
        verify(contaRepositoryMock, times(1)).atualizar(destino);
    }

    @Test
    public void testExecutarTransacao_SaldoInsuficiente() {
        long contaOrigem = 938485762L;
        long contaDestino = 2147483649L;
        double valor = 400.0;

        ContaSaldo origem = new ContaSaldo(contaOrigem, 300.0);
        ContaSaldo destino = new ContaSaldo(contaDestino, 0.0);

        Transacao transacao = new Transacao(2, "09/09/2023 14:15:05", contaOrigem, contaDestino, valor);

        when(contaRepositoryMock.findById(contaOrigem)).thenReturn(origem);
        when(contaRepositoryMock.findById(contaDestino)).thenReturn(destino);

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class,
                () -> transacaoService.executarTransacao(transacao));

        assertEquals("Saldo insuficiente na conta de origem", exception.getMessage());

        verify(contaRepositoryMock, never()).atualizar(origem);
        verify(contaRepositoryMock, never()).atualizar(destino);
    }

    @Test
    public void testExecutarTransacao_ContaInvalida() {
        long contaOrigem = 938485762L;
        long contaDestino = 2147483649L;
        double valor = 150.0;

        Transacao transacao = new Transacao(3, "09/09/2023 14:15:29", contaOrigem, contaDestino, valor);

        when(contaRepositoryMock.findById(contaOrigem)).thenReturn(null);
        when(contaRepositoryMock.findById(contaDestino)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transacaoService.executarTransacao(transacao));

        assertEquals("Conta origem ou destino inválida", exception.getMessage());

        verify(contaRepositoryMock, never()).atualizar(any());
    }

}
