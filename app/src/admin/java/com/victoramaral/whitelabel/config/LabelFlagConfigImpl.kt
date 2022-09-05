package com.victoramaral.whitelabel.config

import android.view.View
import javax.inject.Inject

class LabelFlagConfigImpl @Inject constructor() : LabelFlagConfig {
    override val addButtonVisibility: Int = View.VISIBLE
}