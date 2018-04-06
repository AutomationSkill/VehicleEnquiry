package org.uk.dvla.serviceLayer;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uk.dvla.Utils.VehicleFileType;
import org.uk.dvla.module.FileDetails;
import org.uk.dvla.module.VehicleDetails;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileServiceModuleImpl implements FileServiceModule {

    private static Logger LOG = LoggerFactory.getLogger(FileServiceModuleImpl.class);
    public static final int FIRST_SHEET_INDEX = 0;
    private static final String CSV_DELIMITER = ",";
    private static final String HEADER = "RegistrationNumber";
    private String directoryName;

    public FileServiceModuleImpl(String directoryName){this.directoryName = directoryName;}
    public List<FileDetails> getFileDetails() {
        return getFileDetails();
    }

    public List<FileDetails> getFileDetails(VehicleFileType vehicleFileType) {
        LOG.debug("Given Directory Name : {}", directoryName);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(directoryName);
        File dir = new File(url.getPath());
        System.out.println("directoryName in the getFileDetails " +directoryName);
        File[] files;
        if (null != vehicleFileType) {
            String givenExtension = vehicleFileType.getExtension();
            FileFilter fileFilter = new WildcardFileFilter(givenExtension);
            files = dir.listFiles(fileFilter);
        } else {
            files = dir.listFiles();
        }
        List<FileDetails> fileDetails = new ArrayList();
        for (File file : files) {
            if (file.isFile()) {
                prepareFileDetails(fileDetails, file);
            }
        }
        return fileDetails;
    }

    @Override
    public List<VehicleDetails> getVehicleDetails(String fileName, VehicleFileType vehicleFileType) throws IOException {
        if (vehicleFileType.isCsv()) {
            return getVehicleDetailsFromCsv(fileName);
        }
        return getVehicleDetailsFromExcel(fileName);
    }

    private List<VehicleDetails> getVehicleDetailsFromCsv(String fileName) throws IOException {
        String line = "";
        List<VehicleDetails> vehicleDetailsList = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(HEADER)) {
                    VehicleDetails vehicleDetails = new VehicleDetails();
                    String[] column = line.split(CSV_DELIMITER);
                    System.out.println("coloumn 0 "+(column[0]));
                    vehicleDetails.setRegistrationNumber(column[0]);
                    vehicleDetails.setVehicleMake(column[1]);
                    System.out.println("coloumn 1 "+(column[1]));
                    vehicleDetails.setColour(column[2]);
                    System.out.println("coloumn 2 "+(column[2]));
                    vehicleDetails.setFileName(fileName);

                    vehicleDetailsList.add(vehicleDetails);
                }
            }
        }
        return vehicleDetailsList;
    }

    private List<VehicleDetails> getVehicleDetailsFromExcel(String fileName) throws IOException {
        List<VehicleDetails> listBooks = new ArrayList<>();
        FileInputStream inputStream = null;
        inputStream = new FileInputStream(new File(fileName));
        Workbook workbook = null;
        workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(FIRST_SHEET_INDEX);
        Iterator<Row> rowIterator = firstSheet.iterator();
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            String firstColumnValue = currentRow.getCell(0).getStringCellValue();
            if (!firstColumnValue.startsWith(HEADER)) {
                VehicleDetails VehicleDetails = new VehicleDetails();
                VehicleDetails.setRegistrationNumber(currentRow.getCell(0).getStringCellValue());
                VehicleDetails.setVehicleMake(currentRow.getCell(1).getStringCellValue());
                VehicleDetails.setColour(currentRow.getCell(2).getStringCellValue());
                VehicleDetails.setFileName(fileName);
                listBooks.add(VehicleDetails);
            }
        }
        workbook.close();
        inputStream.close();
        return listBooks;
    }

    private void prepareFileDetails(List<FileDetails> fileDetailsList, File file) {
        FileDetails fileDetails = new FileDetails();
        String fileName = file.getName();
        fileDetails.setFileAbsolutePath(file.getAbsolutePath());
        fileDetails.setFileSize(file.length());
        String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
        fileDetails.setFileName(fileNameWithOutExt);
        String extension = FilenameUtils.getExtension(fileName);
        fileDetails.setFileExtension(extension);
        String mimeType = new MimetypesFileTypeMap().getContentType(fileName);
        fileDetails.setFileMimeType(mimeType);
        fileDetailsList.add(fileDetails);
    }

}
