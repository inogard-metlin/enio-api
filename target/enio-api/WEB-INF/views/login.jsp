<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ENIO API</title>
  
  <!-- Bootstrap -->
  <link href="/webjars/gentelella/1.4.0/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="/css/login.css" rel="stylesheet" />
</head>
<body>
  <div class="login">
    <h1>ENIO API</h1>
    <c:if test="${not empty error}">
      <div class="alert alert-danger text-center">
        <strong><c:out value="${error}" /> </strong>
      </div>
    </c:if>
    <form action="/login" method="post" >
      <input type="text" name="username" placeholder="Username" required="required" />
      <input type="password" name="password" placeholder="Password" required="required" />
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      <button type="submit" class="btn btn-primary btn-block btn-large">Login</button>
    </form>
  </div>
  <!-- jQuery -->
  <script src="/webjars/gentelella/1.4.0/vendors/jquery/dist/jquery.min.js"></script>
  <script src="/js/enio.js"></script>
  <script>
    (function(enio, $, window, document) {
      var page = {
        load: function() {
          $("input[name='username']").focus();
        }
      };
      $(page.load);
    }(window.enio || {}, window.jQuery, window, document));
  </script>
</body>
</html>