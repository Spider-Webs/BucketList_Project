# BucketList_Project

<h2> 개요 </h2>
<b>1. 프로젝트 기간</b> 2022.05.09 ~ 2022.05.26<br>
<strong>2. 프로젝트 인원 2명</strong><br>
3. 프로젝트 내용 공통된 취미의 관심사를 연결해주고, 본인의 경험을 기술하여 사람들과의 관심사 소통창구 역할을 해주는 홈페이지

<h2>요구사항 분석</h2>
<h4> 1.회원 가입 페이지</h4>
<h6> 유효성 검사</h6>
<ul>
  <li>회원가입의 모든칸은 필수 입력 칸 "미입력시 필수 입력값입니다" 메시지 보여주기  </li>
  <li>이름, 비밀번호의 경우 이름[가~힣] 비밀번호[비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자]로 구성 </li>
  <li>이메일의 경우 이메일형식 패턴을 적용 </li>
</ul>
<h6> 아이디 중복확인</h6>
<ul>
  <li>중복확인버튼 클릭시 가입되어있는 아이디가 있는경우 "이미가입되어있는 회원입니다"문구 출력후 회원가입페이지로 이동  </li>
  <li>중복확인버튼 클릭시 가입되어있는 아이디가 없는경우 "사용가능한 아이디입니다"문구 출력후 회원가입페이지로 이동 </li>
</ul>
<h4> 2.로그인및 마이 페이지</h4>
<h6> 로그인 검사</h6>
<ul>
  <li>로그인 않았을 경우, 글쓰기 관련페이지 이동시 로그인 화면으로 이동  </li>
  <li>아이디 비밀번호가 공백인경우 입력값 요청 메시지 보여주기 </li>
  <li>로그인 하였을 경우에만 마이 페이지 메뉴 보기가능 </li>
  <li>마이 페이지에서 가입한사람의 아이디, 이메일, 이름, 전화번호 보여주기</li>
  <li>마이페이지에서 본인이 작성한 게시글 보여주기</li>
</ul>
<h4> 3.공지사항</h4>
<h6> 공지사항</h6>
<ul>
  <li>공지사항 글쓰기 글삭제하기는 관리자아이디만 가능하도록 권한부여  </li>
  <li>관리자 이외에는 글보기만 가능</li>
</ul>
<h4> 4.참여하기</h4>
<h6> 참여하기</h6>
<ul>
  <li>댓글기능을 구현하여, 해당 버킷리스트를 참석하고 싶은 사람의경우 댓글로 의사소통 하기  </li>
  <li>작성자만 글 수정 삭제 권한 부여</li>
</ul>
<h4> 5.커뮤니티</h4>
<h6> 커뮤니티</h6>
<ul>
  <li>본인여행후기글작성페이지 </li>
  <li>참여하기와 동일하게 글 수정 삭제 권한 부여</li>
</ul>
<h4> 6.고객센터</h4>
<h6> 고객센터</h6>
<ul>
  <li>비공개글, 공개글 비밀번호 설정하기 </li>
  <li>비공개글의경우 비밀번호 일치하지않으면 비밀번호 입력화면으로 돌아가기</li>
</ul>



<h2>프로젝트 대표기능</h2>
1. 로그인하여 마이페이지 화면에서 본인이 작성한 글들을 일괄적으로 볼 수 있음<br>
<img src="https://user-images.githubusercontent.com/105139722/171076298-c929e78a-1f4e-4394-b1a4-d7f96df6506c.png">

2. 댓글기능을 구현하여, 버킷리스트의 참석인원을 모집 할 수 있습니다<br>
<img src="https://user-images.githubusercontent.com/105139722/171076764-d893f934-12c2-45fe-9d1c-9140db2f7105.png"> 
3. 공지사항 글쓰기 삭제의경우 관리자에게만 권한 부여<br>
<img src="https://user-images.githubusercontent.com/105139722/171076845-c69fdca3-e7c8-4c80-b8d7-0c8393335dea.png"> 
4. 고객센터 게시글을 비공개글 여부를 설정하여 비밀번호 미일치시 접근 차단
<img src="https://user-images.githubusercontent.com/105139722/171076888-db759bd9-8174-41bf-9dd2-e31f940dc62d.png">

<h2>개발 내용</h2>
<a href="https://blog.naver.com/dyko615/222753328860">Spring Boot JPA 조회수 증가</a><br>
<a href="https://blog.naver.com/dyko615/222753342342">Spring Boot JPA 페이징</a><br>
<a href="https://blog.naver.com/dyko615/222753352933">회원가입 유효성 검사 - 어노테이션</a><br>
<a href="https://blog.naver.com/dyko615/222753360401">회원가입 유효성 검사 - 커스터마이징(비밀번호 일치여부)</a><br>
<a href="https://blog.naver.com/dyko615/222753369441">회원가입 아이디 중복검사</a><br>
<a href="https://blog.naver.com/dyko615/222753376370">Spring Boot 파일 업로드</a><br>

<h2>아쉽고 힘들었던 점</h2>
처음 프로젝트이다보니, 현실과 다르게 이상이 높았던 것 같다. 웹사이트들을 쉽게 접하다보니, 무의식적으로 나도 저 사이트 정도는 만들 수 있다라는 생각이 든 것 같다
시간과 변수등을 고려하지않고 너무 많은 기능을 구현하려고 한것같아 오히려 아쉬웠던 것 같다. 기존에 강의나 책을 보면서 따라 할때는 오류나 문제점이 발생 되 었을때 Q&A 혹은 반복을통해서 해결 할 수 있었지만, 프로젝트는 그렇지 못했다. 혼자 알아보고 오류해결을 위해 다각도로 접근했던 것같다 그렇기에 정말 많은 공부가 된것같다 오히려 느낀거는 더 일찍히 해볼걸 그랬나라는 생각이 들었다 그이유는 해보다보니깐 이 문제에서는 이러한 기술이 더 효율적일것같고, 이 문제에서는 반복적인 코드가있으니 제거하는게 효율적이지 않을까라는 생각이 마구 들었기 때문이다.. 그리고 느낀점은 역시 설계가 중요하다는 부분이다 2주라는 시간이 주어졌을때 시간이 촉박하다 생각하다보니 설계는 대충 건너뛰고 코딩에만 집중했던것같다. 그러다보니 처음 요구사항에 지켜지지 못했던것도 많고, ERD를 참고하면서 했어야했는데 지켜지지못했던부분이 너무많은것같다.<br>
프로젝트가 끝나고 내가 무엇이 부족한지를 다시금 느꼇다 전반적으로 부족할 수 있겠지만 js, jquery, ajax, jpa 부분이 많이 부족하다 느껴졌고 다음 프로젝트에서는 보완되어야 할점은 관계형데이터베이스, 로그인시 Spring Security 적용등을 더 활용해야할것같다..

<h2>개선사항</h2>
<h4>캐시</h4>
<a href="https://blog.naver.com/dyko615/222760317147">캐시 정리자료</a>
공지사항에 게시글 전체 리스트의 경우 반복적이고 관리자만 작성권한을 주었기에, 타인이 보기에 반복적이고 동일한 결과가 나오며,업데이트가 자주 발생되지 않아 캐시를 사용하였다.<br>
그러므로, 공지사항 전체게시글 Service에서는 <b>@Cacheable</b> 사용하여 캐시를 저장하였으며,<br>  공지사항 게시글 저장,삭제 Service에서는<b>@CacheEvict</b>를 사용하여 캐시를 제거하였다.<br>
캐시에대한 고려 사항을 정리해보면<br>
<ul>
  <li>자주 변경되는 데이터일수록 Cache 적용 신중하게 선택해야 한다.</li>
  <li>데이터의 무결성이 깨질 염려가 있다.</li>
</ul>
<hr>
<h4>검색기능 추가</h4>
메인페이지 화면에서 참여하기 게시글에대한 검색기능 추가 
<a href="https://blog.naver.com/dyko615/222761676689">검색기능 정리</a>
현재는 참여하기에대한 게시글만 검색기능을 추가하였다.. 이후 추가적으로 셀렉트 박스를 통해 커뮤니티, 참여하기에대해 각각 검색기능을 설정할지 아님 통합검색기능을 설정할지 
생각해봐야겠다.

<br><br><br><br>


DB - 엑셀
https://docs.google.com/spreadsheets/d/15F4yysVkMPwydwkIkiJJAresrJctdSHgpmIC8dA3-qY/edit#gid=0

간트차트 
https://docs.google.com/spreadsheets/d/18Q_P_u44_oHLFtAkGs3rqCe8gRYR8QN4my8vZ2pLv00/edit#gid=0

