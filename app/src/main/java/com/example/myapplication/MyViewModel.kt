package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel:ViewModel() {
    private val apiService=RetrofitInstance.api
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    fun getposts(){
        viewModelScope.launch {
            try {
                val respose=apiService.getposts()
                if(respose.isNotEmpty())
                {
                    _posts.value=respose
                }
            }catch (e:Exception)
            {
                Log.d("here","error:$e")
            }
        }
    }
}