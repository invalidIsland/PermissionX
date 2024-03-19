package com.permissionx.maomaodev

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    private var callback: PermissionCallback? = null

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val deniedList = ArrayList<String>()
            for ((permission, granted) in permissions) {
                if (!granted) {
                    deniedList.add(permission)
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }

    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        launcher.launch(permissions)
    }

}