/*
 * -----------------------------
 * Copyright (C) 2016, 上海宅米贸易有限公司, All rights reserved.
 * -----------------------------
 * File: WrapperAdapter.java
 * Author: LongLe
 *
 */

package com.songlin.chengyu.widget.pulltorefresh;

import android.support.v7.widget.RecyclerView;

public interface WrapperAdapter
{

    public RecyclerView.Adapter getWrappedAdapter();
}
