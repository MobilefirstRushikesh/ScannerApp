package com.mobilefirst.scannerapp.ui.activities

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.mobilefirst.scannerapp.R
import com.mobilefirst.scannerapp.databinding.ActivityCroppingBinding
import com.mobilefirst.scannerapp.databinding.ActivityMainBinding
import com.permissionx.guolindev.PermissionX
import com.yalantis.ucrop.UCrop
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@AndroidEntryPoint
class CroppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCroppingBinding
    private var imageUri = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCroppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
      filePerms()

        binding.downloadBtn.setOnClickListener {
           saveImageToGallery(viewToBitmap(binding.imgv)!!,System.currentTimeMillis().toString())
        }
    }

    private fun viewToBitmap(view: View): Bitmap? {
        var createBitmap: Bitmap? = null
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        return try {
            createBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            view.draw(Canvas(createBitmap))
            createBitmap
        } catch (e: Exception) {
            createBitmap
        } finally {
            view.destroyDrawingCache()
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap, fileName: String) {
        val resolver = this.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/")
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        }
        Toast.makeText(this, "Downloaded", Toast.LENGTH_SHORT).show()

    }


    private fun filePerms(){
        PermissionX.init(this)
            .permissions( Manifest.permission.ACCESS_MEDIA_LOCATION)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    picsContract.launch("image/*")

                } else {
                    filePerms()
                }
            }
    }

    private fun startCrop(sourceUri: Uri) {
        val destinationUri = Uri.fromFile(File(this.cacheDir, "cropped_image.jpg"))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f) // Set the desired aspect ratio
            .start(this,  111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val croppedUri = UCrop.getOutput(data!!)
            binding.imgv.setImageURI(croppedUri)
            imageUri = croppedUri.toString()

        }else{
           finish()
        }
    }

    private val picsContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                startCrop(it)
            }
        }
}