package com.touge.demo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.touge.library.MaterialCheckedTextView
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.rv)
        val adapter = TagAdapter(prepareData())
        rv.adapter = adapter
    }

    private fun prepareData(): ArrayList<Tag> {
        val names = resources.getStringArray(R.array.tag_names)
        val colors = resources.getIntArray(R.array.tag_colors)
        assert(names.size == colors.size)
        val tags = arrayListOf<Tag>()
        for (k in 0..4) {
            for (i in 0 until names.size) {
                val color = colors[i]
                val name = names[i]
                tags.add(Tag(name, color))
            }
        }
        return tags
    }
}

class TagAdapter(private val tags: ArrayList<Tag>) : RecyclerView.Adapter<TagHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder =
        TagHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false))

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        val efv = holder.itemView.findViewById<MaterialCheckedTextView>(R.id.efv)
        val tag = tags[position]
        efv.text = tag.name
        efv.color = tag.color
        efv.isChecked = tag.checked
        efv.setOnClickListener {
            tag.checked = !tag.checked
            efv.animateCheckedAndInvoke(tag.checked) {}
        }
    }
}

class TagHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

data class Tag(
    val name: String,
    val color: Int,
    val fontColor: Int? = Color.TRANSPARENT,
    var checked: Boolean = false
) {
    private val count = AtomicInteger(0)
    val id: Int by lazy { count.incrementAndGet() }
}