package pt.tornelas.segmentedprogressbar.standard

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import pt.tornelas.segmentedprogressbar.SegmentedProgressBar
import pt.tornelas.segmentedprogressbar.SegmentedProgressBarListener
import pt.tornelas.segmentedprogressbar.app.R
import pt.tornelas.segmentedprogressbar.dataSource
import pt.tornelas.segmentedprogressbar.pager.PagerAdapter

class StandardExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard_example)

        val items = dataSource()

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val spb = findViewById<SegmentedProgressBar>(R.id.spb)
        val btnNext = findViewById<ImageButton>(R.id.btnNext)
        val btnPrevious = findViewById<ImageButton>(R.id.btnPrevious)

        viewPager.adapter = PagerAdapter(supportFragmentManager, items)
        spb.viewPager = viewPager

        spb.segmentCount = items.size
        spb.listener = object : SegmentedProgressBarListener {
            override fun onPage(oldPageIndex: Int, newPageIndex: Int) {
                // New page started animating
            }

            override fun onFinished() {
                finish()
            }
        }

        spb.start()

        btnNext.setOnClickListener { spb.next() }
        btnPrevious.setOnClickListener { spb.previous() }
    }
}
