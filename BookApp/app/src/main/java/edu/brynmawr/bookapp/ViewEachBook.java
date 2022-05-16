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

public class ViewEachBook extends AppCompatActivity {
   public Book book;
   TextView tvTitle;
   TextView tvAuthor;
   TextView tvDesc;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_view_each);

      Bundle bundle = getIntent().getExtras();
      if (bundle == null) {
          return;
      }
      Book book = (Book) bundle.get("data");
      tvTitle = (TextView) findViewById(R.id.tvTitle);
      tvTitle.setText(book.getTitle());

      tvAuthor = (TextView) findViewById(R.id.tvAuthor);
      tvAuthor.setText(book.getAuthor());

      tvDesc = (TextView) findViewById(R.id.tvDesc);
      tvDesc.setText(book.getDescription());
  }
}
