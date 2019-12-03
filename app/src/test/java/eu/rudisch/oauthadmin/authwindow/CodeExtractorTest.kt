package eu.rudisch.oauthadmin.authwindow

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CodeExtractorTest {

    @Test
    fun `should extract code from valid url`() {
        val codeExtractor = CodeExtractor()

        val fooCode = "fooCode"
        val url = "http://localhost:8889/redirect.html?code=${fooCode}"
        val code = codeExtractor.extractCodeFromUrl(url)

        Assertions.assertEquals(fooCode, code)
    }

    @Test
    fun `should return empty string on invalid url`() {
        val codeExtractor = CodeExtractor()

        val fooCode = "fooCode"
        val url = "http://localhost:8889/redirect.${fooCode}"
        val code = codeExtractor.extractCodeFromUrl(url)

        Assertions.assertEquals("", code)
    }

}