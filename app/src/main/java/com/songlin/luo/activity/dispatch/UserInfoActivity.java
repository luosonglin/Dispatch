package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
	private TextView tvUsername;
	private TextView tvGender;
	private TextView tvAge;
	private TextView tvPhone;
	private TextView tvEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_activity);
		initViews();
		Intent intent = getIntent();
		displayUserInfo(intent);
	}

	private void initViews() {
		tvUsername = (TextView) findViewById(R.id.usr_info_username);
		tvGender   = (TextView) findViewById(R.id.usr_info_gender);
		tvAge      = (TextView) findViewById(R.id.usr_info_age);
		tvPhone    = (TextView) findViewById(R.id.usr_info_phone);
		tvEmail    = (TextView) findViewById(R.id.usr_info_email);
	}
	
	private void displayUserInfo(Intent intent) {
		String username = intent.getStringExtra("username");
		String gender   = intent.getStringExtra("gender");
		int age         = intent.getIntExtra("age", -1);
		String phone    = intent.getStringExtra("phone");
		String email    = intent.getStringExtra("email");
		
		tvUsername.setText(username);
		tvGender.setText(gender);
		tvAge.setText(String.valueOf(age));
		tvPhone.setText(phone);
		tvEmail.setText(email);
	}

}
