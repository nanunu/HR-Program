/*1. 부서 정보 : 부서코드, 부서명*/
insert into Department(Dcode, Dname) values('A001', '경영인사팀');
insert into Department(Dcode, Dname) values('B001', '개발팀'); 

/*2. 직급 정보 : 직급코드, 직급명*/
insert into PositionT(position, Pname) values('AAA', '대표이사');
insert into PositionT(position, Pname) values('BBB', '팀장');
insert into PositionT(position, Pname) values('CCC', '사원');

/*2. 사원 정보 : 사원코드, 이름, 부서코드, 직급, 비밀번호, 이메일, 입사일, 생년월일, 우편번호, 주소, 성별, 최종학력, 병역, 결혼여부, 면허자격, 은행, 계좌번호, 예금주, 차량*/
/*사원코드 : 입사년도+월+일+부서코드+z+01~ */
/* 직급 : B - 팀장, C - 사원*/
/* 이사 : CEO_A, CEO_B*/
insert into Employee(Ecode, Ename, Dcode, position, password, email, joinday, phone, birth, zipcode, address, sex, education, military, maritalStatus, licence, bank, bankbook, depositor, car)
      values('CEO_A', '최여진', 'A001', 'AAA', '930601', '6k5bsy@kakao.com', '12-01-01', '010-6564-7809', '930601', '10101', '경남 창원시 의창구', '여', '대졸', false, '미혼', '운전면허2종보통', '우리', '100-1000-11111', '최여진', '63두4167');
insert into Employee(Ecode, Ename, Dcode, position, password, email, joinday, phone, birth, zipcode, address, sex, education, military, maritalStatus, licence, bank, bankbook, depositor)
      values('CEO_B', '김은아', 'B001', 'AAA', '940103', '6k5bsy1@kakao.com', '12-01-01', '010-2183-0258', '940103', '10111', '경남 창원시 마산합포구', '여', '고졸', false,'미혼', '운전면허2종보통', '신한', '123-1234-12345', '김은아');
insert into Employee(Ecode, Ename, Dcode, position, password, email, joinday, phone, birth, zipcode, address, sex, education, military, maritalStatus, licence, bank, bankbook, depositor, car)
      values('160301A001z01', '박해빈', 'A001', 'BBB', '931011', '6k5bsy2@kakao.com', '16-03-01', '010-5199-7400', '931011', '99999', '경남 창원시 마산합포구', '여', '대졸', false, '미혼', '운전면허2종보통', '국민', '66666-777-4444', '박해빈', '16루9115');
insert into Employee(Ecode, Ename, Dcode, position, password, email, joinday, phone, birth, zipcode, address, sex, education, military, maritalStatus, licence, bank, bankbook, depositor, car)
      values('140701A001z01', '김현일', 'A001', 'CCC', '920831', '6k5bsy3@kakao.com', '14-07-01', '010-7456-4742', '920831', '12345', '경남 창원시 성산구', '남', '초대졸', true, '미혼', '운전면허1종보통', '경남', '313-1000-12345', '김현일', '11구6526');
insert into Employee(Ecode, Ename, Dcode, position, password, email, joinday, phone, birth, zipcode, address, sex, education, military, maritalStatus, licence, bank, bankbook, depositor)
      values('160201B001z01', '이창기', 'B001', 'BBB', '920507', '6k5bsy4@kakao.com', '16-02-01', '010-2250-4312', '920507', '55555', '경남 창원시 마산합포구', '남', '대졸', true, '기혼', '운전면허1종보통', '카카오', '9999-9999-99999', '이창기');
insert into Employee(Ecode, Ename, Dcode, position, password, email, joinday, phone, birth, zipcode, address, sex, education, military, maritalStatus, bank, bankbook, depositor)
      values('220701A001z02', '박민후', 'A001', 'CCC', '940926', '6k5bsy5@kakao.com', '22-07-01', '010-5168-8790', '940926', '11111', '경남 함안군 입곡길86', '남', '대졸', true, '미혼', '농협', '811-1224-2821', '박민후');
insert into Employee(Ecode, Ename, Dcode, position, password, email, joinday, phone, birth, zipcode, address, sex, education, military, maritalStatus, bank, bankbook, depositor)
      values('220701B001z01', '김민호', 'B001', 'CCC', '960919', '6k5bsy6@kakao.com', '22-07-01', '010-1111-1111', '960919', '11112', '경남 창원시 마산회원구', '남', '대졸', true, '미혼', '신한', '222-2222-2222', '김민호'); 

      
/*3. 휴가 : 휴가코드, 휴가명칭, 부여시간, 부여일*/
insert into Holiday(Hcode, Hname, Htime) values('H0001', '연차', 8);
insert into Holiday(Hcode, Hname, Htime) values('H0002', '반차', 4);
insert into Holiday(Hcode, Hname) values('H0003', '외출');
insert into Holiday(Hcode, Hname) values('H0004', '지참');
insert into Holiday(Hcode, Hname) values('H0005', '조퇴');
insert into Holiday(Hcode, Hname, Hday) values('H1001', '본인결혼', 5); /*연차차감없음*/
insert into Holiday(Hcode, Hname, Hday) values('H1002', '조사', 3); /*연차차감없음*/

/*4. 사원 휴가 : 사원코드, 부여휴가수, 사용휴가수*/
insert into EH(Ecode, numOfmyholiday) values('CEO_A', 25.000);
insert into EH(Ecode, numOfmyholiday) values('CEO_B', 25.000);
insert into EH(Ecode, numOfmyholiday) values('160301A001z01', 20.000);
insert into EH(Ecode, numOfmyholiday) values('160201B001z01', 20.000);
insert into EH(Ecode, numOfmyholiday, usenumOfholiday) values('140701A001z01', 23.000, 5.125);
insert into EH(Ecode, numOfmyholiday) values('220701A001z02', 6.000);
insert into EH(Ecode, numOfmyholiday) values('220701B001z01', 6.000);

/*5. 휴가 기록 : 휴가기록코드, 사원코드, 휴가코드, 적용일, 시작시간, 종료시간, 사용시간, 사유, 결재상태*/
insert into holiRecord(holiRcode, Ecode, holicode, holiRuseday, holiRstarttime, holiRendtime, holiRusetime, HoliRreason, holiRapproval)
		values(1, '140701A001z01', 'H0004', '22-06-13', '09:00', '10:00', 1, '개인사유', '결재승인');
insert into holiRecord(holiRcode, Ecode, holicode, holiRuseday, holiRdays, holiRreason, holiRapproval) 
		values(2, '140701A001z01', 'H0001', '22-06-20', '5', '개인사유', '결재승인');
insert into holiRecord(holiRcode, Ecode, holicode, holiRuseday, holiRstarttime, holiRendtime, holiRusetime, HoliRreason, holiRapproval)
		values(3, '140701A001z01', 'H0003', '22-06-14', '14:00', '16:00', 2, '개인사유', '반려');

/*6. 출퇴근 기록 : 출퇴근기록코드, 사원코드, 일자, 출근시간, 퇴근시간*/
insert into Commute(Cmcode, Ecode, CmDay, CmAtTime, CmGetoffTime) values(1, '160301A001z01', '22-07-05', '07:50', '17:02');
insert into Commute(Cmcode, Ecode, CmDay, CmAtTime, CmGetoffTime) values(2, '140701A001z01', '22-07-05', '07:40', '17:06');
insert into Commute(Cmcode, Ecode, CmDay, CmAtTime, CmGetoffTime) values(3, '160201B001z01', '22-07-05', '07:50', '17:02');
insert into Commute(Cmcode, Ecode, CmDay, CmAtTime, CmGetoffTime) values(4, '220701A001z02', '22-07-05', '07:50', '17:20');
insert into Commute(Cmcode, Ecode, CmDay, CmAtTime, CmGetoffTime) values(5, '220701B001z01', '22-07-05', '07:50', '17:07');

/*7. 급여(수당) : 급여(수당)코드, 급여(수당)명, 금액*/
insert into Pay(PayCode, PayName, PayMoney) values('P0001', '이사기본급', 8000000);
insert into Pay(PayCode, PayName, PayMoney) values('P0002', '팀장기본급', 5000000);
insert into Pay(PayCode, PayName, PayMoney) values('P0003', '사원기본급', 2000000);
insert into Pay(PayCode, PayName, PayMoney) values('P0004', '식대', 300000);
insert into Pay(PayCode, PayName, PayMoney) values('P0005', '차량유지비', 100000);
insert into Pay(PayCode, PayName, PayMoney) values('P0006', '초과근무', 10000);
insert into Pay(PayCode, PayName, PayMoney) values('P0007', '연차', 100000);
insert into Pay(PayCode, PayName, PayMoney) values('P0010', '결혼축하금', 1000000);

/*8. 공제 : 공제코드, 공제명, 공제율, 공제액*/
insert into Deduction(DeCode, DeName, DeRate) values('D0001', '국민연금', 0.045);
insert into Deduction(DeCode, DeName, DeRate) values('D0002', '국민건강보험', 0.035);
insert into Deduction(DeCode, DeName, DeRate) values('D0003', '장기요양보험', 0.004);
insert into Deduction(DeCode, DeName, DeRate) values('D0004', '고용보험', 0.008);

/*9. 급여명세서 : 명세서코드, 사원코드, 근무월, 급여(수당)코드, 공제코드, 총액, 명세서발송, 지급여부*/
insert into PayStub(PScode, Ecode, PsMonth, AllPayCode, AllDeCode, Sum, send, PSStatus) values(1, 'CEO_A', 6, 'P0001,P0004,P0005', 'D0001,D0002,D0003,D0004', 7746000, true, true);
insert into PayStub(PScode, Ecode, PsMonth, AllPayCode, AllDeCode, Sum, send, PSStatus) values(2, 'CEO_B', 6, 'P0001,P0004', 'D0001,D0002,D0003,D0004', 7650800, true, true);
insert into PayStub(PScode, Ecode, PsMonth, AllPayCode, AllDeCode, Sum, send, PSStatus) values(3, '160301A001z01', 6, 'P0002,P0004,P0005', 'D0001,D0002,D0003,D0004', 5200000, true, true);
insert into PayStub(PScode, Ecode, PsMonth, AllPayCode, AllDeCode, Sum, send, PSStatus) values(4, '140701A001z01', 6, 'P0003,P0004,P0005', 'D0001,D0002,D0003,D0004', 2645000, true, true);
insert into PayStub(PScode, Ecode, PsMonth, AllPayCode, AllDeCode, Sum, send, PSStatus) values(5, '160201B001z01', 6, 'P0002,P0004,p0006', 'D0001,D0002,D0003,D0004', 5300000, true, true);
insert into PayStub(PScode, Ecode, PsMonth, AllPayCode, AllDeCode, Sum, send, PSStatus) values(6, '220701A001z02', 6, 'P0003,P0004', 'D0001,D0002,D0003,D0004', 2200000, true, true);
insert into PayStub(PScode, Ecode, PsMonth, AllPayCode, AllDeCode, Sum, send, PSStatus) values(7, '220701B001z01', 6, 'P0003,P0004', 'D0001,D0002,D0003,D0004', 2200000, true, true);

/*10. 초과근무기록 : 초과근무기록코드, 사원코드, 초과근무일, 시작시간, 종료시간, 총 초과근무시간, 결재상태*/
insert into OverTime(OTCode, Ecode, OTDay, OTstartTime, OTendTime, TimeSum, OTapproval) values(1, '160201B001z01', '22-06-07', '17:00', '21:00', 4, 'completed');

/*11. 탄력근무신청 : 탄력근무기록코드, 사원코드, 탄력근무시작일, 탄력근무종료일, (월)근무시작시간, (월)근무종료시간, (화)근무시작시간, (화)근무종료시간, (수)근무시작시간, (수)근무종료시간, (목)근무시작시간, (목)근무종료시간, (금)근무시작시간, (금)근무종료시간*/
insert into FlexTime(FTCode, Ecode, FTstartday, Ftendday, MonStart, MonEnd, TueStart, TueEnd, WedStart, WedEnd, ThuStart, ThuEnd, FriStart, FriEnd, FTapproval, AdmissionDate)
		values(1, '140701A001z01', '22-06-13', '22-06-17', '09:00', '13:00', '08:00', '18:00', '08:00', '18:00', '08:00', '18:00', '08:00', '18:00', 'completed', '22-06-10');
		
/***
결재대기 --> waiting
결재승인 -->completed
결재취소 -->cancel
반려 --> back
***/