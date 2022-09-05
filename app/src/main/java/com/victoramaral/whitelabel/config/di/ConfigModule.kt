package com.victoramaral.whitelabel.config.di

import com.victoramaral.whitelabel.config.LabelFlagConfig
import com.victoramaral.whitelabel.config.LabelFlagConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ConfigModule {

    @Binds
    fun bindConfig(config: LabelFlagConfigImpl): LabelFlagConfig
}