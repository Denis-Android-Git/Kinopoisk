package com.example.kinopoisk.ui.detail_fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import coil.load
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentDetailBinding
import com.example.kinopoisk.ui.gallary_fragments.PicturesAdapter
import com.example.kinopoisk.ui.gallary_fragments.PicturesViewModel
import com.example.kinopoisk.ui.gallary_fragments.PicturesViewModelFactory
import com.example.kinopoisk.ui.home.HomeFragment
import com.example.kinopoisk.ui.home.MovieListAdapter
import com.example.kinopoisk.utils.onItemClick
import com.example.kinopoisk.utils.onPersonClick
import com.example.kinopoisk.utils.onPictureClick
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var bottomNavBarVisibilityListener: HomeFragment.BottomNavBarVisibilityListener? = null

    private val staffAdapter = StaffAdapter { item, view ->
        onPersonClick(item, view, this)
    }
    private val actorAdapter = ActorAdapter { item, view ->
        onPersonClick(item, view, this)
    }

    private val picturesAdapter = PicturesAdapter { picture, imageView ->
        onPictureClick(picture, imageView, this)
    }
    private val similarsAdapter = MovieListAdapter { movie ->
        onItemClick(movie, this)
    }

    private val dbViewModel: DBViewModel by activityViewModels { DBViewModelFactory(requireActivity().application) }

    private val movieAndActorsViewModel: MovieActorsSimilarsViewModel by viewModels()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        bottomNavBarVisibilityListener = activity as? HomeFragment.BottomNavBarVisibilityListener

        bottomNavBarVisibilityListener?.setBottomNavBarVisibility(true)

        binding.galaryRecycler.adapter = picturesAdapter
        binding.actorsRecycler.adapter = actorAdapter
        binding.staffRecycler.adapter = staffAdapter
        binding.similarRecycler.adapter = similarsAdapter

        val filmId = arguments?.getInt("filmId")
        val kinopoiskId = arguments?.getInt("kinopoiskId")

        bottomSheetBehavior = BottomSheetBehavior.from<View>(binding.sheetBottom).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = 0
        }

        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        binding.backgroundOverlay.visibility = View.VISIBLE
                    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        binding.backgroundOverlay.visibility = View.GONE
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            }
        )
        binding.backgroundOverlay.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.settings.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.textViewCreateNewCollectionBottom.setOnClickListener {
            showAddCollectionDialog()
        }
        binding.imageViewCloseEditCollectionBottom.setOnClickListener {
            unShowAddCollectionDialog()
        }
        binding.textInputLayoutCollectionBottom.setEndIconOnClickListener {
            val collectionName = binding.editTextCollectionBottom.text.toString()
            if (collectionName.isNotEmpty()) {
                dbViewModel.addCollection(collectionName)
                unShowAddCollectionDialog()
            }
        }

        binding.galaryAll.setOnClickListener {
            val bundle = Bundle()
            if (kinopoiskId != 0) {
                bundle.putInt("id", kinopoiskId!!)
            } else {
                bundle.putInt("id", filmId!!)
            }
            findNavController().navigate(R.id.gallaryFragment, bundle)
        }

        if (kinopoiskId != 0) {
            getData(kinopoiskId!!)
            Log.d("getData-1", "getData-1: $kinopoiskId")
            checkIdAndAddToInteresting(kinopoiskId)
        } else {
            getData(filmId!!)
            Log.d("getData-2", "getData-2: $filmId")
            checkIdAndAddToInteresting(filmId)
        }

        movieAndActorsViewModel.similars.onEach {
            similarsAdapter.submitList(it)
            binding.allSimilar.text = it.size.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        movieAndActorsViewModel.staffList.onEach { staffList ->
            actorAdapter.run {
                submitList(staffList.filter {
                    it.professionKey == "ACTOR"
                })
            }
            binding.allActors.text = staffList.filter {
                it.professionKey == "ACTOR"
            }.size.toString()

            staffAdapter.run {
                submitList(staffList.filter {
                    it.professionKey != "ACTOR"
                })
            }
            binding.allStaff.text = staffList.filter {
                it.professionKey != "ACTOR"
            }.size.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        var nameOriginal: String? = null
        var nameRu: String? = null
        var nameEn: String? = null

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                movieAndActorsViewModel.movieDescription.collect { descriptionDto ->
                    binding.share.setOnClickListener {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Как тебе фильм? ${descriptionDto?.webUrl}"
                            )
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }
                    if (descriptionDto?.type == "TV_SERIES") {
                        binding.seriesText.visibility = View.VISIBLE
                        binding.seasonsCount.visibility = View.VISIBLE
                        binding.seriesAll.visibility = View.VISIBLE
                    }
                    binding.shortDescription.text = descriptionDto?.shortDescription
                    binding.description.text = descriptionDto?.description
                    binding.nameEnglish.text =
                        if (descriptionDto?.nameOriginal.isNullOrEmpty()) descriptionDto?.nameRu?.uppercase() else descriptionDto?.nameOriginal?.uppercase()
                    binding.nameRussian.text = descriptionDto?.nameRu
                    binding.textViewNameFilmBottom.text = descriptionDto?.nameRu

                    binding.rating.text =
                        if (descriptionDto?.ratingKinopoisk == 0.0) "" else descriptionDto?.ratingKinopoisk?.toString()
                            ?: ""
                    val rating =
                        if (descriptionDto?.ratingAgeLimits == null) "" else descriptionDto.ratingAgeLimits!!.replace(
                            "age",
                            ""
                        ) + "+"
                    binding.age.text = rating
                    binding.genre.text = descriptionDto?.genres?.take(3)?.joinToString(", ") {
                        it.genre.toString()
                    }
                    binding.textViewGenreFilmBottom.text =
                        descriptionDto?.genres?.take(3)?.joinToString(", ") { it.genre.toString() }
                    binding.year.text = descriptionDto?.year?.toString() ?: ""
                    binding.country.text = descriptionDto?.countries?.take(1)?.joinToString {
                        it.country.toString()
                    } ?: ""
                    binding.time.text = "${descriptionDto?.filmLength ?: ""} мин"
                    binding.movieImage.load(descriptionDto?.posterUrl)
                    binding.imageViewBottomMoviePictures.load(descriptionDto?.posterUrl)

                    nameOriginal = descriptionDto?.nameOriginal ?: ""
                    nameEn = descriptionDto?.nameEn ?: ""
                    nameRu = descriptionDto?.nameRu ?: ""
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                movieAndActorsViewModel.series.collect { seasonList ->
                    val episodeCount = seasonList.sumOf { season ->
                        season.episodes!!.size
                    }

                    binding.seasonsCount.text =
                        "Сезонов: ${seasonList.size}, Серий: $episodeCount"

                    binding.seriesAll.setOnClickListener {
                        val bundle = Bundle()
                        val parcelableList = seasonList as? ArrayList<out Parcelable>
                        bundle.putParcelableArrayList("seasonList", parcelableList)
                        bundle.putString("nameEn", nameEn)
                        bundle.putString("nameOriginal", nameOriginal)
                        bundle.putString("nameRu", nameRu)

                        findNavController().navigate(R.id.seriesFragment, bundle)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                movieAndActorsViewModel.state.collect {
                    when (it) {
                        com.example.data.data.State.Error -> binding.progressCircular.visibility =
                            View.GONE

                        com.example.data.data.State.Loading -> binding.progressCircular.visibility =
                            View.VISIBLE

                        com.example.data.data.State.Success -> binding.progressCircular.visibility =
                            View.GONE
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showAddCollectionDialog() {
        binding.cardViewEditCollectionBottom.visibility = View.VISIBLE
        binding.scroll.visibility = View.GONE
        binding.sheetBottom.visibility = View.GONE
    }

    private fun unShowAddCollectionDialog() {
        binding.cardViewEditCollectionBottom.visibility = View.GONE
        binding.scroll.visibility = View.VISIBLE
        binding.sheetBottom.visibility = View.VISIBLE
    }

    private fun getData(id: Int) {

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                dbViewModel.allCollectionsWithMovies.collect { list ->

                    val bottomSheetAdapter = BottomSheetAdapter(id, dbViewModel)

                    binding.recyclerViewCollectionBottom.adapter = bottomSheetAdapter

                    val filteredList = list.filter {
                        it.collection.collectionName != "Viewed" &&
                                it.collection.collectionName != "interesting"
                    }

                    bottomSheetAdapter.submitList(filteredList)

                    val viewedCollection = list.find { it.collection.collectionName == "Viewed" }
                    val likedCollection = list.find { it.collection.collectionName == "Любимые" }
                    val wantToWatch =
                        list.find { it.collection.collectionName == "Хочу посмотреть" }

                    var isViewed = false
                    var isLiked = false
                    var isMarked = false

                    if (wantToWatch != null) {
                        if (wantToWatch.movies.any { it.movieId == id }) {
                            binding.mark.setImageResource(R.drawable.icon_mark)
                            isMarked = true
                        } else {
                            binding.mark.setImageResource(R.drawable.icon_not_mark)

                        }
                    }

                    if (likedCollection != null) {
                        if (likedCollection.movies.any { it.movieId == id }) {
                            binding.like.setImageResource(R.drawable.icon_like)
                            isLiked = true
                        } else {
                            binding.like.setImageResource(R.drawable.icon_not_like)
                        }
                    }

                    if (viewedCollection != null) {
                        if (viewedCollection.movies.any { it.movieId == id }) {
                            binding.viewed.setImageResource(R.drawable.icon_viewed)
                            isViewed = true
                        } else {
                            binding.viewed.setImageResource(R.drawable.icon_not_viewed)
                        }
                    }

                    binding.mark.setOnClickListener {
                        if (isMarked) {
                            binding.mark.setImageResource(R.drawable.icon_not_mark)
                        } else {
                            binding.mark.setImageResource(R.drawable.icon_mark)
                        }
                        isMarked = !isMarked

                        val foundId1 = wantToWatch?.movies!!.find { it.movieId == id }
                        if (foundId1 == null) {
                            dbViewModel.addMovieId(id, wantToWatch.collection.collectionId)
                        } else {
                            dbViewModel.deleteMovieId(foundId1)
                        }
                    }

                    binding.like.setOnClickListener {
                        if (isLiked) {
                            binding.like.setImageResource(R.drawable.icon_not_like)
                        } else {
                            binding.like.setImageResource(R.drawable.icon_like)
                        }
                        isLiked = !isLiked

                        val foundId2 = likedCollection?.movies?.find { it.movieId == id }

                        if (foundId2 == null) {
                            dbViewModel.addMovieId(
                                id,
                                likedCollection?.collection!!.collectionId
                            )
                        } else {
                            dbViewModel.deleteMovieId(foundId2)
                        }
                    }

                    binding.viewed.setOnClickListener {
                        if (isViewed) {
                            binding.viewed.setImageResource(R.drawable.icon_not_viewed)
                        } else {
                            binding.viewed.setImageResource(R.drawable.icon_viewed)
                        }

                        isViewed = !isViewed

                        val foundId3 = viewedCollection?.movies?.find { it.movieId == id }

                        if (foundId3 == null) {
                            dbViewModel.addMovieId(
                                id,
                                viewedCollection?.collection!!.collectionId
                            )
                        } else {
                            dbViewModel.deleteMovieId(foundId3)
                        }
                    }
                }
            }
        }

        val picturesViewModel =
            ViewModelProvider(
                this,
                PicturesViewModelFactory(id)
            )[PicturesViewModel::class.java]

        picturesViewModel.get20("STILL")

        picturesViewModel.pictures20.onEach {
            picturesAdapter.submitData(PagingData.from(it))
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        movieAndActorsViewModel.getAllDetails(id)
    }

    private fun checkIdAndAddToInteresting(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                dbViewModel.allCollectionsWithMovies.collect { list ->
                    if (list.isNotEmpty()) {
                        val interesting =
                            list.find { it.collection.collectionName == "interesting" }
                        if (interesting != null) {
                            if (!interesting.movies.any { it.movieId == id }) {
                                delay(200)
                                dbViewModel.addMovieId(id, interesting.collection.collectionId)
                            }
                        }
                    }
                }
            }
        }
    }
}