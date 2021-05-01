package pl.java.tks.soap.api;




import pl.java.tks.soap.aggregates.adapters.DeviceSoapAdapter;
import pl.java.tks.soap.model.DeviceDtoSoap;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import java.util.List;
import java.util.UUID;

@WebService(serviceName = "DeviceAPI")
public class SoapDeviceController {


    private DeviceSoapAdapter adapter;

    @Inject
    public SoapDeviceController(DeviceSoapAdapter adapter) {
        this.adapter = adapter;
    }

    public SoapDeviceController() {
    }

    @WebMethod
    public DeviceDtoSoap getDevice(String uuid) {
        return adapter.getDevice(UUID.fromString(uuid));
    }

    @WebMethod
    public List<DeviceDtoSoap> getAllDevices() {
        return adapter.getAllDevices();
    }

    @WebMethod
    public List<DeviceDtoSoap> getAllLaptops() {
        return adapter.getAllLaptops();
    }

    @WebMethod
    public List<DeviceDtoSoap> getAllSmartphones() {
        return adapter.getAllSmartphones();
    }

}
