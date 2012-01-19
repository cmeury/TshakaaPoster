package ch.wurmlo.android.tshakaa;

import java.io.IOException;
import java.net.URISyntaxException;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TshakaaPosterActivity extends Activity {
	
	private UserPrefs prefs;

	/** Called when the activity is first created. */
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String action = intent.getAction();

        final EditText urlText = (EditText) findViewById(R.id.urlText);
        
        if(prefs == null) {
    		this.prefs = new UserPrefs(getApplicationContext());
    		setContentView(R.layout.main);
    		buildUI();
        	String pageUrl = extras.getString("android.intent.extra.TEXT");
	        urlText.setText(pageUrl);
        	
        }
		if(action.equals(Intent.ACTION_SEND)) {
			String pageUrl = extras.getString("android.intent.extra.TEXT");
//			String pageTitle = extras.getString("android.intent.extra.SUBJECT");
	        urlText.setText(pageUrl);
			showDialog(R.layout.tagsdialog);
		}
    }

    private void buildUI() {
		// Linkify the help label
		final TextView helpLabel = (TextView) findViewById(R.id.helpLabel);
		helpLabel.setAutoLinkMask(Linkify.WEB_URLS);
		
		// Initialize the spinner
		ProgressBar spinner = (ProgressBar) findViewById(R.id.spinner);
		spinner.setVisibility(View.INVISIBLE);
		
		// Share Button handler
        final Button shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(R.layout.tagsdialog);
			}
		});
 
		// load user name from prefs
        final EditText userNameEditText = (EditText) findViewById(R.id.userNameText);
		userNameEditText.setText(prefs.getUserId());

		// Login button handler
        final Button saveUserIdButton = (Button) findViewById(R.id.saveUserIdButton);
        saveUserIdButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				prefs.setUserId(userNameEditText.getText().toString());
				showToaster(getString(R.string.toast_userIdHasBeenSaved), 2);
			}
		});
    }
    
    private void postUrl(String tags) {
    	EditText urlText = (EditText) findViewById(R.id.urlText);
		String url = urlText.getText().toString();
		String title;
		try {
			title = Util.getTitle(url);
		} catch (Exception e) {
			title = "unknown title";
			Log.e(TshakaaPosterActivity.class.toString(), e.getMessage());
		}
		ProgressBar spinner = (ProgressBar) findViewById(R.id.spinner);
		spinner.setVisibility(View.VISIBLE);
		try {
			String postURL = PortalCommunication.postURL(prefs.getUserId(), url, title, tags);
			Log.d(TshakaaPosterActivity.class.toString(), postURL);
			showToaster(getString(R.string.toast_postingUrlSuccessful), 2);
		} catch (IOException e) {
			Log.e(TshakaaPosterActivity.class.toString(),
					"Failed to post URL to server.", e);
			showToaster(getString(R.string.toast_postingUrlFailedNetwork), 2);
		} catch (URISyntaxException e) {
			Log.e(TshakaaPosterActivity.class.toString(),
					"Failed to post URL to server.", e);
			showToaster(getString(R.string.toast_postingUrlFailedSyntax), 2);
		} finally {
			spinner.setVisibility(View.INVISIBLE);
		}
    }

    @SuppressWarnings("unused")
	private void showNotification(String tickerText, String title, String message) {
		long when = System.currentTimeMillis();
		Notification notification = new Notification(android.R.drawable.ic_media_play, tickerText, when+3000);
		Context ctx = getApplicationContext();
		Intent intent =  new Intent(this, TshakaaPosterActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, Notification.FLAG_AUTO_CANCEL);
		notification.setLatestEventInfo(ctx, title, message, pendingIntent);
		
		NotificationManager systemService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		systemService.notify(1, notification);
    }
    
    private void showToaster(String message, int duration) {
		Context ctx = getApplicationContext();
		Toast toast = Toast.makeText(ctx, message, duration*1000);
		toast.show();
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog dialog = null;
    	switch(id) {
    	case R.layout.tagsdialog:
            Context context = this;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.tagsdialog);
            dialog.setTitle(R.string.tagsDialogTitle);
            final EditText tagsText = (EditText) dialog.findViewById(R.id.tagsText);
            
            Button dismissButton = (Button) dialog.findViewById(R.id.dialogPostButton);
            dismissButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					postUrl(tagsText.getText().toString());
					dismissDialog(R.layout.tagsdialog);
				}
			});
    	}
    	return dialog;
    }

}