package com.example.a4thtaskappkts.Fragments


import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.a4thtaskappkts.Adapter.Adapter1
import com.example.a4thtaskappkts.Model.Model
import com.example.a4thtaskappkts.R
import com.example.a4thtaskappkts.databinding.FragmentHomeBinding
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var adapter: Adapter1? = null
    private var modelList: MutableList<Model> = ArrayList()
    private var count: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.nestedView.fullScroll(View.FOCUS_DOWN)
        binding.nestedView.isSmoothScrollingEnabled

        // Setup image slider
        setupImageSlider()

        // Setup RecyclerView
        setupRecyclerView()

        // Set click listeners
        setClickListeners()

        // Load initial data
        loadInitialData()

        // Setup load more
        setupLoadMore()


        return root
    }

    private fun setupImageSlider() {
        val slideModels = arrayListOf(
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_6,
            R.drawable.img_7, R.drawable.img_8
        ).map { SlideModel(it, ScaleTypes.CENTER_CROP) }
        binding.imageSlider.setImageList(slideModels)
    }

    private fun setupRecyclerView() {
        adapter = Adapter1(modelList, requireContext())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    private fun setClickListeners() {
        binding.nearby.setOnClickListener {
            showToast("Nearby 20 Streamers")
        }
        binding.multi.setOnClickListener {
            showToast("Multi 80 Streamers")
        }
        binding.newStreams.setOnClickListener {
            showToast("New 200 Streamers")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun loadInitialData() {
        loadJson()
        topLoadJson()
    }

    private fun setupLoadMore() {
        binding.nestedView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                count++
                binding.loadingPB.visibility = View.VISIBLE
                if (count < 18) {
                    loadJson()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadJson() {
        try {
            val jsonData = readJsonDataFromFile()
            val jsonArray = JSONArray(jsonData)

            for (i in 0 until jsonArray.length()) {
                val itemObject = jsonArray.getJSONObject(i)
                val model = Model(
                    itemObject.getString("Type"),
                    itemObject.getString("ThumbNail"),
                    itemObject.getString("Video"),
                    itemObject.getString("User_name"),
                    itemObject.getString("Country_image"),
                    itemObject.getString("Level"),
                    itemObject.getString("Dollars"),
                    itemObject.getString("Language"),
                    itemObject.getString("Location"),
                    itemObject.getString("TypeText")
                )
                modelList.add(model)
            }
            adapter?.notifyDataSetChanged()
        } catch (e: Exception) {
            logError("Error loading JSON", e)
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    private fun topLoadJson() {
        try {
            val jsonData1 = readTopJsonDataFromFile()
            val jsonArray = JSONArray(jsonData1)

            for (i in 0 until jsonArray.length()) {
                val itemObject1 = jsonArray.getJSONObject(i)

                val userNames = arrayOf(
                    binding.userNameTxt1, binding.userNameTxt2, binding.userNameTxt3
                )
                val levelTxts = arrayOf(
                    binding.level1, binding.level2, binding.level3
                )
                val languages = arrayOf(
                    binding.language1, binding.language2, binding.language3
                )
                val types_Images = arrayOf(
                    binding.typeImage1, binding.typeImage2, binding.typeImage3
                )
                val types_Txts = arrayOf(
                    binding.typeTxt1, binding.typeTxt2, binding.typeTxt3
                )
                val thumbnails = arrayOf(
                    binding.thumbNail1, binding.thumbNail2, binding.thumbNail3
                )
                val countries = arrayOf(
                    binding.country1, binding.country2, binding.country3
                )
                val dollars = arrayOf(
                    binding.dollarsTxt1, binding.dollarsTxt2, binding.dollarsTxt3
                )
                val locations = arrayOf(
                    binding.location1, binding.location2, binding.location3
                )
                val imgs = arrayOf(
                    binding.img4, binding.img5, binding.img6
                )

                for (j in userNames.indices) {
                    val type = itemObject1.getString("Type${j + 1}")
                    val country = itemObject1.getString("Country_image${j + 1}")
                    val thumbnail = itemObject1.getString("ThumbNail${j + 1}")

                    // textView
                    val userName = itemObject1.getString("User_name${j + 1}")
                    val level = itemObject1.getString("Level${j + 1}")
                    val language = itemObject1.getString("Language${j + 1}")
                    val dollar = itemObject1.getString("Dollars${j + 1}")
                    val location = itemObject1.getString("Location${j + 1}")
                    val typeText = itemObject1.getString("TypeText${j + 1}")

                    userNames[j].text = userName
                    levelTxts[j].text = level
                    languages[j].text = language
                    dollars[j].text = dollar
                    locations[j].text = location
                    types_Txts[j].text = typeText

                    loadImage(thumbnail, thumbnails[j])
                    loadImage(country, countries[j])
                    loadImage(thumbnail, imgs[j])
                    loadImage(type, types_Images[j])
                }
            }
        } catch (e: Exception) {
            logError("Error loading top JSON", e)
        }
    }

    @Throws(IOException::class)
    private fun readJsonDataFromFile(): String {
        return requireContext().resources.openRawResource(R.raw.data)
            .bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
    }

    private fun readTopJsonDataFromFile(): String {
        return requireContext().resources.openRawResource(R.raw.topdata)
            .bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(url)
            .placeholder(R.drawable.baseline_language_24)
            .into(imageView)
    }

    companion object {
        const val TAG = "HomeFragment"
    }

    private fun logError(message: String, exception: Exception) {
        Log.e(TAG, message, exception)
    }

}