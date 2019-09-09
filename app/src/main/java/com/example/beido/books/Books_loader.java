package com.example.beido.books;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Beido on 7/21/2018.
 */

public class Books_loader extends AsyncTaskLoader<ArrayList<Books>> {

    String url;
    public Books_loader(Context context , String url) {

        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }
    @Override
    public ArrayList<Books> loadInBackground() {

        ArrayList<Books> books= null;
        if (url == null)
            return null;



       try { books = QueryUtils.execute(url);}
       catch (Exception e )
       {
           System.out.println("do in background function error : " + e);
       }

        return books;
    }
}
