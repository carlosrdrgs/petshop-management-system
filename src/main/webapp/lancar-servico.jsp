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
<title>Lançar Serviço Realizado - PetShop Cão Q-Late</title>

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

.form-lancar {
	max-width: 600px;
}

.dl-horizontal dt {
	width: 140px;
	text-align: left;
}

.dl-horizontal dd {
	margin-left: 160px;
}

.service-details ul {
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
				<h1 class="page-header">Lançar Serviço Realizado</h1>

				<c:if test="${not empty agendamento}">
					<div class="panel panel-info">
						<div class="panel-heading">Detalhes do Agendamento</div>
						<div class="panel-body service-details">
							<dl class="dl-horizontal">
								<dt>Cliente:</dt>
								<dd>${clienteDoAgendamento.nome}</dd>
								<dt>Cão:</dt>
								<dd>${agendamento.cao.nome}(${agendamento.cao.raca})</dd>
								<dt>Data Agendada:</dt>
								<dd>
									<fmt:formatDate value="${agendamento.dataAgendamentoForJSTL}"
										pattern="dd/MM/yyyy" />
								</dd>
								<dt>Serviços Agendados:</dt>
								<dd>
									<ul>
										<c:forEach var="svc" items="${agendamento.servicos}">
											<li>${svc.descricao}- <fmt:formatNumber
													value="${svc.valor}" type="currency" currencySymbol="R$ " /></li>
										</c:forEach>
									</ul>
								</dd>
								<dt>Valor Total Calculado:</dt>
								<dd>
									<strong><fmt:formatNumber
											value="${valorTotalCalculado}" type="currency"
											currencySymbol="R$ " /></strong>
								</dd>
							</dl>
						</div>
					</div>


					<form action="controladora" method="post" class="form-lancar">
						<input type="hidden" name="logica"
							value="ProcessaLancarServicoLogica"> <input type="hidden"
							name="id" value="${agendamento.id}"> <input type="hidden"
							name="valorOriginal" value="${valorTotalCalculado}">

						<div class="form-group">
							<label for="dataExecucao">Data de Execução:</label> <input
								type="date" class="form-control" id="dataExecucao"
								name="dataExecucao" required />
						</div>

						<c:if test="${podeAplicarDesconto}">
							<div class="form-group">
								<div class="checkbox">
									<label> <input type="checkbox" id="aplicarDesconto"
										name="aplicarDesconto" value="true"> Aplicar Desconto
										de 10% (Valor com desconto: <fmt:formatNumber
											value="${valorTotalCalculado * 0.9}" type="currency"
											currencySymbol="R$ " />)
									</label>
								</div>
							</div>
						</c:if>

						<button type="submit" class="btn btn-success">
							<span class="glyphicon glyphicon-ok-sign"></span> Confirmar
							Realização
						</button>
						<a href="controladora?logica=ListaAgendamentoLogica"
							class="btn btn-default">Cancelar</a>
					</form>
				</c:if>

				<c:if test="${empty agendamento}">
					<div class="alert alert-danger" role="alert">Agendamento não
						encontrado!</div>
					<a href="controladora?logica=ListaAgendamentoLogica"
						class="btn btn-default">Voltar para Agendamentos Pendentes</a>
				</c:if>

			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>