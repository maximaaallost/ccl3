package com.cc221013.bookify.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc221013.bookify.data.DatabaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val db: DatabaseHandler): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    fun save(student: BccStudent){
        db.insertStudent(student)
    }

    fun getStudents() {
        _mainViewState.update { it.copy(students = db.getStudents()) }
    }

    fun selectScreen(screen: Screen){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    fun clickDelete(student: BccStudent){
        db.deleteStudent(student)
        getStudents()
    }

    fun editStudent(student: BccStudent){
        _mainViewState.update { it.copy(openDialog = true, editStudent = student) }
    }

    fun saveStudent(student: BccStudent){
        _mainViewState.update { it.copy(openDialog = false) }
        db.updateStudent(student)
        getStudents()
    }

    fun dismissDialog(){
        _mainViewState.update { it.copy(openDialog = false) }
    }
}