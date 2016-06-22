package com.nutri.jonas.nutrima.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandler
{

    JSONObject jsonObject;

    public JSONHandler(JSONObject jsonObject)
    {

        this.jsonObject = jsonObject;

        //Log.e( "JSON", jsonObject.toString() );

    }

    public String getString( String tag )
    {

        try
        {

            return jsonObject.getString(tag);

        }catch( JSONException e )
        {

            e.printStackTrace();

        }

        return "";

    }

    public int getInt( String tag )
    {

        try
        {

            return jsonObject.getInt(tag);

        }catch( JSONException e )
        {

            e.printStackTrace();

        }

        return -1;

    }

    public JSONObject getJSONObject( String name )
    {

        try
        {

            return jsonObject.getJSONObject(name);

        }catch( JSONException e )
        {

            e.printStackTrace();

        }

        return null;

    }

    public JSONArray getJSONArray( String name )
    {

        try
        {

            return jsonObject.getJSONArray( name );

        }catch( JSONException e )
        {

            e.printStackTrace();

        }

        return null;

    }

    public static String getStringFromJSONObject( JSONObject tempJsonObject, String name )
    {

        try
        {

            return tempJsonObject.getString( name );

        }catch( JSONException e )
        {

            e.printStackTrace();

        }

        return "";

    }

    public static int getIntFromJSONObject( JSONObject tempJsonObject, String name )
    {

        try
        {

            return tempJsonObject.getInt( name );

        }catch( JSONException e )
        {

            e.printStackTrace();

        }

        return -1;

    }

}