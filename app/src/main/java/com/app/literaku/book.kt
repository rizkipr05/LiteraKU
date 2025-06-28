// Book.kt - Model untuk data buku
package com.app.literaku.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val description: String? = null,
    val publishedDate: String? = null,
    val category: String? = null,
    val rating: Float = 0f,
    val previewLink: String? = null,
    val webReaderLink: String? = null
)

// ApiResponse.kt - Model untuk response API
data class BookSearchResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BookItem>?
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo,
    val accessInfo: AccessInfo? = null
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>? = null,
    val description: String? = null,
    val publishedDate: String? = null,
    val categories: List<String>? = null,
    val averageRating: Float? = null,
    val imageLinks: ImageLinks? = null,
    val previewLink: String? = null,
    val canonicalVolumeLink: String? = null
)

data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null,
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null
)

data class AccessInfo(
    val webReaderLink: String? = null,
    val accessViewStatus: String? = null
)