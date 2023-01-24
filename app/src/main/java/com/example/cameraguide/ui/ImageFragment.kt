package com.example.cameraguide.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.example.cameraguide.databinding.FragmentImageBinding
import com.example.cameraguide.utils.rotate
import com.example.cameraguide.utils.toBitmap

private const val ARG_PARAM1 = "param1"

@ExperimentalGetImage class ImageFragment(
    private var imageProxy: ImageProxy
) : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(image: ImageProxy) =
            ImageFragment(image)
    }

    private lateinit var _binding: FragmentImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageProxy.image?.let {
            _binding.imageView.setImageBitmap(it.toBitmap().rotate(imageProxy.imageInfo.rotationDegrees.toFloat()))
        }
    }
}