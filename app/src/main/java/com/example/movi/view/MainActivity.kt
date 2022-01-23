package com.example.movi.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movi.databinding.ActivityMainBinding
import com.example.movi.viewmodel.MainViewModel
import retrofit2.http.GET
import java.sql.Time
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName", "puducherry")
        binding.CityName.text = cName

        Timer().schedule(5000) {
            viewmodel.refreshData(cName!!)
            getliveData()
        }

//        binding.SwipeRefreshLayout.setOnRefreshListener {
//            binding.llData.visibility = View.GONE
//            binding.tvError.visibility = View.GONE
//            binding.pbLoading.visibility = View.GONE
//
////            var cityName = GET.getString("cityName", cName)?.toLowerCase()
////            edt_city_name.setText(cityName)
//            viewmodel.refreshData(cityName!!)
//            binding.SwipeRefreshLayout.isRefreshing = false
//        }

//        img_search_city.setOnClickListener {
//            val cityName = edt_city_name.text.toString()
//            SET.putString("cityName", cityName)
//            SET.apply()
//            viewmodel.refreshData(cityName)
//            getLiveData()
//            Log.i(TAG, "onCreate: " + cityName)
//        }


    }

    private fun getliveData() {

        viewmodel.weatherData.observe(this, Observer { data ->
            data?.let {

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(binding.imageView)

                binding.Temparature.text = data.main.temp.toString() + "°C"
                binding.humidityValue.text = data.main.humidity.toString() + "%"
                binding.windspeed.text = "Wind Speed " + data.wind.speed.toString()
                binding.SunriseTime.text = Time(data.sys.sunrise.toLong()).toString()
                binding.SunsetTime.text = Time(data.sys.sunrise.toLong()).toString()
            }
        })

        viewmodel.weatherError.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    binding.errorMsg.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.llData.visibility = View.GONE
                } else {
                    binding.errorMsg.visibility = View.GONE
                }
            }
        })

        viewmodel.weatherLoad.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.errorMsg.visibility = View.GONE
                    binding.llData.visibility = View.GONE
                } else {
                    binding.pbLoading.visibility = View.GONE
                }
            }
        })
    }
}