<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 011 11.07.17
  Time: 0:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Помощь</title>
    <script type="text/javascript" src="<c:url value="/js/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-theme.min.css"/>">
    <script type="text/javascript" src="<c:url value="/js/react.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/react-dom.min.js" />"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Статистическая обработка </a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/krugstat/Statistics">Расчет статистики></a></li>
                <li><a href="/krugstat//Profile">Расчет профиля</a></li>
                <li class="active"><a href="/krugstat//Help">Помощь</a></li>
            </ul>
        </div>
    </div>
</nav>
<div id="app"></div>
</body>
<script>
    ReactDOM.render(
               React.DOM.p(null,"Это абзац"),
                document.getElementById("app")
            );
</script>
</html>
