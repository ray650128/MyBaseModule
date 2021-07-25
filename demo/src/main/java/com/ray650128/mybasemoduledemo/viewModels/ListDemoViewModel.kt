package com.ray650128.mybasemoduledemo.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray650128.mybasemoduledemo.model.Student
import com.ray650128.mybasemoduledemo.repository.WebApiRepository

class ListDemoViewModel: ViewModel() {

    private var student: MutableLiveData<Student> = MutableLiveData()

    fun loadStudents(): MutableLiveData<ArrayList<Student>> {
        return WebApiRepository.getStudents()
    }

    fun getStudent(id: Int): MutableLiveData<Student> {
        student = WebApiRepository.getStudent(id)
        return student
    }

    fun getAverage(): MutableLiveData<Float> {
        val student = student.value
        var score = 0
        val average = MutableLiveData<Float>()
        if (student != null) {
            for (subject in student.exams) {
                score += subject.score
            }
            val avg = score.toFloat() / student.exams.size.toFloat()
            average.value = avg
        } else {
            average.postValue(null)
        }
        return average
    }
}