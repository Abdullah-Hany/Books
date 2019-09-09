package com.example.beido.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<Books>> , SearchView.OnQueryTextListener {
    String url = "https://www.googleapis.com/books/v1/volumes?q=";
    private Books_adapter books_adapter;
    LoaderManager loaderManager;
    TextView empty_view;
    String input ;
    Button buy_button , preview_button;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //creating refrences for list and empty views

        ListView books_listview= (ListView) findViewById(R.id.list);
        empty_view  =  (TextView) findViewById(R.id.empty_view);
        books_listview.setEmptyView(empty_view);



        ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.GONE);



            //checking the network status

         // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data


           if (networkInfo != null && networkInfo.isConnected()) {
               // Get a reference to the LoaderManager, in order to interact with loaders.
                loaderManager = getLoaderManager();

               // Initialize the loader. Pass in the int ID constant defined above and pass in null for
               // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
               // because this activity implements the LoaderCallbacks interface).
              // loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
           }

           else
           {
                spinner = (ProgressBar) findViewById(R.id.loading_spinner);
               spinner.setVisibility(View.GONE);

               empty_view.setText(R.string.no_network);
           }





        //initializing the adapter
        books_adapter = new Books_adapter(this , 0,new ArrayList<Books>(),loaderManager );

       //printing the adapter to the screen
        books_listview.setAdapter(books_adapter);

        //getting a reference for the search view

        SearchView search = (SearchView) findViewById(R.id.search_view);

        //setting searchview listener
        search.setOnQueryTextListener(this);





    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        System.out.println("input *******************  "  + query);
        input =query;
        input = input.replace(" ", "%20");
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.VISIBLE);
        empty_view  =  (TextView) findViewById(R.id.empty_view);
        empty_view.setVisibility(View.GONE);


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {



        return false;
    }







    @Override
    public Loader<ArrayList<Books>> onCreateLoader(int i, Bundle bundle) {

        System.out.println("*************url = " + url+input);
        return new Books_loader(this, url+input);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Books>> loader, ArrayList<Books> books) {

        ProgressBar spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.GONE);
        empty_view  =  (TextView) findViewById(R.id.empty_view);
        empty_view.setText(R.string.no_books);


        books_adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            books_adapter.addAll(books);
        }
        loaderManager.destroyLoader(EARTHQUAKE_LOADER_ID);


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Books>> loader) {

        // Loader reset, so we can clear out our existing data.
        books_adapter.clear();

    }


    public void button_clicked (View v)
    {
        if (v.getId() == R.id.sample)
        {
            System.out.println (" ****************id "+v.getId());
            int position= (int) v.getTag();
            Books current_book = books_adapter.getItem(position);
            System.out.println (" ****************titleeee "+current_book.getTitle());




        }
        else if (v.getId() == R.id.buy)
        {

        }

    }
}
