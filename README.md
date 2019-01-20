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
4. LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE) >xml 을 객체화 시키기(부분화면)


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
    


    

