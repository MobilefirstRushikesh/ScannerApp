package com.mobilefirst.scannerapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mobilefirst.scannerapp.R
import com.mobilefirst.scannerapp.data.local.AadhaarDataViewModel
import com.mobilefirst.scannerapp.databinding.ActivityMainBinding
import com.mobilefirst.scannerapp.model.AadhaarData
import com.mobilefirst.scannerapp.ui.adapters.AadhaarRCAdapter
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var cropBtn:Button
    lateinit var scanBtn:Button
    private val viewModel: AadhaarDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scanBtn = findViewById(R.id.scanBtn)
        cropBtn = findViewById(R.id.cropBtn)
viewModel.getAadhaarData()
        viewModel.savedData.observe(this) { aData ->
            if (aData.isNullOrEmpty()) {


            } else {
                showDataList(aData)
            }
        }

        scanBtn.setOnClickListener {
            val intent = Intent(this, ScanningActivity::class.java)
            startActivity(intent)
        }
        cropBtn.setOnClickListener {
            val intent = Intent(this, CroppingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDataList(aData: List<AadhaarData>) {
        val adapter = AadhaarRCAdapter()
        adapter.setData(aData)
        binding.recyclerView .adapter = adapter
    }

}