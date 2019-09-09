package com.example.beido.books;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Beido on 7/13/2018.
 */

public class QueryUtils {
    private static final String LOG_TAG = Activity.class.getName() ;
    private static String SAMPLE_JSON_RESPONSE = null;
    private static URL url ;

    public QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private static String makeHttprequest(URL url) throws IOException
    {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            int responsecode = urlConnection.getResponseCode();
            if (responsecode != 200)
                return null;
            inputStream = urlConnection.getInputStream();
            SAMPLE_JSON_RESPONSE = readFromStream(inputStream);

        }
        catch (IOException e)
        {
            Log.e (LOG_TAG," :   ", e);
        }

        return SAMPLE_JSON_RESPONSE;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static ArrayList<Books> extract_books () throws JSONException
    {
        ArrayList<Books> books = new ArrayList<>();
        try
        {
            JSONObject rawjson = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray items_array = rawjson.optJSONArray("items");
            String [] authors = new String[1];
            String currency = null , country = null, saleability =null , buy_link = null , published_date = null;
            double price = 0.00;
            for (int i = 0 ; i< items_array.length() ; i++)
            {
                JSONObject currnet_book= items_array.optJSONObject(i);

                //accessing volume info

                JSONObject volume_object =currnet_book.optJSONObject("volumeInfo");
                String title = volume_object.optString("title");
                JSONArray authors_array = volume_object.optJSONArray("authors");

                if (authors_array!=null) {
                     authors = new String[authors_array.length()];
                    for (int j = 0; j < authors_array.length(); j++) {
                       String author = authors_array.optString(j);
                        authors[j] = author;
                    }
                }
                else authors[0] = String.valueOf(R.string.not_available);

                String publisher =volume_object.optString("publisher");

                if (publisher.equals(null))
                    publisher = String.valueOf(R.string.not_available);


                published_date = volume_object.optString("publishedDate");


                if (published_date.equals(null) )
                {
                    published_date = String.valueOf(R.string.not_available);
                }
                String language = volume_object.optString("language");
                if (language.equals(null))
                    language=String.valueOf(R.string.not_available);

                String preview = volume_object.optString("previewLink");

                JSONObject image_link =volume_object.optJSONObject("imageLinks");

                System.out.println("inage link ****** " + image_link);
                String image;
                if (image_link!=null)
                {
                   image = image_link.optString("thumbnail");

                }
                else
                {
                    image = "not available";
                }




                //accessing sale info

                JSONObject sale_object =currnet_book.optJSONObject("saleInfo");
               if (!sale_object.equals(null))
               {
                 country = sale_object.optString("country");
                if (country.equals(null))
                country=String.valueOf(R.string.not_available);


                saleability = sale_object.optString("saleability");

                if(saleability.equals(null) || saleability.equals("NOT_FOR_SALE") )
                {

                    currency="Not Available !";
                    price=0.00;

                }

                else
                {
                    buy_link = sale_object.optString("buyLink");

                    JSONObject listPrice = sale_object.optJSONObject("listPrice");

                    if (listPrice !=null) {
                        currency = listPrice.optString("currencyCode");
                        price = listPrice.optDouble("amount", 0);
                    }
                }


               }


                books.add(new Books(title,language,image ,preview,buy_link,country,currency,saleability,price,publisher,published_date,authors ));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return books;
    }

    public static ArrayList<Books> execute(String String_url)
    {
        ArrayList<Books> books = new ArrayList<>();

        try {

            url = createUrl(String_url);
            SAMPLE_JSON_RESPONSE = makeHttprequest(url);
            books = extract_books();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

}
