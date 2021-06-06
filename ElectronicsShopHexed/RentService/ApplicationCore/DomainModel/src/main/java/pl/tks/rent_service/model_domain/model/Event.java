package pl.tks.rent_service.model_domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.resource.Device;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class Event extends Entity {
    private Device device;
    private Client client;
    private Date borrowDate;
    private Date returnDate;

    public Event(Device device, Client client, Date borrowDate) {
        super();
        this.device = device;
        this.client = client;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    public Event(Device device, Client client) {
        super();
        this.device = device;
        this.client = client;
        this.borrowDate = new Date();
        this.returnDate = null;
    }


}
