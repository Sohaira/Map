package com.example.abdullatif.googlemap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser
{
    private HashMap<String,String> getSingleNearbyplace (JSONObject googlePlaceJSON)
    {
        HashMap<String,String> googlePlaceMap = new  HashMap<>();
        String NameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try
        {
            if (!googlePlaceJSON.isNull("name"))
            {
                NameOfPlace= googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("vicinity"))
            {
                vicinity= googlePlaceJSON.getString("vicinity");
            }
            latitude= googlePlaceJSON.getJSONObject("geometry").getJSONObject( "locaton").getString("lat");
            longitude= googlePlaceJSON.getJSONObject("geometry").getJSONObject( "locaton").getString("lng");
            reference = googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name",NameOfPlace);
            googlePlaceMap.put("vicinty",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude );
            googlePlaceMap.put("reference",reference);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String , String>>getAllNearbyPlaces(JSONArray jsonArray)
    {
        int counter = jsonArray.length();
        List<HashMap<String , String>> NearbyPlacesList = new ArrayList<>();
        HashMap<String, String> NearbyplacesMap = null;
        for (int i =0 ; i <counter;i++)
        {
            try
            {
                NearbyplacesMap= getSingleNearbyplace((JSONObject)jsonArray.get(i));
                NearbyPlacesList.add(NearbyplacesMap);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return  NearbyPlacesList;
    }
    public List<HashMap<String,String>> parse(String jSONdata)
    {
        JSONArray jsonArray= null;
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(jSONdata);
            jsonArray  = jsonObject.getJSONArray("results");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return  getAllNearbyPlaces(jsonArray);
    }
}
