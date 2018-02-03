/**
 * recycleView拖拽和侧滑的接口，在RecyclerViewAdapter中实现，在ItemTouchHelperCallback中调用
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

public interface RecyclerviewMoveAndSwipedListener {
    //拖拽时操作，通常是上下交换位置
    void onItemMove(int fromPosition, int toPosition);

    //侧滑时操作，通常是删除当前项目
    void onItemDismiss(int position);

}
