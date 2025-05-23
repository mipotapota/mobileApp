package com.example.calendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends ArrayAdapter<Event> {
    private Context context;
    private List<Event> events;
    private OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(Event event);
        void onEventLongClick(Event event);
    }

    public EventAdapter(Context context, List<Event> events) {
        super(context, 0, events);
        this.context = context;
        this.events = events;
    }

    public void setOnEventClickListener(OnEventClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView titleView = convertView.findViewById(android.R.id.text1);
        TextView timeView = convertView.findViewById(android.R.id.text2);

        // 알림 아이콘과 함께 제목 표시
        String titleText = event.getTitle();
        if (event.hasNotification()) {
            titleText = "🔔 " + titleText;
        }
        titleView.setText(titleText);

        // 시간과 설명 표시
        String description = event.getDescription();
        if (description == null || description.trim().isEmpty()) {
            description = "설명 없음";
        }
        timeView.setText(String.format(Locale.getDefault(), "%02d:%02d - %s",
                event.getHour(), event.getMinute(), description));

        // 클릭 이벤트 처리
        convertView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEventClick(event);
            }
        });

        convertView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onEventLongClick(event);
            }
            return true;
        });

        return convertView;
    }
}