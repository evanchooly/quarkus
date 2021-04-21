package io.quarkus.it.panache

import io.quarkus.it.panache.kotlin.Person.Companion
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.doThrow
import org.mockito.kotlin.mock

@QuarkusTest
class KotlinPanacheMockingTest {
    @Test
    @Order(1)
    fun testPanacheMocking() {
        val mock = mock<Companion>()
        // does not throw (defaults to doNothing)
        mock.voidMethod()
        // make it throw our exception
        val companion = doThrow(RuntimeException("custom"))
            .`when`(mock<Companion>())
        try {
            companion.voidMethod()
            fail("Should have thrown an exception")
        } catch (x: RuntimeException) {
            Assertions.assertEquals("custom", x.message)
        }
    }
}
