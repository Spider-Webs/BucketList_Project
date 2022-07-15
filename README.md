# BucketList_Project
<h2>목차</h2>
<ol>
  <h4><li><a href="#one"> 개요</a></li></h4>
  <h4><li><a href="#two"> 요구사항 분석 </a></li></h4>
  <h4><li><a href="#three"> 프로젝트 대표기능</a> </li></h4>
  <h4><li><a href="#five"> 개선사항</a></li></h4>
</ol>
<hr>
  <h2><a name="one"> 개요</a> </h2>
<b>1. 프로젝트 기간</b> 2022.05.09 ~ 2022.05.26<br>
<strong>2. 프로젝트 인원 2명</strong><br>
3. 프로젝트 내용 공통된 취미의 관심사를 연결해주고, 본인의 경험을 기술하여 사람들과의 관심사 소통창구 역할을 해주는 홈페이지

<h2><a name="two">요구사항 분석</a></h2>
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



<h2><a name="three">프로젝트 대표기능</a></h2>
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


<h2><a name="five">개선사항</a></h2>
<h4>캐시</h4>
<b><a href="https://blog.naver.com/dyko615/222760317147">캐시 정리자료</a></b><br>
공지사항에 게시글 전체 리스트의 경우 반복적이고 관리자만 작성권한을 주었기에, 업데이트가 자주 발생되지 않아 캐시를 사용하였다.<br>
그러므로, 공지사항 전체게시글 Service에서는 <b>@Cacheable</b> 사용하여 캐시를 저장하였으며,<br>  공지사항 게시글 저장,삭제 Service에서는<b>@CacheEvict</b>를 사용하여 캐시를 제거하였다.<br>

<hr>
<h4>검색기능 추가</h4>
<b><a href="https://blog.naver.com/dyko615/222761676689">검색기능 정리</a></b><br>
메인페이지 화면에서 참여하기 게시글에대한 검색기능 추가 <br>
현재는 참여하기에대한 게시글만 검색기능을 추가하였다.. 이후 추가적으로 셀렉트 박스를 통해 커뮤니티, 참여하기에대해 각각 검색기능을 설정할지 아님 통합검색기능을 설정할지 
생각해봐야겠다.
<hr>
<h4>회원가입시 다음주소API 사용</h4>
<b><a href="https://blog.naver.com/dyko615/222774616025">주소API 정리</a></b><br>
기존 회원가입의 경우 단순 아이디 이름 비밀번호 이메일 정도만 기재하면 가입이 가능하게 설정 하였다. <br>
프로젝트 처음의 취지였던 것 중 하나가 버킷리스트 가입자들의 주소를 활용하여, 이후 이벤트등의 상품을 발송을 생각했었다.<br>
그래서 뒤 늦게 주소 API를 활용하여 회원가입시 본인의 거주지 주소를 기재하게끔 추가하였다.
<hr>
<h4>스프링 시큐리티 회원가입 로그인</h4>
<b><a href="https://blog.naver.com/dyko615/222773237412">Security - 로그인, 회원가입</a></b><br>
<b><a href="https://blog.naver.com/dyko615/222780812767">Security - 세부 권한부여</a></b><br>
기존 인터셉터를 이용하여 보안에 대처하였다. 그러나 스프링에서도 시큐리티를 사용하는것을 적극 권장하고 있기에,스프링 시큐리티를 활용한 로그인, 회원가입 정보를 변경하였다. 
<br>기존에 관리자와 일반유저에 구분도 단순 회원가입시 생성한 아이디를 토대로 구분하였다(ex. must = 관리자, 그이외 일반유저) 그렇기 때문에 추가적으로 관리자로 가입이 필요한<br> 경우 추가적인 작업들이 많이 필요하였다. 시큐리티를 사용하여 작성자 권한도 부여하고, 메뉴선택의경우에도 권한별로 부여하였다.
<br>내용이 방대하다보니 포괄적인 변경부분과 세부적인 변경부분을 나누어 작성하였다
<hr>
<h4>OAuth2</h4>
<b><a href="https://blog.naver.com/dyko615/222777687661">OAuth2 - 로그인</a></b><br>
현재 대부분의 사이트의경우 회원가입이 이루어지는 것보다는 소셜 로그인등을 사용한다<br>
사용하지 않을경우 본인이 직접구현해야하는 번거로움이 있으므로 개발자는 소셜로그인에 맡기면 개발에 집중할 수 있다는 장점이있어 접목하게 되었다.
<hr>

<h4>비밀번호 찾기</h4>
<b><a href="https://blog.naver.com/dyko615/222781529745">이메일을 이용한 비밀번호 찾기</a></b><br>
기존 프로젝트 회원가입과 로그인만 구현을 하였다, 기존 프로젝트 설계 당시 비밀번호를 분실 할 경우 이메일을 통한 비밀번호 찾기를 구현하도록 하였으나,
최초버전에서는 구현하지 못하여 비밀번호 찾기에대한 내용을 추가하였다<br>
<br>네이버의경우 특이하게 보안설정에대한 오류가 반복해서 발생되어 구글로 변경하여 해결을하였다.
      
<hr>
<h4>조회수 증가</h4>
<b><a href="https://blog.naver.com/dyko615/222753328860">쿠키를 이용하여 조회수증가 중복방지</a></b><br>      
기존의 조회수를 구현하였으나, 새로고침시 조회수가 무한히 증가하는 문제가있었는데, 쿠키를 이용하여 메인페이지 접속시에 쿠키를 생성, 해당쿠키 유지시간을 설정하여 조회수 증가<br>문제를 해결하였다.

<hr>
<h4>댓글 저장,수정, 삭제 변경</h4>
<b><a href="https://blog.naver.com/dyko615/222788918015">AJAX를 활용하여 댓글 기능 수정</a></b><br>   
이전에도 댓글을 구현하였으나, ORM을 사용하지않고, 테이블만 관계형으로 연결해주어, 설정을해주었기때문에 객체지향설계의 취지에 어긋나는 상황이었다. 해당부분을 보완하고자 JPA를 활용하였고, 웹페이지의 속도향상을 AJAX를 활용하여 댓글 기능을 수정하였다.

<hr>
<h4>Redis를 이용하여 다중서버 세션유지</h4>
<b><a href="https://blog.naver.com/dyko615/222808606845">Redis를 이용하여 다중서버에서 세션유지</a></b><br> 
이전 프로젝트 작업시 DB로 세션저장소를 활용하여 다중서버에서도 세션을 유지한적이 있다. 이번에는 DB가아닌 Redis를 이용한 메모리 db를 세션저장소로 사용하여 변경해보았다.
<br><br><br><br>



<br>
참고
스프링 부트와 AWS로 혼자 구현하는 웹서비스 - 이동욱님

