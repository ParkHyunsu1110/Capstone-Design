package com.example.lovebaby.Model;

public class VaccineMapModel {
    public String SIGUN_NM;
    public String SIGUN_CD;
    public String FACLT_NM;
    public String TELNO;
    public String APPONT_DE;
    public String DATA_STD_DE;
    public String REFINE_LOTNO_ADDR;
    public String REFINE_ROADNM_ADDR;
    public String REFINE_ZIP_CD;
    public String REFINE_WGS84_LOGT;
    public String REFINE_WGS84_LAT;

    public VaccineMapModel(String SIGUN_NM, String SIGUN_CD, String FACLT_NM, String TELNO, String APPONT_DE, String DATA_STD_DE,
                           String REFINE_LOTNO_ADDR, String REFINE_ROADNM_ADDR, String REFINE_ZIP_CD, String REFINE_WGS84_LOGT, String REFINE_WGS84_LAT) {
        this.SIGUN_NM = SIGUN_NM;
        this.SIGUN_CD = SIGUN_CD;
        this.FACLT_NM = FACLT_NM;
        this.TELNO = TELNO;
        this.APPONT_DE = APPONT_DE;
        this.DATA_STD_DE = DATA_STD_DE;
        this.REFINE_LOTNO_ADDR = REFINE_LOTNO_ADDR;
        this.REFINE_ROADNM_ADDR = REFINE_ROADNM_ADDR;
        this.REFINE_ZIP_CD = REFINE_ZIP_CD;
        this.REFINE_WGS84_LOGT = REFINE_WGS84_LOGT;
        this.REFINE_WGS84_LAT = REFINE_WGS84_LAT;
    }
}
