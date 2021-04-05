package disco.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit

@XStreamAlias("labels")
data class Labels(
    @XStreamImplicit(itemFieldName = "label")
    val labels: List<Label>?
)

