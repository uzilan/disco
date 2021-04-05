package disco.model

import com.thoughtworks.xstream.annotations.XStreamAsAttribute

data class Image(
    @XStreamAsAttribute
    val height: Int,
    @XStreamAsAttribute
    val width: Int,
    @XStreamAsAttribute
    val type: String?,
    @XStreamAsAttribute
    val uri: String?,
    @XStreamAsAttribute
    val uri150: String?,
)

