package com.biblioteca.repository;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Usuario;
import com.biblioteca.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Repository específico para a entidade Emprestimo.
 * Fornece métodos de persistência customizados.
 */
public class EmprestimoRepository extends Repository<Emprestimo, Long> {

    public EmprestimoRepository() {
        super(Emprestimo.class);
    }

    /**
     * Busca empréstimos ativos de um usuário.
     *
     * @param usuario Usuário
     * @return Lista de empréstimos ativos
     */
    public List<Emprestimo> findEmprestimosAtivos(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Emprestimo> emprestimos = null;

        try {
            emprestimos = session.createQuery(
                    "FROM Emprestimo WHERE usuario.id = :usuarioId AND ativo = true", Emprestimo.class)
                    .setParameter("usuarioId", usuario.getId())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return emprestimos;
    }

    /**
     * Conta o número de empréstimos ativos de um usuário.
     *
     * @param usuario Usuário
     * @return Número de empréstimos ativos
     */
    public Long countEmprestimosAtivos(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try {
            count = session.createQuery(
                    "SELECT COUNT(*) FROM Emprestimo WHERE usuario.id = :usuarioId AND ativo = true", Long.class)
                    .setParameter("usuarioId", usuario.getId())
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return count != null ? count : 0L;
    }

    /**
     * Busca todos os empréstimos de um usuário.
     *
     * @param usuario Usuário
     * @return Lista de empréstimos
     */
    public List<Emprestimo> findByUsuario(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Emprestimo> emprestimos = null;

        try {
            emprestimos = session.createQuery(
                    "FROM Emprestimo WHERE usuario.id = :usuarioId", Emprestimo.class)
                    .setParameter("usuarioId", usuario.getId())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return emprestimos;
    }

    /**
     * Busca empréstimos atrasados (data de devolução prevista passou).
     *
     * @return Lista de empréstimos atrasados
     */
    public List<Emprestimo> findAtrasados() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Emprestimo> emprestimos = null;

        try {
            emprestimos = session.createQuery(
                    "FROM Emprestimo WHERE ativo = true AND dataDevoluçaoPrevista < CURRENT_DATE", Emprestimo.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return emprestimos;
    }

    /**
     * Busca empréstimos inativos (devolvidos).
     *
     * @return Lista de empréstimos inativos
     */
    public List<Emprestimo> findInativos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Emprestimo> emprestimos = null;

        try {
            emprestimos = session.createQuery(
                    "FROM Emprestimo WHERE ativo = false", Emprestimo.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return emprestimos;
    }
}
