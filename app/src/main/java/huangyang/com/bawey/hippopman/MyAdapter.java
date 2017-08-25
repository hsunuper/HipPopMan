package huangyang.com.bawey.hippopman;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 创建人:hy
 * 日期:  2017/8/25
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Bean.DataBean> list;
    private Context context;

    public MyAdapter(List<Bean.DataBean> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        final ViewHolder viewholder = new ViewHolder(inflate);
        ObjectAnimator.ofFloat(inflate, "alpha", 0f, 1f)
                .setDuration(5000).start();
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewholder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewholder.getAdapterPosition();
                String s = list.get(position).getTitle();
                Toast.makeText(context, "点击了第" + position + "个图片,信息："+s, Toast.LENGTH_SHORT).show();
            }
        });
        viewholder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewholder.getAdapterPosition();
                Toast.makeText(context, "点击了第" + position + "个图片下的文字条目", Toast.LENGTH_SHORT).show();

            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder hoders = (ViewHolder) holder;
        Glide.with(context).load(list.get(position).getUserImg()).into(hoders.image);
        hoders.tv.setText(list.get(position).getIntroduction() + "");
        hoders.age.setText(list.get(position).getUserAge()+"");
        hoders.work.setText(list.get(position).getOccupation()+"");
    }

    @Override
    public int getItemCount() {
        return  list==null?0:list.size();
    }

    static  class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView tv;
        TextView age,work;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.monimages);
            tv = (TextView) itemView.findViewById(R.id.montv);
            age = (TextView) itemView.findViewById(R.id.age);
            work = (TextView) itemView.findViewById(R.id.work);
        }
    }
}
