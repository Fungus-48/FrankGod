package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.Livro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Janela para listar livros disponíveis para empréstimo.
 */
public class JanelaListarLivrosDisponiveis extends JFrame {
    private BibliotecaController controller;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public JanelaListarLivrosDisponiveis(BibliotecaController controller) {
        this.controller = controller;
        inicializarJanela();
    }

    private void inicializarJanela() {
        setTitle("Livros Disponíveis");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de tabela
        JPanel painelTabela = criarPainelTabela();
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
        atualizarTabela();
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Livros Disponíveis para Empréstimo"));

        String[] colunas = {"ID", "Título", "Tema", "Autor", "ISBN", "Data Pub.", "Qtd. Disponível"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });
        painel.add(btnAtualizar);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        painel.add(btnFechar);

        return painel;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Livro> livros = controller.listarLivrosDisponiveis();
        
        for (Livro livro : livros) {
            Object[] linha = {
                livro.getId(),
                livro.getTitulo(),
                livro.getTema(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getDataPublicacao(),
                livro.getQuantidadeDisponivel()
            };
            modeloTabela.addRow(linha);
        }
    }
}
