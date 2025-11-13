package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Janela principal do sistema de biblioteca.
 * Apresenta um menu com opções para acessar as funcionalidades do sistema.
 */
public class JanelaPrincipal extends JFrame {
    private BibliotecaController controller;
    private JPanel painelPrincipal;

    public JanelaPrincipal() {
        this.controller = new BibliotecaController();
        inicializarJanela();
    }

    /**
     * Inicializa a janela principal com componentes.
     */
    private void inicializarJanela() {
        setTitle("Sistema de Gestão de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBackground(new Color(240, 240, 240));

        // Painel superior com título
        JPanel painelTitulo = criarPainelTitulo();
        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);

        // Painel central com botões
        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        // Painel inferior com informações
        JPanel painelRodape = criarPainelRodape();
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    /**
     * Cria o painel superior com título.
     */
    private JPanel criarPainelTitulo() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(51, 102, 153));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel titulo = new JLabel("Sistema de Gestão de Biblioteca");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        painel.add(titulo);
        return painel;
    }

    /**
     * Cria o painel central com botões de opções.
     */
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painel.setBackground(new Color(240, 240, 240));

        // Botão CRUD Livro
        JButton btnCrudLivro = new JButton("CRUD Livro");
        btnCrudLivro.setFont(new Font("Arial", Font.BOLD, 14));
        btnCrudLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaCrudLivro();
            }
        });
        painel.add(btnCrudLivro);

        // Botão CRUD Usuário
        JButton btnCrudUsuario = new JButton("CRUD Usuário");
        btnCrudUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        btnCrudUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaCrudUsuario();
            }
        });
        painel.add(btnCrudUsuario);

        // Botão Fazer Empréstimo
        JButton btnEmprestimo = new JButton("Fazer Empréstimo");
        btnEmprestimo.setFont(new Font("Arial", Font.BOLD, 14));
        btnEmprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaEmprestimo();
            }
        });
        painel.add(btnEmprestimo);

        // Botão Registrar Devolução
        JButton btnDevolucao = new JButton("Registrar Devolução");
        btnDevolucao.setFont(new Font("Arial", Font.BOLD, 14));
        btnDevolucao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaDevolucao();
            }
        });
        painel.add(btnDevolucao);

        // Botão Listar Livros Disponíveis
        JButton btnListarDisponiveis = new JButton("Listar Livros Disponíveis");
        btnListarDisponiveis.setFont(new Font("Arial", Font.BOLD, 14));
        btnListarDisponiveis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaListarLivrosDisponiveis();
            }
        });
        painel.add(btnListarDisponiveis);

        // Botão Sair
        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 14));
        btnSair.setBackground(new Color(200, 50, 50));
        btnSair.setForeground(Color.WHITE);
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        painel.add(btnSair);

        return painel;
    }

    /**
     * Cria o painel inferior com informações.
     */
    private JPanel criarPainelRodape() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(200, 200, 200));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel info = new JLabel("Limite de empréstimos: " + BibliotecaController.getLimiteEmprestimos() + 
                                 " | Dias de empréstimo: " + BibliotecaController.getDiasEmprestimo() + 
                                 " | Multa por dia: R$" + BibliotecaController.getMultaPorDia());
        info.setFont(new Font("Arial", Font.PLAIN, 11));

        painel.add(info);
        return painel;
    }

    private void abrirJanelaCrudLivro() {
        new JanelaCrudLivro(controller).setVisible(true);
    }

    private void abrirJanelaCrudUsuario() {
        new JanelaCrudUsuario(controller).setVisible(true);
    }

    private void abrirJanelaEmprestimo() {
        new JanelaEmprestimo(controller).setVisible(true);
    }

    private void abrirJanelaDevolucao() {
        new JanelaDevolucao(controller).setVisible(true);
    }

    private void abrirJanelaListarLivrosDisponiveis() {
        new JanelaListarLivrosDisponiveis(controller).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });
    }
}
