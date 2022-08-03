<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="model.*" %>
<%
	ArrayList<DepartmentDTO> DepartList = (ArrayList<DepartmentDTO>) request.getAttribute("DepartLIST");
	ArrayList<PositionDTO> PositionList = (ArrayList<PositionDTO>) request.getAttribute("PositionLIST");
	ArrayList<HolidayRecordDTO> SearchList = (ArrayList<HolidayRecordDTO>) request.getAttribute("SearchLIST");
	Map<String,String> Allstaff = (Map<String,String>) request.getAttribute("Allstaff");
	String modle_Dcode = request.getParameter("Dcode");
	String modle_Position = request.getParameter("Position");
%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header.jsp" %>
 	<title>휴가/결근 조회 및 신청</title>
    <script type="text/javascript" src="${path}/resources/JSfile/Description.js"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Free_work.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
	            <!-- content 내용 출력구분.  -->
            <div class="nav-giude">근태관리 > 휴가/결근 내역 및 신청</div>
            <div class="filter-form">
                <form action="day_off.do" class="form-control align">
                    <div class="form-box">
                        <label class="classfy">
                            <span class="classfy-title">부서별</span>
                            <select name="dcode" class="form-select-sm">
                                <option value="all">전체</option>
                                <%
                                	for(int i=0;i<DepartList.size();i++){
                                		DepartmentDTO dto = DepartList.get(i);                                		
                                		if(modle_Dcode!=null&&modle_Dcode.equals(dto.getDcode())){  out.print("<option value='"+dto.getDcode()+"' selected >"+dto.getDname()+"</option>");  }
                                		else if(dcode.equals(dto.getDcode())){ out.print("<option value='"+dto.getDcode()+"' selected >"+dto.getDname()+"</option>");  }
                                		else{ out.print("<option value='"+dto.getDcode()+"' >"+dto.getDname()+"</option>"); }
                                	}
                                %>
                            </select>
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">직급별</span>
                            <select name="position" class="form-select-sm">
                                <option value="all">전체</option>
                                <%
                                	for(int i=0;i<PositionList.size();i++){
                                		PositionDTO dto = PositionList.get(i);
                                		String selected="";
                                		if(modle_Position!=null&&modle_Position.equals(dto.getPosition())){ selected="selected"; }
                                		out.print("<option value='"+dto.getPosition()+"' "+selected+" >"+dto.getPname()+"</option>");
                                	}
                                %>
                            </select>
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">날짜별</span>
                            <input type="date" name="date" class="form-control-sm" value="" />
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">사원번호 혹은 사원명</span>
                            <input type="text" name="ecodeN" value="" />
                        </label>
                        <input type="submit" class="btn-white search-btn" value="조회하기"/>
                    </div>
                </form>
            </div>
            <div class="view-container">                    
                <div class="container">
                    <div class="row">
                        <div class="table-title class-width">부서명</div>
                        <div class="table-title number-width">사원번호</div>                        
                        <div class="table-title name-width">사원명</div>
                        <div class="table-title rank-width">직급</div>
                        <div class="table-title date-width">신청일자</div>                        
                        <div class="table-title confirm-width">승인여부</div>
                        <div class="table-title etc">상세내역</div>
                    </div>
					<%
						for(int i=0; i < SearchList.size(); i++){
							HolidayRecordDTO dto = SearchList.get(i);							
							LocalDate date =  LocalDate.parse(dto.getHoliRuseday());// 적용 시작일
							LocalDate plusdate = date.plusDays(dto.getHoliRdays());// 적용 시작일 에서 사용일수 +한 날짜
							String Ecode = dto.getEcode();
							String condition = dto.getHoliRapproval();
							
							String viewtext="";
							if(condition.equals("waiting")){ viewtext = "결제대기"; }
							else if(condition.equals("cancel")){ viewtext = "결제반려"; }
							else{ viewtext="승인완료"; }
							
							String position_text="";
							if(Allstaff.get(Ecode+"_Position")=="CCC"){ position_text="사원"; }
							else if(Allstaff.get(Ecode+"_Position")=="BBB"){ position_text="팀장"; }
							else{ position_text="대표이사"; }
							
							String class_text="";
							if(Allstaff.get(Ecode+"Dcode")=="A001"){ class_text="경영인사팀";}
							else{ class_text="개발팀"; }
					%>	
                    <div class="row">                
						<div class="content-text"><%=class_text%></div>
                        <div class="content-text"><%=Ecode%></div>
                        <div class="content-text"><%=Allstaff.get(Ecode+"_Ename") %></div>
                        <div class="content-text"><%=position_text %></div>
                        <div class="content-text"><%=date.toString()%> ~ <%=plusdate.toString() %></div>
                        <div class="content-text"><%=viewtext %></div>
                        <!--사원번호 들고 이동함-->
                        <div class="content-text" onclick="description('<%=Ecode%>','off')">상세내역보기</div>
                    </div>
					<%
						}
					%>                   
                </div><!-- container end -->

            </div><!-- view-container end -->
	<%@ include file="/footer.jsp" %>
</body>
</html>
