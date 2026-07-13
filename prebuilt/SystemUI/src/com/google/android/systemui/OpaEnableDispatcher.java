package com.google.android.systemui;

import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import com.android.internal.app.AssistUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.statusbar.phone.ButtonDispatcher;
import com.android.systemui.statusbar.phone.PhoneStatusBar;
import java.util.ArrayList;

public class OpaEnableDispatcher {
    private final AssistUtils mAssistUtils;
    private final Context mContext;

    public OpaEnableDispatcher(Context context, AssistUtils assistUtils) {
        this.mContext = context;
        this.mAssistUtils = assistUtils;
    }

    public void dispatchOpaEnabled(boolean enabled) {
        dispatchUnchecked(enabled ? isGsaCurrentAssistant() : false);
    }

    private void dispatchUnchecked(boolean enabled) {
        PhoneStatusBar bar = (PhoneStatusBar) ((SystemUIApplication) this.mContext.getApplicationContext()).getComponent(PhoneStatusBar.class);
        if (bar == null) {
            return;
        }
        ButtonDispatcher homeDispatcher = bar.getHomeButton();
        ArrayList<View> views = homeDispatcher.getViews();
        for (int i = 0; i < views.size(); i++) {
            View v = views.get(i);
            ((OpaLayout) v).setOpaEnabled(enabled);
        }
    }

    private boolean isGsaCurrentAssistant() {
        ComponentName assistant = this.mAssistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser());
        if (assistant != null) {
            return Constants.OPA_COMPONENT_NAME.equals(assistant.flattenToString());
        }
        return false;
    }
}
