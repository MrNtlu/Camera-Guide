package com.example.cameraguide.ui

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.cameraguide.databinding.FragmentImagePickerBinding
import java.util.*

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
            Log.d("PhotoPicker", "Number of items selected: ${uris.size} $uris")
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
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
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
        _index = 0
        _binding.nextButton.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        _binding.previousButton.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

        if (list.isNotEmpty()) {
            val photo = list[_index]
            val isVideo = getMimeType(photo)?.startsWith("video") == true

            _binding.imageView2.visibility = if (isVideo) View.GONE else View.VISIBLE
            _binding.videoView2.visibility = if (isVideo) View.VISIBLE else View.GONE

            if (isVideo){
                _binding.videoView2.setVideoURI(photo)
                _binding.imageView2.setImageURI(null)

                _binding.videoView2.start()
            } else {
                _binding.videoView2.setVideoURI(null)
                _binding.imageView2.setImageURI(photo)
            }
        } else {
            _binding.imageView2.setImageURI(null)
            _binding.videoView2.setVideoURI(null)
            _binding.imageView2.visibility = View.GONE
            _binding.videoView2.visibility = View.GONE
        }
    }

    // Reference https://stackoverflow.com/a/31691791/7168855
    private fun getMimeType(uri: Uri): String? {
        val mimeType: String? = if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
            val cr = context?.contentResolver
            cr?.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                uri.toString()
            )
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.lowercase(Locale.getDefault())
            )
        }
        return mimeType
    }
}