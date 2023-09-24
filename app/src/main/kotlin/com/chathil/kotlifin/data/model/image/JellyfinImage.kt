package com.chathil.kotlifin.data.model.image

import org.jellyfin.sdk.model.api.ImageType

data class JellyfinImage(
    val baseUrl: String,
    val itemId: String,
    val imageTag: String,
    val imageType: ImageType = ImageType.PRIMARY,
    val imageQuality: Int = 96,
) {
    fun constructUrl(height: Int, width: Int): String {
//        https://demo.jellyfin.org/stable/Items/c541e754-5e58-b28c-d4d0-da7aac89d7e8/Images/Primary?fillHeight=318&fillWidth=223&quality=96&tag=39667053ea27c5928ed1cbaee01c1487
        return "$baseUrl/" +
                "Items/$itemId/" +
                "Images/${imageType.serialName}?" +
                "fillHeight=$height&" +
                "fillWidth=$width&" +
                "quality=$imageQuality&" +
                "tag=$imageTag"
    }

    companion object {
        val Empty = JellyfinImage(
            "", "", "",
        )
    }
}