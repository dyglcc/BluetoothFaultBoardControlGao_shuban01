package com.xiaobailong.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.xiaobailong.base.BaseApplication;


/**
 * Author: dyg on 2016/10/25.
 * Des:用来将一些数据保存在本地
 */
public class SpDataUtils {
    private final static SharedPreferenceUtils sp;

    private static final String USER_ID = "user_id";
    private static final String USER_MSG = "user_msg";
    private static final String USER_ACCOUNT = "login_info";
    private static final String USER_PWD = "user_pwd";
    private static final String U_ID = "uid";
    private static final String TOKEN = "token";
    private static final String AVATAR = "avatar";
    private static final String CURRENT_TEAM_JSON = "current_team";
    private static final String CURRENT_TEAM_PROJECT = "CURRENT_TEAM_PROJECT";
    private static final String USERICON = "usericon";
    private static final String USER_NAME = "username";
    private static final String MAP_NAME = "mapPackageName";
    private static final String KEY_WORD_HISTORY = "keyword_history";
    private static final String IS_FIRST = "is_first";
    private static final String IS_MENU_FIRST = "is_menu_first";

    static {
        sp = new SharedPreferenceUtils();
    }

    public static void saveIsFirst(boolean first) {
        sp.saveBoolean(IS_FIRST, first);
    }

    public static boolean getIsFirst() {
        return sp.getBoolean(IS_FIRST, true);
    }

    public static void saveMenuFirst(boolean first) {
        sp.saveBoolean(IS_MENU_FIRST, first);
    }

    public static boolean getMenuFirst() {
        return sp.getBoolean(IS_MENU_FIRST, true);
    }

    public static void saveUserName(String userName) {
        sp.saveString(USER_NAME, userName);
    }

    public static String getUserName() {
        return sp.getString(USER_NAME, "");
    }

    public static void saveMapName(String mapname) {
        sp.saveString(MAP_NAME, mapname);
    }

    public static String getMapName() {
        return sp.getString(MAP_NAME, "");
    }

    public static void saveUserIcon(String usericon) {
        sp.saveString(USERICON, usericon);
    }

    public static String getUsericon() {
        return sp.getString(USERICON, "");
    }

    public static void saveUID(String uid) {
        sp.saveString(U_ID, uid);
    }

    public static String getUID() {
        return sp.getString(U_ID, "");
    }

    public static void saveToken(String talkid) {
        sp.saveString(TOKEN, talkid);
    }

    public static String getToken() {
        return sp.getString(TOKEN, "");
    }


    public static void saveAvatar(String avatar) {
        sp.saveString(AVATAR, avatar);
    }

    public static String getAvatar() {
        return sp.getString(AVATAR, "");
    }

    public static String getCurrentTeam() {
        return sp.getString(CURRENT_TEAM_JSON, "");
    }

    public static void saveCurrentTeam(String sid) {
        sp.saveString(CURRENT_TEAM_JSON, sid);
    }

    public static String getCurrentProjects() {
        return sp.getString(CURRENT_TEAM_PROJECT, "");
    }

    public static void saveCurrentProjects(String sid) {
        sp.saveString(CURRENT_TEAM_PROJECT, sid);
    }


    public static void saveLoginResult(String account) {
        sp.saveString(USER_ACCOUNT, account);
    }

    public static void saveUserPwd(String password) {
        sp.saveString(USER_PWD, password);
    }

    public static String getLoginResult() {
        return sp.getString(USER_ACCOUNT, "");
    }

    public static String getUserPwd() {
        return sp.getString(USER_PWD, "");
    }

    public static void saveUsrId(String userId) {
        sp.saveString(USER_ID, userId);
    }

    public static void delUserAccount() {
        sp.delString(USER_ACCOUNT);
    }

    public static void delPwd() {
        sp.delString(USER_PWD);
    }

    public static void delUserMsg() {
        sp.delString(USER_MSG);
    }

    public static void delUserId() {
        sp.delString(USER_ID);
    }

    public static void saveUserMsg(String userMsg) {
        sp.saveString(USER_MSG, userMsg);
    }

    public static String getUserMsg() {
        String userMsg = sp.getString(USER_MSG, "");
        return userMsg;
    }

    public static String getUserId() {
        return sp.getString(USER_ID, "");
    }

    static class SharedPreferenceUtils {
        public SharedPreferences.Editor mEditor;
        public SharedPreferences mSharedPreferences;

        public SharedPreferenceUtils() {
            mSharedPreferences = BaseApplication.app.getSharedPreferences(BaseApplication.app.getPackageName(), Context.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }

        public void delString(String key) {
            mEditor.remove(key).commit();
        }

        public void delBoolean(String key) {
            mEditor.remove(key).commit();
        }

        public void delInt(String key) {
            mEditor.remove(key).commit();
        }

        public void saveBoolean(String key, Boolean value) {
            mEditor.putBoolean(key, value).commit();
        }

        public boolean getBoolean(String key, Boolean defValue) {
            return mSharedPreferences.getBoolean(key, defValue);
        }

        public void saveInt(String key, int value) {
            mEditor.putInt(key, value).commit();
        }

        public int getInt(String key, int defValue) {
            return mSharedPreferences.getInt(key, defValue);
        }

        public void saveString(String key, String value) {
            mEditor.putString(key, value).commit();
        }

        public String getString(String key, String defValue) {
            return mSharedPreferences.getString(key, defValue);
        }

        public void saveLong(String key, long value) {
            mEditor.putLong(key, value).commit();
        }

        public long getLong(String key, long defValue) {
            return mSharedPreferences.getLong(key, defValue);
        }
    }
}
