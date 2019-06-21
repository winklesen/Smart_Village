package com.samuelbernard147.smartvillage.Helper;

import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.samuelbernard147.smartvillage.service.FireJobService;
import com.samuelbernard147.smartvillage.service.FloodJobService;
import com.samuelbernard147.smartvillage.service.PanicJobService;

public class NotifHelper {
    private FirebaseJobDispatcher mDispatcher;
    public final String PANIC_TAG = "panicnotif";
    public final String FLOOD_TAG = "floodnotif";
    public final String FIRE_TAG = "firenotif";

    public NotifHelper(Context context) {
        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    public void startDispatcherPanic() {
        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString(PanicJobService.EXTRA_PANIC, "panic");

        Job myJob = mDispatcher.newJobBuilder()
                .setService(PanicJobService.class)
                .setTag(PANIC_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 0))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_ANY_NETWORK
                )
                .setExtras(myExtrasBundle)
                .build();
        mDispatcher.mustSchedule(myJob);
    }

    public void startDispatcherFlood() {
        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString(FloodJobService.EXTRA_FLOOD, "flood");

        Job myJob = mDispatcher.newJobBuilder()
                .setService(FloodJobService.class)
                .setTag(FLOOD_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 0))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_ANY_NETWORK
                )
                .setExtras(myExtrasBundle)
                .build();
        mDispatcher.mustSchedule(myJob);
    }

    public void startDispatcherFire() {
        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString(FireJobService.EXTRA_FIRE, "fire");

        Job myJob = mDispatcher.newJobBuilder()
                .setService(FireJobService.class)
                .setTag(FIRE_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 0))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_ANY_NETWORK
                )
                .setExtras(myExtrasBundle)
                .build();
        mDispatcher.mustSchedule(myJob);
    }

    public void cancelDispatcher(String tag) {
        switch (tag) {
            case PANIC_TAG:
                mDispatcher.cancel(PANIC_TAG);
                break;
            case FLOOD_TAG:
                mDispatcher.cancel(FLOOD_TAG);
                break;
            case FIRE_TAG:
                mDispatcher.cancel(FIRE_TAG);
                break;
        }
    }
}
