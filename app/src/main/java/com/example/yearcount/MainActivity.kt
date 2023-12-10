package com.example.yearcount

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {


    // 변수선언
    // tvSelectedDate의 id를 가진 Text 부분을 선택한 날짜로 바꿔주기 위해서는 먼저 변수선언해야함
    // private로 설정해놔야 나중에 MainActivity의 다른클래스에서는 사용할수없게됨(즉, 앱이 오작동을 일으키는 불상사가 일어나지 않게하기 위해서)
    // Texview의 변수를 null형식으로 설정한이유는 지금은 초기화하지 않을거지만 나중에 변수를 부여할수도 있기 때문 / 수정가능하게하기 위해서 var사용
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // btn이라는 변수 초기화(레이아웃에서 텍스트 뷰의 id를 가져옴)
        val btn : Button = findViewById(R.id.btnDateSelect)

        // tvSelectedDate이라는 변수 초기화(레이아웃에서 텍스트 뷰의 id를 가져옴)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        // tvAgeInMinute변수 초기화(레이아웃에서 텍스트 뷰의 id를 가져옴)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)



        // btnDateSelect(버튼) 눌렀을때
        btn.setOnClickListener {

            clickDateSelect()   // clickDateSelect 함수안에 있는 내용이 실행
        }

    }



    // clickDatePicker함수
    private fun clickDateSelect(){

        val myCalendar = Calendar.getInstance()     //캘린더를 불러옴
        val year = myCalendar.get(Calendar.YEAR)    //현재 년도
        val month = myCalendar.get(Calendar.MONTH)  //현재 월 (1월:0 ,12월:11)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) // 현재 날짜(일)

        //달력 다이어로그
        val dpd =
            // DatePickerDialog사용해서 캘린더 다이얼로그 띄우기
            DatePickerDialog(this,
                // DatePickerDialog 눌렀을때 (전달할 매개변수(view는 여기서 쓰진않음(_로도 표현가능))
                // 년,월,일 매개변수 전달
                DatePickerDialog.OnDateSetListener { view, selectedyear, selectedmonth, selecteddayofMonth ->

                //month +1은 month를 0~11까지로 값을 읽기 때문에 +1을 해줘야 1~12월로 할 수 있다.
                Toast.makeText(this, "${selectedyear}년 ${selectedmonth + 1}월 ${selecteddayofMonth}일", Toast.LENGTH_SHORT).show()

                    //tvSelectedDate의 id를 가진 Text 부분을 내가 선택한 날짜로 바꿔주는 코드
                val selectedDate = "$selectedyear/${selectedmonth + 1}/$selecteddayofMonth"
                    //setText(text)를 이용해서 selectedDate의 결괏값을 텍스트뷰에 표현 (null형식이라서 ?씀)
                tvSelectedDate?.text = selectedDate.toString()

                    // 날짜 형식설정(SimpleDateFormat을 활용해 theDate변수를 만든다음)
                val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
                    val theDate = sdf.parse(selectedDate)   //그걸 theDate변수에 전달 //parse는 Date형태로 바꿔줌



                    // 선택한 날짜(분)
                    // 달력에서 선택한 날짜 담은변수 theDate 만들기
                    //여기서 Date 개체의 시간(밀리초)가 표시된다
                    //그리고 우리가 알고 있듯이 밀리초라는 공식은 1000으로 나누면 초로 변환될 수 있다
                    //그리고 초를 60으로 나누어 분으로 변환할 수 있다
                    //이제 선택한 날짜를 분 단위로 변경
                    // null안정성을 위해 let안에 코드써준다
                    //그리고 time과 getTime은 같은 기능이다.
                theDate?.let {
                    val selectedDateInMinutes = theDate.time /1000/6

                    // 여기서는 위에서 사용한 날짜 형식을 사용하여 현재 날짜를 분석했다.
                    // selectedDateInMinutes으로 선택한 날짜에서 현재까지 얼마나 시간이 지났는지 초단위로
                    // format을 통해 1970년 1월1일부터 "현재시각"을 초단위로 알수있음
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))




                    currentDate?.let {
                        // 현재 날짜(분)
                        // 지금까지 지난시간을 분단위로(선택한날짜와 현재날짜 사이 지난시간을 분단위로)
                        val currentDateInMinutes = currentDate.time /1000/6

                        //select는 1970년 1월 1일 자정부터 태어날 날 자정까지의 시간
                        //current는 1970년 1월 1일 자정부터 오늘 날까지의 시간
                        //이제 몇 분 안에 차이를 확인하겠다.
                        //현재 Minutes에서 선택한 Minutes를 뺀다.
                        //현재 Minutes에서 선택한 Minutes를 빼야지 두 날짜사이의 분을 정확히 알수있다 
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes



                        // differenceInMinutes의 결괏값을 텍스트뷰에 표현
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }
                }

            },
            year,
            month,
            day
        )



        //달력에서 날짜 선택을 전날까지만 가능하게 제한(오늘과 미래날짜 선택못하게)
        //86400000은 하루의 ms 값이다. 86400000의 값을 빼지 않으면 날짜 선택을 오늘까지만 제한 가능
        //datepicker속성을 특정날짜(전날)까지만 선택할수있게 maxDate대입 (한시간 360만 밀리초*24 =8640만 밀리초)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000

        dpd.show()
    }
}