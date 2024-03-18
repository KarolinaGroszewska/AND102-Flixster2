package com.example.flixster2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var tvShow = intent.getSerializableExtra("show") as? TVShow
        if (tvShow != null) {
            val detailTitle: TextView = findViewById(R.id.detailTitle)
            val detailDesc: TextView = findViewById(R.id.detailDesc)
            val detailImage: ImageView = findViewById(R.id.image)
            val detailYear: TextView = findViewById(R.id.detailYear)
            val detailRating: TextView = findViewById(R.id.detailCountry)
            val detailLanguage: TextView = findViewById(R.id.detailLang)

            detailTitle.text = tvShow.dataTitle
            detailDesc.text = tvShow.dataDesc
            detailYear.text = "Date first aired: " + tvShow.dataAired
            detailRating.text = "Rating: " + tvShow.dataRating + "/10"
            val locale = Locale(tvShow.dataLang)
            val language = locale.getDisplayLanguage(locale)
            detailLanguage.text = "Original Language: $language"
            val imageUrl = "https://image.tmdb.org/t/p/w500" + tvShow.dataImage
            Picasso.get().load(imageUrl).into(detailImage)
        }
    }
}