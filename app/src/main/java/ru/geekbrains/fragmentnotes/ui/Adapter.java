package ru.geekbrains.fragmentnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.fragmentnotes.CardsSource;
import ru.geekbrains.fragmentnotes.NoteData;
import ru.geekbrains.fragmentnotes.R;


public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {
    private CardsSource dataSource;
    private OnClickListener listener;
    private Fragment fragment;
    private int menuPosition;

    public Adapter( Fragment fragment) {

        this.fragment = fragment;
    }

    public void setDataSource(CardsSource dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public int getMenuPosition() {
        return menuPosition;
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
            registerContextMenu(itemView);
            title.setOnLongClickListener(v -> {
                itemView.showContextMenu(150, 20);
                menuPosition = getLayoutPosition();
                return true;
            });
        }


        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                itemView.setOnLongClickListener(v -> {
                    menuPosition=getAdapterPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }


        public void setData(NoteData noteData) {
            title.setText(noteData.getName());
            description.setText(noteData.getDiscriptionShort());

        }

    }


}

