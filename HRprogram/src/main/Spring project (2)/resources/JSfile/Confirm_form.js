/*
    근무신청 유형 select값에 따라 근무코드 활성화/비활성화 시키는 함수
*/
function Confirm_select(type_tmp){
        
    var code_tmp=document.getElementById('code_select'); 
    var type = type_tmp[type_tmp.selectedIndex].value;

    //근무신청이 바뀔때마다 신청일자삭제.
    document.getElementById("startday").value="";
    document.getElementById("endday").value="";

    //근무신청유형 - 탄력근무선택후 생성된 div를 삭제하는 로직
    var div_box = document.getElementById('input-box');
    while(div_box.hasChildNodes()) { div_box.removeChild(div_box.firstChild); }

    //근무신청유형 - 휴가근무선택시 근무코드 활성화시키는 코드. 아닐시 비활성화
    if(type!="off"){ code_tmp.value="not"; code_tmp.setAttribute("disabled","true"); }
    else{ code_tmp.removeAttribute("disabled","flase"); }

}

/*
    근무신청 유형 select가 선택되어있는 상태를 가정으로
    신청일자 startday에 값이 입력이 되면,
    근무신청유형에 따라 날짜를 계산하는 함수로 이동시키는 함수.
*/
function Confirm_day(date){    
    let end_day = document.getElementById("endday");
    
    var week_day = new Date(date.value);
    var end_date = new Date(date.value);

    var week_text = ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"];

    if(week_day.getDay() == 1){
        end_date.setDate(week_day.getDate()+4);        
        var MM = end_date.getMonth()+1; //+1을 해주는 이유 - Date객체는 월을 0부터 시작함.
        var dd = end_date.getDate(); //일자를 가져오는 함수.
        //MM 이 1~9월이면 if문 실행
        if(MM < 10) { MM = "0"+(end_date.getMonth()+1); }// 01...09 형식으로 변환.
        //dd가 9일이하면 if 문 실행 
        if(dd < 10) { dd = "0"+end_date.getDate(); }// 01...09 형식으로 변환.

        var text = end_date.getFullYear()+"-"+MM+"-"+dd; // yyyy-mm-dd형식으로 만듦.        
        end.value = text; // input date태그에 값 넣기
        alert(end.value);
        /*
            요일별로 근무 시간 입력폼 만들기.
        */
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
       
    }
    else{        
        alert('시작일자를 월요일로 맞춰주세요.');
        date.value="";
        return false;
    }

}

function day_free(date,end){     
    
}
/*

*/
function day_off(date,end){

    var code = document.getElementById('code_select');
    var code_value = code[code.selectedIndex].value;

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

function freetime(weekday){
    
    var start = document.getElementById("start_"+weekday).value;
    var end = document.getElementById("end_"+weekday).value;

    var start_date = new Date(start);
    var end_date = new Date(end);

    if(start != null & end != null){ 
        let code_option = document.getElementById("code_select")[document.getElementById("code_select").selectedIndex].value;
        switch(code_option){
            case "x": break;
            case "x": break;
            case "x": break;
            case "x": break;
            case "x": break;
            case "x": break;
            case "x": break;
            case "x": break;
        }
    }

}