package id.myone.firestore_project_example

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import id.myone.firestore_project_example.di.appModule
import id.myone.firestore_project_example.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appModule, viewModelsModule)
        }
    }
}