<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<%
	List<DepartmentDTO> dList = (List<DepartmentDTO>) request.getAttribute("dList");
	List<FlextimeDTO> fList = (List<FlextimeDTO>) request.getAttribute("fList");
	Map<String, EmployeeDTO> map = (Map<String, EmployeeDTO>) request.getAttribute("map");
%>
<html>
<head>
	<%@ include file="/header.jsp" %>
 	<title>탄력근무제 조회 / 승인</title>
    <script type="text/javascript" src="${path}/resources/JSfile/Description.js?ver=<%=System.currentTimeMillis()%>"></script>
    <link rel="stylesheet"  href="${path}/resources/CSSfile/Free_work.css" />
</head>
<body>
	<%@ include file="/main-container.jsp" %>
		            <!-- content 내용 출력구분.  -->
            <div class="nav-giude">근태관리 > 탄력근무제 조회 및 승인</div>
            <div class="filter-form">
                <form action="#" class="form-control align">
                    <div class="form-box">
                        <label class="classfy">
                            <span class="classfy-title">부서별</span>
                            <select name="class" class="form-select-sm">
                                <option value="all">전체</option>
                                <%
                                	for(int i=0; i<dList.size(); i++){
                                %>
                                <option value="<%=dList.get(i).getDcode() %>"><%=dList.get(i).getDname() %></option>
                                <%
                                	}
                                %>
                            </select>
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">직급별</span>
                            <select name="rank" class="form-select-sm">
                                <option value="all">전체</option>
                                <option value="staff">사원</option>
                                <option value="general">팀장</option>
                                <option value="ceo">대표이사</option>
                            </select>
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">날짜별</span>
                            <input type="date" name="date" class="form-control-sm">
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">사원번호 혹은 사원명</span>
                            <input type="text" name="number" />
                        </label>
                        <input type="button" class="btn-white search-btn" value="조회하기"/>
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
                        <div class="table-title confirmday-width">승인일자</div>
                        <div class="table-title confirm-width">승인여부</div>
                        <div class="table-title etc">상세내역</div>
                    </div>
					<%
						for(int i=0; i<fList.size(); i++){
							String ecode = fList.get(i).getEcode();
							EmployeeDTO eDTO = map.get(ecode);
					%>
                    <div class="row">                
                        <div class="content-text">부서이름</div>
                        <div class="content-text"><%=ecode %></div>
                        <div class="content-text"><%=eDTO.getEname() %></div>
                        <div class="content-text"><%=eDTO.getPosition() %></div>
                        <div class="content-text"><%=fList.get(i).getFTstartday()%>~<%=fList.get(i).getFTendday() %></div>                
                        <div class="content-text"><%=fList.get(i).getAdmissionDate() %></div>
                        <div class="content-text"><%=fList.get(i).getFTapproval() %></div>
                        <!--사원번호 들고 이동함-->
                        <div class="content-text" onclick="description('<%=fList.get(i).getEcode() %>','free')">상세내역보기</div>
                    </div>
					<%
						}
					%>
                </div><!-- container end -->

            </div><!-- view-container end -->
	<%@ include file="/footer.jsp" %>
</body>
</html>