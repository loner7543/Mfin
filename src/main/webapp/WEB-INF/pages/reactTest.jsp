<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 004 04.09.17
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin</title>
    <script type="text/javascript" src="<c:url value="/js/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/plotly/plotly.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/react.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/react-dom.min.js" />"></script>
</head>
<body>
<div id="app"></div>
<script>
    ReactDOM.render(
        React.DOM.h1(
            {
                style: {
                    background: "black",
                    color: "white",<!--Исп-ся названия css свойств из react-->
                    fontFamily: "Verdana",
                }
            },
            "Hello World!"
        ), <!--1- свойства 2- дочерний элемент что рендерить и куда рендерить-->
        document.getElementById("app")
    );
</script>
</body>
</html>
