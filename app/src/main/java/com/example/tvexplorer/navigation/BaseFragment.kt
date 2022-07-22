package com.example.tvexplorer.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import com.example.tvexplorer.com.example.tvexplorer.tools.Const
import timber.log.Timber
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
) : Fragment() {
    lateinit var viewModel: VM private set
    private var _binding: VB? = null
    protected val binding get() = _binding ?: throw Exception("${this::class.java.simpleName} _binding is null")
    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(Const.TAG).i("BaseFragment.onCreate ")
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.tag(Const.TAG).i("BaseFragment.onCreateView ")
        _binding = inflateMethod.invoke(null, inflater, container, false) as VB
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}