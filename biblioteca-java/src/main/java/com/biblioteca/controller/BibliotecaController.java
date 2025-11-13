package com.biblioteca.controller;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Controlador principal do sistema de biblioteca.
 * Gerencia a lógica de negócio para operações de empréstimo, devolução e gerenciamento.
 */
public class BibliotecaController {
    private LivroRepository livroRepository;
    private UsuarioRepository usuarioRepository;
    private EmprestimoRepository emprestimoRepository;

    // Constantes de negócio
    private static final int LIMITE_EMPRESTIMOS = 5;
    private static final int DIAS_EMPRESTIMO = 14;
    private static final double MULTA_POR_DIA = 5.0;

    public BibliotecaController() {
        this.livroRepository = new LivroRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.emprestimoRepository = new EmprestimoRepository();
    }

    // ============ OPERAÇÕES COM LIVROS ============

    /**
     * Cadastra um novo livro no sistema.
     *
     * @param titulo Título do livro
     * @param tema Tema do livro
     * @param autor Autor do livro
     * @param isbn ISBN do livro
     * @param dataPublicacao Data de publicação
     * @param quantidade Quantidade de exemplares
     * @return Livro cadastrado
     */
    public Livro cadastrarLivro(String titulo, String tema, String autor, 
                                String isbn, LocalDate dataPublicacao, Integer quantidade) {
        if (livroRepository.findByIsbn(isbn) != null) {
            throw new IllegalArgumentException("Livro com este ISBN já existe!");
        }

        Livro livro = new Livro(titulo, tema, autor, isbn, dataPublicacao, quantidade);
        Long id = livroRepository.save(livro);
        return livroRepository.findById(id);
    }

    /**
     * Atualiza informações de um livro.
     *
     * @param livro Livro com dados atualizados
     */
    public void atualizarLivro(Livro livro) {
        livroRepository.update(livro);
    }

    /**
     * Deleta um livro do sistema.
     *
     * @param livro Livro a ser deletado
     */
    public void deletarLivro(Livro livro) {
        livroRepository.delete(livro);
    }

    /**
     * Obtém um livro pelo ID.
     *
     * @param id ID do livro
     * @return Livro encontrado
     */
    public Livro obterLivro(Long id) {
        return livroRepository.findById(id);
    }

    /**
     * Lista todos os livros cadastrados.
     *
     * @return Lista de livros
     */
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    /**
     * Lista livros disponíveis para empréstimo.
     *
     * @return Lista de livros disponíveis
     */
    public List<Livro> listarLivrosDisponiveis() {
        return livroRepository.findDisponivel();
    }

    // ============ OPERAÇÕES COM USUÁRIOS ============

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param nome Nome do usuário
     * @param sexo Sexo do usuário
     * @param celular Celular do usuário
     * @param email Email do usuário
     * @return Usuário cadastrado
     */
    public Usuario cadastrarUsuario(String nome, String sexo, String celular, String email) {
        if (usuarioRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Usuário com este email já existe!");
        }

        Usuario usuario = new Usuario(nome, sexo, celular, email);
        Long id = usuarioRepository.save(usuario);
        return usuarioRepository.findById(id);
    }

    /**
     * Atualiza informações de um usuário.
     *
     * @param usuario Usuário com dados atualizados
     */
    public void atualizarUsuario(Usuario usuario) {
        usuarioRepository.update(usuario);
    }

    /**
     * Deleta um usuário do sistema.
     *
     * @param usuario Usuário a ser deletado
     */
    public void deletarUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    /**
     * Obtém um usuário pelo ID.
     *
     * @param id ID do usuário
     * @return Usuário encontrado
     */
    public Usuario obterUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return Lista de usuários
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // ============ OPERAÇÕES DE EMPRÉSTIMO ============

    /**
     * Realiza um empréstimo de livro para um usuário.
     * Valida as regras de negócio antes de realizar o empréstimo.
     *
     * @param usuario Usuário que está pegando emprestado
     * @param livro Livro a ser emprestado
     * @return Empréstimo realizado
     * @throws IllegalArgumentException Se violar alguma regra de negócio
     */
    public Emprestimo fazerEmprestimo(Usuario usuario, Livro livro) {
        // Validar limite de empréstimos
        Long emprestimoAtivos = emprestimoRepository.countEmprestimosAtivos(usuario);
        if (emprestimoAtivos >= LIMITE_EMPRESTIMOS) {
            throw new IllegalArgumentException("Usuário atingiu o limite de " + LIMITE_EMPRESTIMOS + " empréstimos!");
        }

        // Validar disponibilidade do livro
        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new IllegalArgumentException("Livro não possui exemplares disponíveis!");
        }

        // Criar empréstimo
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(DIAS_EMPRESTIMO);

        Emprestimo emprestimo = new Emprestimo(usuario, livro, dataEmprestimo, dataDevolucaoPrevista);
        Long id = emprestimoRepository.save(emprestimo);

        // Atualizar quantidade disponível do livro
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
        livroRepository.update(livro);

        return emprestimoRepository.findById(id);
    }

    /**
     * Registra a devolução de um livro emprestado.
     * Calcula multa se houver atraso.
     *
     * @param emprestimo Empréstimo a ser devolvido
     * @return Empréstimo atualizado com devolução registrada
     */
    public Emprestimo registrarDevolucao(Emprestimo emprestimo) {
        if (!emprestimo.getAtivo()) {
            throw new IllegalArgumentException("Este empréstimo já foi devolvido!");
        }

        LocalDate dataDevolucao = LocalDate.now();
        emprestimo.setDataDevolucao(dataDevolucao);
        emprestimo.setAtivo(false);

        // Calcular multa se houver atraso
        if (dataDevolucao.isAfter(emprestimo.getDataDevoluçaoPrevista())) {
            long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevoluçaoPrevista(), dataDevolucao);
            double multa = diasAtraso * MULTA_POR_DIA;
            emprestimo.setMulta(multa);
        }

        emprestimoRepository.update(emprestimo);

        // Atualizar quantidade disponível do livro
        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        livroRepository.update(livro);

        return emprestimo;
    }

    /**
     * Lista todos os empréstimos de um usuário.
     *
     * @param usuario Usuário
     * @return Lista de empréstimos
     */
    public List<Emprestimo> listarEmprestimosUsuario(Usuario usuario) {
        return emprestimoRepository.findByUsuario(usuario);
    }

    /**
     * Lista empréstimos ativos de um usuário.
     *
     * @param usuario Usuário
     * @return Lista de empréstimos ativos
     */
    public List<Emprestimo> listarEmprestimosAtivos(Usuario usuario) {
        return emprestimoRepository.findEmprestimosAtivos(usuario);
    }

    /**
     * Lista todos os empréstimos atrasados.
     *
     * @return Lista de empréstimos atrasados
     */
    public List<Emprestimo> listarEmprestimosAtrasados() {
        return emprestimoRepository.findAtrasados();
    }

    /**
     * Obtém um empréstimo pelo ID.
     *
     * @param id ID do empréstimo
     * @return Empréstimo encontrado
     */
    public Emprestimo obterEmprestimo(Long id) {
        return emprestimoRepository.findById(id);
    }

    /**
     * Lista todos os empréstimos do sistema.
     *
     * @return Lista de empréstimos
     */
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    // ============ GETTERS ============

    public LivroRepository getLivroRepository() {
        return livroRepository;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public EmprestimoRepository getEmprestimoRepository() {
        return emprestimoRepository;
    }

    public static int getLimiteEmprestimos() {
        return LIMITE_EMPRESTIMOS;
    }

    public static int getDiasEmprestimo() {
        return DIAS_EMPRESTIMO;
    }

    public static double getMultaPorDia() {
        return MULTA_POR_DIA;
    }
}
