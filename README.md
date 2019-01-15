##2019 01 12 TDL
1. text 속성 조정
2. 라디오 버튼 및 라디오 그룹
3. hint attribute
4. inputtype 을 조정을 하면 입력시 키패드 같은 것을 떠오르게 할 수 있다.


##2019 01 13 TDL
1. 동작은 자바 소스 파일 > activity - xml % activity 연결이 되어 진다.
2. 인플레이션 xml 파일의 객체를 메모리화 하는 과정 (코드의 순서가 바뀌면 에러가 일어난다.)
3. setContentView() > xml 메모리상에 객체화 하는 것 (Layoutinflater)
4. LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE) >xml 을 객체화 시키기(부분화면)


##2019 01 15 TDL
1. 어플리케이션을 구성 4가지 요소
    - 액티비티
    - 서비스
    - 브로드캐스트 수신자
    - 내용 제공자 -> 보안으로 인하여 바로 파일 사용이 불가능하다.
2.