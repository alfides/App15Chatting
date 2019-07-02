package example.app15chatting.util;

import android.app.Activity;
import android.widget.Toast;

public class EndToast {

	///Field
	private Activity activity;
	// 다시 취소버튼 누르는 시간
	private long re_PressLimitTime = 2000;
	private long firstPressedTime = 0;

	// Constructor
	private EndToast(){
	}
	public EndToast(Activity activity) {
		this.activity = activity;
	}

	///Method
	public void showEndToast(String message){

		long tempTime = System.currentTimeMillis();
		long re_PressIntervalTime = tempTime - firstPressedTime;

		if( 0<= re_PressLimitTime && re_PressLimitTime >= re_PressIntervalTime){

			activity.finish();

		}else{

			firstPressedTime = tempTime;
			Toast.makeText( 	activity, message,Toast.LENGTH_SHORT).show();

		}
	}

}//end of class

