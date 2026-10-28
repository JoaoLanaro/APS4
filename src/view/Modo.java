package view;

/**
 * VIEW (apoio) - Modo de funcionamento dos paineis das abas Atualizar e Deletar.
 * O mesmo painel (JogadorPanel, TimePanel, VinculoPanel) serve as duas abas:
 *  - ATUALIZAR: campos editaveis e botao "Atualizar";
 *  - REMOVER:   campos so de leitura e botao "Remover" (com confirmacao).
 */
public enum Modo { ATUALIZAR, REMOVER }