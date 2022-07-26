<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<%
	String msg = (String)request.getAttribute("msg"); 
	if(msg!=null){
%>
	<script>alert("<%=msg%>");</script>
<%	
	}
	List<FlextimeDTO> fList = (List<FlextimeDTO>) request.getAttribute("fList");
	Map<String, EmployeeDTO> eDTOmap = (Map<String, EmployeeDTO>) request.getAttribute("eDTOmap");
	Map<String, String> dMap = (Map<String, String>) request.getAttribute("dMap");
	Map<String, String> pMap = (Map<String, String>) request.getAttribute("pMap");
	String dc = (String) request.getAttribute("dc");
	String po = (String) request.getAttribute("po");
	
	//페이징
	Map<String, String> searchMap = (Map<String, String>) request.getAttribute("searchMap");
	PagingDTO pageDTO = (PagingDTO) request.getAttribute("pageDTO");
	
%>
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
                <form action="flextimeProcess.do" class="form-control align">
                    <div class="form-box">
                        <label class="classfy">
                            <span class="classfy-title">부서별</span>
                            <select name="dcode" class="form-select-sm">
                                <option value="all">전체</option>
                                <%
	                                Iterator<String> dkeys = dMap.keySet().iterator();
	                       			while (dkeys.hasNext()) {
	                       				String text="";
		                       			String key = dkeys.next();
		                       			if(key.equals(dc)){ 
		                       				text="selected";
		                       			}
		                       			String value = dMap.get(key);
                                %>
                                <option value="<%=key%>" <%=text%>><%=value%></option>
                                <%
                                	}
                                %>
                            </select>
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">직급별</span>
                            <select name="position" class="form-select-sm">
                                <option value="all">전체</option>
                                <%
                                	Iterator<String> pkeys = pMap.keySet().iterator();
                            		while (pkeys.hasNext()) {
                            			String text="";
                            			String key = pkeys.next();
                            			if(key.equals(po)){
                            				text="selected";
                            			}
                            			String value = pMap.get(key);
                                %>
                                <option value="<%=key%>" <%=text%>><%=value%></option>
								<%
                            		}
								%>
                            </select>
                        </label>
                        <label class="classfy">
                            <span class="classfy-title">날짜별</span>
                            <input type="date" name="pickdate" class="form-control-sm">
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
                        <div class="table-title confirmday-width">결재일자</div>
                        <div class="table-title confirm-width">결재상태</div>
                        <div class="table-title etc">상세내역</div>
                    </div>
					<%
						for(int i=0; i<fList.size(); i++){
							String ecode1 = fList.get(i).getEcode();
							EmployeeDTO eDTO = eDTOmap.get(ecode1);
					%>
                    <div class="row">                
                        <div class="content-text"><%=dMap.get(eDTO.getDcode()) %></div>
                        <div class="content-text"><%=ecode1 %></div>
                        <div class="content-text"><%=eDTO.getEname() %></div>
                        <div class="content-text"><%=pMap.get(eDTO.getPosition()) %></div>
                        <div class="content-text"><%=fList.get(i).getFTstartday()%>~<%=fList.get(i).getFTendday() %></div>                
                        <%
                        	if(fList.get(i).getAdmissionDate()==null){
                        %>
                        <div class="content-text"></div>
                        <%
                        	}else{
                        %>
                        <div class="content-text"><%=fList.get(i).getAdmissionDate() %></div>
                        <%
                        	}
                        %>
                        <div class="content-text"><%=fList.get(i).getFTapproval() %></div>
                        <!--사원번호 들고 이동함-->
                        <div class="content-text" onclick="description2('<%=fList.get(i).getFTCode() %>','<%=fList.get(i).getEcode() %>')">상세내역보기</div>
                    </div>
					<%
						}
					%>
					<div class="pages">
					<%
					
					%>
					</div>
                </div><!-- container end -->
				<div class="pages">
					<%
						int total = pageDTO.getTotal_page();
						int pageNum = pageDTO.getPageNum();
						int start = pageDTO.getStartpage();
						int end = pageDTO.getEndpage(); 
						
						String URL ="";
						
						//if : searchMap 있을 때, else : searchMap없을 때 (검색하지 않았을 때)
						if(searchMap!=null){
							URL = "flextimeProcess.do?dcode="+dc+"&position="+po+"&pickdate="+searchMap.get("pickdate")+"&ecodeNename="+searchMap.get("ecodeNename")+"&pageNum=";
						}else{
							URL = "free_work.do?pageNum=";
						}
						if(pageNum==1){
					%>
						<div class="left"><a><i class="fa-solid fa-angle-left"></i></a></div>
					<%
						}else{
					%>
						<div class="left"><a href="<%=URL%><%=pageNum-1%>"><i class="fa-solid fa-angle-left"></i></a></div>
					<%
						}
						for(int i=start; i<=end; i++){
							if(i==pageNum){
					%>
					<div class="number"><a href="<%=URL%><%=i%>"><b style="color:red;"><%=i%></b></a></div>
					<%
							}else{
					%>
					<div class="number"><a href="<%=URL%><%=i%>"><b style="color:black;"><%=i%></b></a></div>
					<%
							}			
						}
						if(pageNum==end){
					%>
						<div class="right"><a><i class="fa-solid fa-angle-right"></i></a></div>
					<%
						}else{
					%>
						<div class="right"><a href="<%=URL%><%=pageNum+1%>"><i class="fa-solid fa-angle-right"></i></a></div>
					<%		
						}		
					%>
				</div>
            </div><!-- view-container end -->
	<%@ include file="/footer.jsp" %>
</body>
</html>