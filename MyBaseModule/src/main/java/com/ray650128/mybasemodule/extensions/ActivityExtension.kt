package com.ray650128.mybasemodule.extensions

import androidx.activity.ComponentActivity
import androidx.annotation.NonNull
import com.ray650128.mybasemodule.util.PermissionUtil


fun ComponentActivity.checkPermissions(permissions: Array<String>,
                                       @NonNull permissionResultCallback: PermissionUtil.PermissionResultCallback) {
    PermissionUtil.checkPermission(this, permissions, permissionResultCallback)
}


fun ComponentActivity.requestPermissionsResult(requestCode: Int,
                                                 @NonNull permissions: Array<out String>,
                                                 @NonNull grantResults: IntArray) {
    PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
}