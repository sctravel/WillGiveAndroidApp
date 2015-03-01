package com.apps.willgiveAndroid;

import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.login.UserLogoutAsyncTask;
import com.apps.willgiveAndroid.user.RetrieveUserFavorateCharitiesAsyncTask;
import com.apps.willgiveAndroid.user.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfileFragment extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
        View view = inflater.inflate(R.layout.user_profile, container,false);
        
        TextView nameView = (TextView) view.findViewById(R.id.userName);
        ImageView userIconView = (ImageView) view.findViewById(R.id.userProfileIcon);
        User user = ( (WillGiveMainPageActivity) getActivity()).getUser();
        nameView.setText( "  "+user.getFirstName()+" "+user.getLastName() + "\n  Email: "+user.getEmail()); 
        
        ImageLoader.getInstance().displayImage(ServerUrls.HOST_URL+user.getImageIconUrl(), userIconView);
        
        Button logoutBtn = (Button) view.findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				logout();
			}
        });
        

    	
        return view;

    }
	
	private void logout() {
		AsyncTask<Context, Integer, Boolean> task = new UserLogoutAsyncTask( (WillGiveMainPageActivity) getActivity());
		task.execute(getActivity().getApplicationContext());
	}
}
