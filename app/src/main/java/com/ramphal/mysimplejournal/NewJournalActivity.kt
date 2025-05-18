package com.ramphal.mysimplejournal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import com.google.android.material.color.MaterialColors
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.ramphal.mysimplejournal.JournalDetailActivity
import com.ramphal.mysimplejournal.data.DailyJournalDao
import com.ramphal.mysimplejournal.data.DailyJournalModel
import com.ramphal.mysimplejournal.databinding.ActivityNewJournalBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewJournalActivity : AppCompatActivity() {

    private var binding: ActivityNewJournalBinding? = null
    private var journalTitlesList: List<String>? = null
    private var customColorList: List<Int>? = null
    private var titleIndex: Int = -1
    private var colorIndex: Int = 0
    private var datePicker: MaterialDatePicker<Long?>? = null
    private var selectedImageUri: Uri? = null
    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                permissions->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                val view = findViewById<View>(R.id.main)
                if (!isGranted){
                    if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE){
                        Snackbar.make(view,
                            "Oops you just denied the permission",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }else if (permissionName == Manifest.permission.WRITE_EXTERNAL_STORAGE){
                        Snackbar.make(view,
                            "Oops you just denied the permission",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    val openGalleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result->
            if (result.resultCode == RESULT_OK && result.data != null){
                val uri = result.data!!.data!!
                selectedImageUri = uri
                binding?.ivImage?.setImageURI(uri)
                binding?.imageCard?.visibility = View.VISIBLE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = ActivityNewJournalBinding.inflate(layoutInflater)
        setContentView(binding?.main)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildTransitions()
        window.sharedElementReturnTransition = buildTransitions()
        window.sharedElementExitTransition = buildTransitions()
        journalTitlesList = Constant.getJournalTitlesList()
        customColorList = Constant.getCustomColorList()
        datePicker = datePicker()
            .setTitleText("Choose date")
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)  // Show calendar view
            .build()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val journalDao = (application as MyJournalApp).db.dailyJournalDao()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding!!.nestedScrollView) { view, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.setPadding(0, 0, 0, imeHeight)
            insets
        }
        val journalId = intent.getIntExtra("id", -1)
        if (journalId != -1){
            lifecycleScope.launch {
                journalDao.getById(journalId).collect{
                    it?.let {
                        binding?.tvTitle?.text = it.title
                        binding?.journalEntry?.setText(it.content)
                        binding?.btnDate?.text = it.date
                        binding?.main?.setBackgroundColor(getColor(customColorList!![it.color]))
                        binding?.appBarLayout?.setBackgroundColor(getColor(customColorList!![it.color]))
                        binding?.main?.statusBarBackground = ContextCompat.getDrawable(this@NewJournalActivity, customColorList!![it.color])
                        it.image?.let { it1 ->
                            binding?.ivImage?.setImageURI(it1.toUri())
                            binding?.imageCard?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }else{
            binding?.appBarLayout?.setBackgroundColor(ContextCompat.getColor(this@NewJournalActivity, R.color.surface))
            newTitle()
            newColor()
        }

        binding?.btnNewTitle?.setOnClickListener {
            newTitle()
        }

        binding?.btnColor?.setOnClickListener {
            newColor()
        }

        binding?.btnEditTitle?.setOnClickListener {
            onEditBtnClicked()
        }

        binding?.btnDate?.setOnClickListener {
            showDatePicker(datePicker!!)
        }

        binding?.btnAddImage?.setOnClickListener{
            if (isReadStorageAllowed()) {
                val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                openGalleryLauncher.launch(pickIntent)
            }else{
                requestStoragePermission()
            }
        }

        binding?.btnRemoveImage?.setOnClickListener {
            binding?.ivImage?.setImageURI(null)
            binding?.imageCard?.visibility = View.GONE
        }

        binding?.toolBar?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.toolBar?.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_save -> {
                    if (journalId == -1){
                        saveData(journalDao)
                    }else{
                        updateData(journalDao, journalId)
                    }
                    true
                }
                else -> false
            }
        }

    }

    private fun updateData(journalDao: DailyJournalDao, id: Int) {

        val title = binding?.tvTitle?.text.toString()
        val date = binding?.btnDate?.text.toString()
        val entry = binding?.journalEntry?.text.toString()
        val color = colorIndex
        val imagePath = selectedImageUri?.toString() ?: ""

        if (title.isNotEmpty() && date.isNotEmpty() && entry.isNotEmpty()){
            lifecycleScope.launch {
                journalDao.update(
                    DailyJournalModel(
                        id = id,
                        title = title,
                        date = date,
                        content = entry,
                        image = imagePath,
                        color = color
                    )
                )
                Toast.makeText(applicationContext, "Journal Updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }else{
            Snackbar.make(binding?.main!!,"Please Write Something to Save", Snackbar.LENGTH_SHORT).show()
        }

    }

    fun saveData(journalDao: DailyJournalDao){

        val title = binding?.tvTitle?.text.toString()
        val date = binding?.btnDate?.text.toString()
        val entry = binding?.journalEntry?.text.toString()
        val color = colorIndex
        val imagePath = selectedImageUri?.toString() ?: ""

        if (title.isNotEmpty() && date.isNotEmpty() && entry.isNotEmpty()){
            lifecycleScope.launch {
                journalDao.insert(
                    DailyJournalModel(
                        title = title,
                        date = date,
                        content = entry,
                        image = imagePath,
                        color = color
                    )
                )
                Toast.makeText(applicationContext, "Journal Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }else{
            Snackbar.make(binding?.main!!,"Please Write Something to Save", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun buildTransitions(): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            addTarget(R.id.main)
            setAllContainerColors(ContextCompat.getColor(this@NewJournalActivity, R.color.surface))
            duration = 400
            pathMotion = MaterialArcMotion()
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN
        }
    }

    private fun onEditBtnClicked(){
        val dialogView = layoutInflater.inflate(R.layout.edit_title_dilog, null)
        val etTitle = dialogView.findViewById<TextInputEditText>(R.id.et_new_title)
        etTitle.setText(binding?.tvTitle?.text)
        MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Save") { dialog, _ ->
                val newTitle = etTitle.text.toString().trim()
                if (!(newTitle.isEmpty())) {
                    binding?.tvTitle?.text = newTitle
                    hideKeyboard(etTitle)
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun isReadStorageAllowed(): Boolean{
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ){
            showRationalDialog()
        }else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ){
            showRationalDialog()
        }else{
            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    private fun showRationalDialog() {

        MaterialAlertDialogBuilder(this)
            .setTitle("Storage Permission Required")
            .setMessage("Storage permission is necessary for this feature. Please enable it in app settings.")
            .setPositiveButton("Cancel") { dialog, _->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun newColor() {
        colorIndex = (0..19).random()
        binding?.main?.setBackgroundColor(ContextCompat.getColor(this@NewJournalActivity, customColorList!![colorIndex]))
        binding?.appBarLayout?.setBackgroundColor(ContextCompat.getColor(this@NewJournalActivity, customColorList!![colorIndex]))
        binding?.main?.statusBarBackground = ContextCompat.getDrawable(this@NewJournalActivity, customColorList!![colorIndex])
    }

    private fun newTitle(){
        titleIndex = (titleIndex + 1) % journalTitlesList!!.size
        binding?.tvTitle?.text = journalTitlesList?.get(titleIndex)
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showDatePicker(datePicker: MaterialDatePicker<Long?>) {

        datePicker.addOnPositiveButtonClickListener { selection ->
            binding?.btnDate?.text = selection?.toFormattedDate()
        }

        datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
    }

    fun Long.toDate(): Date = Date(this)

    fun Date.format(pattern: String = "dd/MM/yyyy"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(this)
    }

    fun Long.toFormattedDate(pattern: String = "dd/MM/yyyy"): String =
        this.toDate().format(pattern)

}


