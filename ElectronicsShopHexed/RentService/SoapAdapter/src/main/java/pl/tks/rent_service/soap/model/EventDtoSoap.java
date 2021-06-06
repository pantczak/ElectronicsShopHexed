package pl.tks.rent_service.soap.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventDtoSoap", propOrder = {
        "uuid",
        "device",
        "client",
        "borrowDate",
        "returnDate"
})
public class EventDtoSoap {
    private String uuid;
    private DeviceDtoSoap device;
    private UserDtoSoap client;
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar borrowDate;
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar returnDate;
}
