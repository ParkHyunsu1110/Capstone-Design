package com.example.lovebaby;

import com.example.lovebaby.Model.VaccineMapModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public static ArrayList<VaccineMapModel> jsonParser(String json){
        ArrayList<VaccineMapModel> map_item = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(json);
            String predictions = jsonObject.getString("TbChildnatnPrvntncltnmdnstM");
            JSONArray jsonArray = new JSONArray(predictions);
            JSONObject subJsonObject = jsonArray.getJSONObject(1);
            String row = subJsonObject.getString("row");
            JSONArray jsonArray2 = new JSONArray(row);

            for (int i=0; i < jsonArray2.length(); i++) {
                JSONObject subJsonObject2 = jsonArray2.getJSONObject(i);
                VaccineMapModel vaccineModel = new VaccineMapModel(
                    subJsonObject2.getString("SIGUN_NM"),
                    subJsonObject2.getString("SIGUN_CD"),
                    subJsonObject2.getString("FACLT_NM"),
                    subJsonObject2.getString("TELNO"),
                    subJsonObject2.getString("APPONT_DE"),
                    subJsonObject2.getString("DATA_STD_DE"),
                    subJsonObject2.getString("REFINE_LOTNO_ADDR"),
                    subJsonObject2.getString("REFINE_ROADNM_ADDR"),
                    subJsonObject2.getString("REFINE_ZIP_CD"),
                    subJsonObject2.getString("REFINE_WGS84_LOGT"),
                    subJsonObject2.getString("REFINE_WGS84_LAT")
                );
                map_item.add(vaccineModel);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return map_item;
    }
}
