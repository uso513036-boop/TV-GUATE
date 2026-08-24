package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.GuatemalaTvRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("GuateTV", appName)
    }

    @Test
    fun `verify real guatemala channels loaded`() {
        val channels = GuatemalaTvRepository.channels
        assertTrue(channels.isNotEmpty())
        assertTrue(channels.any { it.id == "canal3" })
        assertTrue(channels.any { it.id == "canal7" })
        assertTrue(channels.any { it.id == "tn23" })
        assertTrue(channels.any { it.id == "guatevision" })
    }

    @Test
    fun `verify real-time EPG program guide generation`() {
        val guides = GuatemalaTvRepository.getAllChannelsWithGuide()
        assertTrue(guides.isNotEmpty())
        val canal7Guide = guides.first { it.channel.id == "canal7" }
        assertTrue(canal7Guide.programs.isNotEmpty())
    }
}
