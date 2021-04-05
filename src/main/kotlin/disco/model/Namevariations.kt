package disco.model

import com.thoughtworks.xstream.annotations.XStreamImplicit

data class Namevariations(
    @XStreamImplicit(itemFieldName = "name")
    val names: List<String>?
)
