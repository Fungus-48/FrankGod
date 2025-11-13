package com.biblioteca;

import com.biblioteca.view.JanelaPrincipal;
import com.biblioteca.util.HibernateUtil;

import javax.swing.*;

/**
 * Classe principal que inicia a aplicação.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Configurar look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Inicializar Hibernate
            HibernateUtil.getSessionFactory();

            // Iniciar aplicação
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new JanelaPrincipal().setVisible(true);
                }
            });

            // Adicionar hook para encerramento limpo
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    HibernateUtil.shutdown();
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                    "Erro ao iniciar a aplicação: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
