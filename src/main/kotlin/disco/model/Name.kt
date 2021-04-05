package disco.model

import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter

@XStreamConverter(value = ToAttributedValueConverter::class, strings = ["name"])
data class Name(
    @XStreamAsAttribute
    val id: Int,
    val name: String
)
