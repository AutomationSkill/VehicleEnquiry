package org.uk.dvla.Utils;

public enum VehicleFileType {

    CSV("*.csv"), EXCEL ("*.xlsx");
    private String extension;

    VehicleFileType(String extension){
        this.extension = extension;}

        public String getExtension(){
            return extension;

        }
        public boolean isCsv(){ return this ==CSV;}
    }

