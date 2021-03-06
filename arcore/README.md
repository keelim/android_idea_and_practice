# README.md

## 2019 01 12 TIL

1. text 속성 조정
2. 라디오 버튼 및 라디오 그룹
3. hint attribute
4. input type 을 조정을 하면 입력시 키패드 같은 것을 떠오르게 할 수 있다.

## 2019 01 13 TIL

1. 동작은 자바 소스 파일 &gt; activity - xml % activity 연결이 되어 진다.
2. 인플레이션 xml 파일의 객체를 메모리화 하는 과정 \(코드의 순서가 바뀌면 에러가 일어난다.\)
3. setContentView\(\) &gt; xml 메모리상에 객체화 하는 것 \(Layoutinflater\)
4. LayoutInflater inflater = \(LayoutInflater\) getSystemService\(Context.LAYOUT\_INFLATER\_SERVICE\) &gt;xml 을 객체화 시키기\(부분 화면\)

## 2019 01 15 TIL

1. 어플리케이션을 구성 4가지 요소
   * 액티비티
   * 서비스
   * 브로드캐스트 수신자
   * 내용 제공자 -&gt; 보안으로 인하여 바로 파일 사용이 불가능하다.
2. mainfest.xml &gt; intent filter를 구문을 넣어야지만 app 실행

## 2019 01 16 TIL

1. intent 무언 가를 전달 해 주는 것을 \(시스템이 일단은 받는다. \)
   * 명시적 인텐트 -&gt; tel, http
   * 암시적 인텐트
   * startActivity\(intent\); &gt; 이것으로 사용을 한다.
2. flag , ExtraData \(액티비티는 stack의 구조를 띄고 있다. \) &gt;원하는대로 화면이 바뀌지 않을 수 있다. -flag &gt; option? intent 의 flag 를 넣어주는 것이다.
   * onCreate -&gt; CallBack 으로 재사용시에는 호출되지 않는다. Cleartop, singleTop
   * Serializable &gt; 객체 직렬화 Parcelable &gt; Android 권장
3. 수명 주기 lifeCycle onCreate\(\), onResume, onDestoryed, onStart\(\), onPause &gt;Thread 하고 비슷
4. 서비스 데몬 처럼 실행이 되어져야 한다. &gt; 시스템이 자동으로 재시작을 해줘야 한다. 
   * service 를 상속을 한다. 

## 2019 01 17 TIL

1. 화면은 task로 묶여있어서 옵션을 주는 flag 를 한다. 
2. 서비스는 화면이 없다. &gt; 액티비티로 넣어 주기 위해서는 옵션을 주어야 하며 Intent로 전달을 한다.
3. 브로드 캐스트 수신자 
   * 여러 사람에게 데이터를 뿌려주는 것 &gt; boradcast receiver &gt;intent 를 보내고 받을 수 있다. 
   * 시스템이 관리를 한다. 
     * 일반
     * 순차

## 2019 01 19 TIL

1. manifest에서 intent 필터를 설정
2. extended controls 를 통하여 가상 머신을 제어를 한다. 
3. widget applicate
   * 기본적인 이벤트 처리 메커니즘 &gt; Toast,  대화상자
   * animation 처리 &gt; 쓰레드
   * 프래그먼트
4. 이벤트 처리
   * 터치, 키, 제스처, 포커스 , 화면 방향 변경

## 2019 01 20 TIL

1. TextView &gt; Event Handling
2. 제스터 사용 &gt; 터치 이벤트를 이용을 하여 처리를 한다. 
   * 이벤트 리스터를 붙여서 사용을 한다. 

## 2019 01 21 TIL

1. 방향 전환의 따른 이벤트 처리 방법
   * 가로와 세로의 액티비티를 두가지로 만들 수 있다. layout-land &gt; 가로의 방향을 설정을 할 수 있다. 
   * 상태를 저장을 하여 하나의 액티비티로 가로와 세로를 설정을 할 수 있다. 
2. 토스트와 대화상자
   * 레이아웃 인플레이션을 통하여 토스트를 정의를 할 수 가 있다. 
   * xml을 Toast화 하여 설정?
   * 토스트의 각각 설정 가능
   * selector 상태에 따라 이미지를 다르게 할 수 있는 것
   * shape / snap bar를 사용을 한다.  
3. 알림 대화 상자
   * alert dialog snack bar &gt; 
4. 프로그레스 바 &gt; 진행 상태를 보여주는 위젯
   * 코드의 진행율 값?
   * 중간 중간 사용하게 되는 경우는 많다. &gt; 직접 dialog 를 구성을 하는 것도 

       나쁘지 않다. 
5. seek bar &gt; seek bar Listener    
6. 애니메이션 
   * xml에서 애니메이션을 저장을 한다. 
   * anim 폴더를 사용하여 xml 을 저장한다. 

## 2019 01 22 TIL

1. 페이지 슬라이딩 &gt; 애니에이션 anim 폴더를 만드록 animation.xml을 만들어 사용
   * translate &gt; 모든 것은 R.id 로 검색을 한다. 

     -starㅅANimation
2. 프래그먼트 &gt;한 화면의 여러 부분 화면을 넣는 방식 &gt; 뷰 처럼 정의를 하는 방식
   * 추가한 화면들을 또 넣어줘야 한다. &gt; 액티비티는 독립된 것이라서
   * 부분적인 레이아웃을 독립적으로 동작할 수 있게 하는 것 &gt; 프래그먼트
   * 부분 화면을 독립적으로 만들어주며 액티비티를 그대로 본떠 만든 것
   * 액티비티에서 요청을 하여야 한다. 인텐트는 안드로이드 시스템이 알아먹는 것.
   * 액티비티 화면 전체를 하는 것처럼 &gt; 프래그먼트도 화면 전환의 효과를 가지고 올 수 있다. 
   * 가벼운 전환을 할 수 있다. &gt; 보안적인 면에서도 나은 장점이 있다. 
   * xml, source &gt; inflation과정을 진행을 해야 한다. 
   * xml &gt; fragment를 추가를 하는 것 // 소스를 이용하여 추가를 하는 방법
   * 프래그먼트 매니저를 통하여 프래그 먼트를 추가를 한다. 

## 2019 01 23 TIL

1. 프래그 먼트  &gt; 그 안의 부분화면도 전환 효과를 볼 수 있다.  fragment manager 를 통하여 관리를 한다. 
   * 메소드를 통하여 프래그 먼트를 바뀌어야 한다.
   * 액티비티 메소드를 통하여 만든다. 
   * 각각의 프래그 먼트를 독립적으로 사용을 할 수 있다. &gt; 
2. ActionBar 와 탭을 사용을 하기
   * 메뉴와 툴바의 통합으로 이루어진다. &gt; OptionMenu가 ActionBar 통합이 되어 있다.  &gt; Context menu &gt; 타이틀 부분의 통합이 되어 있다. 
   * res\menu &gt;menu.xml 을 만든다. 
   * showAsAction &gt; alwys 항상 작동을 하는 것

## 2019 01 24 TIL

1. app 모듈에서 외부 라이브러리를 통하여 툴바를 구성을 할 수 있다.
   * support.designer 를 통하여 구성을 할 수 있다. &gt; xml을 따로 설정을 해야 한다. 
   * fragment 는 onCreateView 를 통하여 inflation을 해야 한다. &gt; ViewGroup 을 통하여 참조를 하고 참조된 값을 반환한다. 
   * 메인액티비티 자바소스에서 프래그 먼트를 선언을 하고 매니저를 통하여 트랜지액션을 한다.  &gt; commit 을 해야 한다. 
2. 웹뷰의 사용?  &gt; Google. &gt; Toggle 

## 2019 01 26 TIL 

1. 브로드 캐스트 수신자 > 시스템에서 인텐트를 통하여 전달을 한다.  
     - 인텐트 객체를 보내고 받는다. > SMS로 전달된 데이터를 받는다. 
     - Other > Broadcast recevier > onReceive 를 통하여 전달을 받는다.  > CallBack
     - manifest를 통하여 시스템에서 알아 듣는다.
     - 인텐트를 걸러서 받는 정보 이다. 
     - sdk 23 이후 권한을 한번 더 확인을 하는 작업이 필요하다. 
     - 노트북으로 작업을 하는 경우 > Heap의 영역이 너무 많이 소요할 수 있다. > Settings > Build ... >Compiler >Command line >Xmx512m 으로 설정을 한다. 

## 2019 01 28 TIL 
1. 선택 위젯의 사용과 커스텀뷰 만들기 > 리스트 뷰
     - 아이템이 여러개가 있을 수 있고 리스트 뷰는 껍데기 역할만을 한다. > 어댑터를 통하여 아이템을 관리를 한다. 여러 개의 데이터를 관리를 할 때 선택 위젯을 통하여 관리를 하는 구나?
     - 리스트 뷰만 잘 만들 수 있다면 웬만한 화면은 구현이 가능하다. 
     - 그리드 뷰 격자의 형태를 만드는 것이 쉬워진다. = 
2. 나인패치 이미지
     - 이미지가 늘어나면서 짤리는 부분을 구별하여 적용을 할 수 있는 것
     - png 확장자 옆의 .9를 활용하여 나인패치 라고 정의를 한다. 
     - 나인패치는 늘이는 작업을 이미지의 깨지는 문제를 방지하는 것
     - 까만 부분은 정보 픽셀로 늘어나도 되는 것을 설정을 한다.  
3. 비트맵 버튼 만들기
     - onDraw() > 내부에서 그래픽으로 그리는 것 > 그리는 과정에서 추가적으로 그릴 수 있도록 > Callback을 통하여 그릴 수 있게 하는 것
     - onMeausure() > 일정 영역 할당 받고 정의 하는 것
     - invalidate() > Touch 를 통한 이벤트 처리
     - 클래스 정의 상속 > 버튼을 클릭을 하고 터치를 하는 것을 처리하는것 > 터치 리스너
4. 리스트뷰
     - 원본 데이터를 관리를 하는 것이 어댑터 이다.  > 어댑터의 관리 방식
     - 리스트 뷰 자체는 껍데기 이다. > 아이템을 위해서 뷰를 요청을 한다.
     - 콤보 박스(스피너), 갤러리, 그리드 뷰 > 어댑터라는 패턴을 사용을 한다. 
     - 일반적으로 BaseAdapter 를 extends 를 하면 된다. 
     - 어댑터는 데이터를 가르킨다. 
     - 부분 화면을 구성을 한다. > 인프레이션 필요

## 2019 01 29 TIL
1. 스피너를 활용을 하기 
     - 어댑터를 이용을 하여 스피너를 구성을 한다. 
     - 어댑터를 이용을 하여 초기화를 하고 데이터를 넣어주어 표시를 한다. 
2. 그리드 뷰 > 리스트뷰와의 유사점
     - 격자의 형태로 만드는 것
     - 칼럼을 활용을 하여 > 테이블 모양의 뷰 생성
     - 그리드뷰를 위한 어댑터 클래스 정의> 어댑터를 이용하여 화면 표시
3. 복합 위젯 만들기
      - 여러 개의 뷰를 포함한 복합 위젯 만들기
      - picker 날짜 시간을 선택을 하는 widget
      -  xml 객체를 inflate 하는 과정이 필요하다. 
      - picker 는 init 을 통하여 초기화를 할 수 가 있다. 
      - 이벤트를 전달할 수 있도록 리스너를 정의를 한다. 
4. 월별 달력을 사용하기
      - 날짜와 관련된 Class Date, Calendar
      - 1일이 시작하는 요일이 무엇인지를 아는 것이 필요하다. 
      - 몇일 까지 있는지를 알아야 한다. 

## 2019 01 30 TIL
1. 선택위젯 > 어댑터를 이용을 하여 사용을 한다. 
     - 고정된 정보는 배열을 이용하도 상관은 없을 듯 하다.
     - 내부 클래스 BaseAdapter extends
     - 달력을 만들기
2. 그래픽 . 
     - 뷰를 상속을 하여 그래픽을 사용을 한다.
     - 필수 생성자 위에서 2개를 사용을한다.
     - onDraw Canvas > 콜백함수로 그리기 전에 호출이 된다.
     - 메소드를 재정의 하여 Canvas를 통한다.
     - 자바 소스 파일을 xml로 입력을 시켜서 얻는 것이다.
3. 그리기 객체를 만들어서 적용(drawble)
     - drawble 를 통하여 다른 속성을 더 늘릴 수 있다.
     - 각각을 조정을 할 수 있는 것이 낫다는 장점이 있다. > 객체를 만드러야 애니메이션조정 가능
4. 비트맵 이미지 사용하기
     - 비트맵 > 다시 그리기를 할 때 깜박임 현상 발생
     - 뷰에 직접적으로 그리는 것 > 훨씬 자연스럽게 넘어 갈 수 있다.
     - 파일 및 리소스로 저장되어 있는 것을 가지고 오는 것이다.
     - 성능의 영향을 줄이기 위해 메모리의 미리 그려두고 darwble로 가져온다
     - BitmapFactory를 통하여 메모리로 옮겨준다.
     - 더블 버퍼링
          - 메모리의 비트맵을 만들어 놓고 Canvas 를 통하여 이미지를 뿌려주는 것이다. 
5. 페인트 보드
     - 손글씨를 쓰는 기능 > 그래픽을 그릴 수 있는 뷰가 필요하다. > 터치 이벤트 Drawing

## 2019 01 31
1. 페인트 보드를 상속을 하는 뷰를 생성을 한다. 
     - 더블 버퍼링을 통하여 그린다.  > 
     - 소스 파일을 통하여 뷰를 xml로 불러올 수 있다. 
     - 액티비티를 사용을 하여 구성을 할 수 있다. 
     - 그리드 뷰는 어댑터를 설정을 하고 numColumns를 하여도 된다. > 색상 선택을 위한 그리드 뷰
     - 어댑터는 4가지의 필수 메소드가 있으면 리스트나 배열로 관리
     
## 2019 02 03 TIL
1. comply algorithm dictionary `sort algorithm`
2. 카메라 객체를 사용하기
    - `image ` 를 다루는 방법
    - `scale type` > graphics matrix > matrix 를 통하여 이미지를 회전을 할 수 가 있다. 
3.커버 플로우를 통한 갤러리 구현
    - view page를 통한 구현
    - graphics 소프트 웨어 카메라
    - 뷰의 구성과 비슷하다. 
    - 이미지의 회전이 가능 하다. 
    - `translate(), rotate(), `
    - 복합 위젯이라 어댑터를 사용을 해야 한다. 
    - 카메라 객체를 통하여 회전과 기타 등을 할 수 가 있다. 

## 2019 02 04 TIL 
1. 스레드와 애니메이션의 활용
    - 별도로 동시의 작동을 하게 하는 것
    - `animation`에서 많이 사용을 한다. 
    - 스레드 동작 방식이 표준 자바와 다르다. > handler 의 접근이 어렵다. 
    > 핸들러의 사용 방식 - 쓰레드의 접근 방법
    - 핸들러
          - 메인은 기본적인 쓰레드 
          - 쓰레드가 리소스의 직접적인 접근을 하면 X
          - deadlock > error 발생 (Build에서 에러가 나옴)
          - 동시적 접근의 해결
          - 큐의 형태를 통하여 해결을 한다. 
     - `Thread,sleep(1000)` 1초
     - 메인 쓰레드 만이 UI를 접근할 수 있다. 
     - 핸들러에게 메시지를 받고 핸들러는  UI를 접근 할 수 있다. 
     - 번들을 통하여 전달을 한다. `handler usage complex `
2. Handler를 일반 객체를 통한 접근
     - 람다 식의 예제

## 2019 02 05
1. 일정시간 후에 실행하기

## 2019 02 25 
1. Hearing the lecture
     - Android
## 2019 03 01
1. rss 를 통한 뉴스정보를 가지고 오기
     - <item>을 통하여 기사의 정보를 받을 수 있다. 
     - 리스트 뷰를 가지고 뉴스를 가지고 올 수 있다. 
     - 데이터를 파싱을 하는 방법 
          - xml
          - json > 웹페이에서 사용하는 자바스크립트 객체 파일 `main`
          - Document Builder factory 빌더를 객체 이용 `Document`
          - `DOM parser` > 웹 브라우저에게 DOM 객체를 통하여 브라우저가 파싱을 하는 것
          - DOM 객체를 통하여 `getDocumentElement` 
          - node의 개념을 통하여 분리가 되어 리턴을 하게 된다. 
          - 태그를 기준으로 노드가 생성이 된다. 
          - node를 분리를 해서 데이터를 생성을 하는 것이다. 
          - `Thread`, `handling`을 통하여 뿌린다. > 라이브러리 이용 가능
          - 메인은 `json`이기 때문에 json을 사용을 하는 것이 현명하다.

## 2019 03 19 TIL 
1. ContentProvider
2. Service --> 액티비티와 반대되는 경향 (백그라운드 서비스)
3. BroadcastReceiver (알림) --> 통신
4. Activity --> 눈에 보일 때만

액티비티 --> 창 (UI의 이벤트를 발생시킨다. )
맥에서의 finder
액티비티의 생명주기는 알고 있어야 한다.
실행의 상태를 runtime 이라고 한다. 

인텐트
1. 인텐트는 의사소통을 책임
2. 의사소통을 하는 규격 --> 변수와 정보들
3. Intent 우편 배달부
     - 액티비티 - 액티비티
     - 앱 - 앱
     - 다양한 것들을 섞어서 넣을 수 가 있다. 
     - 종류
          - 명시적
          - <code>Intent intent  = new Intent(this, NewActivity.class);
          startActivity(intent);</code>
          - 암시적 (앱과 앱간의 통신에서는 사용을 한다. )
          - <code>Intent sendIntent = new Intent();
          sendIntent.setAction(Intent.ACTION_CALL);
          startActivity(sendIntent)</code>

## 2019 03 20
1. developer android.com
2. 패키지 이름이 식별자가 된다. 
     -  그래서 회사 이름이 자동으로 등록이 된다. 
3. onCreated 를 작성을 한다. , manifest 설명문
     - manifest 설정
4. 런처 --> 첫 시작시 설정을 해주는 것
5. logcat 을 통하여 설정이 가능하다. 
6. 런처는 시작 액티비티를 사용한다. 
7. 액티비티
     - 중요한 클래스
     - 생명주기를 갖고 있다. --> 자원관리를 위해서 사용을 한다. 
     - onCreate()에서 액티비티 작성을 시작하자
8. 인텐트
     - 다른 액티비티를 시작할 수 있게 도와준다. --> 명시적
     - 다른 앱을 실행 할 수 도 있다.  --> 암묵적 (시스템이 골라주는 것이다. )
     - 자료를 넣어 보낼 수 있다. 
9. 스튜디오     
     - Logcat 에러 확인 --> 구글에서 확인
     - http://developer.android.com/
10. View
      - 화면 그 자체
      - Activit에 씌우는 화면 껍데기 --> 재활용 가능
      - XML, JAVA 둘 중 하나로 작성 가능(보통 XML)
      - Widget, Adapeter, Layout
      
## 2019 03 21
1. 용도가 뚜렷한 것들이 전부 Widget들이다. 
     - TextView, Button, ImageView
2. Adapter
     - 위젯들을 묶은 것들을 여러가지를 보여줄 때
     - 많은 정보를 길게 스크롤하며 나열할 때 많이 쓴다. 
     - ListView, GridView, RecycleView
     - 배열과 같은 형태들을 주로 사용한다. 
3. Layout
     - 담을 수 있는 틀
     - 화면 공간을 배분할 때 많이 쓴다. 
     - LinearLayout, RelativeLayout, FrameLayout
4. 
     - match-parent --> 상위하고 맞춘다. 
     - wrap-content --> 하위의 맞춘다. 
5. xml --> 거의 가드라인을 준다. 
6. res/drawble
7. 이미지 뷰를 코드로 접근을 해서 가지고 올 수 있다. 
     - 코드 적으로 접근을 할 수 있다. 
     - xml을 주로 한다. 

## 2019 03 23
1. Layout 어떤 것들은 담는 상자
     - LinearLayout 선형 레이아웃
          - 겹치지 않게 쌓을 수 있다. 
          - 관계를 부여를 한다. 상하좌우를 지정한다. 
     - FrameLayout
          - gravity는 프레임에서는 뷰가 겹칠 수 있기 때문에
          - 똑같이 정렬을 하기 때문에

## 2019 03 25
1. manifest 는 모든 파일의 구성요소를 등록을 해야 한다. 
     <code>- <activity>
     - <service>
     - <receiver>
     - <provider></code>
     - 인텐트 필터 --> ?
2. 동작하는 기기의 요구사항을 파악을 할 필요가 있다. 
     - 특정 API이상에서는 권한을 받아야 한다. 
3. 리소스 들은 항상 코드 외부에서 작동해야 한다. 
     - 그래야 독립적인 관리가 가능
     - R class를 통하여 리소스 액세스가 가능하다. 
4. 리소스의 유형
     - animator/ --> 속성 애니메이션 관리
     - anim/ tween 애니메이션 정의
     - color/ 색상의 상태 정의
     - drawble/ 사진 파일
     - mipmap/ 서로 다른 밀도의 런처 아이콘
     - layout/ 사용자 인터페이스 레이아웃을 정의
     - menu 하위 컨텍스트를 정의
     - raw/ 원시 형태로 저장하는 임의 파일
     - values 문자열 정수 및 색 같은 단순 값들이 저장
     * 리소스 파일은 res/ 아래 바로 저장하면 컴파일 오류가 걸림

5. event driven
     - 그래픽 같은 경우는 event driven을 통하여 동작을 한다. 
     - A 버튼을 눌렀다. 
     - B back key를 눌렀다. 
     - C 인터넷이 끊겼다. 
     - D 터치를 했다. 
     - E 화면을 가로로 뒤집었다. 
     - F 카톡이 왔다. 
     - G 문자가 왔다. 
     - H 전화가 왔다. 
     - 배터리가 매우 부족하다. --> 15%알림 전부 Call Back함수
     언제 발생할지 모르는 것을 --> Listener를 등록을 한다. 
6. Listener은 인터페이스 이다. 
7. Listener은 결국 콜백이다. 
8. View 액티비티에 씌우는 껍데기 --> 모양을 씌우는 것이다. 
     - 화면을 나타낸다. 
9. View
     - Widget
          - TextView
          - Image View
     - Layout
          - LinearLayout
          - RelativeLayout
          - FrameLayout
     - Adapter View
          - ListView
          - GridView
          - RecyclerView
     - 상호 작용 --> 리스너 --> 콜백

## 2019 03 26
1. 인텐트 일종의 메시지 객체 --> 다른 앱 구성요소로 부터 작업 요청 가능
     - 액티비티의 시작
     - 서비스의 시작
     - 브로드캐스트 전달
2. 종류
     - 명시적 인텐트
          - 본인 앱에서의 구성요소들에게 전달을 할 때
          - 액티비티 혹은 서비스
          - 명시적 인텐트를 생성하여 액티비티나 서비스를 시작하도록 하면, 시스템이 즉시 Intent 
          객체에서 지정된 앱 구성 요소를 시작합니다.           
     - 암시적 인텐트
          - 일반적인 작업을 선언을 하여 해당 요소를 사용할 수 있는 앱에게 보낸다. 
     - 인텐트 필터 해당 구성 요소가 수신하고자 하는 인텐트의 유형을 나타낸 것입니다. 
     - 서비스를 시작을 할때는 명식저 인텐트만 사용을 한다. 

## 2019 03 27
1. Fragment    
     - 만들어진 의도는 유연한 UI 제공
     - 작은 Activity를 부모로 가진다. 
     - Activity Multiple Fragment
     - 코드를 나누는 측면에서는 편하다. 
     - fragment 거의 독립된 객체
2. Activity에 생명주기를 따르게 된다. 
     - 부모의 생명주기를 거의 따른다. 
     - onCreateView()
3. 생성 기법
     - xml --> 편리하다. 
     - 코드적 기법 --> 언제든지 교체가 가능하다. 
     - fragment 매니저를 사용을 해도
     - 꼭 커밋을 사용을 해야 한다. 

## 2019 04 0
1
1. 프래그 먼트
     - fragment 의 뷰를 만든다. 
     - 부모의 생성 주기의 따른다. 
2. onCreateView 를 통한다. 
     - View 를 리턴을 하면 inflater.inflate(fragment, null);
3. xml 
     - id와 name 을 가지고 있어야 한다. 
4. 코드적
     - getSupportFragmentManager를 통하여
     - getSupportFragmentManager.beginTrasiaction().add(R.layout.fragment, fragment).commit() 으로 추가한다.
     
## 2019 04 28 TIL 
1. ViewPager
    - 하나의 페이지는 fragment로 구현을 한다. 
2. Adapter
    - 변환을 시키는 것 
    - AdapterView 는 Adapter를 통하여 만들 수 있다. 
    - Data --> Adapter --> AdapterView
    - 데이터를 변환 시키는 것으로 생각을 하면 된다. 
    - 자바 디자인 패턴 중 하나다. --> Builder pattern 하고 비슷한듯 하다.
    
## 2019 04 30 TIL 
1. SharedPreference  
    - 간단한 값을 저장하는 기능
    - 설정에서 사용을 하는 것
    - 저장 값들을 외부 파일로 저장을 하는 것
    - key-value 의 형태로 저장
    - 앱의 최초 실행 유무를 체킹을 할 수 있다. 
2. 2가지 단계를 나눈다. 
    - 저장
        - editor.putInt()
        - editor.commit()
    - 불러오기
        - getResources().getInteger
        - getInt().
    - 최초 사용자의 여부 
        - 불리언 값?
    - SharedPreference 를 가지고 DB 만들기?
    

## 2019 05 07 TIL 
1. SharedPreference 구별 확인
   - SharedPreference editor 가 있어서 저장을 따로 해주어야 한다.
   <code>        SharedPreferences share = getPreferences(Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = share.edit();
                 editor.putInt(SHARED_PREF_FIRST_USER_KEY, 1);
                 editor.commit();
   </code>
   - 앱 정보를 통하여 캐시와 데이터를 지우면 초기화 된다.
2. Fragment + ViewPager
    - 프레그 먼트를 View pager 를 통하여 움직이면서 확인을 할 수 있다.
    - 어댑터 --> 변환을 하는 것이라 생각하면 된다.
    - Preference key, value
    - List, Map, Table, Dictionary
    - json 에서 사용을 한다.
    - 서버는 json 의 규격을 통하여 정보를 전달하게 된다.
3. RecyclerView

## 2019 05 13 TIL 
1. Recycler View
    - ListView, GridView의 형태
    - 타임라인의 성격을 갖는 것들을 구현을 한다.
        - LinearLayoutManager
        - GridLayout
        - StaggeredLayout
2. Recycler View Holder
    - View Holder
    - Cache -> 캐시 많이 쓰이거나 쓰일 것이라 예상되는 것들을 빨리 찾아 쓸 수 있는 곳에 미리 배치
    - 빠르면 빠를 수록 비싸진다.
    - 가격 대비 성능 캐싱 기법으로 
    - findViewById 생각보다 댓가가 큰 메소드이다. 
    - 미리 뷰를 찾아놓고 캐시를 하는 것
3. Recycler View Adapter
    - RecyclerView 만을 위한 어댑터
    - <뷰홀더> 를 갖고 있는 어댑터

## 2019 05 19 TIL 
1. selector 를 이용하여 xml 방식으로 작동을 할 수 있다. 
    - button on, button off
    - drawable 에서 사용을 한다.
    - 굳이 이미지를 직접적으로 import 하지 않아도 된다.?
    - 코드로 다양한 상태 이미지를 알 수 있다. <selector>
2. 리싸이클러 뷰
    - data, holder, adapter
    - adapter layout manager 가 필요하다.
    - 같은 형식의 자료들을 스크롤 형식으로 만들고 싶을 때는 RecyclerView를 사용하는 것이 답
    - findView 많이 안하기 위해서 ViewHolder 에 저장을 하는 것이다. 
3. 쓰레드를 사용을 하는 법
    - 동기화 사용을 하는 것
    - Thread 작업의 흐름
    - main/UI
    - 멀티 쓰레딩 -> main Thread 
    - 일을 나누는 것으로 생각 -> 일은 한정 되어 있다.
    - 너무 많이 사용하는 것도 부담이 될 수 있다. 
4. UI thread
    - UI Drawing --> 5초동안 반응이 없으면 꺼버린다. ANR (android no response)
    - TouchEvent
    //아래 2개는 특별히 하나로 사용할 필요가 없다. 
    - Huge Calculation
    - Internet
5. 쓰레드 2가지 구현법
    -  Thread java class 
        - Handler 객체로 값을 보낸다. 
    -  Handler android class
        - Handler 를 통하여 구현을 한다. 
    - AsyncTask (사용하는 것이 편한다.)
        - Thread, Task 를 2가지를 전부 포함된다. 
6. AsyncTask
    - 새로운 쓰레드를 생성해서 작업
    - 코드 간결해서 많이 쓰인다. 
    - 가변형으로 파라미터를 받을 수 있다.
    - doInBackground -- added thread
    - onPostExecute -- UI thread
    
## 2019 05 20 TIL 
1. AsyncTask
    - doInBackground --> added thread 에서 실행
    - 나머지는 UI thread 에서 실행
    - 작동되는 원리는 대충 알겠다. 
    - 객체를 만들고 execute() 메소드를 실행
    - java thread run() 같은 역할이라 생각
2. 이미지 라이브러리 image library
    - 거의 필수로 사용하는 것이라 생각을 하면 된다. 
    - HTTP Client 기능
    - 디스크, 메모리 캐시 기능
    - 이미지 처리 기능
    - 상용에서는 이미지와 같은 기능들을 잘되어 있어야 한다.
    - 다운로드를 다시 불러오지 않아야 한다.
    - LRU 캐시 알고리즘을 사용을 한다. 
    - 고해상도 이미지를 줄여서 사용을 해야 한다. 
        -  out of memory
        -  썸네일 제작 --> 이미지 처리 기능
    - Glide 를 사용을 하는 것이 낫다.?
    