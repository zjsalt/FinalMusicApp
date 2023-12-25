package tdtu.report.Database;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_LOGGED_IN_USER_EMAIL = "loggedInUserEmail";

    private SharedPreferences preferences;

    private static AppPreferences instance;

    private AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context);
        }
        return instance;
    }

    public void setLoggedInUserEmail(String email) {
        preferences.edit().putString(KEY_LOGGED_IN_USER_EMAIL, email).apply();
    }

    public String getLoggedInUserEmail() {
        return preferences.getString(KEY_LOGGED_IN_USER_EMAIL, "");
    }
}
