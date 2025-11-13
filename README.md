# SegmentedProgressBar

An Instagram-like stories segmented progress bar for Android

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mustafiz012/segmentedprogressbar)](https://search.maven.org/artifact/io.github.mustafiz012/segmentedprogressbar)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-SegmentedProgressBar-green.svg?style=flat )]( https://android-arsenal.com/details/1/8229 )

![screen-20210306-002812_2 (3)](https://user-images.githubusercontent.com/17816841/110188846-0a784800-7e15-11eb-85c2-897e572173c2.gif)

## Setup

Add SegmentedProgressBar to your app's `build.gradle` dependencies:

```gradle
dependencies {
    implementation 'io.github.mustafiz012:segmentedprogressbar:1.0.0'
}
```

**Note:** Maven Central is automatically included in Gradle projects, so no additional repository configuration is needed.

Find all versions available on [Maven Central](https://search.maven.org/artifact/io.github.mustafiz012/segmentedprogressbar)

## Usage

### Add it to your layout xml file

```
<pt.tornelas.segmentedprogressbar.SegmentedProgressBar
        android:id="@+id/spb"
        android:layout_width="match_parent"
        android:layout_height="5dp"
        android:layout_margin="8dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:segmentBackgroundColor="@color/colorAccent"
        app:segmentSelectedBackgroundColor="@color/colorPrimary"
        app:segmentStrokeColor="@android:color/black"
        app:segmentSelectedStrokeColor="@android:color/black"
        app:segmentStrokeWidth="1dp"
        app:segmentCornerRadius="2dp"
        app:segmentMargins="@dimen/default_segment_margin"
        app:totalSegments="10"
        app:timePerSegment="2000"/>
```

Alternatively, you can set all properties in your Fragment/Activity:

```
val spb = findViewById<SegmentedProgressBar>(R.id.spb)
spb.segmentCount = 10
...
```

### Initialize it and start it

```
val spb = findViewById<SegmentedProgressBar>(R.id.spb)
spb.start()
```

## Available methods

```
// Starts/resumes progress animation
spb.start()

// Pauses progress animation
spb.pause()

// Restarts progress animation
spb.reset()

// Completes animation of current segment, starts animating following segment
spb.next()

// Resets animation of current segment, starts animating previous segment
spb.previous()

// Resets animation of current segment
spb.restartSegment()

// Skips X segments (-X option available)
spb.skip(X)

// Restarts animation for segment at position X
spb.setPosition(X)
```

## Set a ViewPager

You can sync a [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2) with a SegmentedProgressBar by doing

```
spb.viewPager = viewPager
```

This will
- Update the SegmentedProgressBar segments automatically when a swipe action is done on a ViewPager
- Pause/Resume progress animation when user touches down/up on the view pager (Like you do to pause an Instagram story)

## Set a Listener

```
spb.listener = object : SegmentedProgressBarListener {
    override fun onPage(oldPageIndex: Int, newPageIndex: Int) {
        // New page started animating
    }

    override fun onFinished() {
        // All segments animated and finished animation
    }
}
```

## Features

- Instagram-like segmented progress bar animation
- Sync with ViewPager2 for automatic page transitions
- Pause/resume on touch interaction
- Customizable segment appearance (colors, stroke, corner radius, margins)
- Programmatic control (start, pause, reset, next, previous, skip, setPosition)
- Listener callbacks for page changes and completion events
- Configurable animation duration per segment

## Additional Configuration

You can also configure the animation duration programmatically:

```kotlin
val spb = findViewById<SegmentedProgressBar>(R.id.spb)
spb.timePerSegmentMs = 3000L // Set animation duration to 3 seconds per segment
```

**Note:** The `timePerSegmentMs` property must be greater than 0.

Feel free to checkout the [sample](https://github.com/mustafiz012/SegmentedProgressBar/tree/master/app) on this repository

## License

```
MIT License

Copyright (c) 2020 Tiago Ornelas
Copyright (c) 2025 Md. Mustafizur Rahman (Fork & Updates)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
