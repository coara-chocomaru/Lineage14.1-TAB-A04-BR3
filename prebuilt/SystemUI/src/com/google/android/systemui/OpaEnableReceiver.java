package com.google.android.systemui;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.app.AssistUtils;
import com.android.keyguard.KeyguardUpdateMonitor;

/* JADX INFO: loaded from: classes.dex */
public class OpaEnableReceiver extends BroadcastReceiver {
    private static final String TAG = "OpaEnableReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        boolean enabled = intent.getBooleanExtra(Constants.OPA_ENABLED, false);
        Log.i(TAG, "Received " + intent + " with enabled = " + enabled + " userId=" + KeyguardUpdateMonitor.getCurrentUser());
        ContentResolver cr = context.getContentResolver();
        Settings.Secure.putIntForUser(cr, Constants.OPA_ENABLED_SETTING, enabled ? 1 : 0, KeyguardUpdateMonitor.getCurrentUser());
        new OpaEnableDispatcher(context, new AssistUtils(context)).dispatchOpaEnabled(enabled);
    }
}
