package com.example.cameraguide.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cameraguide.databinding.FragmentImagePickerBinding

class ImagePickerFragment : Fragment() {

    companion object {
        fun newInstance() = ImagePickerFragment()
    }

    private lateinit var _binding: FragmentImagePickerBinding
    private var _index = 0
    private val list = mutableListOf<Uri>()

    // Registers a photo picker activity launcher in single-select mode.
    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
        // Callback is invoked after the user selects media items or closes the
        // photo picker.
        if (uris.isNotEmpty()) {
            list.addAll(uris)
            setImage()
            Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
        } else {
            list.clear()
            setImage()
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.pickImageButton.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        _binding.previousButton.setOnClickListener {
            if (_index > 0) {
                _index--
            } else {
                _index = list.size - 1
            }
            setImage()
        }
        _binding.nextButton.setOnClickListener {
            if (_index + 1 < list.size) {
                _index++
            } else {
                _index = 0
            }
            setImage()
        }
    }

    private fun setImage() {
        _binding.nextButton.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        _binding.previousButton.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

        if (list.isNotEmpty()) {
            val image = list[_index]
            _binding.imageView2.setImageURI(image)
        } else {
            _binding.imageView2.setImageURI(null)
        }
    }
}