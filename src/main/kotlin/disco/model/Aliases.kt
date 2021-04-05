package disco.model

import com.thoughtworks.xstream.annotations.XStreamImplicit

data class Aliases(
    @XStreamImplicit(itemFieldName = "name")
    val names: List<Name>?
)
