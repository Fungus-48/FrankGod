package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import com.biblioteca.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Repository específico para a entidade Livro.
 * Fornece métodos de persistência customizados.
 */
public class LivroRepository extends Repository<Livro, Long> {

    public LivroRepository() {
        super(Livro.class);
    }

    /**
     * Busca um livro pelo ISBN.
     *
     * @param isbn ISBN do livro
     * @return Livro encontrado ou null
     */
    public Livro findByIsbn(String isbn) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Livro livro = null;

        try {
            livro = session.createQuery(
                    "FROM Livro WHERE isbn = :isbn", Livro.class)
                    .setParameter("isbn", isbn)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return livro;
    }

    /**
     * Busca livros por título (busca parcial).
     *
     * @param titulo Título do livro
     * @return Lista de livros encontrados
     */
    public List<Livro> findByTitulo(String titulo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Livro> livros = null;

        try {
            livros = session.createQuery(
                    "FROM Livro WHERE titulo LIKE :titulo", Livro.class)
                    .setParameter("titulo", "%" + titulo + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return livros;
    }

    /**
     * Busca livros por autor (busca parcial).
     *
     * @param autor Autor do livro
     * @return Lista de livros encontrados
     */
    public List<Livro> findByAutor(String autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Livro> livros = null;

        try {
            livros = session.createQuery(
                    "FROM Livro WHERE autor LIKE :autor", Livro.class)
                    .setParameter("autor", "%" + autor + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return livros;
    }

    /**
     * Busca livros por tema.
     *
     * @param tema Tema do livro
     * @return Lista de livros encontrados
     */
    public List<Livro> findByTema(String tema) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Livro> livros = null;

        try {
            livros = session.createQuery(
                    "FROM Livro WHERE tema = :tema", Livro.class)
                    .setParameter("tema", tema)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return livros;
    }

    /**
     * Busca livros disponíveis (quantidade > 0).
     *
     * @return Lista de livros disponíveis
     */
    public List<Livro> findDisponivel() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Livro> livros = null;

        try {
            livros = session.createQuery(
                    "FROM Livro WHERE quantidadeDisponivel > 0", Livro.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return livros;
    }
}
