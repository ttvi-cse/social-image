package com.hcmut.social.controller.restcontroller;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hcmut.social.BuildConfig;
import com.hcmut.social.LoginManager;
import com.hcmut.social.controller.ControllerCenter;
import com.hcmut.social.controller.DataController;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by John on 10/6/2016.
 */

public class RESTController implements DataController {

    public static final String ROOT_URL = BuildConfig.ROOT_DOMAIN + "api/";
    public static final String BASIC_AUTHENTICATION = "BASIC IyNhcG9sbG8yMDE1IyM=";
    public static final String SECURE_AUTHENTICATION = "Bearer %s";

    public static final String AVATAR_PATH = "users/%s/avatar";

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    private static final DataController[] REST_HANDLER_LIST = {
            new RESTUserController(),
            new RESTPostController()
    };
    private static final String TAG = RESTController.class.getSimpleName();

    @Override
    public void doRequest(RequestData requestData) throws IOException {

    }

    @Override
    public void registerEvent(ControllerCenter controllerCenter) {
        for (DataController controller : REST_HANDLER_LIST)
            controller.registerEvent(controllerCenter);
    }

    @Override
    public boolean isFromCache() {
        return false;
    }

    protected String createURL(String path) {
        return ROOT_URL + path;
    }

    protected HttpURLConnection createURLConnection(String surl, String method) throws IOException {

        HttpURLConnection urlConnection = getBasicHttpURLConnection(surl, method);

        urlConnection.addRequestProperty("Basic-Authorization",
                createBasicAuthorization());
        if (LoginManager.getInstance().getLoginModel() != null)
            urlConnection.addRequestProperty("Authorization", createSecureAuthorization());

        return urlConnection;
    }

    protected HttpURLConnection getBasicHttpURLConnection(String surl, String method) throws IOException {
        URL url = new URL(surl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);
        return urlConnection;
    }

    protected HttpURLConnection createURLConnection(String url, String method, String contentType) throws IOException {
        HttpURLConnection urlConnection = createURLConnection(url, method);
        urlConnection.addRequestProperty("Content-Type", contentType);

        return urlConnection;
    }

    protected HttpsURLConnection createHttpsURLConnection(String surl, String method) throws IOException {

        URL url = new URL(surl);

        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);

        urlConnection.addRequestProperty("Basic-Authorization",
                createBasicAuthorization());
        if (LoginManager.getInstance().getLoginModel() != null)
            urlConnection.addRequestProperty("Authorization", createSecureAuthorization());

        return urlConnection;
    }

    protected HttpsURLConnection createHttpsURLConnection(String url, String method, String contentType) throws IOException {
        HttpsURLConnection urlConnection = createHttpsURLConnection(url, method);
        urlConnection.addRequestProperty("Content-Type",
                contentType);
        return urlConnection;
    }

    protected void doHTTPRequest(HttpURLConnection conn, RequestData reqData, TypeToken type) {
        ResponseData resData;

        try {

            Log.i(TAG, "Load API: " + conn.getURL().toString());

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            String jsonData = reqData.toJSONString();

            if (jsonData != null) {
                // Send POST output.
                Log.i(TAG, "REQUEST_PARAMS: " + jsonData);
                DataOutputStream printout = new DataOutputStream(conn.getOutputStream());
                printout.write(jsonData.getBytes());
                printout.flush();
                printout.close();
            }

            int response = conn.getResponseCode();

            if (response / 200 == 1) {

                InputStream is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                Log.d(TAG, "RESPONSE_STRING: " + contentAsString);
                if (contentAsString.isEmpty())
                    resData = new ResponseData();
                else resData = new Gson().fromJson(contentAsString, type.getType());

                resData.setReturnCode(ResponseData.RETURN_OK);

//                if(resData.getError() == null)
//                    resData.setReturnCode(ResponseData.RETURN_OK);
//                else
//                    resData.setReturnCode(ResponseData.ERROR_DATA_INVALID);
            } else {
                resData = new ResponseData();
                resData.setReturnCode(response);
                try {
                    if(reqData.getType() == RequestData.TYPE_LOGIN && conn.getResponseCode() == 401){
                        String message = "";
                        InputStream is = conn.getErrorStream();
                        if(null != is){
                            String contentAsString = readIt(is);
                            if(null != contentAsString && !contentAsString.isEmpty()){
                                try {
                                    JSONObject jsonObject = new JSONObject(contentAsString);
                                    if(null != jsonObject && jsonObject.length() > 0
                                            && jsonObject.has("message")){
                                        message = jsonObject.getString("message");
                                    }
                                }
                                catch (JsonSyntaxException ex){
                                    ex.printStackTrace();
                                }
                            }
                        }

                        resData.setReturnCode(ResponseData.ERROR_UNAUTHORIZED);
                        if(!message.isEmpty()){
                            resData.setErrorMessage(message);
                        }
                    }
                    else {
                        resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                    }
                }
                catch (Exception ex){
                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            resData = new ResponseData();
            try {
                if(reqData.getType() == RequestData.TYPE_LOGIN && conn.getResponseCode() == 401){
                    String message = "";
                    InputStream is = conn.getErrorStream();
                    if(null != is){
                        String contentAsString = readIt(is);
                        if(null != contentAsString && !contentAsString.isEmpty()){
                            try {
                                JSONObject jsonObject = new JSONObject(contentAsString);
                                if(null != jsonObject && jsonObject.length() > 0
                                        && jsonObject.has("message")){
                                    message = jsonObject.getString("message");
                                }
                            }
                            catch (JsonSyntaxException ex){
                                ex.printStackTrace();
                            }
                        }
                    }

                    resData.setReturnCode(ResponseData.ERROR_UNAUTHORIZED);
                    if(!message.isEmpty()){
                        resData.setErrorMessage(message);
                    }
                }
                else {
                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                }
            }
            catch (Exception ex){
                resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resData = new ResponseData();
            resData.setReturnCode(ResponseData.ERROR_DATA_INVALID);
        } finally {
            conn.disconnect();
        }

        DataCenter.getInstance().fireResponseCallback(reqData, resData, false);
    }

    protected void doHTTPRequestUploadPhoto(HttpURLConnection conn, RequestData reqData, TypeToken type, String path) {
        ResponseData resData;

        try {
            File selectedFile = new File(path);
            if (!selectedFile.isFile()) {
                return;
            }

            String attachmentName = "avatar";
            String[] parts = path.split("/");
            String attachmentFileName = parts[parts.length -1];
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            Log.i(TAG, "Load API: " + conn.getURL().toString());

            conn.setUseCaches(false);
            conn.setDoOutput(true);

            String jsonData = reqData.toJSONString();
            if (jsonData != null) {
                Log.i(TAG, "REQUEST_PARAMS: " + jsonData);
            }

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream request = new DataOutputStream(conn.getOutputStream());

            /*file*/
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);

            // begin write file's byte
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            int bytesRead,bytesAvailable,bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable,maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer,0,bufferSize);

            while (bytesRead > 0){
                request.write(buffer,0,bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer,0,bufferSize);
            }
            // end write file's byte

            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

            request.flush();
            request.close();

            int response = conn.getResponseCode();

            if (response / 200 == 1) {

                InputStream is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                Log.d(TAG, "RESPONSE_STRING: " + contentAsString);
                if (contentAsString.isEmpty())
                    resData = new ResponseData();
                else resData = new Gson().fromJson(contentAsString, type.getType());

                resData.setReturnCode(ResponseData.RETURN_OK);

            } else {
                resData = new ResponseData();
                resData.setReturnCode(response);
                try {
                    String message = "";
                    InputStream is = conn.getErrorStream();
                    if(null != is){
                        String contentAsString = readIt(is);

                        if(null != contentAsString && !contentAsString.isEmpty()){
                            try {
                                /*going to update*/
                                JSONObject jsonObject = new JSONObject(contentAsString);
                                Log.e(TAG, "RESPONSE_STRING: " + contentAsString);
                                if(null != jsonObject && jsonObject.length() > 0
                                        && jsonObject.has("error")){
                                    JSONObject errorObj = jsonObject.getJSONObject("error");
                                    Object obj = errorObj.get("message");
                                    if (obj instanceof JSONObject) {
                                        message = errorObj.getJSONObject("message").getString("message");
                                    } else if (obj instanceof String) {
                                        message = errorObj.getString("message");
                                    } else {
                                        message = "Not handled message";
                                    }
                                }
                                /*end going to update*/
                            }
                            catch (JsonSyntaxException ex){
                                ex.printStackTrace();
                            }
                        }
                    }

                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                    if(!message.isEmpty()){
                        resData.setErrorMessage(message);
                    }
                }
                catch (Exception ex){
                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            resData = new ResponseData();
            try {
                if(reqData.getType() == RequestData.TYPE_LOGIN && conn.getResponseCode() == 401){
                    String message = "";
                    InputStream is = conn.getErrorStream();
                    if(null != is){
                        String contentAsString = readIt(is);
                        if(null != contentAsString && !contentAsString.isEmpty()){
                            try {
                                /*going to update*/
                                JSONObject jsonObject = new JSONObject(contentAsString);
                                Log.e(TAG, "RESPONSE_STRING: " + contentAsString);
                                if(null != jsonObject && jsonObject.length() > 0
                                        && jsonObject.has("error")){
                                    JSONObject errorObj = jsonObject.getJSONObject("error");
                                    Object obj = errorObj.get("message");
                                    if (obj instanceof JSONObject) {
                                        message = errorObj.getJSONObject("message").getString("message");
                                    } else if (obj instanceof String) {
                                        message = errorObj.getString("message");
                                    } else {
                                        message = "Not handled message";
                                    }
                                }
                                /*end going to update*/
                            }
                            catch (JsonSyntaxException ex){
                                ex.printStackTrace();
                            }
                        }
                    }

                    resData.setReturnCode(ResponseData.ERROR_UNAUTHORIZED);
                    if(!message.isEmpty()){
                        resData.setErrorMessage(message);
                    }
                }
                else {
                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                }
            }
            catch (Exception ex){
                resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resData = new ResponseData();
            resData.setReturnCode(ResponseData.ERROR_DATA_INVALID);
        } finally {
            conn.disconnect();
        }

        DataCenter.getInstance().fireResponseCallback(reqData, resData, false);
    }

    protected void doHTTPRequestUploadFile(HttpURLConnection conn, RequestData reqData,
                                           TypeToken type, String path, String title, String content, String locationId) {
        ResponseData resData;

        try {
            File selectedFile = new File(path);
            if (!selectedFile.isFile()) {
                return;
            }

            String attachmentName = "file";
            String[] parts = path.split("/");
            String attachmentFileName = parts[parts.length -1];
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            Log.i(TAG, "Load API: " + conn.getURL().toString());

            conn.setUseCaches(false);
            conn.setDoOutput(true);

            String jsonData = reqData.toJSONString();
            if (jsonData != null) {
                Log.i(TAG, "REQUEST_PARAMS: " + jsonData);
            }

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream request = new DataOutputStream(conn.getOutputStream());

            /*file*/
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);

            // begin write file's byte
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            int bytesRead,bytesAvailable,bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable,maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer,0,bufferSize);

            while (bytesRead > 0){
                request.write(buffer,0,bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer,0,bufferSize);
            }
            // end write file's byte

            request.writeBytes(crlf);

            /*title*/
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" + "title" + "\"" + crlf);
            request.writeBytes(crlf);

            request.writeBytes(title);
            request.writeBytes(crlf);

            /*content*/
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" + "content" + "\"" + crlf);
            request.writeBytes(crlf);

            request.writeBytes(content);
            request.writeBytes(crlf);

            /*location_id*/
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" + "location" + "\"" + crlf);
            request.writeBytes(crlf);

            request.writeBytes(locationId);
            request.writeBytes(crlf);

            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

            request.flush();
            request.close();

            int response = conn.getResponseCode();

            if (response / 200 == 1) {

                InputStream is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                Log.d(TAG, "RESPONSE_STRING: " + contentAsString);
                if (contentAsString.isEmpty())
                    resData = new ResponseData();
                else resData = new Gson().fromJson(contentAsString, type.getType());

                resData.setReturnCode(ResponseData.RETURN_OK);

            } else {
                resData = new ResponseData();
                resData.setReturnCode(response);
                try {
                    String message = "";
                    InputStream is = conn.getErrorStream();
                    if(null != is){
                        String contentAsString = readIt(is);

                        if(null != contentAsString && !contentAsString.isEmpty()){
                            try {
                                /*going to update*/
                                JSONObject jsonObject = new JSONObject(contentAsString);
                                Log.e(TAG, "RESPONSE_STRING: " + contentAsString);
                                if(null != jsonObject && jsonObject.length() > 0
                                        && jsonObject.has("error")){
                                    JSONObject errorObj = jsonObject.getJSONObject("error");
                                    Object obj = errorObj.get("message");
                                    if (obj instanceof JSONObject) {
                                        message = errorObj.getJSONObject("message").getString("message");
                                    } else if (obj instanceof String) {
                                        message = errorObj.getString("message");
                                    } else {
                                        message = "Not handled message";
                                    }
                                }
                                /*end going to update*/
                            }
                            catch (JsonSyntaxException ex){
                                ex.printStackTrace();
                            }
                        }
                    }

                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                    if(!message.isEmpty()){
                        resData.setErrorMessage(message);
                    }
                }
                catch (Exception ex){
                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            resData = new ResponseData();
            try {
                if(reqData.getType() == RequestData.TYPE_LOGIN && conn.getResponseCode() == 401){
                    String message = "";
                    InputStream is = conn.getErrorStream();
                    if(null != is){
                        String contentAsString = readIt(is);
                        if(null != contentAsString && !contentAsString.isEmpty()){
                            try {
                                /*going to update*/
                                JSONObject jsonObject = new JSONObject(contentAsString);
                                Log.e(TAG, "RESPONSE_STRING: " + contentAsString);
                                if(null != jsonObject && jsonObject.length() > 0
                                        && jsonObject.has("error")){
                                    JSONObject errorObj = jsonObject.getJSONObject("error");
                                    Object obj = errorObj.get("message");
                                    if (obj instanceof JSONObject) {
                                        message = errorObj.getJSONObject("message").getString("message");
                                    } else if (obj instanceof String) {
                                        message = errorObj.getString("message");
                                    } else {
                                        message = "Not handled message";
                                    }
                                }
                                /*end going to update*/
                            }
                            catch (JsonSyntaxException ex){
                                ex.printStackTrace();
                            }
                        }
                    }

                    resData.setReturnCode(ResponseData.ERROR_UNAUTHORIZED);
                    if(!message.isEmpty()){
                        resData.setErrorMessage(message);
                    }
                }
                else {
                    resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
                }
            }
            catch (Exception ex){
                resData.setReturnCode(ResponseData.ERROR_NOT_CONNECTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resData = new ResponseData();
            resData.setReturnCode(ResponseData.ERROR_DATA_INVALID);
        } finally {
            conn.disconnect();
        }

        DataCenter.getInstance().fireResponseCallback(reqData, resData, false);
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        String s = "";
        String line = "";

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));

        // Read response until the end
        while ((line = rd.readLine()) != null) {
            s += line;
        }

        // Return full string
        return s;
    }

    private String createBasicAuthorization() {
        return BASIC_AUTHENTICATION;
    }

    private String createSecureAuthorization() {
        return String.format(SECURE_AUTHENTICATION, LoginManager.getInstance().getToken());
    }
}
