<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="path" value="${pageContext.request.contextPath}" /> <!-- 서버의 루트경로 잡기 -->    
<%
	if(request.getParameter("mailsend") != null){
%>
	<script>alert('임시 비밀번호가 발송되었습니다! 이메일을 확인해주세요.');</script>
<%		
	}
%>
<!DOCTYPE html>
<html>
<head>
	<title>로그인</title>
	<link rel="stylesheet" href="${path}/resources/CSSfile/Login.css" />
    <script src="https://kit.fontawesome.com/ef95c4be0b.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="${path}/resources/JSfile/Login.js"></script>        
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">     
</head>
<body>
	<div class="login-container">
            <div class="company-logo">Team_Company</div>
            <form action="login.do" method="post" id="login_form" name="login_form">
                <input type="text" name="Ecode" id="Ecode" class="form-control" placeholder="사원번호" />
                <label class="login-fail">사원번호를 입력해주세요!</label>
                <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호" />
                <label class="login-fail">비밀번호를 입력해주세요!</label>                            
            </form>
            <div class="form-button">
                <a class="lost" onclick="location.href='./lost_pw.jsp'">비밀번호 찾기</a>
                <a class="login" onclick="login()"> 로그인</a>
            </div>
            <div class="copy">
               <p>Copy All Right &copy; 2022 All Right Reserved</p>
            </div>
       </div>
</body>
</html>