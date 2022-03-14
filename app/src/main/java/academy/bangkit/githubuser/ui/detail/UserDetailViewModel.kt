package academy.bangkit.githubuser.ui.detail

import academy.bangkit.githubuser.model.UserDetail
import academy.bangkit.githubuser.network.ApiConfig
import academy.bangkit.githubuser.utils.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val userDetail = MutableLiveData<UserDetail>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = _message

    fun setUserDetail(username: String) {
        _isLoading.postValue(true)

        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _isError.postValue(false)
                    userDetail.postValue(response.body())
                } else {
                    _isError.postValue(true)
                    _message.postValue(Event(response.message()))
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isError.postValue(true)
                _message.postValue(Event(t.message!!))
            }
        })
    }

    fun getUserDetail(): LiveData<UserDetail> {
        return userDetail
    }
}