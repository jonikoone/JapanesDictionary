package com.jonikoone.dictionaryforlearning

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = findNavController(R.id.fragmentNavigationHost)
        NavScreens.navController = navController
        NavigationUI.setupWithNavController(mainBottomNavigation, navController)


    }


}

/*
* <!--<fragment
        android:id="@+id/labelListFragment"
        android:name="com.jonikoone.dictionaryforlearning.fragments.labels.LabelListFragment"
        android:label="LabelListFragment"
        tools:layout="@layout/fragment_labels_list">
        <action
            android:id="@+id/action_labelListFragment_to_labelItemFragment"
            app:destination="@id/labelItemFragment"
            app:enterAnim="@anim/my_open_enter"
            app:exitAnim="@anim/fragment_open_exit" />
    </fragment>
    <fragment
        android:id="@+id/labelItemFragment"
        android:name="com.jonikoone.dictionaryforlearning.fragments.labels.LabelItemFragment"
        android:label="LabelItemFragment"
        tools:layout="@layout/fragment_label">
        <action
            android:id="@+id/action_labelItemFragment_to_dialogColorPicker"
            app:destination="@id/dialogColorPicker"
            app:enterAnim="@anim/my_open_enter"
            app:exitAnim="@anim/fragment_open_exit" />
    </fragment>
    <fragment
        android:id="@+id/dialogColorPicker"
        android:name="com.jonikoone.dictionaryforlearning.fragments.DialogColorPicker"
        android:label="DialogColorPicker"
        app:enterAnim="@anim/nav_default_enter_anim"
        app:exitAnim="@anim/nav_default_exit_anim"
        app:popEnterAnim="@anim/nav_default_pop_enter_anim"
        app:popExitAnim="@anim/nav_default_pop_exit_anim"
        tools:layout="@layout/dialog_color_picker" />
    <fragment
        android:id="@+id/wordsListFragment"
        android:name="com.jonikoone.dictionaryforlearning.fragments.words.WordsListFragment"
        android:label="WordsListFragment"
        tools:layout="@layout/fragment_words_list">
        <action
            android:id="@+id/action_wordsListFragment_to_wordFragment"
            app:destination="@id/wordFragment" />
    </fragment>
    <fragment
        android:id="@+id/wordFragment"
        android:name="com.jonikoone.dictionaryforlearning.fragments.words.WordItemFragment"
        android:label="WordFragment"
        tools:layout="@layout/fragment_word" />

    <fragment
        android:id="@+id/dictionariesFragment"
        android:name="com.jonikoone.dictionaryforlearning.fragments.dictionary.DictionaresFragment"
        android:label="DictionariesFragment">
        <action
            android:id="@+id/action_dictionariesList_to_dictionaryItem"
            app:destination="@id/dictionaryFragment" />
    </fragment>-->
*
* */