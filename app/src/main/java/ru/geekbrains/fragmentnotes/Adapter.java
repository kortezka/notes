package ru.geekbrains.fragmentnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {
    private CardsSource dataSource;
    private OnClickListener listener;

    public Adapter(CardsSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setData(dataSource.getCardData(position));

    }


    @Override
    public int getItemCount() {
        return dataSource.size();

    }

    interface OnClickListener {
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            description = itemView.findViewById(R.id.textView2);
            title.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
        }


        public void setData(NoteData noteData) {
            title.setText(noteData.getName());
            description.setText(noteData.getDiscriptionShort());

        }

    }


}

