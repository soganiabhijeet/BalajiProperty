package com.example.balajiproperty.sqlite.model;

import android.provider.BaseColumns;

public final class MessageSent {
    private MessageSent() {
    }

    /* Inner class that defines the table contents */
    public static class MessageSentEntry implements BaseColumns {
        public static final String TABLE_NAME = "message_sent";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
