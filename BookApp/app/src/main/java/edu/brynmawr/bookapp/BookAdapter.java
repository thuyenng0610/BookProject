package edu.brynmawr.bookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    //stretch updates
    Context context;
    List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookView = LayoutInflater.from(context).inflate(R.layout.each_book, parent, false);
        return new ViewHolder(bookView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = books.get(position);
        holder.tvTitle.setText(book.title);
        holder.tvAuthor.setText(book.author);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(book);
            }
        });
    }

    private void onClickGoToDetail(Book book) {
        Intent intent = new Intent(context, ViewEachBook.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", book);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout_item;
        TextView tvTitle;
        TextView tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
        }

//        public void bind(Book book) {
//            tvTitle.setText(book.title);
//            tvAuthor.setText(book.author);
//            layout_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickGoToDetail(book);
//                }
//            });
//        }
    }
}