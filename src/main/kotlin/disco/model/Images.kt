package disco.model

import com.thoughtworks.xstream.annotations.XStreamImplicit

data class Images(
    @XStreamImplicit(itemFieldName = "image")
    val images: List<Image>
)
