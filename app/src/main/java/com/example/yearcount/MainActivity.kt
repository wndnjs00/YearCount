package com.example.yearcount

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // btn이라는 변수에 btnDatePicker 버튼id선언
        val btn : Button = findViewById(R.id.btnDatePicker)

        // btnDatePicker버튼 눌렀을때
        btn.setOnClickListener {
            clickDatePicker()   // clickDatePicker함수안에 있는 내용이 실행

        }

    }

    // clickDatePicker함수
    fun clickDatePicker(){

        //현재 년,월,일 을 가져옴
        val myCalender = Calendar.getInstance()     //날짜
        val year = myCalender.get(Calendar.YEAR)    //년도
        val month = myCalender.get(Calendar.MONTH)  //월
        val day = myCalender.get(Calendar.DAY_OF_MONTH) //날짜

        // DatePickerDialog사용해서 캘린더 띄우기
        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->    //전달할 매개변수


            },
            year,
            month,
            day
            )


        Toast.makeText(this,"버튼이 눌렸습니다", Toast.LENGTH_SHORT).show()
    }
}