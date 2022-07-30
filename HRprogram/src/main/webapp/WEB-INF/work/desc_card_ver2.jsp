<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import = "model.FlextimeDTO" %>
<%@ page import = "model.EmployeeDTO" %>
<%@ page import = "java.time.LocalDate" %>
<c:set var="path" value="${pageContext.request.contextPath}" /> <!-- 서버의 루트경로 잡기 -->   
<%
	if(session.getAttribute("Ecode")==null){ out.print("<script>location.replace('login.do');</script>"); }
	
	String position = (String) session.getAttribute("position");
	String ecode = (String) session.getAttribute("Ecode");
	String dcode = (String) session.getAttribute("Dcode");
	FlextimeDTO ftDTO = (FlextimeDTO) request.getAttribute("ftDTO");
	EmployeeDTO eDTO = (EmployeeDTO) request.getAttribute("eDTO");
	String dname = (String) request.getAttribute("dname");
	String pname = (String) request.getAttribute("pname");
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
                <div class="title">직급</div><div class="contents"><%=pname%></div>
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
          		<!-- 
          			결재승인하는 경우(팀장 / 이사) 
					"waiting";
					결재승인 ==> 함수실행
					반려 ==> 함수실행
					
					"back"; "cancel";
					결재취소 ==> disabled
					반려 ==> disabled
					
					"completed";
					결재완료 ==> disabled
					결재취소 ==> 함수실행 (DB에 back으로 변경)
					******* 시작일이 되면 결재완료, 결재취소 disabled
          		 -->
                <%
                	/*((session:이사&&신청:팀장)||(session:팀장&&신청:사원&&팀장부서==사원부서))*/
                	if((position.equals("AAA") && eDTO.getPosition().equals("BBB"))||
                			((position.equals("BBB") && eDTO.getPosition().equals("CCC")) && 
                				dcode.equals(eDTO.getDcode()))){
                		String text1="";
                		String text2="";
                		String text3="";
                		String text4="";
                		String text5="";
 						if(LocalDate.now().isAfter(LocalDate.parse(ftDTO.getFTstartday().toString()))){
 							text1="결재완료";
 							text2="결재취소";
 							text3="disabled";
 							text4="disabled";
 						}else{
 							if(ftDTO.getFTapproval().equals("waiting")){
 	 							text1="결재승인";
 	 							text2="반려";
 	 							text5="no11";
 	 						}else if(ftDTO.getFTapproval().equals("completed")){
 	 							text1="결재완료";
 	 							text2="결재취소";
 	 							text3="disabled";
 	 							text5="no11";
 	 						}else if(ftDTO.getFTapproval().equals("back")||ftDTO.getFTapproval().equals("cancel")){
 	 							text1="결재취소";
 	 							text2="반려";
 	 							text3="disabled";
 	 							text4="disabled";
 	 						}
 						}
                %>
                <input type="button" value="<%=text1%>" class="btn btn-primary" onclick="con('ok')" <%=text3%>/>
                <input type="button" value="<%=text2%>" class="btn btn-danger" onclick="con('<%=text5%>')" <%=text4%>/>
                <!-- 
                	**본인세션일 경우**
                	결재승인받는 경우(사원 / 팀장)
					"waiting"; 
					결재취소 ==> 함수실행 (DB에서 삭제)
					
					"back";
					반려 ==> disabled
					
					"cancel";
					결재취소 ==> disabled
					
					"completed";
					결재취소 ==> 함수실행 (DB에 calcel로 변경)
					******* 시작일이 되면 결재취소 disabled
                 -->
                <%
                	}else if(ecode.equals(eDTO.getEcode())){
                		String text1="결재취소";
                		String text2="";
                		String text3="";
                		if(LocalDate.now().isAfter(LocalDate.parse(ftDTO.getFTstartday().toString()))){
                			text3="disabled";
                		}else{
                    		if(ftDTO.getFTapproval().equals("waiting")){
                    			text2="no22";
                    		}else if(ftDTO.getFTapproval().equals("back")){
                    			text1="반려";
                    			text3="disabled";
                    		}else if(ftDTO.getFTapproval().equals("cancel")){
                    			text3="disabled";
                    		}else if(ftDTO.getFTapproval().equals("completed")){
                    			text2="no22";
                    		}
                		}
                %>
                <input type="button" value="<%=text1%>" class="btn btn-danger" onclick="con('<%=text2%>')" <%=text3%>/>
                <%
                	}else{
                %>
                <%		
                	}
                %>	
            </div>
        </form>
    </div>
</body>
<script>
    function con(result){
	
        let text = "결재를 승인하시겠습니까?";
        let url = "result_ok2.do?FTcode=<%=ftDTO.getFTCode()%>";
        
        if(result=="no11"){    
        	text = "결재를 취소하시겠습니까?";
        	url="result_fail11.do?FTcode=<%=ftDTO.getFTCode()%>&FTapproval=<%=ftDTO.getFTapproval()%>";
        	
        }else if(result=="no22"){   
        	text = "결재를 취소하시겠습니까?";
        	url="result_fail22.do?FTcode=<%=ftDTO.getFTCode()%>&FTapproval=<%=ftDTO.getFTapproval()%>";
        }
        
        if(confirm(text)){ 
            document.descform.action = url;
            document.descform.submit();
        }   
        else{ return false; }
        
    }
</script>
</html>