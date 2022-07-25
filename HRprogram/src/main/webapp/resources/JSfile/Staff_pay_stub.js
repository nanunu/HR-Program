function pay_stub_open(number){
    var url = "/html/money/pay_stub_pop.html?number="+number;    
    var option = "width=850px,height=900px,left=400px";
    window.open(url,"_blank",option);
}

var call = false;
function checkbox_all(){        
    var checkbox = document.querySelectorAll(".staff_list");    
    if(call == false){ // 전체선택 안했을시 & 취소했을 상황에 실행 ====> 전체선택
        for(var i = 0;i<checkbox.length;i++){ checkbox[i].checked = true; }
        call = true;
    }
    else{ // 전체선택 선택했을시 실행 ====> 전체선택취소
        for(var i = 0;i<checkbox.length;i++){ checkbox[i].checked = false; }
        call = false; // 
    }
}

let calculate = true;
function pay_cal(number){
    if(!calculate){ calculate=true; }
    location.replace("/staff_pay_pop.html?number="+number);
}
function admission(){
    if(!calculate){ 
        alert('정산버튼을 먼저 눌러주세요.');
        return false;
    }
    
    if(confirm('정산을 완료하시겠습니까?')){
        location.replace("./staff_pay_pop.html?");
        opener.location.reload();
        window.close();
    }
}