# Sistema de Gerenciamento de PetShop "Cão Q-Late"
Este é um projeto acadêmico completo desenvolvido para a disciplina de Programação Web II. Trata-se de uma aplicação web robusta para a gestão interna de um petshop, permitindo o controle total sobre clientes, seus animais, os serviços oferecidos e o fluxo de agendamentos.

A arquitetura do projeto segue rigorosamente o padrão MVC (Model-View-Controller), utilizando uma Servlet Controladora Única para gerenciar todas as requisições, delegando as ações para classes de Lógica específicas.

Funcionalidades Principais
Autenticação Segura: Sistema de Login/Logout para o administrador, com senhas protegidas usando hash jBCrypt (sem texto puro).

Gestão de Clientes: CRUD completo (Adicionar, Listar, Alterar, Remover) para clientes, com validação de dados no servidor (CPF e data de nascimento futura).

Gestão de Cães: CRUD completo para cães, com associação obrigatória a um cliente.

Gestão de Serviços: CRUD completo para os serviços oferecidos, com a regra de negócio específica de permitir alteração apenas do valor.

Fluxo de Agendamento:

Agendar: Permite selecionar um cliente, um de seus cães, múltiplos serviços e uma data.

Validação de Data: O sistema bloqueia o agendamento em datas que já possuem um serviço (regra de 1 por dia).

Listar Pendentes: Exibe todos os agendamentos futuros com status "AGENDADO".

Lançar Serviço: Marca um agendamento como "REALIZADO", calcula o valor total e aplica a regra de desconto de 10% para 3 ou mais serviços.

Cancelar: Permite o cancelamento, respeitando a regra de prazo de 24h.

Relatórios:

Histórico por Cão: Lista todos os serviços já realizados para um cão específico.

Faturamento por Período: Gera um relatório de faturamento total com base nos serviços realizados em um intervalo de datas.

Gestão de Sessão: A sessão do usuário expira automaticamente após 2 minutos de inatividade.

Tecnologias Utilizadas
Linguagem: Java 17+

Servidor: Apache Tomcat 10.1

Tecnologias Web: Servlets e JavaServer Pages (JSP)

Banco de Dados: PostgreSQL

Persistência: JDBC puro

Frontend: JSTL (para evitar scriptlets nas JSPs), HTML e CSS.

Segurança: jBCrypt (para hash de senhas)

Gerenciamento de Dependências: Apache Maven
