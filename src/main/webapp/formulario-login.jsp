<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login - PetShop Cão Q-Late</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

<style>
body {
	background-color: #f8f8f8;
}

.login-container {
	max-width: 400px;
	margin: 100px auto;
	background-color: #fff;
	padding: 30px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.login-container h2 {
	text-align: center;
	margin-bottom: 25px;
}
</style>
</head>
<body>

	<div class="container">
		<div class="login-container">
			<h2>PetShop Cão Q-Late</h2>

			<c:if test="${param.erro == 'loginInvalido'}">
				<div class="alert alert-danger" role="alert">Login ou senha
					inválidos.</div>
			</c:if>
			<c:if test="${param.msg == 'logout'}">
				<div class="alert alert-success" role="alert">Logout realizado
					com sucesso.</div>
			</c:if>
			<c:if test="${param.msg == 'timeout'}">
				<div class="alert alert-warning" role="alert">Sua sessão
					expirou por inatividade. Faça login novamente.</div>
			</c:if>


			<form action="controladora" method="post">
				<input type="hidden" name="logica" value="EfetuaLoginLogica">

				<div class="form-group">
					<label for="login">Login:</label> <input type="text"
						class="form-control" id="login" name="login" required />
				</div>
				<div class="form-group">
					<label for="senha">Senha:</label> <input type="password"
						class="form-control" id="senha" name="senha" required />
				</div>
				<button type="submit" class="btn btn-primary btn-block">
					<span class="glyphicon glyphicon-log-in"></span> Entrar
				</button>
			</form>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>