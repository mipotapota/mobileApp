package com.example.memoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private List<Memo> memoList;
    private OnMemoClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public interface OnMemoClickListener {
        void onMemoClick(int position);
        void onMemoCopyClick(int position);
    }

    public MemoAdapter(List<Memo> memoList, OnMemoClickListener listener) {
        this.memoList = memoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {
        Memo memo = memoList.get(position);
        holder.textTitle.setText(memo.getTitle());

        // 내용 미리보기 (최대 50자)
        String previewContent = memo.getContent();
        if (previewContent.length() > 50) {
            previewContent = previewContent.substring(0, 50) + "...";
        }
        holder.textPreview.setText(previewContent);

        // 날짜 표시
        String formattedDate = dateFormat.format(new Date(memo.getTimestamp()));
        holder.textDate.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    class MemoViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        TextView textPreview;
        TextView textDate;
        ImageButton buttonCopy;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textPreview = itemView.findViewById(R.id.textPreview);
            textDate = itemView.findViewById(R.id.textDate);
            buttonCopy = itemView.findViewById(R.id.buttonCopy);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onMemoClick(position);
                    }
                }
            });

            buttonCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onMemoCopyClick(position);
                    }
                }
            });
        }
    }
}
