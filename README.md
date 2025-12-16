<div align="center">
  <h1>🚗 Sistema de Gestão de Lavação (v3.0)</h1>
  <p>
    <strong>Projeto "Mão na Massa 3" - POO Java | IFSC Florianópolis</strong>
  </p>
  
  <p>
    <img src="https://img.shields.io/badge/Java-17+-orange?style=flat&logo=java" alt="Java" />
    <img src="https://img.shields.io/badge/Feature-Exceptions-red?style=flat" alt="Exceptions" />
    <img src="https://img.shields.io/badge/Feature-Collections-blue?style=flat" alt="Collections" />
  </p>
</div>

## 📖 Sobre
Evolução do sistema de lavação automotiva. Nesta versão (Mão na Massa 3), o foco expandiu para a gestão completa de **Ordens de Serviço (OS)**, incluindo cálculo financeiro, pontuação de fidelidade e uma arquitetura mais robusta com tratamento de erros e separação de responsabilidades.

<div align="center">
  <img src="./Diagrama de classe completo - Lavacao.png" alt="UML" width="800px">
</div>

## 💻 Novos Conceitos Aplicados

* **Tratamento de Exceções:** Implementação da classe `ExceptionLavacao` para capturar e tratar erros de negócio (ex: remover item de lista vazia ou adicionar item em OS fechada).
* **Classe Associativa:** A classe `ItemOS` foi criada para resolver a relação N:N entre `OrdemServico` e `Servico`, armazenando atributos específicos como valor e observações.
* **Coleções (Collections):** Substituição de vetores estáticos por `List<T>` e `ArrayList`, permitindo manipulação dinâmica de clientes, veículos e itens da OS.
* **Organização em Pacotes:** Estruturação do projeto em camadas lógicas:
    * `domain`: Classes de domínio (Modelo, Cliente, OS).
    * `exceptions`: Exceções personalizadas.
    * `report`: Classes responsáveis pela geração de relatórios (Impressão).
    * `mainapp`: Ponto de entrada da aplicação.
* **Membros Estáticos:** Uso de atributos estáticos (`Servico.pontos`) para compartilhar valores comuns entre instâncias.

## 🛠️ Estrutura de Classes (Destaques)

* **OrdemServico:** O "coração" desta etapa. Realiza agregação com `Veiculo` e composição com `ItemOS`. Possui lógica para calcular totais e aplicar descontos.
* **Relatorio & ImpressaoOS:** Classes especializadas que demonstram associação por dependência, formatando a saída de dados para o console.

