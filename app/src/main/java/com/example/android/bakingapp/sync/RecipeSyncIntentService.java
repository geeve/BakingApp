package com.example.android.bakingapp.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Administrator on 2017/11/18 0018.
 * com.example.android.bakingapp.sync,BakingApp
 * 从网路获取数据，并保存到数据库中
 */

public class RecipeSyncIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public RecipeSyncIntentService() {
        super("RecipeSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
