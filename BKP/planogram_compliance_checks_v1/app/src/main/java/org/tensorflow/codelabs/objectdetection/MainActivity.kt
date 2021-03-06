/**
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.codelabs.objectdetection

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import android.graphics.RectF
import java.io.InputStream
//import android.util.Log

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val TAG = "TFLite - ODT"
        const val REQUEST_IMAGE_CAPTURE: Int = 1
        private const val MAX_FONT_SIZE = 96F
    }

    private lateinit var captureImageFab: Button
    private lateinit var inputImageView: ImageView
    private lateinit var imgSampleOne: ImageView
    private lateinit var imgSampleTwo: ImageView
    private lateinit var imgSampleThree: ImageView
    private lateinit var tvPlaceholder: TextView
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        captureImageFab = findViewById(R.id.captureImageFab)
        inputImageView = findViewById(R.id.imageView)
        imgSampleOne = findViewById(R.id.imgSampleOne)
        imgSampleTwo = findViewById(R.id.imgSampleTwo)
        imgSampleThree = findViewById(R.id.imgSampleThree)
        tvPlaceholder = findViewById(R.id.tvPlaceholder)

        captureImageFab.setOnClickListener(this)
        imgSampleOne.setOnClickListener(this)
        imgSampleTwo.setOnClickListener(this)
        imgSampleThree.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
            resultCode == Activity.RESULT_OK
        ) {
            setViewAndDetect(getCapturedImage())
        }
    }

    /**
     * onClick(v: View?)
     *      Detect touches on the UI components
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.captureImageFab -> {
                try {
                    dispatchTakePictureIntent()
                } catch (e: ActivityNotFoundException) {
                    Log.e(TAG, e.message.toString())
                }
            }
            R.id.imgSampleOne -> {
                setViewAndDetect(getSampleImage(R.drawable.img_meal_one))
            }
            R.id.imgSampleTwo -> {
                setViewAndDetect(getSampleImage(R.drawable.img_meal_two))
            }
            R.id.imgSampleThree -> {
                setViewAndDetect(getSampleImage(R.drawable.img_meal_three))
            }
        }
    }

    // Parse that JSON with Gson
    //fun parseJSON() {
      //  Gson().fromJson(readJSONFromAsset(), YourObjectModel::class.java)
    //}
    /**
     * runObjectDetection(bitmap: Bitmap)
     *      TFLite Object Detection function
     */
    private fun runObjectDetection(bitmap: Bitmap) {
        // Step 1: Create TFLite's TensorImage object
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
                .setMaxResults(30)
                .setScoreThreshold(0.3f)
                .build()
        val detector = ObjectDetector.createFromFileAndOptions(
                this,
             //   "salad.tflite",
            "planogram_compliance_model.tflite",
                options
        )

        // Step 3: Feed given image to the detector
        val results = detector.detect(image)
        val utils = Utils()
        val jsonFileString = utils.getJsonDataFromAsset(applicationContext, "planogram.json")
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }
        val gson = Gson()
        //val listPlanogramType = object : TypeToken<`Planogram.kt`>() {}.type
        //var planograms: List<Planogram> = gson.fromJson(jsonFileString, listPlanogramType)
        val planograms = gson.fromJson(jsonFileString,Planogram::class.java)
        //planograms.forEachIndexed{ idx, shelf -> Log.i("data", "> Item $idx:\n$shelf") }
        Log.i("check", planograms.shelf_details.elementAt(1).shelf_id.toString())
        //val pepsi_box = RectF( 500.0f, 720.0f, 1100.0f, 1010.0f)
        val pepsi_box = RectF( 340.0f, 500.0f, 750.0f, 700.0f)
        val pepsi_box_count = 5
        var add_pepsi_box_count = pepsi_box_count
        var rm_pepsi_box_count = 0

        //val sevenup_box = RectF( 100.0f, 700.0f, 500.0f, 1020.0f)
        val sevenup_box = RectF( 70.0f, 500.0f, 340.0f, 700.0f)
        val sevenup_box_count = 4
        var add_sevenup_box_count = sevenup_box_count
        var rm_sevenup_box_count = 0

        val beans_box = RectF( 30.0f, 150.0f, 380.0f,330.0f)
        val beans_box_count = 4
        var add_beans_box_count = beans_box_count
        var rm_beans_box_count = 0

        //val tomato_box = RectF( 550.0f, 200.0f, 1100.0f, 530.0f)
        val tomato_box = RectF( 380.0f, 150.0f, 750.0f, 330.0f)
        val tomato_box_count = 4
        var add_tomato_box_count = tomato_box_count
        var rm_tomato_box_count = 0

        //val orange_box = RectF( 160.0f, 1100.0f, 550.0f, 1500.0f)
        val orange_box = RectF( 100.0f, 750.0f, 350.0f, 970.0f)
        val orange_box_count = 4
        var add_orange_box_count = orange_box_count
        var rm_orange_box_count = 0

        //val blueberries_box = RectF( 500.0f, 1100.0f, 1100.0f, 1450.0f)
        val blueberries_box = RectF( 350.0f, 750.0f, 600.0f, 970.0f)
        val blueberries_box_count = 4
        var add_blueberries_box_count = blueberries_box_count
        var rm_blueberries_box_count = 0


        for (item in results){
            if (pepsi_box.left <= item.boundingBox.left
                    && pepsi_box.top <= item.boundingBox.top
                && pepsi_box.right >= item.boundingBox.right
                && pepsi_box.bottom >= item.boundingBox.bottom
                //&& item.categories.first().label == "pepsi"
            )
                {
                    if(item.categories.first().label == "pepsi")
                    add_pepsi_box_count = add_pepsi_box_count - 1
                    else rm_pepsi_box_count = rm_pepsi_box_count + 1
                }
            if (sevenup_box.left <= item.boundingBox.left
                && sevenup_box.top <= item.boundingBox.top
                && sevenup_box.right >= item.boundingBox.right
                && sevenup_box.bottom >= item.boundingBox.bottom
                //&& item.categories.first().label == "sevenup"
            )
            {
                if(item.categories.first().label == "sevenup")
                add_sevenup_box_count = add_sevenup_box_count - 1
                else rm_sevenup_box_count = rm_sevenup_box_count + 1
            }
            if (tomato_box.left <= item.boundingBox.left
                && tomato_box.top <= item.boundingBox.top
                && tomato_box.right >= item.boundingBox.right
                && tomato_box.bottom >= item.boundingBox.bottom
                //&& item.categories.first().label == "tomato"
            )
            {
                if( item.categories.first().label == "tomato")
                add_tomato_box_count = add_tomato_box_count - 1
                else rm_tomato_box_count = rm_tomato_box_count + 1
            }
            if (beans_box.left <= item.boundingBox.left
                && beans_box.top <= item.boundingBox.top
                && beans_box.right >= item.boundingBox.right
                && beans_box.bottom >= item.boundingBox.bottom
                //&& item.categories.first().label == "beans"
            )
            {
                if(item.categories.first().label == "beans")
                add_beans_box_count = add_beans_box_count - 1
                else rm_beans_box_count = rm_beans_box_count + 1
            }
            if (orange_box.left <= item.boundingBox.left
                && orange_box.top <= item.boundingBox.top
                && orange_box.right >= item.boundingBox.right
                && orange_box.bottom >= item.boundingBox.bottom
                //&& item.categories.first().label == "orange"
            )
            {
                if(item.categories.first().label == "orange")
                add_orange_box_count = add_orange_box_count - 1
                else rm_orange_box_count = rm_orange_box_count + 1
            }
            if (blueberries_box.left <= item.boundingBox.left
                && blueberries_box.top <= item.boundingBox.top
                && blueberries_box.right >= item.boundingBox.right
                && blueberries_box.bottom >= item.boundingBox.bottom
                //&& item.categories.first().label == "blueberries"
            )
            {
                if(item.categories.first().label == "blueberries")
                add_blueberries_box_count = add_blueberries_box_count - 1
                else rm_blueberries_box_count = rm_blueberries_box_count + 1
            }
        }
        // Step 4: Parse the detection result and show it
        //val bkp_resultToDisplay = results.map {
            // Get the top-1 category and craft the display text
           // it.boundingBox.
           // val category = it.categories.first()
            //it.boundingBox.
            //val text = "${category.label}, ${category.score.times(100).toInt()}%"

            // Create a data object to display the detection result
            //DetectionResult(it.boundingBox, text)
         //   DetectionResult(pepsi_box, text)
        //}
        val resultToDisplay = mutableListOf<DetectionResult>()
        resultToDisplay.add(DetectionResult(pepsi_box,
            "Pepsi +"+add_pepsi_box_count+"|"+"-"+rm_pepsi_box_count))
        resultToDisplay.add(DetectionResult(sevenup_box,
            "Sevenup +"+add_sevenup_box_count+"|"+"-"+rm_sevenup_box_count))
        resultToDisplay.add(DetectionResult(orange_box,
            "Orange +"+add_orange_box_count+"|"+"-"+rm_orange_box_count))
        resultToDisplay.add(DetectionResult(tomato_box,
            "Tomato +"+add_tomato_box_count+"|"+"-"+rm_tomato_box_count))
        resultToDisplay.add(DetectionResult(beans_box,
            "Beans +"+add_beans_box_count+"|"+"-"+rm_beans_box_count))
        resultToDisplay.add(DetectionResult(blueberries_box,
            "Blueberries +"+add_blueberries_box_count+"|"+"-"+rm_blueberries_box_count))
        // Draw the detection result on the bitmap and show it.
        //384x512 pixels is the input image size
        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
        runOnUiThread {
            inputImageView.setImageBitmap(imgWithResult)
        }
    }

    /**
     * debugPrint(visionObjects: List<Detection>)
     *      Print the detection result to logcat to examine
     */
    private fun debugPrint(results : List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(TAG, "Detected object: ${i} ")
            Log.d(TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(TAG, "    Confidence: ${confidence}%")
            }
        }
    }

    /**
     * setViewAndDetect(bitmap: Bitmap)
     *      Set image to view and call object detection
     */
    private fun setViewAndDetect(bitmap: Bitmap) {
        // Display capture image
        inputImageView.setImageBitmap(bitmap)
        tvPlaceholder.visibility = View.INVISIBLE

        // Run ODT and display result
        // Note that we run this in the background thread to avoid blocking the app UI because
        // TFLite object detection is a synchronised process.
        lifecycleScope.launch(Dispatchers.Default) { runObjectDetection(bitmap) }
    }

    /**
     * getCapturedImage():
     *      Decodes and crops the captured image from camera.
     */
    private fun getCapturedImage(): Bitmap {
        // Get the dimensions of the View
        val targetW: Int = inputImageView.width
        val targetH: Int = inputImageView.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = max(1, min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inMutable = true
        }
        val exifInterface = ExifInterface(currentPhotoPath)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateImage(bitmap, 90f)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateImage(bitmap, 180f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateImage(bitmap, 270f)
            }
            else -> {
                bitmap
            }
        }
    }

    /**
     * getSampleImage():
     *      Get image form drawable and convert to bitmap.
     */
    private fun getSampleImage(drawable: Int): Bitmap {
        return BitmapFactory.decodeResource(resources, drawable, BitmapFactory.Options().apply {
            inMutable = true
        })
    }

    /**
     * rotateImage():
     *     Decodes and crops the captured image from camera.
     */
    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    /**
     * createImageFile():
     *     Generates a temporary image file for the Camera app to write to.
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    /**
     * dispatchTakePictureIntent():
     *     Start the Camera app to take a photo.
     */
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    Log.e(TAG, e.message.toString())
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "org.tensorflow.codelabs.objectdetection.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /**
     * drawDetectionResult(bitmap: Bitmap, detectionResults: List<DetectionResult>
     *      Draw a box around each objects and show the object's name.
     */
    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        //if(it.text.contains("beans",ignoreCase = true))
            pen.textAlign = Paint.Align.LEFT
        //else pen.textAlign = Paint.Align.RIGHT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE

            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = MAX_FONT_SIZE
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }
}

/**
 * DetectionResult
 *      A class to store the visualization info of a detected object.
 */
data class DetectionResult(val boundingBox: RectF, val text: String)

