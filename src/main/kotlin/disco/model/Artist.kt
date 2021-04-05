package disco.model

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("artist")
data class Artist(
    val id: Int,
    val name: String,
    val realname: String?,
    val profile: String,
    @XStreamAlias("data_quality")
    val dataQuality: String,
    val images: Images?,
    val urls: Urls?,
    val aliases: Aliases?,
    val namevariations: Namevariations?,
    val members: Members?,
    val groups: Groups?,
)

