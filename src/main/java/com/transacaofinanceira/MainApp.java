package com.transacaofinanceira;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.transacaofinanceira.application.exceptions.SaldoInsuficienteException;
import com.transacaofinanceira.application.service.TransacaoService;
import com.transacaofinanceira.application.usecases.TransacaoServiceImpl;
import com.transacaofinanceira.domain.ContaSaldo;
import com.transacaofinanceira.domain.Transacao;
import com.transacaofinanceira.infrastructure.repository.ContaRepository;
import com.transacaofinanceira.infrastructure.repository.ContaRepositoryImpl;

public class MainApp {
    public static void main(String[] args) {
    	
    	Map<Long, ContaSaldo> saldos = new HashMap<>();

    	ContaRepository contaRepository = new ContaRepositoryImpl(saldos);
    	
        TransacaoService transacaoService = new TransacaoServiceImpl(contaRepository);

        List<Transacao> transacoes = Arrays.asList(
            new Transacao(1, "09/09/2023 14:15:00", 938485762L, 2147483649L, 150),
            new Transacao(2, "09/09/2023 14:15:05", 2147483649L, 210385733, 149),
            new Transacao(3, "09/09/2023 14:15:29", 347586970, 238596054, 1100),
            new Transacao(4, "09/09/2023 14:17:00", 675869708, 210385733, 5300),
            new Transacao(5, "09/09/2023 14:18:00", 238596054, 674038564, 1489),
            new Transacao(6, "09/09/2023 14:18:20", 573659065, 563856300, 49),
            new Transacao(7, "09/09/2023 14:19:00", 938485762L, 2147483649L, 44),
            new Transacao(8, "09/09/2023 14:19:01", 573659065, 675869708, 150)
        );

        for (Transacao transacao : transacoes) {
            try {
                transacaoService.executarTransacao(transacao);
            } catch (SaldoInsuficienteException e) {
                System.out.println("Erro na transação " + transacao.getCorrelationId() + ": " + e.getMessage());
            }
        }
    }
}