package disco.model

import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter

@XStreamConverter(value = ToAttributedValueConverter::class, strings = ["label"])
data class Sublabel(
    @XStreamAsAttribute
    val id: Int,
    val label: String
)

