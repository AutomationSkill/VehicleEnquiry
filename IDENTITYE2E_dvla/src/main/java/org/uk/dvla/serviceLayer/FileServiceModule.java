package org.uk.dvla.serviceLayer;

import org.uk.dvla.Utils.VehicleFileType;
import org.uk.dvla.module.FileDetails;
import org.uk.dvla.module.VehicleDetails;

import java.io.IOException;
import java.util.List;

public interface FileServiceModule {
    List<FileDetails> getFileDetails();

    List<FileDetails> getFileDetails(VehicleFileType vehicleFileType);

    List<VehicleDetails> getVehicleDetails(String fileName, VehicleFileType vehicleFileType) throws IOException;

}
