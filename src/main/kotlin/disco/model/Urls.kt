package disco.model

import com.thoughtworks.xstream.annotations.XStreamImplicit

data class Urls(
    @XStreamImplicit(itemFieldName = "url")
    val urls: List<String>
)

