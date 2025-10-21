# 🚀 Sistema de Gestão para Academia de Boxe (V1.1)

**Projeto acadêmico desenvolvido para a disciplina de Linguagens de Programação. O objetivo foi aplicar conceitos de Programação Orientada a Objetos (POO), interface gráfica e conexão com banco de dados em um software funcional.**

Este sistema gerencia uma academia de boxe, utilizando Java com interface gráfica (Swing).

## ✨ Funcionalidades Principais

* **Controle de Acesso:** Tela de Login com perfis de usuário (Administrador, etc.).
* **Módulo de Atendimento (PDV):** Um Ponto de Venda para registrar serviços/produtos, selecionar cliente/vendedor e finalizar vendas com múltiplas formas de pagamento.
* **Gestão de Agendamentos:** Módulo completo para agendar e gerenciar aulas e serviços.
* **CRUD de Alunos:** Cadastro completo de alunos (Criar, Ler, Atualizar, Deletar) com listagem em tabela.

## 💻 Tecnologias Utilizadas

* **Linguagem:** Java
* **Interface Gráfica:** Java Swing (JFrame, JPanel, JTable, etc.)
* **Banco de Dados:** MySQL
* **Conexão:** JDBC (com padrão de projeto DAO)
* **IDE:** IntelliJ IDEA

## ⚙️ Como Executar o Projeto

Para rodar este projeto na sua máquina, siga os passos abaixo:

1.  **Clone o Repositório**
    ```bash
    git clone [https://github.com/MatheusCesarMC/Sistema-Academia-Boxe-Java.git](https://github.com/MatheusCesarMC/Sistema-Academia-Boxe-Java.git)
    ```
2.  **Banco de Dados**
    * Crie um novo banco de dados no seu MySQL (ex: `bd_academia`).
    * Importe o script `academiaboxe.sql` (incluso neste repositório) para criar as tabelas e estruturas.

3.  **Configurar a Conexão**
    * Abra o projeto na sua IDE (IntelliJ, Eclipse, etc.).
    * Vá até a classe `FabricaConexao.java` (dentro do pacote `DAO`).
    * Altere a `URL`, `USUARIO` e `SENHA` do banco de dados para as suas credenciais locais do MySQL.

4. **Adicionar o Driver JDBC**
   * O arquivo `mysql-connector-j-8.4.0.jar` já está incluído neste repositório.
   * Você só precisa adicionar este arquivo ao *build path* (caminho de compilação) do seu projeto na sua IDE.

6.  **Executar**
    * Os dados de login para teste estão no arquivo `Login` (incluso neste repositório).
    * Execute a classe principal `Main.java` (localizada no pacote `Main` ou `View`).

---
*Projeto desenvolvido por Matheus Cesar como requisito avaliativo para a disciplina de Linguagens de Programação.*
