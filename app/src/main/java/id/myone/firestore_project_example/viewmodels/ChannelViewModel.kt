package id.myone.firestore_project_example.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import id.myone.firestore_project_example.models.channel.UserChannelModel
import id.myone.firestore_project_example.repository.ChannelRepository
import id.myone.firestore_project_example.utils.SecureDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ChannelViewModel(
    private val repository: ChannelRepository,
    private val secureDataStore: SecureDataStore,
) : ViewModel() {

    var isLoadingProcess = MutableStateFlow(false)

    private val _loginChannelData = MutableSharedFlow<Boolean>()
    val loginChannelData: SharedFlow<Boolean> = _loginChannelData

    private val userChannelQuerySearch = MutableStateFlow("")
    private val lastDocumentSnapshot = MutableStateFlow<DocumentSnapshot?>(null)

    val channelListData = userChannelQuerySearch.debounce(500).mapLatest {
            repository.searchUserChannel(it, lastDocumentSnapshot.value)
        }

    fun searchUserChannel(query: String) {
        userChannelQuerySearch.value = query
    }


    fun loginChannel(name: String) {

        viewModelScope.launch(Dispatchers.IO) {
            isLoadingProcess.value = true

            val payload = UserChannelModel(userName = name)
            val result = repository.loginChannel(payload)

            secureDataStore.loginChannelSession(payload)

            isLoadingProcess.value = false
            _loginChannelData.emit(result)

        }
    }
}