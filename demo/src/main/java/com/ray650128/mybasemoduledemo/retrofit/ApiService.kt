package com.ray650128.mybasemoduledemo.retrofit

import com.ray650128.mybasemoduledemo.model.Student
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("ray650128/api_demo/students")
    fun getStudents(): Call<ArrayList<Student>>

    @GET("ray650128/api_demo/students/{id}")
    fun getStudent(@Path("id") id: Int): Call<Student>

}