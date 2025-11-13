<%@ taglib uri="jakarta.tags.core" prefix="c" %>
 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ include file="autenticacao.jsp" %>
 <!DOCTYPE html>
 <html lang="pt-br">
 <head>
 <meta charset="UTF-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <title>PetShop Cão Q-Late</title>

 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

 <style>
    .sidebar {
        background-color: #f8f8f8; 
        border-right: 1px solid #e7e7e7;
        padding-top: 20px;
        min-height: calc(100vh - 700px);
    }
    .main-content {
        padding: 20px;
    }
    .sidebar .nav-pills > li > a {
        border-radius: 0;
        color: #777; 
    }
    .sidebar .nav-pills > li.active > a,
    .sidebar .nav-pills > li.active > a:hover,
    .sidebar .nav-pills > li.active > a:focus {
        background-color: #e7e7e7;
        color: #555;
    }
    .sidebar .nav-pills > li > a:hover,
    .sidebar .nav-pills > li > a:focus {
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
     .sidebar .dropdown-menu > li > a {
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
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        Agendamentos <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="controladora?logica=CadastraAgendamentoLogica">Novo</a></li>
                        <li><a href="controladora?logica=ListaAgendamentoLogica">Consultar</a></li>
                    </ul>
                </li>
                <li><a href="controladora?logica=RelatorioLogica">Relatório</a></li>
            </ul>
             <a href="controladora?logica=EfetuaLogoutLogica" class="logout-link">Sair <span class="glyphicon glyphicon-log-out"></span></a>
        </div>

        <div class="col-sm-9 col-md-10 main-content">
            <h1 class="page-header">Página Principal</h1>
            <p>Selecione uma opção no menu lateral.</p>
        </div>
    </div>
 </div>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

 </body>
 </html>