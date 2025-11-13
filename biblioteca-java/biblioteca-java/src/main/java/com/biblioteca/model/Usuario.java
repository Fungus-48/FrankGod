package com.biblioteca.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe que representa um Usuário no sistema de biblioteca.
 * Mapeada para a tabela 'usuarios' no banco de dados.
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "sexo", length = 1)
    private String sexo;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    // Construtores
    public Usuario() {
    }

    public Usuario(String nome, String sexo, String celular, String email) {
        this.nome = nome;
        this.sexo = sexo;
        this.celular = celular;
        this.email = email;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
