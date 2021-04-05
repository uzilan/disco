package disco.model

import com.thoughtworks.xstream.annotations.XStreamImplicit

data class Groups(
    @XStreamImplicit(itemFieldName = "name")
    val names: List<Name>,
)
