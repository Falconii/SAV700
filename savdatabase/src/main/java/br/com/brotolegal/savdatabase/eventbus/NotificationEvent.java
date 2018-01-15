package br.com.brotolegal.savdatabase.eventbus;

import android.content.Intent;

/**
 * Created by Falconi on 19/09/2016.
 */
public class NotificationEvent {

    private Intent intent;


    public NotificationEvent(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
