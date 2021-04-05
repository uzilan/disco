package disco.model

import com.thoughtworks.xstream.annotations.XStreamImplicit

data class Members(
    @XStreamImplicit(itemFieldName = "id")
    val unusedId: List<Int>?,
    @XStreamImplicit(itemFieldName = "name")
    val names: List<Name>?,
)
