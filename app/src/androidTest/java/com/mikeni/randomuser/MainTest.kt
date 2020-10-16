package com.mikeni.randomuser

import androidx.test.core.app.launchActivity
import com.mikeni.randomuser.data.local.UserDao
import com.mikeni.randomuser.data.local.UserDatabase
import com.mikeni.randomuser.ui.MainActivity
import com.mikeni.randomuser.ui.user.UserFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class MainTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun init() {
        hiltRule.inject()
        dao = database.usersDao()
    }

    @After
    fun cleanupDb() {
        database.close()
    }

    @Test
    fun mainActivityTest() {
        val scenario = launchActivity<MainActivity>()
    }

    @Test
    fun mainFragmentTest() {
        val scenario = launchFragmentInHiltContainer<UserFragment>()
    }

}