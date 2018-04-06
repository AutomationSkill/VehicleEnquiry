package org.uk.dvla.tests;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uk.dvla.Utils.VehicleFileType;
import org.uk.dvla.module.FileDetails;
import org.uk.dvla.module.VehicleDetails;
import org.uk.dvla.serviceLayer.FileServiceModuleImpl;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class VehicleModuleTests {
    private static final Logger LOG = LoggerFactory.getLogger(VehicleModuleTests.class);
    private FileServiceModuleImpl vehicleModuleService;
    String directoryName;

    @Before
    public void setUp() {
        directoryName = "inputFiles";
        vehicleModuleService = new FileServiceModuleImpl(directoryName);
    }

    /*List only files with Csv extension*/
    @Test
    public void listOnlyFilesWithExtension() {
        List<FileDetails> fileDetails = vehicleModuleService.getFileDetails(VehicleFileType.CSV);
        for (FileDetails fileDetail : fileDetails) {
            LOG.info("fileDetail: {}", fileDetail);
        }
        assertNotNull(fileDetails);
    }

    @Test
    public void listOnlyFilesWithExtensionUsingType() {
        String fileType = "EXCEL";
        LOG.info("fileType {}", VehicleFileType.valueOf(fileType).getExtension());
        List<FileDetails> fileDetails = vehicleModuleService.getFileDetails(VehicleFileType.valueOf(fileType));
        for (FileDetails fileDetail : fileDetails) {
            LOG.info("fileDetail: {}", fileDetail);
        }
        assertNotNull(fileDetails);
    }


    @Test
    public void listFileDetails() {
        List<FileDetails> fileDetails = vehicleModuleService.getFileDetails();
        for (FileDetails fileDetail : fileDetails) {
            LOG.info("fileDetail: {}", fileDetail);
        }
        assertNotNull(fileDetails);
    }


    @Test
    public void listOfAllVehicleDetails() throws Exception {
        List<FileDetails> fileDetails = vehicleModuleService.getFileDetails(VehicleFileType.CSV);
        for (FileDetails fileDetail : fileDetails) {
            List<VehicleDetails> vehicleDetails = vehicleModuleService.getVehicleDetails(fileDetail.getFileAbsolutePath(), VehicleFileType.CSV);
            for (VehicleDetails inputFiles : vehicleDetails) {
                LOG.info("inputFiles: {}", inputFiles);
            }
            assertNotNull(fileDetails);
        }
    }

    /*Get all the vehicle content using Excel*/
    @Test
    public void listOfAllVehicleDetailsUsingExcel() throws Exception {
        List<FileDetails> fileDetails = vehicleModuleService.getFileDetails(VehicleFileType.EXCEL);
        for (FileDetails fileDetail : fileDetails) {
            List<VehicleDetails> vehicleDetails = vehicleModuleService.getVehicleDetails(fileDetail.getFileAbsolutePath(), VehicleFileType.EXCEL);
            for (VehicleDetails inputDetails : vehicleDetails) {
                LOG.info("vehicle input Details from Excel: {}", inputDetails);
            }
            assertNotNull(fileDetails);
        }
    }
}
