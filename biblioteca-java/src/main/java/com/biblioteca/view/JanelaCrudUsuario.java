package com.biblioteca.view;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Janela para gerenciar CRUD de Usuários.
 */
public class JanelaCrudUsuario extends JFrame {
    private BibliotecaController controller;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtNome, txtCelular, txtEmail;
    private JComboBox<String> combSexo;

    public JanelaCrudUsuario(BibliotecaController controller) {
        this.controller = controller;
        inicializarJanela();
    }

    private void inicializarJanela() {
        setTitle("CRUD de Usuários");
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
        JPanel painel = new JPanel(new GridLayout(1, 8, 5, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Usuário"));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("Sexo:"));
        combSexo = new JComboBox<>(new String[]{"M", "F"});
        painel.add(combSexo);

        painel.add(new JLabel("Celular:"));
        txtCelular = new JTextField();
        painel.add(txtCelular);

        painel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));

        String[] colunas = {"ID", "Nome", "Sexo", "Celular", "Email"};
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
                adicionarUsuario();
            }
        });
        painel.add(btnAdicionar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarUsuario();
            }
        });
        painel.add(btnAtualizar);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarUsuario();
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

    private void adicionarUsuario() {
        try {
            String nome = txtNome.getText().trim();
            String sexo = (String) combSexo.getSelectedItem();
            String celular = txtCelular.getText().trim();
            String email = txtEmail.getText().trim();

            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha nome e email!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.cadastrarUsuario(nome, sexo, celular, email);
            JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarUsuario() {
        try {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(modeloTabela.getValueAt(linha, 0).toString());
            Usuario usuario = controller.obterUsuario(id);

            usuario.setNome(txtNome.getText().trim());
            usuario.setSexo((String) combSexo.getSelectedItem());
            usuario.setCelular(txtCelular.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());

            controller.atualizarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarUsuario() {
        try {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(modeloTabela.getValueAt(linha, 0).toString());
            Usuario usuario = controller.obterUsuario(id);

            int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este usuário?", 
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                controller.deletarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuário deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
        combSexo.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Usuario> usuarios = controller.listarUsuarios();
        for (Usuario usuario : usuarios) {
            Object[] linha = {
                usuario.getId(),
                usuario.getNome(),
                usuario.getSexo(),
                usuario.getCelular(),
                usuario.getEmail()
            };
            modeloTabela.addRow(linha);
        }
    }
}
