<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import="java.util.*" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>공용 CSS틀</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/ef95c4be0b.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<c:url value='/resources/JSfile/Index.js' />"></script>
    <script type="text/javascript" src="<c:url value='/resources/JSfile/Login.js' />"></script>
    <!-- <link rel="stylesheet"  href="<c:url value='/resources/CSSfile/Index.css' />" /> -->
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Index.css" />
    <link rel="stylesheet"  href="<c:url value='/resources/CSSfile/Work_record.css' />" />
</head>
<body>
    <div class="main-container">
        <div class="left-container">
            <a class="company-logo" href="/html/index.html"></a>
            <div class="user-box">
                <img src="/resources/image/staff/Sample_img.jpg" class="profile_img" />
                <div class="profile_info">
                    <p class="class">대표이사</p>
                    <span class="name">김xx 이사</span>
                </div>
            </div>
            <a class="title-menu" onclick="submenu_open('time')"><i class="fa-solid fa-calendar-check"></i><span class="title-text">근태관리</span><i class="fa-solid fa-angle-right angle"></i></a>
            <a class="title-menu" onclick="submenu_open('money')"><i class="fa-solid fa-comment-dollar"></i><span class="title-text">급여관리</span><i class="fa-solid fa-angle-right angle"></i></a>
            <a class="title-menu" onclick="submenu_open('view')"><i class="fa-solid fa-circle-question"></i><span class="title-text">직원조회</span><i class="fa-solid fa-angle-right angle"></i></a>
            <a class="title-menu" onclick="submenu_open('etc')"><i class="fa-solid fa-circle-question"></i><span class="title-text">Sample_Menu</span><i class="fa-solid fa-angle-right angle"></i></a>                               
            <!-- Index.js로 서브메뉴 추가. -->
            <div class="sub-left-container" id="sub-menubar">
                <ul class="sub-menu" id="ul">
                    <!-- js참고 함수로 li추가. -->
                </ul>
            </div>
        </div>

        <!-- main view -->
        <div class="right-container">
            <!-- 상단 메뉴바 출근 퇴근etc -->
            <nav class="nav-bar">
                <div class="nav-box">                        
                    <a class="btn btn-link" href="/html/input_form/confirm_form.html">근태관련신청하기</a>
                    <a class="button_log" href="#loout" onclick="logout()">퇴근하기</a>
                </div>
            </nav>
            <!-- content 내용 출력구분.  -->














        </div><!-- right-container end -->
    </div><!-- main-container end -->
</body>
</html>