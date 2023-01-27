package com.example.cameraguide.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cameraguide.R
import com.example.cameraguide.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {

    companion object {
        fun newInstance() = EntryFragment()
    }

    private lateinit var _binding: FragmentEntryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.videoButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, VideoFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        _binding.photoButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        _binding.imagePickerButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, PhotoPickerFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }
}