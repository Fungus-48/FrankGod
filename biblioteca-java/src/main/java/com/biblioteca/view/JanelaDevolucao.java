package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Janela para registrar devoluções de livros.
 */
public class JanelaDevolucao extends JFrame {
    private BibliotecaController controller;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public JanelaDevolucao(BibliotecaController controller) {
        this.controller = controller;
        inicializarJanela();
    }

    private void inicializarJanela() {
        setTitle("Registrar Devolução");
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
        painel.setBorder(BorderFactory.createTitledBorder("Empréstimos Ativos"));

        String[] colunas = {"ID", "Usuário", "Livro", "Data Empréstimo", "Data Devolução Prevista", "Dias Restantes"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnDevolver = new JButton("Registrar Devolução");
        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDevolucao();
            }
        });
        painel.add(btnDevolver);

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

    private void registrarDevolucao() {
        try {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um empréstimo!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(modeloTabela.getValueAt(linha, 0).toString());
            Emprestimo emprestimo = controller.obterEmprestimo(id);

            if (!emprestimo.getAtivo()) {
                JOptionPane.showMessageDialog(this, "Este empréstimo já foi devolvido!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Emprestimo devolvido = controller.registrarDevolucao(emprestimo);

            String mensagem = "Devolução registrada com sucesso!\n\n" +
                    "Livro: " + devolvido.getLivro().getTitulo() + "\n" +
                    "Data de Devolução: " + devolvido.getDataDevolucao();

            if (devolvido.getMulta() > 0) {
                mensagem += "\n\nAtenção: Multa por atraso: R$ " + String.format("%.2f", devolvido.getMulta());
            }

            JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Emprestimo> emprestimos = controller.listarEmprestimos();
        
        for (Emprestimo emp : emprestimos) {
            if (emp.getAtivo()) {
                long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(
                        java.time.LocalDate.now(), 
                        emp.getDataDevoluçaoPrevista()
                );

                Object[] linha = {
                    emp.getId(),
                    emp.getUsuario().getNome(),
                    emp.getLivro().getTitulo(),
                    emp.getDataEmprestimo(),
                    emp.getDataDevoluçaoPrevista(),
                    diasRestantes + " dias"
                };
                modeloTabela.addRow(linha);
            }
        }
    }
}
