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
<title>PetShop Cão Q-Late - Clientes</title>

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

.table th {
	background-color: #f5f5f5;
}

.action-buttons form {
	display: inline-block;
	margin-right: 5px;
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
				<h1 class="page-header">Lista de Clientes</h1>

				<c:if test="${param.msg == 'adicionado'}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						Cliente adicionado com sucesso!
					</div>
				</c:if>
				<c:if test="${param.msg == 'alterado'}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						Cliente alterado com sucesso!
					</div>
				</c:if>
				<c:if test="${param.msg == 'removido'}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						Cliente removido com sucesso!
					</div>
				</c:if>

				<p>
					<a href="cadastra-cliente.jsp" class="btn btn-primary"> <span
						class="glyphicon glyphicon-plus"></span> Cadastrar Novo Cliente
					</a>
				</p>

				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Nome</th>
								<th>CPF</th>
								<th>Email</th>
								<th>Telefone</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="cliente" items="${clientes}">
								<tr>
									<td>${cliente.nome}</td>
									<td>${cliente.cpf}</td>
									<td>${cliente.email}</td>
									<td>${cliente.telefone}</td>
									<td class="action-buttons">
										<form action="controladora" method="get"
											style="display: inline;">
											<input type="hidden" name="logica"
												value="AlteraClienteLogica"> <input type="hidden"
												name="id" value="${cliente.id}">
											<button type="submit" class="btn btn-warning btn-xs">
												<span class="glyphicon glyphicon-pencil"></span> Alterar
											</button>
										</form>
										<form action="controladora" method="post"
											style="display: inline;">
											<input type="hidden" name="logica"
												value="RemoveClienteLogica"> <input type="hidden"
												name="id" value="${cliente.id}">
											<button type="submit" class="btn btn-danger btn-xs"
												onclick="return confirm('Tem certeza que deseja excluir este cliente?');">
												<span class="glyphicon glyphicon-trash"></span> Excluir
											</button>
										</form>
										<form action="controladora" method="post"
											style="display: inline;">
											<input type="hidden" name="logica"
												value="ListaCaesClienteLogica"> <input type="hidden"
												name="id" value="${cliente.id}">
											<button type="submit" class="btn btn-info btn-xs">
												<span class="glyphicon glyphicon-eye-open"></span> Ver Cães
											</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>