package com.example.beido.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Beido on 7/20/2018.
 */

public class Books_adapter extends ArrayAdapter<Books>  {

    private String image_link;
    private ImageView image;
    LoaderManager loaderManager ;
    private Context con;

    public Books_adapter(@NonNull Context context, int resource, @NonNull List<Books> objects, LoaderManager loaderManager ) {

        super(context, resource, objects);
        this.loaderManager = loaderManager ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Books current_book = getItem(position);
        View listView = convertView;
        if (listView == null)
        {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.book_page,parent,false);
        }

        //setting the title
        TextView title = (TextView) listView.findViewById(R.id.title2);
        title.setText(current_book.getTitle());

        //setting the authors

        TextView authors = (TextView) listView.findViewById(R.id.authors2);
        int number_of_authors = current_book.getAuthors().length;
        String [] author = current_book.getAuthors();
        authors.setText(author[0]);
        authors.append("\n");

        for (int i = 1 ; i<number_of_authors ; i++)
        {
            authors.append(author[i]);
            authors.append("\n");
        }

        //setting the book cover image

        image = (ImageView)listView.findViewById(R.id.photo_image_view);
        image_link = current_book.getImage_link();
        if (!image_link.equals("not available")) {

            //getting the image in a background thread and resizing it into the image view
            Picasso.with(getContext())
                    .load(image_link).resize(100, 100).into(image);

        }
        else
        {
            image.setImageResource(R.drawable.not_available);
        }



        //setting the publisher

        TextView publisher = (TextView) listView.findViewById(R.id.publisher2);
        publisher.setText(current_book.getPublisher());

        //setting the publisher's date

        TextView publisher_date = (TextView) listView.findViewById(R.id.published_date2);

        //Date date = new Date((Date)current_book.getPublisher_date());

        publisher_date.setText(current_book.getPublisher_date());

        //setting the language

        TextView language = (TextView) listView.findViewById(R.id.language2);
        String book_language = current_book.getLanguage();
        if (book_language.equals("en"))
            language.setText(R.string.English);
        else
            language.setText(book_language);

        //setting the country

        TextView country  = (TextView) listView.findViewById(R.id.country2);
        String book_country= current_book.getCountry();
        if (book_country.equals("EG"))
        country.setText(R.string.Egypt);
        else country.setText(book_country);

        //checking to see if it is saleable or not
        String saleable = current_book.getSaleability();

        System.out.println("***************saleability " + saleable);

        Button buy_button=(Button) listView.findViewById(R.id.buy) , preview_button = (Button) listView.findViewById(R.id.sample);
        //if salaable print sale info

        if (saleable .equals("FOR_SALE") ) {
            System.out.println("***************for saleee ");
            TextView price = (TextView) listView.findViewById(R.id.price2);
            price.setVisibility(View.VISIBLE);
            price.setText(formate_price(current_book.getPrice()));

            TextView currency = (TextView) listView.findViewById(R.id.currency_code);
            currency.setVisibility(View.VISIBLE);
            currency.setText(current_book.getCurrency_code());

            buy_button.setVisibility(View.VISIBLE);


        }
        else if (saleable.equals("NOT_FOR_SALE"))
        {
            System.out.println("*************** not  for saleee ");

            TextView price = (TextView) listView.findViewById(R.id.price2);
            price.setVisibility(View.GONE);
            TextView price1 = (TextView) listView.findViewById(R.id.price);
            price1.setText("Not for sale !");

            TextView currency = (TextView) listView.findViewById(R.id.currency_code);
            currency.setVisibility(View.GONE);


            buy_button.setVisibility(View.GONE);

        }




        //buy_button click listener
        buy_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri URI = null;
                Intent websiteIntent;

                URI= Uri.parse(current_book.getBuy_link());
                websiteIntent= new Intent(Intent.ACTION_VIEW, URI);

                //starts the intent
               Context con=  getContext();
                con.startActivity(websiteIntent);
            }

        });

        //sample button click listener



        //buy_button click listener
        preview_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri URI = null;
                Intent websiteIntent;

                URI= Uri.parse(current_book.getPreview_link());
                websiteIntent= new Intent(Intent.ACTION_VIEW, URI);

                //starts the intent
                Context con=  getContext();
                con.startActivity(websiteIntent);
            }

        });








        return listView;
    }


    private String formate_price (double price) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(price);
    }


}
