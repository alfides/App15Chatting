package example.app15chatting.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class EndAlertDialog {

	///Field
	// 경고창을 추상화한 Builder :: Inner Static Member Class
	private AlertDialog.Builder alertDialogBuilder;
	private Activity activity;

	///Constructor
	private EndAlertDialog(){
	}
	public EndAlertDialog(Activity activity){
		this.activity = activity;
	}

	///Method
	public void showEndDialog(){

		// 경고창을 추상화한 Builder :: Inner Static Member Class
		alertDialogBuilder = new AlertDialog.Builder(activity);

		// AlertDialog 상태값 SET
		alertDialogBuilder.setTitle("종료확인");
		alertDialogBuilder.setMessage("종료하시겠습니까?");

		//==> alert 창 이후 취소버튼 불가
		alertDialogBuilder.setCancelable(false);

		alertDialogBuilder.setPositiveButton(	"예" ,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.finish();
					}
				});

		alertDialogBuilder.setNegativeButton(	"아니요" ,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				} );

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void showEndDialog(String title , String message ){

		// 경고창을 추상화한 Builder :: Inner Static Member Class
		alertDialogBuilder = new AlertDialog.Builder(activity);

		// AlertDialog 상태값 SET
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(message);

		//==> alert 창 이후 취소버튼 불가
		alertDialogBuilder.setCancelable(false);

		alertDialogBuilder.setPositiveButton(	"확인" ,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						activity.finish();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void showEndDialogToActivity(String title , String message , final Class cls ){

		// 경고창을 추상화한 Builder :: Inner Static Member Class
		alertDialogBuilder = new AlertDialog.Builder(activity);

		// AlertDialog 상태값 SET
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(message);

		//==> alert 창 이후 취소버튼 불가
		alertDialogBuilder.setCancelable(false);

		alertDialogBuilder.setPositiveButton(	"확인" ,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// :: 다른 컴포넌트로 이동 위해
						//    컴포넌트를 생산할수 있는 Class 정보를
						//    Intent 생성자를 통해전달 intent instance 생성
						Intent intent = new Intent(activity, cls);

						// :: intent 객체가 갖는 정보의 컴포넌트 호출
						activity.startActivity(intent);

						// 현 Activity 종료
						activity.finish();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}//end of class