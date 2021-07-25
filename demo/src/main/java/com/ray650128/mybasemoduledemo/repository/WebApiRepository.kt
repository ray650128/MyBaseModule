package com.ray650128.mybasemoduledemo.repository

import androidx.lifecycle.MutableLiveData
import com.ray650128.mybasemoduledemo.model.Student
import com.ray650128.mybasemoduledemo.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WebApiRepository {

    private val allStudents: MutableLiveData<ArrayList<Student>> by lazy {
        MutableLiveData<ArrayList<Student>>()
    }

    private val student: MutableLiveData<Student> by lazy {
        MutableLiveData<Student>()
    }

    fun getStudents(): MutableLiveData<ArrayList<Student>> {
        val call = RetrofitClient.getApi().getStudents()
        call.enqueue(object : Callback<ArrayList<Student>> {
            override fun onResponse(call: Call<ArrayList<Student>>, response: Response<ArrayList<Student>>) {
                if (response.isSuccessful) {
                    allStudents.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<Student>>, t: Throwable) {
                allStudents.postValue(null)
            }
        })
        return allStudents
    }

    fun getStudent(id: Int): MutableLiveData<Student> {
        val call = RetrofitClient.getApi().getStudent(id)
        call.enqueue(object : Callback<Student> {
            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.isSuccessful) {
                    student.value = response.body()
                }
            }

            override fun onFailure(call: Call<Student>, t: Throwable) {
                student.postValue(null)
            }
        })
        return student
    }
}