package kr.co.company.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.Objects;

public class MyPagerAdapter extends PagerAdapter {

    Context context;
    int[] images;
    String[] imageTexts; // 이미지 설명 텍스트 배열 추가

    LayoutInflater layoutInflater;

    public MyPagerAdapter(Context context, int[] images, String[] imageTexts) {
        this.context = context;
        this.images = images;
        this.imageTexts = imageTexts;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        TextView textView = (TextView) itemView.findViewById(R.id.textView);

        imageView.setImageResource(images[position]);
        textView.setText(imageTexts[position]); // 해당 위치의 텍스트 설정

        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}