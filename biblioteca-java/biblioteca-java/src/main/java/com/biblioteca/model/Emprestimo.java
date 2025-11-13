package com.biblioteca.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe que representa um Empréstimo de livro no sistema de biblioteca.
 * Mapeada para a tabela 'emprestimos' no banco de dados.
 */
@Entity
@Table(name = "emprestimos")
public class Emprestimo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDate dataDevoluçaoPrevista;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Column(name = "multa")
    private Double multa;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    // Construtores
    public Emprestimo() {
        this.ativo = true;
        this.multa = 0.0;
    }

    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo, 
                      LocalDate dataDevoluçaoPrevista) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevoluçaoPrevista = dataDevoluçaoPrevista;
        this.ativo = true;
        this.multa = 0.0;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevoluçaoPrevista() {
        return dataDevoluçaoPrevista;
    }

    public void setDataDevoluçaoPrevista(LocalDate dataDevoluçaoPrevista) {
        this.dataDevoluçaoPrevista = dataDevoluçaoPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", usuario=" + usuario.getNome() +
                ", livro=" + livro.getTitulo() +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevoluçaoPrevista=" + dataDevoluçaoPrevista +
                ", dataDevolucao=" + dataDevolucao +
                ", multa=" + multa +
                ", ativo=" + ativo +
                '}';
    }
}
