package com.projects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class buyActivity extends AppCompatActivity {
    String toast_msg;
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        // Load items
        TextView quantityText = findViewById(R.id.buy_quantity);
        TextView totalPrice = findViewById(R.id.buy_total);
        TextView titleText = findViewById(R.id.buy_book_title);
        TextView type = findViewById(R.id.buy_book_type);
        TextView author = findViewById(R.id.buy_book_author);
        TextView desc = findViewById(R.id.buy_book_desc);
        TextView price = findViewById(R.id.buy_book_price);
        ImageView cover = findViewById(R.id.buy_book_img);
        SeekBar quantity_bar = findViewById(R.id.buy_book_quantity);
        Button buyNow = findViewById(R.id.buy_book_buy_btn);
        // Get intent
        Intent intent = getIntent();
        // Take extras from intent and set in text views
        double book_price = Double.parseDouble(intent.getStringExtra("price"));
        int available_books = Integer.parseInt(intent.getStringExtra("quantity"));
        // Set extras
        titleText.setText(intent.getStringExtra("title"));
        type.setText(intent.getStringExtra("type"));
        author.setText(intent.getStringExtra("author"));
        desc.setText(intent.getStringExtra("desc"));
        price.setText("€" + book_price);
        toast_msg = intent.getStringExtra("msg");
        // Load book image
        Glide.with(this).load(intent.getStringExtra("cover")).into(cover);
        // Set max value same as quantity on bar
        quantity_bar.setMax(available_books);
        // Calculate price on bar value change
        quantity_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Set quantity
                quantityText.setText("" + i);
                // Set price
                totalPrice.setText("€" + String.format("%.2f", i*book_price));
                quantity = i;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Enable button if bar isn't = 0
                buyNow.setEnabled(seekBar.getProgress() != 0);
            }
        });


        // Close activity on cancel
        findViewById(R.id.buy_book_cancel).setOnClickListener(view -> finish());
    }

    public void buy(View view){
        // Show bought message
        Toast.makeText(view.getContext(),toast_msg, Toast.LENGTH_SHORT).show();
    }
}