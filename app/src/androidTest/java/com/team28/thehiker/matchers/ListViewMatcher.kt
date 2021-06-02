package com.team28.thehiker.matchers

import android.view.View
import android.widget.ListView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ListViewMatcher(size : Int): TypeSafeMatcher<View>() {

    private var passedSize : Int = size


    override fun describeTo(description: Description?) {
    }

    override fun matchesSafely(item: View?): Boolean {

        val listView: ListView = item as ListView
        return listView.count <= passedSize
    }

}