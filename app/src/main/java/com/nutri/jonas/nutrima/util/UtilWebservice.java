package com.nutri.jonas.nutrima.util;

public class UtilWebservice
{

    public final static String SUCCESS_TAG = "success";
    public final static String ERROR_TAG = "error";
    public final static String DADOS_TAG = "dados";

    public final static int SUPER_GAMES_SCHOOLS_URL = 1;

    public static String getWebserviceURL( String webserviceName, int url )
    {

        String hostName = "", projectName = "", webServicerepository = "";

        if( url == 1 )
        {

            hostName = "http://supergamesschools.com.br/";
            projectName = "nms/";
            webServicerepository = "app/services/";

        }

        return hostName + projectName + webServicerepository + webserviceName + ".class.php";

    }

}