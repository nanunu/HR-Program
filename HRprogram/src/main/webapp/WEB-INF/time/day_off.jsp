<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="model.*" %>
<%
	ArrayList<DepartmentDTO> DepartList = (ArrayList<DepartmentDTO>) request.getAttribute("DepartLIST");
	ArrayList<PositionDTO> PositionList = (ArrayList<PositionDTO>) request.getAttribute("PositionLIST");
	ArrayList<HolidayRecordDTO> SearchList = (ArrayList<HolidayRecordDTO>) request.getAttribute("SearchLIST");
	Map<String,String> Allstaff = (Map<String,String>) request.getAttribute("Allstaff");
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
                                		String selected = "";
                                		if(dcode.equals(dto.getDcode())){ selected = "selected"; }                                		
                                		out.print("<option value='"+dto.getDcode()+"' "+selected+">"+dto.getDname()+"</option>");
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
                                		out.print("<option value='"+dto.getPosition()+"'>"+dto.getPname()+"</option>");
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
                            <input type="text" name="ecodeNename" />
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
							String holiRapproval = dto.getHoliRapproval();
							String viewtext="";
							if(holiRapproval.equals("waiting")){ viewtext = "결제대기"; }
							else if(holiRapproval.equals("cancel")){ viewtext = "결제반려"; }
							else if(holiRapproval.equals("complated")){ viewtext = "승인완료"; }							
					%>	
                    <div class="row">                
                        <div class="content-text">경영(인사)부</div>
                        <div class="content-text"><%=Ecode%></div>
                        <div class="content-text"><%=Allstaff.get(Ecode+"_Ename") %></div>
                        <div class="content-text"><%=Allstaff.get(Ecode+"_position") %></div>
                        <div class="content-text"><%=date.toString()%> ~ <%=plusdate.toString() %></div>
                        <div class="content-text"><%=viewtext %></div>
                        <!--사원번호 들고 이동함-->
                        <div class="content-text" onclick="description('220101A001B','off')">상세내역보기</div>
                    </div>
					<%
						}
					%>                   
                </div><!-- container end -->

            </div><!-- view-container end -->
	<%@ include file="/footer.jsp" %>
</body>
</html>
