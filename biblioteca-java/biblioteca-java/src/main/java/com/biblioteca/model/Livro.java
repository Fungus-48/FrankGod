package com.biblioteca.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe que representa um Livro no sistema de biblioteca.
 * Mapeada para a tabela 'livros' no banco de dados.
 */
@Entity
@Table(name = "livros")
public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "tema", nullable = false, length = 100)
    private String tema;

    @Column(name = "autor", nullable = false, length = 255)
    private String autor;

    @Column(name = "isbn", unique = true, length = 20)
    private String isbn;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "quantidade_disponivel", nullable = false)
    private Integer quantidadeDisponivel;

    @Column(name = "quantidade_total", nullable = false)
    private Integer quantidadeTotal;

    // Construtores
    public Livro() {
    }

    public Livro(String titulo, String tema, String autor, String isbn, 
                 LocalDate dataPublicacao, Integer quantidade) {
        this.titulo = titulo;
        this.tema = tema;
        this.autor = autor;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.quantidadeDisponivel = quantidade;
        this.quantidadeTotal = quantidade;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tema='" + tema + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                ", quantidadeTotal=" + quantidadeTotal +
                '}';
    }
}
