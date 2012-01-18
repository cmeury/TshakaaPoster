package ch.wurmlo.android.tshakaa;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserPrefs {
	private static final String PREFS_NAME = "tshakaa1";
	
    private static final String PREFS_USERID_KEY = "userId";
    private static final String PREFS_USERID_DEFAULT = "";
    
    private SharedPreferences prefs;
    
    public UserPrefs(Context ctx) {
    	this.prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

	public String getUserId() {
	  	return prefs.getString(PREFS_USERID_KEY, PREFS_USERID_DEFAULT);
	}
    
    public void setUserId(String userId) {
    	SharedPreferences.Editor editor = prefs.edit();
    	editor.putString(PREFS_USERID_KEY, userId);
    	editor.commit();
    	Log.i(UserPrefs.class.toString(), "User ID " + userId + " stored in preferences");
    }
}
