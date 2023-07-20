package models

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
data class Valute(
    @XmlAttribute
    val id:String,
    @XmlAttribute
    val numCode: String,
    @XmlAttribute
    val charCode: String,
    @XmlAttribute
    val nominal: String,
    @XmlAttribute
    val name: String,
    @XmlAttribute
    val value: String)