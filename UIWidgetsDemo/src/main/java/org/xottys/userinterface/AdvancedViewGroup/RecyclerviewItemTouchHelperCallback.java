/**
 * 实现Recycleview拖拽排序与侧滑删除的标准方法，此时需要覆写下列方法：
 * 1)getMovementFlags(RecyclerView, ViewHolder)，明确侧滑和拖拽方向
 * 2)onMove(RecyclerView, ViewHolder, ViewHolder)，拖拽时的操作
 * 3)onSwiped(ViewHolder, int)，侧滑时的操作
 * 4)isLongPressDragEnabled()，可选，缺省值为true：支持长按RecyclerView item可进入拖动操作
 * 5)isItemViewSwipeEnabled()，可选，缺省值为true：在RecyclerView任意位置触摸时启用滑动操作
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:MatrialWidgetsFragment
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdvancedViewGroup;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class RecyclerviewItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private RecyclerviewMoveAndSwipedListener moveAndSwipedListener;

    public RecyclerviewItemTouchHelperCallback(RecyclerviewMoveAndSwipedListener listener) {
        this.moveAndSwipedListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            // for recyclerView with gridLayoutManager, support drag all directions, not support swipe
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);

        } else {
            // for recyclerView with linearLayoutManager, support drag up and down, and swipe lift and right
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // If the 2 items are not the same type, no dragging
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        moveAndSwipedListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        moveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
