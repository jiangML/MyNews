package com.me.jiang.mynews.view.fragment;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.me.jiang.mynews.view.iview.ICommonView;

/**
 * Created by Administrator on 2016/5/6.
 */
public class BaseFragment extends Fragment implements ICommonView{

    protected ProgressDialog dialog;
    protected Toast toast;

    @Override
    public void showToast(String message) {
        if(toast==null)
        {
            toast= Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

    @Override
    public void showDialog(String title, String message) {

    }

    @Override
    public void showProgressDialog(String title, String message) {
        if(dialog==null)
        {
            dialog= ProgressDialog.show(getActivity(), title, message);
        }else {
            dialog.setTitle(title);
            dialog.setMessage(message);
            dialog.show();
        }
    }

    @Override
    public void hideDialog() {
        if(dialog!=null&&dialog.isShowing())
        {
            dialog.dismiss();
        }
    }
}
