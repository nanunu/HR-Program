<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.SessionDTO" %>
<%
	SessionDTO dto = (SessionDTO) session.getAttribute("SessionDTO");
	if(dto==null){ out.print("<script>location.replace('login.do');</script>"); }
%>
    <div class="main-container">
        <div class="left-container">
            <a class="company-logo" href="/html/index.html"></a>
            <div class="user-box">
                <img src="${path}/resources/image/staff/Sample_img.jpg" class="profile_img" />
                <div class="profile_info">
                    <p class="class"><%=dto.getPosition()%>  </p>
                    <span class="name"><%=dto.getEname()%></span>
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
                	<a class="btn btn-link" href="form_ver1.do">휴가/초과근무신청하기</a>                        
                    <a class="btn btn-link" href="form_ver2.do">탄력근무신청하기</a>
                    <a class="button_log" href="#loout" onclick="logout()">퇴근하기</a>
                </div>
            </nav>
            <!-- content 내용 출력구분.  -->
