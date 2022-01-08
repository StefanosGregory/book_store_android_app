package com.projects;

import android.net.Uri;
import android.view.LayoutInflater;
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

public class bookAdapter  extends FirebaseRecyclerAdapter<book, bookAdapter.bookViewholder>{
    public bookAdapter(
            @NonNull FirebaseRecyclerOptions<book> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull bookViewholder holder, int position, @NonNull book model)
    {

        Glide.with(holder.imageView.getContext()).load(model.getCover()).into(holder.imageView);
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewType.setText(model.getType());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewAuthor.setText(model.getAuthor());
        holder.textViewPrice.setText("€"+model.getPrice());

        int quantity =Integer.parseInt(model.getQuantity());

        if( quantity >= 1){
            holder.buttonBuy.setEnabled(true);
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
        ImageView imageView;
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
        }
    }

}
