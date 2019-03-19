package com.example.kamusku.util;

import android.view.View;

/**
 * Created by User on 1/19/2019.
 */

public class InterfaceOnItemClick {

    public interface KamusItemCallback{
        void onItemClick(int position, View view);
    }
}
