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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.makeyourcs.BuildConfig
import com.example.makeyourcs.R
import com.example.makeyourcs.data.AccountClass
import com.example.makeyourcs.databinding.ActivityUploadBinding
import com.example.makeyourcs.databinding.FragmentUserBinding
import com.example.makeyourcs.ui.user.management.UserMgtViewModel
import com.example.makeyourcs.ui.user.management.UserMgtViewModelFactory
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_upload.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

class UploadActivity : AppCompatActivity(), KodeinAware {
    val TAG = "UPLOADACTIVITY"
    override val kodein by kodein()
    private val factory: UploadViewModelFactory by instance()

    var PICK_IMAGE_FROM_ALBUM = 0
    val AUTOCOMPLETE_REQUEST_CODE = 1

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

        binding.tagPeople.setOnClickListener {
            Log.d("tagPeople","in tag people listener")
            var people = MutableLiveData<List<String>>()
            people=viewModel.searchAccount("dmlfid")

            println(people)
        }

/*//        binding.tagLoc.setOnClickListener{
//
//            if (!Places.isInitialized()) {
//                Places.initialize(getApplicationContext(),BuildConfig.API_KEY , Locale.US);
//            }
//
//            // Set the fields to specify which types of place data to
//            // return after the user has made a selection.
//            val fields = listOf(Place.Field.ID, Place.Field.NAME)
//
//            // Start the autocomplete intent.
//            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
//                .build(this)
//            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
//        }*/

        //back button
        binding.backBtn.setOnClickListener {
            super.onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

        else if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            Log.i("resultCode", "resultCode: ${resultCode.toString()}")

            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i("RESULT_OK", "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("RESULT_ERROR", status.statusMessage)
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                    Log.i("RESULT_CANCELED","canceled")
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}