
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate, java.time.LocalTime"%>
<%@page import="java.util.List"%>
<%@page import="model.DepartmentDTO, model.PositionDTO, model.EmployeeDTO, model.OverTimeDTO, model.HolidayRecordDTO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="path" value="${pageContext.request.contextPath}" /> <!-- 서버의 루트경로 잡기 -->
<%
	String card_class = (String) request.getAttribute("Card_class");

	List<DepartmentDTO> Dlist = (List<DepartmentDTO>) request.getAttribute("DepartLIST");
	List<PositionDTO> Plist = (List<PositionDTO>) request.getAttribute("PositionLIST");
	EmployeeDTO Info = (EmployeeDTO) request.getAttribute("StaffDTO");

	String condition = "", Reason = "" , startday ="" , endday = "", starttime = "", endtime = "";
	

	if(card_class.equals("OverTime")){
		OverTimeDTO dto = (OverTimeDTO) request.getAttribute("SearchDTO");
		Reason = dto.getOtReason();//신청사유
		condition = dto.getOtApproval();//결제상태
		
		startday = dto.getOtDay();
		endday = dto.getOtDay();
		
		starttime = dto.getOtStartTime().substring(0,5);
		endtime = dto.getOtEndTime().substring(0,5);
		
	}
	else{
		HolidayRecordDTO dto = (HolidayRecordDTO) request.getAttribute("SearchDTO");
		
		Reason = dto.getHoliRreason();//신청사유
		condition = dto.getHoliRapproval();//결제상태
		
		
		LocalDate start = LocalDate.parse(dto.getHoliRuseday());// 신청일 시작일
		startday = start.toString();
		
    	if(dto.getHolicode().substring(0,2).equals("H1")){ //휴가코드가 경조사일경우	
    		LocalDate end = start.plusDays(dto.getHoliRdays());//신청일 종료일
    		
    		endday = end.toString();
    	}
    	else{//휴가코드가 그외 나머지일경우,
    		LocalDate end = start;//신청일 종료일  
    		
    		starttime = dto.getHoliRstarttime().substring(0,5);
    		endtime = dto.getHoliRendtime().substring(0,5);
    		
    		endday = end.toString();    		
    	}
    	
		
	}	
	
	

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
            <%
            	//부서명 for
            	for(int i=0;i<Dlist.size();i++){
            		DepartmentDTO Ddto = Dlist.get(i);
            		if(Info.getDcode()==Ddto.getDcode()){
            			out.print("<div class='title'>부서명</div><div class='contents'>"+Ddto.getDname()+"</div>");	
            		}            		
            	}
            	//직급별 for
            	for(int i =0;i<Plist.size();i++){
            		PositionDTO Pdto = Plist.get(i);
            		if(Info.getPosition()==Pdto.getPosition()){
            			out.print("<div class='contents'>팀장</div>''<div class='title'>직급</div><div class='contents'>"+Pdto.getPname()+"</div>");
            		}
            	}
            	
            %>  
            </div>
            <div class="box">
                <div class="title">사원번호</div><div class="contents"><%=Info.getEcode() %></div>
                <div class="title">사원명</div><div class="contents"><%=Info.getEname() %></div>
            </div>
            <div class="box">
                <div class="title">신청일자</div><div class="contents day" style="width:400px"><%=startday%> ~ <%=endday%></div>                                    
            </div>
            <div class="box">
            	<div class="title">신청시간</div><div class="contents day"><%=starttime%> ~ <%=endtime%></div>
            </div>
            <div class="box">
                <div class="title">신청사유</div>
            </div>
            <div class="box">
                <div class="desc-contents"><%=Reason %></div>
            </div>
            <div class="btn-right">
            <%  
            	if(condition.equals("waiting") ){
            		out.print("<input type='button' value='결제승인' class='btn btn-primary' onclick='con('ok')' />");
                    out.print("<input type='button' value='반려' class='btn btn-danger' onclick='con('no')' />");
                    out.print("<input type='button' value='결제취소' class='btn btn-danger' onclick='con('no')' />");
            	}
            	else if(condition.equals("back")){
            		out.print("<input type='button' value='결제승인' class='btn btn-primary' onclick='con('ok')' />");                    
                    out.print("<input type='button' value='결제취소' class='btn btn-danger' onclick='con('no')' />");            		
            	}
            	else{
            		out.print("<input type='button' value='결제승인취소' class='btn btn-danger' onclick='con('no')' />"); 
            	}
            %>  
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