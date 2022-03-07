package com.games.alertdialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileWriter
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //alert dialog with basic functionality
    fun simpleAlert(view: View) {
        val builder = AlertDialog.Builder(this)
                .setTitle("Reminder")
                .setMessage("It's time to water the plant")
                .setIcon(R.drawable.ic_flower)
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(this, "Thank you for taking care of your plants!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { _, _ ->
                    Toast.makeText(this, "Your plant might dry out soon!", Toast.LENGTH_SHORT).show()
                }
                .setNeutralButton("Remind later", null)
                .create()
        builder.show()
    }
    //alert dialog with a custom design
    fun customDesignAlert(view: View) {
        val builder = AlertDialog.Builder(this).create()
        val view: View = layoutInflater.inflate(R.layout.custom_dialog, null)
        val buttonThumbUp = view.findViewById<FloatingActionButton>(R.id.fabThumbUp)
        val buttonThumbDown = view.findViewById<FloatingActionButton>(R.id.fabThumbDown)
        builder.setView(view)
        buttonThumbUp.setOnClickListener {
            Toast.makeText(this, ":D", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }
        buttonThumbDown.setOnClickListener {
            Toast.makeText(this, ";c", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }
        //sets the base alert dialog's background to transparent so only the custom one is shown
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.show()
    }
    //alert dialog with sliding animation on enter and on exit
    fun sliderAlert(view: View) {
        //first slider
        val builder = AlertDialog.Builder(this)
            .setTitle("Do you have a minute?")
            .setMessage("It's important for us to know your opinion")
            .setCancelable(false)
            .setPositiveButton("Next") { _, _ ->
                //first slider dialog is dismissed and the second one is called
                sliderAlert2()
            }
            .setNegativeButton("Skip") { _, _ ->
            }
            .create()
        //animation might be not displaying, check if Developer mode -> Window Animation Scale is on on your phone
        builder.window?.attributes?.windowAnimations = R.style.DialogAnimation
        builder.show()
    }
    //second alert dialog with sliding animation on enter and on exit
    private fun sliderAlert2(){
        val foxSounds = arrayOf("Ring-ding-ding", "Wa-pa-pa", "Hatee-ho", "Joff-tchoff")
        val builder2 = AlertDialog.Builder(this)
                .setTitle("What does the Fox say?")
                .setSingleChoiceItems(foxSounds, 0) { _, _ ->
                }
                .setPositiveButton("Submit") { _, _ -> Toast.makeText(this, "Thank you for your opinion!", Toast.LENGTH_SHORT).show()}
                .setNegativeButton("I don't know...") { _, _ -> Toast.makeText(this, "Next time!", Toast.LENGTH_SHORT).show()}
                .create()
        builder2.window?.attributes?.windowAnimations = R.style.DialogAnimation
        builder2.show()
    }
    //alert dialog with a rating bar and text entry
    fun reviewAlert(view: View) {
        val view: View = layoutInflater.inflate(R.layout.rating_dialog, null)
        val builder = AlertDialog.Builder(this)
                .setIcon(android.R.drawable.star_on)
                .setTitle("Please leave us a feedback")
                .setView(view)
                .setPositiveButton("Submit") { _, _ ->
                    Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                    //given rating and review are saved in the file
                    saveReviewData(view)
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
        builder.show()
    }
    //saves the reviewAlert's user's input into a file
    private fun saveReviewData(view: View) {
        val reviewText = view.findViewById<EditText>(R.id.tvReview).text.toString()
        val rating = view.findViewById<RatingBar>(R.id.ratingbar).rating
        try {
            val myPath = this.filesDir
            val file = File(myPath, "Review.txt")
            var fileWriter = FileWriter(file)
            Log.d("thisPath", file.toString())
            fileWriter.write(reviewText + '\n' + rating)
            fileWriter.close()

        } catch (exception: IOException) {
            Log.d("myPath", getApplicationInfo().dataDir)
        }
    }
    /*
    custom design dialog with a gif imageVew
    In build.gradle dependencies should be implemented:
    'pl.droidsonroids.gif:android-gif-drawable:1.2.17'
     */
    fun animationAlert(view: View) {
        val view: View = layoutInflater.inflate(R.layout.animation_dialog, null)
        val builder = AlertDialog.Builder(this)
                .setView(view)
                .create()
        val buttonDelete = view.findViewById<Button>(R.id.btnAnimDelete)
        val buttonOpen = view.findViewById<Button>(R.id.btnAnimOpen)
        buttonDelete.setOnClickListener{
            Toast.makeText(this, "The truth is out there", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }
        buttonOpen.setOnClickListener{
            Toast.makeText(this, "The summit is on the 29/02", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.show()
    }

    //custom design dialog that changes the background color of the activity
    fun colorPickAlert(view: View) {
        val builder = AlertDialog.Builder(this)
                .create()
        val view: View = layoutInflater.inflate(R.layout.color_pick_dialog, null)
        builder.setView(view)
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.findViewById<FloatingActionButton>(R.id.fabClose).setOnClickListener{
            builder.dismiss()
        }
        view.findViewById<Button>(R.id.btnYellow).setOnClickListener {
            bgColorPicker(R.color.yellow_200)
        }
        view.findViewById<Button>(R.id.btnBlue).setOnClickListener {
            bgColorPicker(R.color.teal_200)
        }
        view.findViewById<Button>(R.id.btnGreen).setOnClickListener {
            bgColorPicker(R.color.green_400)
        }
        view.findViewById<Button>(R.id.btnGrey).setOnClickListener {
            bgColorPicker(R.color.grey_300)
        }
        view.findViewById<Button>(R.id.btnPink).setOnClickListener {
            bgColorPicker(R.color.purple_200)
        }
        view.findViewById<Button>(R.id.btnWhite).setOnClickListener {
            bgColorPicker(R.color.white)
        }
        builder.show()
    //changes the background color of the activity
    }
    fun bgColorPicker(c: Int){
        val mainActivityLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        mainActivityLayout.setBackgroundColor(getResources().getColor(c))
    }

}