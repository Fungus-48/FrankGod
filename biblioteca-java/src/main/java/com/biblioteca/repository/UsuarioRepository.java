package com.biblioteca.repository;

import com.biblioteca.model.Usuario;
import com.biblioteca.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Repository específico para a entidade Usuario.
 * Fornece métodos de persistência customizados.
 */
public class UsuarioRepository extends Repository<Usuario, Long> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    /**
     * Busca um usuário pelo email.
     *
     * @param email Email do usuário
     * @return Usuário encontrado ou null
     */
    public Usuario findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = null;

        try {
            usuario = session.createQuery(
                    "FROM Usuario WHERE email = :email", Usuario.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return usuario;
    }

    /**
     * Busca usuários por nome (busca parcial).
     *
     * @param nome Nome do usuário
     * @return Lista de usuários encontrados
     */
    public List<Usuario> findByNome(String nome) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> usuarios = null;

        try {
            usuarios = session.createQuery(
                    "FROM Usuario WHERE nome LIKE :nome", Usuario.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return usuarios;
    }

    /**
     * Busca usuários por sexo.
     *
     * @param sexo Sexo do usuário (M ou F)
     * @return Lista de usuários encontrados
     */
    public List<Usuario> findBySexo(String sexo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> usuarios = null;

        try {
            usuarios = session.createQuery(
                    "FROM Usuario WHERE sexo = :sexo", Usuario.class)
                    .setParameter("sexo", sexo)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return usuarios;
    }
}
