/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appetite.appetiteoperationsapp.Database;

import android.net.Uri;
import android.provider.BaseColumns;


public final class AppContract
{
    public static final String CONTENT_AUTHORITY = "com.appetite.appetiteoperationsapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USERS = "users";
    public static final String PATH_CLIENTS = "clients";
    public static final String PATH_TASK = "tasks";
    public static final String PATH_TASK_PROGRESS = "tasks_progress";

    public static final String PATH_BOTH = "both";

    public static final Uri BOTH_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOTH);

    private AppContract() {}

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);

        public final static String TABLE_NAME = "users";

        public final static String SNO = "sno";
        public final static String ID = "_id";
        public final static String USERID = "userid";
        public final static String TYPE = "type";
        public final static String NAME = "name";
    }

    public static final class ClientsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CLIENTS);

        public final static String TABLE_NAME = "clients";

        public final static String SNO = "sno";
        public final static String ID = "_id";
        public final static String CLIENTID = "clientid";
        public final static String NAME = "name";
        public final static String ADDRESS = "address";
        public final static String NUMBER = "number";
    }

    public static final class TaskEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASK);

        public final static String TABLE_NAME = "tasks";

        public final static String SNO = "sno";
        public final static String ID = "_id";
        public final static String TASKID = "taskid";
        public final static String CLIENTID = "clientid";
        public final static String NAME = "name";
        public final static String CLIENT_DEADLINE = "client_deadline";
        public final static String DESCRIPTION = "description";
        public final static String STATUS = "status";
    }

    public static final class TaskProgressEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASK_PROGRESS);

        public final static String TABLE_NAME = "tasks_progress";

        public final static String SNO = "sno";
        public final static String ID = "_id";
        public final static String TASKID = "taskid";
        public final static String STATUS = "status";
        public final static String DEADLINE = "deadline";
        public final static String EMPLOYEE = "employee";
        public final static String START_TIME = "starttime";
        public final static String END_TIME = "endtime";
    }

}

