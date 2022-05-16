package edu.brynmawr.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ViewBooks extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);

        RecyclerView rvBooks = findViewById(R.id.rvBooks);
        books = new ArrayList<>();
        BookAdapter bookAdapter = new BookAdapter(this, books);
        rvBooks.setAdapter(bookAdapter);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        books.addAll(onConnectButtonClick());
        bookAdapter.notifyDataSetChanged();
    }

    public List<Book> onConnectButtonClick() {
        List<Book> books = new ArrayList<>();

        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {

                            URL url = new URL("http://10.0.2.2:3000/allapp");

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.connect();
                            int responsecode = conn.getResponseCode();
                            if (responsecode != 200) {
                                System.out.println("Unexpected status code: " + responsecode);
                            } else {
                                Scanner in = new Scanner(url.openStream());
                                JSONParser parser = new JSONParser();
                                while (in.hasNext()) {
                                    String array = in.nextLine();
                                    System.out.println(array);
                                    JSONArray resp_arr = (JSONArray) parser.parse(array);
                                    Iterator iter = resp_arr.iterator();
                                    while (iter.hasNext()) {
                                        JSONObject event = (JSONObject) iter.next();
                                        String title = (String) event.get("title");
                                        String author = (String) event.get("author");
                                        String desc = (String) event.get("description");
                                        Book curr = new Book(title, author, desc);
                                        books.add(curr);
                                    }
                                }

                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
            executor.awaitTermination(2, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}