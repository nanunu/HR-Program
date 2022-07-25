function description(num,page){    
    var url = "/html/time/desc_card_ver1.html?number="+num+"&page="+page;    //기본 승인팝업창 주소. --> 신청사유있는 버전.
    var option = "width=500px,height=500px,left=700px,top=300px";
    if(page=="free"){ url= "/html/time/desc_card_ver2.html?number="+num+"&page="+page; } // 탄력근무제 신청시에만 신청사유없는 버전으로 팝업출력
    window.open(url,"_blank",option);
}
