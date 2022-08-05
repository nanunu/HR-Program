function description(pknum,Ecode,page){	
    let url = "./"+page+"card.do?pknum="+pknum+"&Ecode="+Ecode;    //기본 승인팝업창 주소. --> 신청사유있는 버전.
    let option = "width=550px,height=550px,left=700px,top=300px";
    window.open(url,"_blank",option);
}

function description2(num,ecode){    
    let url = "./desc_card_ver2.do?number="+num+"&ecode="+ecode;
    let option = "width=500px,height=500px,left=700px,top=300px";
    // 탄력근무제 신청시에만 신청사유없는 버전으로 팝업출력
    window.open(url,"_blank",option);
}
