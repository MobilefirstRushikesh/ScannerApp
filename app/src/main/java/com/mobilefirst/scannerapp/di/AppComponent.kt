package com.mobilefirst.scannerapp.di

import com.mobilefirst.scannerapp.data.local.AadhaarDataViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(viewModel: AadhaarDataViewModel)
}
