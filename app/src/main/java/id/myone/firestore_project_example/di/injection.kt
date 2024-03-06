package id.myone.firestore_project_example.di

import com.google.firebase.firestore.FirebaseFirestore
import id.myone.firestore_project_example.repository.ChannelRepository
import id.myone.firestore_project_example.repository.ChatRepository
import id.myone.firestore_project_example.repository.ChatSessionRepository
import id.myone.firestore_project_example.viewmodels.ChannelViewModel
import id.myone.firestore_project_example.viewmodels.ChatUserSessionViewModel
import id.myone.firestore_project_example.viewmodels.ChatUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single { ChatSessionRepository(get()) }
    single { ChatRepository(get()) }
    single { ChannelRepository(get()) }
}

val viewModelsModule = module {

    viewModel { ChatUserSessionViewModel(get(), get()) }
    viewModel { ChatUserViewModel(get()) }
    viewModel { ChannelViewModel(get()) }

}