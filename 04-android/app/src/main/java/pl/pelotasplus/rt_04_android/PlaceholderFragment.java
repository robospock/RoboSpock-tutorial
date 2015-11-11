package pl.pelotasplus.rt_04_android;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alek on 26/09/14.
 */
public class PlaceholderFragment extends Fragment {
    private Button toastButton, activityButton, textButton;
    private TextView textView;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        toastButton = (Button) rootView.findViewById(R.id.toastButton);
        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Test toast!", Toast.LENGTH_SHORT).show();
            }
        });

        activityButton = (Button) rootView.findViewById(R.id.activityButton);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://robospock.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getActivity().startActivity(intent);
            }
        });

        textView = (TextView) rootView.findViewById(R.id.textView);

        textButton = (Button) rootView.findViewById(R.id.textButton);
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("RoboSpock!");
            }
        });

        return rootView;
    }

    public void setLabel(String label) {
        textView.setText(label);
    }
}
