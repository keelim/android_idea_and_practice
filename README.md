#README.md

##2019 01 12 TIL
1. text 속성 조정
2. 라디오 버튼 및 라디오 그룹
3. hint attribute
4. inputtype 을 조정을 하면 입력시 키패드 같은 것을 떠오르게 할 수 있다.

##2019 01 13 TIL
1. 동작은 자바 소스 파일 > activity - xml % activity 연결이 되어 진다.
2. 인플레이션 xml 파일의 객체를 메모리화 하는 과정 (코드의 순서가 바뀌면 에러가 일어난다.)
3. setContentView() > xml 메모리상에 객체화 하는 것 (Layoutinflater)
4. LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE) >xml 을 객체화 시키기(부분 화면)

##2019 01 15 TIL
1. 어플리케이션을 구성 4가지 요소
    - 액티비티
    - 서비스
    - 브로드캐스트 수신자
    - 내용 제공자 -> 보안으로 인하여 바로 파일 사용이 불가능하다.
2. mainfest.xml > intent filter를 구문을 넣어야지만 app 실행

##2019 01 16 TIL
1. intent 무언 가를 전달 해 주는 것을 (시스템이 일단은 받는다. )
    - 명시적 인텐트 -> tel, http
    - 암시적 인텐트
    - startActivity(intent); > 이것으로 사용을 한다.
2. flag , ExtraData (액티비티는 stack의 구조를 띄고 있다. ) >원하는대로 화면이 바뀌지 않을 수 있다.
    -flag > option? intent 의 flag 를 넣어주는 것이다.
    - onCreate -> CallBack 으로 재사용시에는 호출되지 않는다. Cleartop, singleTop
    - Serializable > 객체 직렬화 Parcelable > Android 권장

3. 수명 주기 lifeCycle onCreate(), onResume, onDestoryed, onStart(), onPause >Thread 하고 비슷
4. 서비스 데몬 처럼 실행이 되어져야 한다. > 시스템이 자동으로 재시작을 해줘야 한다. 
    -  service 를 상속을 한다. 
    
##2019 01 17 TIL
1. 화면은 task로 묶여있어서 옵션을 주는 flag 를 한다. 
2. 서비스는 화면이 없다. > 액티비티로 넣어 주기 위해서는 옵션을 주어야 하며 Intent로 전달을 한다.
3. 브로드 캐스트 수신자 
    - 여러 사람에게 데이터를 뿌려주는 것 > boradcast receiver >intent 를 보내고 받을 수 있다. 
    - 시스템이 관리를 한다. 
        - 일반
        - 순차

##2019 01 19 TIL
1. manifest에서 intent 필터를 설정
2.  extended controls 를 통하여 가상 머신을 제어를 한다. 
3. widget applicate
    - 기본적인 이벤트 처리 메커니즘 > Toast,  대화상자
    - animation 처리 > 쓰레드
    - 프래그먼트
4. 이벤트 처리
    - 터치, 키, 제스처, 포커스 , 화면 방향 변경
    
##2019 01 20 TIL
1. TextView > Event Handling
2. 제스터 사용 > 터치 이벤트를 이용을 하여 처리를 한다. 
    - 이벤트 리스터를 붙여서 사용을 한다. 

##2019 01 21 TIL
1. 방향 전환의 따른 이벤트 처리 방법
    - 가로와 세로의 액티비티를 두가지로 만들 수 있다. layout-land > 가로의 방향을 설정을 할 수 있다. 
    - 상태를 저장을 하여 하나의 액티비티로 가로와 세로를 설정을 할 수 있다. 
2. 토스트와 대화상자
     - 레이아웃 인플레이션을 통하여 토스트를 정의를 할 수 가 있다. 
     - xml을 Toast화 하여 설정?
     - 토스트의 각각 설정 가능
     - selector 상태에 따라 이미지를 다르게 할 수 있는 것
     -  shape / snap bar를 사용을 한다.  
3. 알림 대화 상자
    -  alert dialog snack bar > 
4. 프로그레스 바 > 진행 상태를 보여주는 위젯
    - 코드의 진행율 값?
    - 중간 중간 사용하게 되는 경우는 많다. > 직접 dialog 를 구성을 하는 것도 
        나쁘지 않다. 
5. seek bar > seek bar Listener    
6. 애니메이션 
    - xml에서 애니메이션을 저장을 한다. 
    - anim 폴더를 사용하여 xml 을 저장한다. 

##2019 01 22 TIL
1. 페이지 슬라이딩 > 애니에이션 anim 폴더를 만드록 animation.xml을 만들어 사용
    - translate > 모든 것은 R.id 로 검색을 한다. 
    -starㅅANimation
2. 프래그먼트 >한 화면의 여러 부분 화면을 넣는 방식 > 뷰 처럼 정의를 하는 방식
     - 추가한 화면들을 또 넣어줘야 한다. > 액티비티는 독립된 것이라서
     - 부분적인 레이아웃을 독립적으로 동작할 수 있게 하는 것 > 프래그먼트
     - 부분 화면을 독립적으로 만들어주며 액티비티를 그대로 본떠 만든 것
     - 액티비티에서 요청을 하여야 한다. 인텐트는 안드로이드 시스템이 알아먹는 것.
     - 액티비티 화면 전체를 하는 것처럼 > 프래그먼트도 화면 전환의 효과를 가지고 올 수 있다. 
     - 가벼운 전환을 할 수 있다. > 보안적인 면에서도 나은 장점이 있다. 
     - xml, source > inflation과정을 진행을 해야 한다. 
     - xml > fragment를 추가를 하는 것 // 소스를 이용하여 추가를 하는 방법
     - 프래그먼트 매니저를 통하여 프래그 먼트를 추가를 한다. 

##2019 01 23 
1. 프래그 먼트  > 그 안의 부분화면도 전환 효과를 볼 수 있다.  fragment manager 를 통하여 관리를 한다. 
    - 메소드를 통하여 프래그 먼트를 바뀌어야 한다.
    -  액티비티 메소드를 통하여 만든다. 
    - 각각의 프래그 먼트를 독립적으로 사용을 할 수 있다. > 
2. ActionBar 와 탭을 사용을 하기
    - 메뉴와 툴바의 통합으로 이루어진다. > OptionMenu가 ActionBar 통합이 되어 있다.  > Context menu > 타이틀 부분의 통합이 되어 있다. 
    - res\menu >menu.xml 을 만든다. 
    - showAsAction > alwys 항상 작동을 하는 것