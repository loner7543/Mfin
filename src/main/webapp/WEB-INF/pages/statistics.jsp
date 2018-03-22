<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 010 10.07.17
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Статистика</title>
    <script type="text/javascript" src="<c:url value="/js/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/plotly/plotly.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-theme.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/ststistics.css"/>">
    <script type="text/javascript" src="<c:url value="/js/statistics.js"/>"></script>
</head>
<body class="wraper">
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
                <li class="active"><a href="/krugstat/Statistics">Расчет статистики></a></li>
                <li><a href="/krugstat//Profile">Расчет профиля</a></li>
                <li><a href="/krugstat//Help">Помощь</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="column left-column">
    <form:form method="post" action="save.html" enctype="multipart/form-data" onsubmit="onSubmit(this)">
        <input type="file" id="my_file" name="files[]" multiple="multiple">
        <input type="submit" value="Upload" class="btn btn-primary"/>
    </form:form>
    <ul id="fileNamesList">
    </ul><br>
    <input type="button" value="Рассчитать статистику" onclick="calculateStatistics()" class="btn btn-primary">
    <input type="button" value="Рассчет профиля" onclick="onCalculateProfile()" class="btn btn-primary"><br>
</div>
<div class="column right-column">
    <div class="row first-row">
        <div id="statBlock">
            <label id="sko">Среднеквадратическое отклонение:</label><br>
            <label id="psist">Систематическая составляющая:</label><br>
            <label id="psum">Суммарная погрешность: </label><br>
            <label id="dov">Доверительный интервал: </label><br>
            <label id="PinPoint">Погрешность в точке: </label><br>
            <label id="opt">Ближайший к оптимальному профиль:</label><br>
            <label id="mediumLabel">Среднее по выборке:</label><br>
            <label id="kxSqare"></label>
        </div>
    </div>
    <div class="row second-row">
        <div id="amplitudesHistogram"></div><br>
        <input type="button" value="Записать амплитуды в файл" class="btn btn-primary">
        <input type="button" value="Считать из файла" class="btn btn-primary">
    </div>
    <div class="row third-row">
        <div id="int"></div><br>
        <input type="button" value="Записать высоты в файл" class="btn btn-primary">
        <input type="button" value="Считать высоты из файла" class="btn btn-primary">
    </div>
</div>
</body>
</html>
