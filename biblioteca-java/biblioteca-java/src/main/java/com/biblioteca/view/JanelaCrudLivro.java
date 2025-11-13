package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.Livro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * Janela para gerenciar CRUD de Livros.
 */
public class JanelaCrudLivro extends JFrame {
    private BibliotecaController controller;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtTitulo, txtTema, txtAutor, txtIsbn;
    private JSpinner spinnerData, spinnerQuantidade;

    public JanelaCrudLivro(BibliotecaController controller) {
        this.controller = controller;
        inicializarJanela();
    }

    private void inicializarJanela() {
        setTitle("CRUD de Livros");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de entrada de dados
        JPanel painelEntrada = criarPainelEntrada();
        painelPrincipal.add(painelEntrada, BorderLayout.NORTH);

        // Painel de tabela
        JPanel painelTabela = criarPainelTabela();
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
        atualizarTabela();
    }

    private JPanel criarPainelEntrada() {
        JPanel painel = new JPanel(new GridLayout(2, 4, 5, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Livro"));

        painel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painel.add(txtTitulo);

        painel.add(new JLabel("Tema:"));
        txtTema = new JTextField();
        painel.add(txtTema);

        painel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        painel.add(txtAutor);

        painel.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        painel.add(txtIsbn);

        painel.add(new JLabel("Data Publicação:"));
        spinnerData = new JSpinner(new SpinnerDateModel());
        painel.add(spinnerData);

        painel.add(new JLabel("Quantidade:"));
        spinnerQuantidade = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
        painel.add(spinnerQuantidade);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Livros Cadastrados"));

        String[] colunas = {"ID", "Título", "Tema", "Autor", "ISBN", "Data Pub.", "Qtd. Total", "Qtd. Disp."};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarLivro();
            }
        });
        painel.add(btnAdicionar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarLivro();
            }
        });
        painel.add(btnAtualizar);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarLivro();
            }
        });
        painel.add(btnDeletar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        painel.add(btnLimpar);

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

    private void adicionarLivro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String tema = txtTema.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();
            LocalDate data = LocalDate.now();
            Integer quantidade = (Integer) spinnerQuantidade.getValue();

            if (titulo.isEmpty() || tema.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.cadastrarLivro(titulo, tema, autor, isbn, data, quantidade);
            JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarLivro() {
        try {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um livro!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(modeloTabela.getValueAt(linha, 0).toString());
            Livro livro = controller.obterLivro(id);

            livro.setTitulo(txtTitulo.getText().trim());
            livro.setTema(txtTema.getText().trim());
            livro.setAutor(txtAutor.getText().trim());
            livro.setIsbn(txtIsbn.getText().trim());
            livro.setQuantidadeTotal((Integer) spinnerQuantidade.getValue());

            controller.atualizarLivro(livro);
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarLivro() {
        try {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um livro!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(modeloTabela.getValueAt(linha, 0).toString());
            Livro livro = controller.obterLivro(id);

            int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este livro?", 
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                controller.deletarLivro(livro);
                JOptionPane.showMessageDialog(this, "Livro deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtTema.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        spinnerQuantidade.setValue(1);
        tabela.clearSelection();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Livro> livros = controller.listarLivros();
        for (Livro livro : livros) {
            Object[] linha = {
                livro.getId(),
                livro.getTitulo(),
                livro.getTema(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getDataPublicacao(),
                livro.getQuantidadeTotal(),
                livro.getQuantidadeDisponivel()
            };
            modeloTabela.addRow(linha);
        }
    }
}
