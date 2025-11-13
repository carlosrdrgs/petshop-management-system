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
<title>Relatório de Faturamento - PetShop Cão Q-Late</title>

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
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"> Agendamentos <span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="controladora?logica=CadastraAgendamentoLogica">Novo</a></li>
							<li><a href="controladora?logica=ListaAgendamentoLogica">Consultar</a></li>
						</ul></li>
					<li class="active"><a
						href="controladora?logica=RelatorioLogica">Relatório</a></li>
				</ul>
				<a href="controladora?logica=EfetuaLogoutLogica" class="logout-link">Sair
					<span class="glyphicon glyphicon-log-out"></span>
				</a>
			</div>

			<div class="col-sm-9 col-md-10 main-content">
				<h1 class="page-header">Relatório de Faturamento por Período</h1>

				<form action="controladora" method="get"
					class="form-inline well well-sm">
					<input type="hidden" name="logica"
						value="GerarRelatorioFaturamentoLogica">

					<div class="form-group">
						<label for="dataInicio">Data Início:</label> <input type="date"
							class="form-control" id="dataInicio" name="dataInicio" required
							value="${param.dataInicio}">
					</div>

					<div class="form-group">
						<label for="dataFim">Data Fim:</label> <input type="date"
							class="form-control" id="dataFim" name="dataFim" required
							value="${param.dataFim}">
					</div>

					<button type="submit" class="btn btn-primary">
						<span class="glyphicon glyphicon-stats"></span> Gerar Relatório
					</button>
				</form>
				<hr>

				<c:if test="${not empty resultadosRelatorio}">
					<h2>
						Resultados para o período de
						<fmt:formatDate value="${dataInicio}" pattern="dd/MM/yyyy" />
						a
						<fmt:formatDate value="${dataFim}" pattern="dd/MM/yyyy" />
					</h2>

					<div class="table-responsive">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Data Execução</th>
									<th>Cão</th>
									<th>Serviços</th>
									<th>Valor Cobrado</th>
									<th>Desconto?</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="ag" items="${resultadosRelatorio}">
									<tr>
										<td><fmt:formatDate value="${ag.dataExecucaoForJSTL}"
												pattern="dd/MM/yyyy" /></td>
										<td>${ag.cao.nome}(${ag.cao.raca})</td>
										<td class="service-list">
											<ul>
												<c:forEach var="svc" items="${ag.servicos}">
													<li>${svc.descricao}</li>
												</c:forEach>
											</ul>
										</td>
										<td><fmt:formatNumber value="${ag.valorTotal}"
												type="currency" currencySymbol="R$ " /></td>
										<td>${ag.descontoAplicado ? 'Sim (10%)' : 'Não'}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<h3 class="text-success">
						Faturamento Total no Período: <strong><fmt:formatNumber
								value="${faturamentoTotal}" type="currency" currencySymbol="R$ " /></strong>
					</h3>
				</c:if>

				<c:if
					test="${empty resultadosRelatorio and not empty param.dataInicio}">
					<div class="alert alert-warning" role="alert">Nenhum serviço
						realizado encontrado para o período selecionado.</div>
				</c:if>


			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>