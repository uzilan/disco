package disco.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit

@XStreamAlias("artists")
data class Artists(
    @XStreamImplicit(itemFieldName = "artist")
    val labels: List<Artist>?
)

