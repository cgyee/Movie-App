import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.movieapp.R;

public class MovieSearchResult extends RelativeLayout {
    private TextView txt;
    private ImageButton image;
    private RelativeLayout.LayoutParams params;

    MovieSearchResult(Context context) {
        super(context);
        init();
    }

    public MovieSearchResult(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        params = new RelativeLayout.LayoutParams(80, 80);
        image = new ImageButton(getContext());
        image.setImageResource(R.drawable.ic_launcher_background);
        image.setLayoutParams(params);
        params.leftMargin = 0;
        params.topMargin = 40;
        this.addView(image, params);

        txt = new TextView(getContext());
        txt.setText("Testing");
        params= new RelativeLayout.LayoutParams(100, 100);
        params.leftMargin =40;
        params.topMargin =20;
        this.addView(txt, params);
    }

}
