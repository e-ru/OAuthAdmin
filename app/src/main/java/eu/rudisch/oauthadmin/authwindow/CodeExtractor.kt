package eu.rudisch.oauthadmin.authwindow

import timber.log.Timber

class CodeExtractor {
    fun extractDodeFromUrl(url: String): String {
        Timber.i("extractDodeFromUrl: $url")
        val split = url.split("code=")
        return if (split.size == 2) split[1] else ""
    }
}
