package id.myone.firestore_project_example.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.repository.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ChannelViewModel(
    private val repository: ChannelRepository
) : ViewModel() {

    private val _loginChannelData = MutableSharedFlow<Boolean>()
    val loginChannelData: SharedFlow<Boolean> = _loginChannelData

    fun loginChannel(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.loginChannel(UserChannelModel(userName = name))
            _loginChannelData.emit(result)
        }
    }
}