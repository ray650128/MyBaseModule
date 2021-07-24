package com.ray650128.mybasemodule.viewModelUtils

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

private val vmStores = HashMap<String, CustomViewModelStore>()

/**
 * 將 ViewModel 與 Activity 綁定
 */
fun ComponentActivity.bindViewModel() {
    this::class.java.declaredFields.forEach { field ->
        field.getAnnotation(ViewModelField::class.java)?.also { scope -> // 取得作用域
            val element = scope.scopeName
            val store: CustomViewModelStore
            if (vmStores.keys.contains(element)) { //如果該作用域在快取內，則從快取中取得 ViewModel store
                store = vmStores[element]!!
            } else {    // 如果作用域不存在則建立一個新的 ViewModel store
                store = CustomViewModelStore()
                vmStores[element] = store
            }
            store.bindHost(this)
            val clazz = field.type as Class<ViewModel>
            val vm = ViewModelProvider(store, VMFactory()).get(clazz)
            // ViewModel 給值
            field.set(this, vm)
        }
    }
}

/**
 * 將 ViewModel 與 Fragment 綁定
 */
fun Fragment.bindViewModel() {
    this::class.java.declaredFields.forEach { field ->
        field.getAnnotation(ViewModelField::class.java)?.also { scope -> // 取得作用域
            val element = scope.scopeName
            val store: CustomViewModelStore
            if (vmStores.keys.contains(element)) { //如果該作用域在快取內，則從快取中取得 ViewModel store
                store = vmStores[element]!!
            } else {    // 如果作用域不存在則建立一個新的 ViewModel store
                store = CustomViewModelStore()
                vmStores[element] = store
            }
            store.bindHost(this)
            val clazz = field.type as Class<ViewModel>
            val vm = ViewModelProvider(store, VMFactory()).get(clazz)
            // ViewModel 給值
            field.set(this, vm)
        }
    }
}

class CustomViewModelStore : ViewModelStoreOwner {
    private val bindTargets = ArrayList<LifecycleOwner>()
    private var vmStore: ViewModelStore? = null

    // 綁定
    fun bindHost(host: LifecycleOwner) {
        if (!bindTargets.contains(host)) {
            bindTargets.add(host)
            // 綁定生命週期
            host.lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        bindTargets.remove(host)
                        if (bindTargets.isEmpty()) {    // 如果 ViewModel store 沒有關聯的對象，則釋放資源
                            vmStores.entries.find { it.value == this@CustomViewModelStore }?.also { map ->
                                vmStore?.clear()
                                vmStores.remove(map.key)
                            }
                        }
                    }
                }
            })
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        if (vmStore == null)
            vmStore = ViewModelStore()
        return vmStore!!
    }
}

class VMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.newInstance()
    }
}
