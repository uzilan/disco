package disco.model

import com.thoughtworks.xstream.annotations.XStreamImplicit

data class Sublabels(
    @XStreamImplicit(itemFieldName = "label")
    val labels: List<Sublabel>
)
