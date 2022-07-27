



// ============= Comfirm_form_ver1 용 JS ============= //



function Confirm_startday(start_day){
	let end_day = document.getElementById("endday");
	alert(start_day);
	let start = new Date(start_day.value);
	let end = new Date(start_day.value);
	let type = document.getElementById("work")[document.getElementById("work").selectedIndex].value;
	alert(type);
	
	if(type=="OverTime"){
	 	let MM = end.getMonth()+1; 
        let dd = end.getDate();  
        
        //MM 이 1~9월이면 if문 실행
        //+1을 해주는 이유 - Date객체는 월을 0부터 시작함.
        if(end.getMonth()+1 < 10) { MM = "0"+(end.getMonth()+1); }// 01...09 형식으로 변환.
        
        //dd가 9일이하면 if 문 실행
        //일자를 가져오는 함수. 
        if(end.getDate() < 10) { dd = "0"+end.getDate(); }// 01...09 형식으로 변환.

        var text = end.getFullYear()+"-"+MM+"-"+dd; // yyyy-mm-dd형식으로 만듦.        
        end_day.value = text; // input date태그에 값 넣기
	}
	else{
		let code_value = HolidayDay_getvalue;
		alert(code_value);
	}
	
}
/*
	근무코드 value값 반환하는 함수
*/
function Holiday_getvalue(){
	return document.getElementById("Hcode_select")[document.getElementById("Hcode_select").selectIndex].value;
}

/*
    근무신청 유형 select값에 따라 근무코드 활성화/비활성화 시키는 함수
*/
function Confirm_select(type_tmp){
    
    var type = type_tmp[type_tmp.selectedIndex].value;        
    var code_tmp=document.getElementById('Hcode_select'); 
    
    //근무신청이 바뀔때마다 신청일자삭제.
    document.getElementById("startday").value="";
    document.getElementById("endday").value="";

    //근무신청유형이 휴가근무신청이 아닐경우 근무코드 비활성화시키는 함수.
    if(type!="HoliRecord"){ 
		code_tmp.value=null; 
		code_tmp.setAttribute("disabled",null);
	}
    else{ code_tmp.removeAttribute("disabled",null); }

}

/*
    근무신청 유형 select값 - over (초과근무일때)실행되는 함수
    당일 처리되는 근무임으로 시작일과 종료일이 같다. 8시간.(08시~17시)
*/
function over_time_checking(){
    var select = document.getElementById('confirm_select');
    var select_value = select[select.selectedIndex].value;

    if(select_value!="over"){
        alert("이 항목란은 초과 시간근무일 경우에만 작성 할 수 있습니다.");
        select_value=null;
        document.getElementById('confirm_select').focus();
        return false;
    }
}
/*
 	최종 유효성감사하는 함수.
*/
function Confirm_Checking_ver1(){
	/*
	let start_day= document.getElementById("startday")[document.getElementById("startday").selectedIndex].value;
	let end_day= document.getElementById("endday")[document.getElementById("endday").selectedIndex].value;
	let work= document.getElementById("work")[document.getElementById("work").selectedIndex].value;
	let Hcode= document.getElementById("Hode")[document.getElementById("Hcode").selectedIndex].value;
	let start_time= document.getElementById("starttime")[document.getElementById("starttime").selectedIndex].value;
	let end_time= document.getElementById("endtime")[document.getElementById("endtime").selectedIndex].value;
	*/
	let work =  document.getElementById("confirm_select");
	
	document.form_data.action = work[work.selectedIndex].value+".do";
	document.form_data.submit();
}



// ============= Comfirm_form_ver2 용 JS ============= //



/*
    탄력근무 신청시 사용되는함수
    신청일자 startday에 값이 입력이 되면, 날짜를 계산하는 함수
*/
function Confirm_day(date){ 
	
    var end_day = document.getElementById("endday");
    
    var week_day = new Date(date.value);// 2022-07-25.
    var end_date = new Date(date.value);// 2022-07-25.

    var week_text = ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"];
	
    if(week_day.getDay() == 1){
    	 
        end_date.setDate(week_day.getDate()+4); 
        
        var MM = end_date.getMonth()+1; 
        var dd = end_date.getDate();  
        
        //MM 이 1~9월이면 if문 실행
        //+1을 해주는 이유 - Date객체는 월을 0부터 시작함.
        if(end_date.getMonth()+1 < 10) { MM = "0"+(end_date.getMonth()+1); }// 01...09 형식으로 변환.
        
        //dd가 9일이하면 if 문 실행
        //일자를 가져오는 함수. 
        if(end_date.getDate() < 10) { dd = "0"+end_date.getDate(); }// 01...09 형식으로 변환.

        var text = end_date.getFullYear()+"-"+MM+"-"+dd; // yyyy-mm-dd형식으로 만듦.        
        end_day.value = text; // input date태그에 값 넣기        
       	
       	return true;        
        /*
            요일별로 근무 시간 입력폼 만들기.        
	        var div_box = document.getElementById("input-box");
	        for(var i = 1;i <= 5; i++ ){// 1: 월요일 ~ 6:토요일 ** ( 0: 일요일 )
	            var div_puls = document.createElement("set-time");            
	            //본사는 탄력근무제를 4시간단위로 끊음. 최소 4시간은 작업해야함. 출근시간 8~17시 최대 근무가능시간 22시까지
	            //number태그는 요일별로 출퇴근시간을 받는 태그.
	            //첫번째 number는 출근시간을 지정하는 태그 -> min =8  기본 출근적용시간 max = 18(오후6시) 하루동안 근무해야하는 4시간을 채우기위해 최대 오후 6시부터까지는 미룰수있음. 기본값 8
	            //두번째 number는 퇴근시간을 지정하는 태그 -> min =12 < 기본 출근시간 8에서 +4시간적용한 시간.  max=22 야간 수당 금지를 위해 10시까지
	            div_puls.innerHTML = "<label>"+week_text[i]+"</label><input type='number' name='free-day-start' id='start_"+i+"' class='form-control set-time' min='8' max='18' value='8'  onchange='freetime("+i+")' />~<input type='number' name='free-day-end' id='end_"+i+"' class='form-control set-time' min='12' max='22' onchange='freetime("+i+")' />";
	            div_box.appendChild(div_puls);
	        }
        */
       
    }
    else{        
        alert('시작일자를 월요일로 맞춰주세요.');
        date.value="";
        return false;
    }

}

/*  
	탄력근무 시작시간과 종료시간 시간차를 구해서 jsp에 출력하는 함수
*/
function freetime(day_class){
	
	let start_time = document.getElementsByName("freedaystart");
	let end_time = document.getElementsByName("freedayend");	
	let label_out = document.getElementById(day_class);
	let index = day_class-1;
	
	if(end_time[index].value == null||end_time[index].value==""){
		return false;
	}
	else if(start_time[index].value==null||start_time[index].value==""){
		return false;
	}
	else{
		let start = Number.parseInt(start_time[index].value);
		let end = Number.parseInt(end_time[index].value);
		
		let time = end - start ;
						
		if(time<4){ 
			label_out.innerHTML = "4시간";
			end_time[index].value = start+4;
		}
		else if(time>12){ 
			label_out.innerHTML = "12시간";
			end_time[index].value = start+12;
		 }
		else{ label_out.innerHTML = time+" 시간"; }
	}				 	
}

/*
	탄력근무 신청버튼 누른후 최종 유효성검사 함수 
 */ 
function Confirm_Checking(){
	
	var start_time = document.getElementsByName("freedaystart");	
	var end_time = document.getElementsByName("freedayend");		

	let total_time = 0;
	for(var index=0;index<start_time.length;index++){
		
		let start = Number.parseInt(start_time[index].value);
		let end = Number.parseInt(end_time[index].value);

		if(start==null||end==null){ total_time=0; break; }
		
		let time = end - start ;		
		total_time = total_time + time;	
	}	

	if(total_time==40){		
		if(Confirm_day(document.getElementById("startday"))){
			if(confirm('정말 결제요청을 하시겠습니까?')){ document.form_data.submit(); }
			else{ return false; }
		}
	}
	else{		
		alert("근무시간을 40시간으로 맞춰주세요!");
		return false;
	}
	
}
