package com.mobilefirst.scannerapp.ui.activities

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.mobilefirst.scannerapp.R
import com.mobilefirst.scannerapp.model.AadhaarData
import com.mobilefirst.scannerapp.data.local.AadhaarDataViewModel
import com.permissionx.guolindev.PermissionX
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Document
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

@AndroidEntryPoint
class ScanningActivity : AppCompatActivity() {
    lateinit var tv: TextView
    private val viewModel: AadhaarDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning)
        tv = findViewById(R.id.tv)
        cameraPerms()
        viewModel.insertionResult.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun cameraPerms(){
        PermissionX.init(this)
            .permissions( Manifest.permission.CAMERA)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                   initCameraView()

                } else {
                    cameraPerms()
                }
            }
    }

    private fun addToDb(data: AadhaarData) {
        viewModel.insertAadhaarData(data)
    }

    private fun initCameraView(){
        // Start QR code scanning
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setBeepEnabled(false)
        integrator.captureActivity
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // QR code data is available

                try {
                    val qrCodeData = result.contents
                    val document = parseXml(qrCodeData)
                    val aadhaarData = extractData(document)
                    Log.d("SCANNED_DATA"," :  $qrCodeData")
                   addToDb(aadhaarData)
                }
                catch (e : Exception){
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show()
                    finish()
                }
            } else {
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            finish()
        }
    }


    private fun parseXml(xmlString: String): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        return builder.parse(ByteArrayInputStream(xmlString.toByteArray()))
    }

    private fun extractData(document: Document): AadhaarData {
        val root = document.documentElement
        return AadhaarData(
            uid = root.getAttribute("uid"),
            name = root.getAttribute("name"),
            gender = root.getAttribute("gender"),
            yob = root.getAttribute("yob"),
            co = root.getAttribute("co"),
            loc = root.getAttribute("loc"),
            vtc = root.getAttribute("vtc"),
            dist = root.getAttribute("dist"),
            state = root.getAttribute("state"),
            pc = root.getAttribute("pc")
        )
    }


}