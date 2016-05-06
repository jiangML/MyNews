package com.me.jiang.mynews.view.iview;

/**
 * Created by Administrator on 2016/5/6.
 * 公共view显示的接口
 */
public interface ICommonView {
    /**
     * 显示toast
     * @param message
     */
    void showToast(String message);

    /**
     * 显示对话框
     * @param title
     * @param message
     */
    void showDialog(String title,String message);

    /**
     * 显示进度条对话框
     * @param title
     * @param message
     */
    void showProgressDialog(String title,String message);


    /**
     * 隐藏对话框
     */
    void hideDialog();



}
