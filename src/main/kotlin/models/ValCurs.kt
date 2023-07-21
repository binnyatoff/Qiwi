package models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "ValCurs", strict = false)
data class ValCurs(
    @field:ElementList(entry = "Valute", inline = true, required = false)
    var valute: List<Valute>? = null,
    @field:Attribute(name = "Date")
    var date: String? = null,
    @field:Attribute(name = "name")
    var name: String? = null
)

@Root(name = "Valute", strict = false)
data class Valute(
    @field:Attribute(name = "ID", required = false)
    var id: String? = null,
    @field:Element(name = "NumCode", required = false)
    var numCode: String? = null,
    @field:Element(name = "CharCode", required = false)
    var charCode: String? = null,
    @field:Element(name = "Nominal", required = false)
    var nominal: String? = null,
    @field:Element(name = "Name", required = false)
    var name: String? = null,
    @field:Element(name = "Value", required = false)
    var value: String? = null
)