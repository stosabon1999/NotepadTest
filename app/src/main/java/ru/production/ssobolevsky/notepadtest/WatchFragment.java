package ru.production.ssobolevsky.notepadtest;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WatchFragment extends Fragment {

    private TextView mTextViewTitle;
    private TextView mTextViewSubtitle;

    public WatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTextViewTitle = view.findViewById(R.id.tv_watch_title);
        mTextViewSubtitle = view.findViewById(R.id.tv_watch_subtitle);
        MyNote note = (getArguments().getParcelable(EditActivity.DATA));
        mTextViewTitle.setText(note.getTitle());
        mTextViewSubtitle.setText(note.getSubtitle());

    }

    public static final Fragment newInstance(Bundle bundle) {
        Fragment fragment = new WatchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }



}
