package com.projects;

import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Locale;

public class bookAdapter  extends FirebaseRecyclerAdapter<book, bookAdapter.bookViewholder>{
    public bookAdapter(
            @NonNull FirebaseRecyclerOptions<book> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "book.xml") with data in
    // model class(here "book.class")
    @Override
    protected void
    onBindViewHolder(@NonNull bookViewholder holder, int position, @NonNull book model)
    {

        Glide.with(holder.imageView.getContext()).load(model.getCover()).into(holder.imageView);
        if(Locale.getDefault().getDisplayLanguage().equals("Ελληνικά")){
            //Greek
            holder.textViewTitle.setText(model.getTitle_el());
            holder.textViewType.setText(model.getType_el());
            holder.textViewDescription.setText(model.getDescription_el());
        }else
        {
            //English
            holder.textViewTitle.setText(model.getTitle());
            holder.textViewType.setText(model.getType());
            holder.textViewDescription.setText(model.getDescription());
        }
        holder.textViewAuthor.setText(model.getAuthor());
        holder.textViewPrice.setText("€"+model.getPrice());
        holder.tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuFragment.tts.speak(model.getTitle(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        int quantity =Integer.parseInt(model.getQuantity());

        if(quantity >= 1){
            holder.buttonBuy.setEnabled(true);

            holder.buttonBuy.setOnClickListener(view -> {
                Intent myIntent = new Intent(holder.imageView.getContext(), buyActivity.class);

                if(Locale.getDefault().getDisplayLanguage().equals("Ελληνικά")){
                    //Greek
                    myIntent.putExtra("title", model.getTitle_el());
                    myIntent.putExtra("type", model.getType_el());
                    myIntent.putExtra("desc", model.getTitle_el());
                    myIntent.putExtra("msg", "Ολοκλήρωση αγοράς με επιτυχία!");
                }else
                {
                    //English
                    myIntent.putExtra("title", model.getTitle());
                    myIntent.putExtra("type", model.getType());
                    myIntent.putExtra("desc", model.getDescription());
                    myIntent.putExtra("msg", "Complete purchase successfully!");
                }

                myIntent.putExtra("author", model.getAuthor());
                myIntent.putExtra("price", model.getPrice());
                myIntent.putExtra("quantity", model.getQuantity());
                myIntent.putExtra("price", model.getPrice());
                myIntent.putExtra("cover", model.getCover());
                holder.imageView.getContext().startActivity(myIntent);
            });
        }
    }

    @NonNull
    @Override
    public bookViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book, parent, false);
        return new bookAdapter.bookViewholder(view);
    }

    // Sub Class to create references of the views in Card
    // view (here "person.xml")
    class bookViewholder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewType, textViewDescription, textViewAuthor, textViewPrice;
        Button buttonBuy;
        ImageView imageView, tts;
        public bookViewholder(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            buttonBuy = itemView.findViewById(R.id.buttonBuy);
            tts = itemView.findViewById(R.id.book_card_tts);
        }
    }

}
