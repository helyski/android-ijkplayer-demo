/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
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

package tv.danmaku.ijk.media.example.content;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.File;

public class PathCursorLoader extends AsyncTaskLoader<Cursor> {
    private File mPath;

    public PathCursorLoader(Context context) {
        this(context, Environment.getExternalStorageDirectory());
    }

    public PathCursorLoader(Context context, String path) {
        super(context);
        try {
            if("/".equals(path) || "".equals(path)){
                path = "/sdcard";
            }

            Log.d("PathCursorLoader", "PathCursorLoader: " + path);
            mPath = new File(path);
            if(mPath.isDirectory()){
                Log.d("PathCursorLoader", "PathCursorLoader: " + path + ",isDirectory");
                File[] files = mPath.listFiles();
                for (File file : files) {
                    Log.d("PathCursorLoader", "PathCursorLoader: " + path + ",file=" + file);
                }
            }
        }catch (Exception e){
            Log.e("PathCursorLoader", "PathCursorLoader: " + path + ",error=" + Log.getStackTraceString(e));
            e.printStackTrace();
        }
    }

    public PathCursorLoader(Context context, File path) {
        super(context);
        mPath = path;
    }

    @Override
    public Cursor loadInBackground() {
        File[] file_list = null;
        try {
            file_list = mPath.listFiles();
            Log.d("PathCursorLoader", "loadInBackground: " + mPath + ",file_list size=" + file_list.length);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new PathCursor(mPath, file_list);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
