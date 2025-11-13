<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="autenticacao.jsp"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Agendamentos Pendentes - PetShop Cão Q-Late</title>

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
	margin-bottom: 5px;
}

.service-list ul {
	padding-left: 20px;
	margin-bottom: 0;
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
					<li><a href="controladora?logica=ListaClienteLogica">Clientes</a></li>
					<li><a href="controladora?logica=ListaCaoLogica">Cães</a></li>
					<li><a href="controladora?logica=ListaServicosLogica">Serviços</a></li>
					<li class="dropdown active"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-haspopup="true" aria-expanded="false"> Agendamentos <span
							class="caret"></span>
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
				<h1 class="page-header">Agendamentos Pendentes</h1>

				<c:if test="${not empty erroCancelamento}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${erroCancelamento}
					</div>
				</c:if>
				<c:if test="${param.msg == 'CanceladoSucesso'}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						Agendamento cancelado com sucesso!
					</div>
				</c:if>
				<c:if test="${param.msg == 'ServicoLancado'}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						Serviço lançado com sucesso!
					</div>
				</c:if>

				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Data Agendada</th>
								<th>Nome do Cão</th>
								<th>Raça</th>
								<th>Serviços</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="ag" items="${agendamentosPendentes}">
								<tr>
									<td><fmt:formatDate value="${ag.dataAgendamentoForJSTL}"
											pattern="dd/MM/yyyy" /></td>
									<td>${ag.cao.nome}</td>
									<td>${ag.cao.raca}</td>
									<td class="service-list">
										<ul>
											<c:forEach var="svc" items="${ag.servicos}">
												<li>${svc.descricao}</li>
											</c:forEach>
										</ul>
									</td>
									<td class="action-buttons">
										<form action="controladora" method="get">
											<input type="hidden" name="logica"
												value="LancarServicoLogica"> <input type="hidden"
												name="id" value="${ag.id}">
											<button type="submit" class="btn btn-success btn-xs">
												<span class="glyphicon glyphicon-ok-sign"></span> Lançar
												Serviço
											</button>
										</form>
										<form action="controladora" method="post">
											<input type="hidden" name="logica"
												value="RemoveAgendamentoLogica"> <input
												type="hidden" name="id" value="${ag.id}">
											<button type="submit" class="btn btn-danger btn-xs"
												onclick="return confirm('Tem certeza que deseja cancelar este agendamento?');">
												<span class="glyphicon glyphicon-remove-sign"></span>
												Cancelar
											</button>
										</form>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty agendamentosPendentes}">
								<tr>
									<td colspan="5">Nenhum agendamento pendente encontrado a
										partir de hoje.</td>
								</tr>
							</c:if>
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