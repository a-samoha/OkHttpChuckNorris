package com.example.okhttp.chucknorris

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.okhttp.chucknorris.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val URL = "https://api.icndb.com/jokes/random"
    var okHttpClient: OkHttpClient = OkHttpClient()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
            loadRandomFact()
        }
    }

    private fun loadRandomFact() {
        binding.progressBar.visibility = View.VISIBLE

        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                val txt = (JSONObject(json).getJSONObject("value").get("joke")).toString()

                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    binding.factTv.text = Html.fromHtml(txt)
                }
            }
        })
    }
}