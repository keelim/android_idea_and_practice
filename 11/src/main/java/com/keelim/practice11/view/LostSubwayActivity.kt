package com.keelim.practice11.view

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.keelim.practice11.R
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class LostSubwayActivity : Activity() {
    var textview: TextView? = null
    var doc: Document? = null
    var layout: LinearLayout? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lost_subway)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        textview = findViewById(R.id.textView1)
        val task = GetXMLTask()
        val api_key = "424a6d657769797338345674617a63"
        val url = "http://openapi.seoul.go.kr:8088/$api_key/xml/ListLostArticleService/1/200/s1/"
        task.execute(url)
    }

    private inner class GetXMLTask : AsyncTask<String?, Void?, Document?>() {
        protected override fun doInBackground(vararg urls: String): Document? {
            val url: URL
            try {
                url = URL(urls[0])
                val dbf = DocumentBuilderFactory.newInstance()
                val db = dbf.newDocumentBuilder() //XML문서 빌더 객체를 생성
                doc = db.parse(InputSource(url.openStream())) //XML문서를 파싱한다.
                doc.getDocumentElement().normalize()
            } catch (e: Exception) {
                Toast.makeText(baseContext, "Parsing Error", Toast.LENGTH_SHORT).show()
            }
            return doc
        }

        protected override fun onPostExecute(doc: Document) {
            val s = StringBuilder()
            //row태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
            val nodeList = doc.getElementsByTagName("row")
            //row태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
            for (i in 0 until nodeList.length) { //데이터를 추출
                s.append("No.").append(i).append("\n")
                val node = nodeList.item(i) //row 노드
                val fstElmnt = node as Element
                var nameList = fstElmnt.getElementsByTagName("ID")
                val nameID = nameList.item(0) as Element
                nameList = nameID.childNodes
                s.append("분실물 ID = ").append(nameList.item(0).nodeValue).append("\n")
                // <ID>분실물 ID</ID>
                val getNameList = fstElmnt.getElementsByTagName("GET_NAME")
                // <GET_NAME>습득물품명</GET_NAME>
                s.append("습득물품명 = ").append(getNameList.item(0).childNodes.item(0).nodeValue).append("\n")
                val getDateList = fstElmnt.getElementsByTagName("GET_DATE")
                // <GET_DATE>습득일자</GET_DATE>
                s.append("습득일자 = ").append(getDateList.item(0).childNodes.item(0).nodeValue).append("\n")
                val takePlaceList = fstElmnt.getElementsByTagName("TAKE_PLACE")
                // <TAKE_PLACE>수령가능장소</TAKE_PLACE>
                s.append("수령가능장소 = ").append(takePlaceList.item(0).childNodes.item(0).nodeValue).append("\n")
                val contactList = fstElmnt.getElementsByTagName("CONTACT")
                // <CONTACT>수령가능장소연락처</CONTACT>
                s.append("수령가능장소연락처 = ").append(contactList.item(0).childNodes.item(0).nodeValue).append("\n")
                val cateList = fstElmnt.getElementsByTagName("CATE")
                // <CATE>습득물분류</CATE>
                s.append("습득물분류 = ").append(cateList.item(0).childNodes.item(0).nodeValue).append("\n")
                //NodeList get_positionList = fstElmnt.getElementsByTagName("GET_POSITION");
// <GET_POSITION>습득위치_회사명</GET_POSITION>
//s += "습득위치_회사명 = "+  get_positionList.item(0).getChildNodes().item(0).getNodeValue() +"\n";
                val statusList = fstElmnt.getElementsByTagName("STATUS")
                // <STATUS>분실물상태</STATUS>
                s.append("분실물상태 = ").append(statusList.item(0).childNodes.item(0).nodeValue).append("\n\n-----------------------------------------------------\n\n")
            }
            textview!!.text = s.toString()
            super.onPostExecute(doc)
        }
    }
}