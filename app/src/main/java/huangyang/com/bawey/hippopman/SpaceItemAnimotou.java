package huangyang.com.bawey.hippopman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 创建人:hy
 * 日期:  2017/8/25
 */

public class SpaceItemAnimotou extends RecyclerView.ItemDecoration{
    private int dividerHeight;
    private Paint paint;
    public SpaceItemAnimotou(Context context) {
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.margin);
        paint=new Paint();
        paint.setColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        /**
         * 类似加了一个bottom的padding
         */
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            /**
             * 绘制的矩形也就是从，item的左上角，到右下角，类似于背景，
             * 正好显示出一个横线，就是getItemOffsets空出来的范围
             */
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
