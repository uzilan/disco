package disco.model

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("label")
data class Label(
    val id: Int,
    val name: String,
    val contactinfo: String,
    val profile: String,
    val parentLabel: String?,
    @XStreamAlias("data_quality")
    val dataQuality: String,
    val images: Images?,
    val urls: Urls?,
    val sublabels: Sublabels?,
)

