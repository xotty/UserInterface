/**
 * recycleView适配器，此时需要覆写下列方法：
 * 1)onCreateViewHolder(ViewGroup parent, int viewType)，为Item创建视图
 * 2)onBindViewHolder(RecyclerView.ViewHolder holder, int position)，将数据绑定到Item的视图上
 * 3)getItemCount()，行数
 * 4)getItemViewType(int position)，可选，缺省值为0：item的类型
 * 另外还要：
 * 1）实现每一行内容的RecyclerView.ViewHolder，有几种就要实现几种
 * 2）实现每一行内容的onClickListener
 * 3）实现ItemTouchHelperCallback中拖拽和侧滑接口
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:RecyclerViewAdapter
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdvancedViewGroup;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.xottys.userinterface.R;

import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerviewMoveAndSwipedListener {

    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;
    private final String FOOTER = "footer";
    private Context context;
    private List<String> mItems;
    private int color = 0;
    private View parentView;
    private MyItemClickListener mItemClickListener;

    public RecyclerViewAdapter(Context context, List data) {
        this.context = context;
        mItems = data;
    }

    public void setItems(List<String> data) {
        this.mItems.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(int position, String insertData) {
        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    public void addItems(List<String> data) {
        mItems.addAll(data);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addFooter() {
        mItems.add(FOOTER);
        notifyItemInserted(mItems.size() - 1);
    }

    public void removeFooter() {
        mItems.remove(mItems.size() - 1);
        notifyItemRemoved(mItems.size());
    }

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }

    //根据ItemViewType，引用不同的Item视图：item_recycle_view和item_recycle_footer
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = parent;

        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_content, parent, false);

            return new RecyclerViewHolder(view, mItemClickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    //设置Item的数据，这里只是设置左侧圆环（rela_round）的颜色
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewHolder) {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

            if (color == 1) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_blue, null)));
            } else if (color == 2) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_green, null)));
            } else if (color == 3) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_yellow, null)));
            } else if (color == 4) {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.google_red, null)));
            } else {
                recyclerViewHolder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.gray, null)));
            }

            /*设置Item监听器的第二种方法
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, position);
                    }
                }
            });*/

        }
    }

    //两种不同的Item：TYPE_NORMAL，TYPE_FOOTER
    @Override
    public int getItemViewType(int position) {
        String s = mItems.get(position);
        if (s.equals(FOOTER)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    //Item数量（行数）
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    //实现ItemTouchHelperCallback中拖拽功能：上下换位
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    //实现ItemTouchHelperCallback中侧滑功能：删除Item并用snackBar提示
    @Override
    public void onItemDismiss(final int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        Snackbar.make(parentView, context.getString(R.string.item_swipe_dismissed), Snackbar.LENGTH_SHORT)
                .setAction(context.getString(R.string.item_swipe_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addItem(position, mItems.get(position));
                    }
                }).show();
    }

    //创建一个回调接口
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    //定义itemView 的视图之一,减少findViewById()的次数
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        /*设置Item监听器的第二种方法
        private View mView;*/
        private RelativeLayout rela_round;

        public RecyclerViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            /*设置Item监听器的第二种方法
            mView=itemView;*/
            rela_round = (RelativeLayout) itemView.findViewById(R.id.rela_round);

            //设置监听器的第一种方法：将全局的监听赋值给接口
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }
    }

    //定义itemView 的视图之二，减少findViewById()的次数
    private class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progress_bar_load_more;

        private FooterViewHolder(View itemView) {
            super(itemView);
            progress_bar_load_more = (ProgressBar) itemView.findViewById(R.id.progress_bar_load_more);
        }
    }

}
