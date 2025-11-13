package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Janela para fazer empréstimos de livros.
 */
public class JanelaEmprestimo extends JFrame {
    private BibliotecaController controller;
    private JComboBox<String> combUsuarios, combLivros;
    private JLabel lblInfo;

    public JanelaEmprestimo(BibliotecaController controller) {
        this.controller = controller;
        inicializarJanela();
    }

    private void inicializarJanela() {
        setTitle("Fazer Empréstimo");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de seleção
        JPanel painelSelecao = criarPainelSelecao();
        painelPrincipal.add(painelSelecao, BorderLayout.CENTER);

        // Painel de informações
        lblInfo = new JLabel("Selecione um usuário e um livro");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        painelPrincipal.add(lblInfo, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
        carregarDados();
    }

    private JPanel criarPainelSelecao() {
        JPanel painel = new JPanel(new GridLayout(2, 2, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Empréstimo"));

        painel.add(new JLabel("Usuário:"));
        combUsuarios = new JComboBox<>();
        painel.add(combUsuarios);

        painel.add(new JLabel("Livro:"));
        combLivros = new JComboBox<>();
        painel.add(combLivros);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnEmprestar = new JButton("Fazer Empréstimo");
        btnEmprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerEmprestimo();
            }
        });
        painel.add(btnEmprestar);

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

    private void carregarDados() {
        // Carregar usuários
        List<Usuario> usuarios = controller.listarUsuarios();
        for (Usuario usuario : usuarios) {
            combUsuarios.addItem(usuario.getId() + " - " + usuario.getNome());
        }

        // Carregar livros disponíveis
        List<Livro> livros = controller.listarLivrosDisponiveis();
        for (Livro livro : livros) {
            combLivros.addItem(livro.getId() + " - " + livro.getTitulo() + " (" + livro.getQuantidadeDisponivel() + ")");
        }
    }

    private void fazerEmprestimo() {
        try {
            if (combUsuarios.getSelectedIndex() < 0 || combLivros.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário e um livro!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Extrair IDs
            String usuarioStr = (String) combUsuarios.getSelectedItem();
            Long usuarioId = Long.parseLong(usuarioStr.split(" - ")[0]);
            Usuario usuario = controller.obterUsuario(usuarioId);

            String livroStr = (String) combLivros.getSelectedItem();
            Long livroId = Long.parseLong(livroStr.split(" - ")[0]);
            Livro livro = controller.obterLivro(livroId);

            // Fazer empréstimo
            Emprestimo emprestimo = controller.fazerEmprestimo(usuario, livro);

            JOptionPane.showMessageDialog(this, 
                    "Empréstimo realizado com sucesso!\n\n" +
                    "Usuário: " + usuario.getNome() + "\n" +
                    "Livro: " + livro.getTitulo() + "\n" +
                    "Data de devolução: " + emprestimo.getDataDevoluçaoPrevista(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Recarregar dados
            combLivros.removeAllItems();
            List<Livro> livrosDisponiveis = controller.listarLivrosDisponiveis();
            for (Livro l : livrosDisponiveis) {
                combLivros.addItem(l.getId() + " - " + l.getTitulo() + " (" + l.getQuantidadeDisponivel() + ")");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
