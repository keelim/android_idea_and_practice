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

class Public_institutionActivity : Activity() {
    var textview: TextView? = null
    var doc: Document? = null
    var layout: LinearLayout? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.public_institution)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        textview = findViewById(R.id.textView1)
        val task = GetXMLTask()
        //String api_key = "";
        val url = "http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundInfoAccToClAreaPd?serviceKey=sNPGscNguU3hts7hokCosm6jB819ahxyAZJ8I9VUTRs94PlOg8M9JehtQceZToKaRwpDhKrjOhRt1fqFvbxBpA%3D%3D&N_FD_LCT_CD=LCA000&numOfRows=100"
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
            val nodeList = doc.getElementsByTagName("item")
            //row태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
            for (i in 0 until nodeList.length) { //데이터를 추출
                s.append("No.").append(i).append("\n")
                val node = nodeList.item(i) //row 노드
                val fstElmnt = node as Element
                var nameList = fstElmnt.getElementsByTagName("depPlace")
                val nameID = nameList.item(0) as Element
                nameList = nameID.childNodes
                s.append("보관장소 = ").append(nameList.item(0).nodeValue).append("\n")
                val getNameList = fstElmnt.getElementsByTagName("fdFilePathImg")
                if (getNameList.item(0).childNodes.item(0).nodeValue == "https://www.lost112.go.kr/lostnfs/images/sub/img02_no_img.gif") {
                    s.append("습득물 사진 이미지 = 이미지가 준비중입니다.\n")
                } else {
                    s.append("습득물 사진 이미지 = ").append(getNameList.item(0).childNodes.item(0).nodeValue).append("\n")
                }
                val getDateList = fstElmnt.getElementsByTagName("fdPrdtNm")
                s.append("물품명 = ").append(getDateList.item(0).childNodes.item(0).nodeValue).append("\n")
                val takePlaceList = fstElmnt.getElementsByTagName("fdSbjt")
                s.append("게시제목 = ").append(takePlaceList.item(0).childNodes.item(0).nodeValue).append("\n")
                val cateList = fstElmnt.getElementsByTagName("fdYmd")
                s.append("습득일자 = ").append(cateList.item(0).childNodes.item(0).nodeValue).append("\n")
                val get_positionList = fstElmnt.getElementsByTagName("prdtClNm")
                s.append("물품분류명 = ").append(get_positionList.item(0).childNodes.item(0).nodeValue).append("\n\n-----------------------------------------------------\n\n")
            }
            textview!!.text = s.toString()
            super.onPostExecute(doc)
        }
    }
}