package com.example.makeyourcs.ui.upload

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.ActivityUploadBinding
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.user.management.UserMgtViewModel
import com.example.makeyourcs.ui.user.management.UserMgtViewModelFactory
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_upload.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class UploadActivity : FragmentActivity(), KodeinAware {
    val TAG = "UPLOADACTIVITY"
    override val kodein by kodein()
    private val factory: UploadViewModelFactory by instance()

    var PICK_IMAGE_FROM_ALBUM = 0
    var photoUri: Uri? = null
    lateinit var binding: ActivityUploadBinding
    private lateinit var viewModel: UploadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload)
        viewModel = ViewModelProviders.of(this, factory).get(UploadViewModel::class.java)
        binding.viewmodel = viewModel

        //open the album
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_IMAGE_FROM_ALBUM)


//        // Initialize the AutocompleteSupportFragment.
//        val autocompleteFragment =
//            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
//                    as AutocompleteSupportFragment
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                // TODO: Get info about the selected place.
//                Log.i(TAG, "Place: ${place.name}, ${place.id}")
//            }
//
//            override fun onError(status: Status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: $status")
//            }
//        })

        //back button
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                //This it path to the selected image
                photoUri = data?.data
                binding.uploadImage.setImageURI(photoUri)
            } else {
                //exit the addPhotoActivity if you leave the album without selecting it
                finish()
            }
        }
    }
}