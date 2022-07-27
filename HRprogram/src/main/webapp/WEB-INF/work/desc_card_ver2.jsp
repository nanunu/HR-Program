<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import = "model.FlextimeDTO" %>
<%@ page import = "model.EmployeeDTO" %>
<c:set var="path" value="${pageContext.request.contextPath}" /> <!-- 서버의 루트경로 잡기 -->   
<%
	FlextimeDTO ftDTO = (FlextimeDTO) request.getAttribute("ftDTO");
	EmployeeDTO eDTO = (EmployeeDTO) request.getAttribute("eDTO");
	String dname = (String) request.getAttribute("dname");
	long[] time = (long[]) request.getAttribute("time");
%>
<!DOCTYPE html>
<html>
<head>
	<title>상세설명</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/ef95c4be0b.js" crossorigin="anonymous"></script>        
    <script type="text/javascript" src="${path}/resources/JSfile/Description.js"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Desc_card.css" />
</head>
<body>
    <div class="desc-container">
        <form action="#" name="descform" method="post">
            <div class="box">
                <div class="title">부서명</div><div class="contents"><%=dname%></div>                    
                <div class="title">직급</div><div class="contents"><%=eDTO.getPosition()%></div>
            </div>
            <div class="box">
                <div class="title">사원번호</div><div class="contents"><%=eDTO.getEcode()%></div>
                <div class="title">사원명</div><div class="contents"><%=eDTO.getEname()%></div>
            </div>
            <div class="box">
                <div class="title">탄력근무적용일</div><div class="contents day"><%=ftDTO.getFTstartday()%> ~ <%=ftDTO.getFTendday()%></div>                    
            </div>
            <div class="week-list">
                <div class="title">월요일</div><div class="contents"><%=ftDTO.getMonStart()%> ~ <%=ftDTO.getMonend()%></div><div class="time"><%=time[0]%>시간</div>
                <div class="title">화요일</div><div class="contents"><%=ftDTO.getTueStart()%> ~ <%=ftDTO.getTueend()%></div><div class="time"><%=time[1]%>시간</div>
                <div class="title">수요일</div><div class="contents"><%=ftDTO.getWedStart()%> ~ <%=ftDTO.getWedend()%></div><div class="time"><%=time[2]%>시간</div>
                <div class="title">목요일</div><div class="contents"><%=ftDTO.getThuStart()%> ~ <%=ftDTO.getThuend()%></div><div class="time"><%=time[3]%>시간</div>
                <div class="title">금요일</div><div class="contents"><%=ftDTO.getFriStart()%> ~ <%=ftDTO.getFriend()%></div><div class="time"><%=time[4]%>시간</div>
                <div class="title">총 근무시간</div><div class="contents"></div><div class="time"><%=time[5]%>시간</div>
            </div>
            <div class="btn-right">
                <input type="button" value="승인완료" class="btn btn-secondary" disabled />                
                <input type="button" value="승인확인" class="btn btn-primary" onclick="con('ok')" />
                <input type="button" value="승인취소" class="btn btn-danger" onclick="con('no')" />	
            </div>
        </form>
    </div>
</body>
<script>
    function con(result){

        var text = "완료";
        var url = "/result_ok.do";

        if(result!="ok"){ text="취소"; url="/result_fail.do"; }    

        if(confirm(text)){ 
            document.descform.action = url;
            document.descform.submit();
        }   
        else{ return false; }
        
    }
</script>
</html>