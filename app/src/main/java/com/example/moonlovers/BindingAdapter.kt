
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.moonlovers.R

@BindingAdapter("age")
fun buildImage(imageView: ImageView, age: Double?) {
    if (age == null) {
        imageView.setImageResource(R.drawable.img_moon15)
    } else {
        val context = imageView.context
        val imgNumber: String = Math.round(age).toInt().toString()
        val resource = context.resources.getIdentifier(
            "img_moon$imgNumber",
            "drawable",
            context.packageName
        )
        imageView.setImageResource(resource)
    }
}

@BindingAdapter("age")
fun buildText(textView: TextView, age: Double?) {
    textView.visibility = View.VISIBLE
    textView.text = age.toString()
}