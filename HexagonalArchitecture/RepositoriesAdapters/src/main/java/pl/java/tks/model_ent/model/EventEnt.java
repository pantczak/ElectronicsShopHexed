package pl.java.tks.model_ent.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.java.tks.model_ent.model.resource.DeviceEnt;
import pl.java.tks.model_ent.model.user.ClientEnt;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(force = true)
public class EventEnt extends EntityEnt {
    private DeviceEnt device;
    private ClientEnt client;
    private Date borrowDate;
    private Date returnDate;

    public EventEnt(DeviceEnt device, ClientEnt client, Date borrowDate) {
        super();
        this.device = device;
        this.client = client;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    public EventEnt(DeviceEnt device, ClientEnt client) {
        super();
        this.device = device;
        this.client = client;
        this.borrowDate = new Date();
        this.returnDate = null;
    }

}
