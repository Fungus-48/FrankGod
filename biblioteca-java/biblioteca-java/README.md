# Sistema de Gestão de Biblioteca - Java Swing

Um sistema completo de gerenciamento de biblioteca desenvolvido em **Java Swing** com arquitetura **MVC/MVCR**, utilizando **Hibernate ORM** para persistência de dados.

## 📋 Sobre o Projeto

Este é um sistema acadêmico de CRUD (Create, Read, Update, Delete) para gerenciamento de livros, usuários e empréstimos em uma biblioteca. O projeto foi desenvolvido seguindo as melhores práticas de arquitetura de software.

**Funcionalidades principais:**
- Gerenciamento completo de livros (cadastro, edição, exclusão, listagem)
- Gerenciamento completo de usuários (cadastro, edição, exclusão, listagem)
- Sistema de empréstimo de livros com regras de negócio
- Registro de devolução com cálculo automático de multa
- Listagem de livros disponíveis para empréstimo
- Interface gráfica intuitiva em Java Swing

## 🏗️ Arquitetura

O projeto segue a arquitetura **MVC/MVCR** (Model-View-Controller-Repository):

```
Model (model/)
├── Livro.java          - Entidade que representa um livro
├── Usuario.java        - Entidade que representa um usuário
└── Emprestimo.java     - Entidade que representa um empréstimo

View (view/)
├── JanelaPrincipal.java           - Janela principal do sistema
├── JanelaCrudLivro.java           - Interface para CRUD de livros
├── JanelaCrudUsuario.java         - Interface para CRUD de usuários
├── JanelaEmprestimo.java          - Interface para fazer empréstimos
├── JanelaDevolucao.java           - Interface para registrar devoluções
└── JanelaListarLivrosDisponiveis.java - Interface para listar livros

Controller (controller/)
└── BibliotecaController.java      - Lógica de negócio principal

Repository (repository/)
├── Repository.java                - Classe base genérica para CRUD
├── LivroRepository.java           - Métodos de persistência para Livro
├── UsuarioRepository.java         - Métodos de persistência para Usuario
└── EmprestimoRepository.java      - Métodos de persistência para Emprestimo

Util (util/)
└── HibernateUtil.java             - Gerenciador de SessionFactory
```

## 🛠️ Tecnologias Utilizadas

- **Java 11** - Linguagem de programação
- **Swing** - Framework para interface gráfica
- **Hibernate 5.6.15** - ORM para persistência
- **MySQL 8.0** - Banco de dados
- **Maven** - Gerenciador de dependências
- **JPA** - Java Persistence API

## 📦 Dependências

```xml
- hibernate-core 5.6.15.Final
- mysql-connector-java 8.0.33
- javax.persistence-api 2.2
- slf4j-api 1.7.36
- slf4j-simple 1.7.36
- junit 4.13.2
```

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 11 ou superior
- Maven 3.6 ou superior
- MySQL Server 8.0 ou superior

### Passos de Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/biblioteca-java.git
cd biblioteca-java
```

2. **Configure o banco de dados**
```sql
CREATE DATABASE biblioteca_db;
USE biblioteca_db;
```

3. **Atualize as credenciais do banco em `hibernate.cfg.xml`**
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/biblioteca_db</property>
<property name="hibernate.connection.username">seu_usuario</property>
<property name="hibernate.connection.password">sua_senha</property>
```

4. **Compile o projeto**
```bash
mvn clean compile
```

5. **Execute a aplicação**
```bash
mvn exec:java -Dexec.mainClass="com.biblioteca.Main"
```

Ou execute o JAR gerado:
```bash
mvn clean package
java -jar target/biblioteca-sistema-1.0.0-jar-with-dependencies.jar
```

## 📚 Guia de Uso

### Tela Principal
A tela principal apresenta um menu com as seguintes opções:

1. **CRUD Livro** - Gerenciar livros (criar, editar, deletar, listar)
2. **CRUD Usuário** - Gerenciar usuários (criar, editar, deletar, listar)
3. **Fazer Empréstimo** - Emprestar livros para usuários
4. **Registrar Devolução** - Registrar devoluções e calcular multas
5. **Listar Livros Disponíveis** - Ver livros disponíveis para empréstimo
6. **Sair** - Encerrar a aplicação

### Operações CRUD

#### Livros
- **Adicionar**: Preencha os dados (título, tema, autor, ISBN, quantidade) e clique em "Adicionar"
- **Atualizar**: Selecione um livro na tabela, modifique os dados e clique em "Atualizar"
- **Deletar**: Selecione um livro e clique em "Deletar"
- **Listar**: Todos os livros são exibidos automaticamente na tabela

#### Usuários
- **Adicionar**: Preencha os dados (nome, sexo, celular, email) e clique em "Adicionar"
- **Atualizar**: Selecione um usuário, modifique os dados e clique em "Atualizar"
- **Deletar**: Selecione um usuário e clique em "Deletar"
- **Listar**: Todos os usuários são exibidos automaticamente na tabela

### Empréstimos

**Regras de Negócio:**
- Um usuário pode emprestar até **5 livros** simultaneamente
- Um livro só pode ser emprestado se houver exemplares disponíveis
- O prazo máximo de empréstimo é de **14 dias**
- Após o prazo, o sistema calcula multa de **R$ 5.00 por dia de atraso**

**Como fazer um empréstimo:**
1. Clique em "Fazer Empréstimo"
2. Selecione o usuário e o livro
3. Clique em "Fazer Empréstimo"
4. O sistema confirmará a data de devolução prevista

**Como registrar devolução:**
1. Clique em "Registrar Devolução"
2. Selecione o empréstimo na tabela
3. Clique em "Registrar Devolução"
4. Se houver atraso, a multa será calculada automaticamente

## 🗄️ Banco de Dados

### Tabela: livros
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | Chave primária auto-incrementada |
| titulo | VARCHAR(255) | Título do livro |
| tema | VARCHAR(100) | Tema/Categoria do livro |
| autor | VARCHAR(255) | Autor do livro |
| isbn | VARCHAR(20) | ISBN único |
| data_publicacao | DATE | Data de publicação |
| quantidade_total | INT | Quantidade total de exemplares |
| quantidade_disponivel | INT | Quantidade disponível para empréstimo |

### Tabela: usuarios
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | Chave primária auto-incrementada |
| nome | VARCHAR(255) | Nome do usuário |
| sexo | VARCHAR(1) | Sexo (M ou F) |
| celular | VARCHAR(20) | Número de celular |
| email | VARCHAR(255) | Email único |

### Tabela: emprestimos
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INT | Chave primária auto-incrementada |
| usuario_id | INT | Chave estrangeira para usuários |
| livro_id | INT | Chave estrangeira para livros |
| data_emprestimo | DATE | Data do empréstimo |
| data_devolucao_prevista | DATE | Data prevista de devolução |
| data_devolucao | DATE | Data real de devolução |
| multa | DOUBLE | Multa por atraso |
| ativo | BOOLEAN | Se o empréstimo está ativo |

## 🧪 Testes

Para testar as funcionalidades:

1. **Teste CRUD de Livros**
   - Adicione alguns livros com dados variados
   - Edite informações de um livro
   - Delete um livro

2. **Teste CRUD de Usuários**
   - Cadastre alguns usuários
   - Edite informações de um usuário
   - Delete um usuário

3. **Teste de Empréstimo**
   - Faça empréstimos de livros para usuários
   - Verifique se a quantidade disponível diminui
   - Tente emprestar mais de 5 livros (deve falhar)
   - Tente emprestar um livro sem exemplares (deve falhar)

4. **Teste de Devolução**
   - Registre devoluções dentro do prazo (sem multa)
   - Registre devoluções com atraso (com multa)
   - Verifique se a quantidade disponível aumenta

## 📝 Estrutura de Pacotes

```
com.biblioteca
├── Main.java                    - Classe principal
├── controller
│   └── BibliotecaController.java
├── model
│   ├── Livro.java
│   ├── Usuario.java
│   └── Emprestimo.java
├── repository
│   ├── Repository.java
│   ├── LivroRepository.java
│   ├── UsuarioRepository.java
│   └── EmprestimoRepository.java
├── view
│   ├── JanelaPrincipal.java
│   ├── JanelaCrudLivro.java
│   ├── JanelaCrudUsuario.java
│   ├── JanelaEmprestimo.java
│   ├── JanelaDevolucao.java
│   └── JanelaListarLivrosDisponiveis.java
└── util
    └── HibernateUtil.java
```

## 🎯 Requisitos Atendidos

- ✅ Arquitetura MVC/MVCR implementada
- ✅ CRUD completo para Livros
- ✅ CRUD completo para Usuários
- ✅ Sistema de Empréstimo com validações
- ✅ Sistema de Devolução com cálculo de multa
- ✅ Hibernate ORM para persistência
- ✅ Interface Java Swing
- ✅ Banco de dados MySQL
- ✅ Regras de negócio implementadas
- ✅ Código bem documentado

## 👨‍💻 Desenvolvedor

Desenvolvido como trabalho acadêmico para a disciplina de Linguagem de Programação III.

## 📄 Licença

Este projeto é fornecido como está para fins educacionais.

---

**Última atualização:** Dezembro 2024
