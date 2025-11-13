<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="autenticacao.jsp"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>PetShop Cão Q-Late - Alterar Cliente</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

<style>
html, body {
	min-height: auto;
	height: auto;
}

.sidebar {
	background-color: #f8f8f8;
	border-right: 1px solid #e7e7e7;
	padding-top: 20px;
}

.row {
	min-height: auto;
	display: block;
}

.main-content {
	padding: 20px;
}

.sidebar .nav-pills>li>a {
	border-radius: 0;
	color: #777;
}

.sidebar .nav-pills>li.active>a, .sidebar .nav-pills>li.active>a:hover,
	.sidebar .nav-pills>li.active>a:focus {
	background-color: #e7e7e7;
	color: #555;
}

.sidebar .nav-pills>li>a:hover, .sidebar .nav-pills>li>a:focus {
	background-color: #eee;
}

.sidebar .dropdown-menu {
	position: static;
	float: none;
	width: auto;
	margin-top: 0;
	background-color: transparent;
	border: 0;
	box-shadow: none;
}

.sidebar .dropdown-menu>li>a {
	color: #777;
	padding-left: 30px;
}

.logout-link {
	display: block;
	padding: 10px 15px;
	margin-top: 20px;
	color: #777;
	border-top: 1px solid #e7e7e7;
}

.logout-link:hover {
	background-color: #eee;
	text-decoration: none;
}

.form-cadastro {
	max-width: 600px;
}
</style>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<h3>PetShop Cão Q-Late</h3>
				<ul class="nav nav-pills nav-stacked">
					<li><a href="controladora?logica=ListaAdministratorLogica">Administradores</a></li>
					<li class="active"><a
						href="controladora?logica=ListaClienteLogica">Clientes</a></li>
					<li><a href="controladora?logica=ListaCaoLogica">Cães</a></li>
					<li><a href="controladora?logica=ListaServicosLogica">Serviços</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"> Agendamentos <span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="controladora?logica=CadastraAgendamentoLogica">Novo</a></li>
							<li><a href="controladora?logica=ListaAgendamentoLogica">Consultar</a></li>
						</ul></li>
					<li><a href="controladora?logica=RelatorioLogica">Relatório</a></li>
				</ul>
				<a href="controladora?logica=EfetuaLogoutLogica" class="logout-link">Sair
					<span class="glyphicon glyphicon-log-out"></span>
				</a>
			</div>

			<div class="col-sm-9 col-md-10 main-content">
				<h1 class="page-header">Alterar Cliente</h1>

				<c:if test="${not empty erroValidacao}">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign"
							aria-hidden="true"></span> ${erroValidacao}
					</div>
				</c:if>

				<form action="controladora" method="post" class="form-cadastro">
					<input type="hidden" name="logica"
						value="ProcessaAlteraClienteLogica"> <input type="hidden"
						name="id" value="${cliente.id}">

					<div class="form-group">
						<label for="nome">Nome:</label> <input type="text"
							class="form-control" id="nome" name="nome"
							value="${cliente.nome}" required />
					</div>
					<div class="form-group">
						<label for="cpf">CPF:</label> <input type="text"
							class="form-control" id="cpf" name="cpf" value="${cliente.cpf}"
							required />
					</div>
					<div class="form-group">
						<label for="email">Email:</label> <input type="email"
							class="form-control" id="email" name="email"
							value="${cliente.email}" />
					</div>
					<div class="form-group">
						<label for="telefone">Telefone:</label> <input type="text"
							class="form-control" id="telefone" name="telefone"
							value="${cliente.telefone}" />
					</div>
					<div class="form-group">
						<label for="dataNascimento">Data de Nascimento:</label> <input
							type="date" class="form-control" id="dataNascimento"
							name="dataNascimento" value="${cliente.dataNascimento}" required />
					</div>

					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-ok"></span> Salvar Alterações
					</button>
					<a href="controladora?logica=ListaClienteLogica"
						class="btn btn-default">Voltar para Lista</a>
				</form>

			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>