package models

import javax.xml.bind.annotation.XmlAnyElement
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "ValCurs")
data class ValCurs(
    @XmlAttribute
    val date: String,
    @XmlElement
    val name: String,
    @XmlElement
    val valute: List<Valute>

)