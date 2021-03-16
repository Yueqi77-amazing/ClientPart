package com.android3.siegertpclient.utils

import android.content.Context
import com.android3.siegertpclient.ui.dummyretrofit.util.Constants

class Token {
    companion object {
        fun getBearerToken(context: Context) : String? {
            val localData = PreferencesProvider(context)
            return localData.getString(Constants.KEY_TOKEN)
        }
    }
}