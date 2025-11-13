package com.biblioteca.repository;

import com.biblioteca.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Classe base genérica para operações CRUD com Hibernate.
 * Fornece métodos comuns para todas as entidades.
 *
 * @param <T> Tipo da entidade
 * @param <ID> Tipo do identificador
 */
public abstract class Repository<T, ID> {
    protected Class<T> entityClass;

    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Salva uma nova entidade no banco de dados.
     *
     * @param entity Entidade a ser salva
     * @return ID da entidade salva
     */
    public ID save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        ID id = null;

        try {
            transaction = session.beginTransaction();
            id = (ID) session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return id;
    }

    /**
     * Atualiza uma entidade existente.
     *
     * @param entity Entidade a ser atualizada
     */
    public void update(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Deleta uma entidade do banco de dados.
     *
     * @param entity Entidade a ser deletada
     */
    public void delete(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Busca uma entidade pelo ID.
     *
     * @param id ID da entidade
     * @return Entidade encontrada ou null
     */
    public T findById(ID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = null;

        try {
            entity = session.get(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entity;
    }

    /**
     * Busca todas as entidades.
     *
     * @return Lista de todas as entidades
     */
    public List<T> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> entities = null;

        try {
            entities = session.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entities;
    }
}
