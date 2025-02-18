package edu.temple.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
const val TEXTKEY = "fontSize"
class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create array of integers that are multiples of 5
        // Verify correctness by examining array values.
        val textSizes = Array(20){(it + 1) * 5}

        this.onResume()

        Log.d("Array values", textSizes.contentToString())

        with (findViewById<RecyclerView>(R.id.textSizeSelectorRecyclerView)) {

            // TODO Step 2: Implement lambda body to launch new activity and pass value
            adapter = TextSizeAdapter(textSizes){

                //this is by using the lambda
                    size: Int ->
                startActivity(
                    Intent(//because this is inside the scope function, you have to say @MainActivity to be specific.
                        //if the line below instantiating the Intent was made outside of the scope, then you can just say 'this'
                        this@MainActivity, DisplayActivity::class.java //we are using the context of DisplayActivity Why?
                    ).apply{//apply in order to use putsextra
                        putExtra(TEXTKEY, size.toFloat())
                    }
                )


//                //dont need lambda
//                startActivity(
            //                Intent(
            //                this@MainActivity, MainActivity::class.java
            //                ).apply{
                //                        putExtra(TEXTKEY, it.toFloat())
                //                    }
                //this 'it' is referencing the textSizes array.
                //the "it' does the job of passing the array through to the activity

            }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }



    }
}


/* Convert to RecyclerView.Adapter */
class TextSizeAdapter (private val textSizes: Array<Int>, private val callback: (Int)->Unit) : RecyclerView.Adapter<TextSizeAdapter.TextSizeViewHolder>() {

    // TODO Step 1: Complete onClickListener to return selected number
    inner class TextSizeViewHolder(val textView: TextView ) : RecyclerView.ViewHolder (textView) {

        init {
            textView.setOnClickListener { callback(textSizes[adapterPosition]) }//from recycler view, viewholder
            //you want to pass the value of the array that is associated with the value of the array that is being clicked
            //
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextSizeViewHolder {
        return TextSizeViewHolder(TextView(parent.context).apply { setPadding(5, 20, 0, 20) })
    }

    override fun onBindViewHolder(holder: TextSizeViewHolder, position: Int) {
        holder.textView.apply {
            text = textSizes[position].toString()
            textSize = textSizes[position].toFloat()
        }
    }

    override fun getItemCount(): Int {
        return textSizes.size
    }

}








