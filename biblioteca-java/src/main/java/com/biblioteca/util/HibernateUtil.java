package com.biblioteca.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Classe utilitária para gerenciar a SessionFactory do Hibernate.
 * Implementa o padrão Singleton para garantir uma única instância.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    /**
     * Obtém a SessionFactory do Hibernate.
     * Se não existir, cria uma nova instância.
     *
     * @return SessionFactory configurada
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .configure()
                        .build();

                sessionFactory = new MetadataSources(registry)
                        .buildMetadata()
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao criar SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    /**
     * Fecha a SessionFactory.
     * Deve ser chamado ao encerrar a aplicação.
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
