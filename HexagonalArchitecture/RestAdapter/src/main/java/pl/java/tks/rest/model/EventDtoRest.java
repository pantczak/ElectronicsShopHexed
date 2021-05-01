package pl.java.tks.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;


@Data
public class EventDtoRest {
    private String uuid;
    private DeviceDtoRest device;
    private UserDtoRest client;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date borrowDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date returnDate;
}
