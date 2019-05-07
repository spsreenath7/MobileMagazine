package com.wit.magazine.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wit.magazine.R;
import com.wit.magazine.models.Bookmark;
import com.wit.magazine.models.SharedArticle;

import static com.wit.magazine.activities.HomeActivity.app;

public class ArticlePageFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public Button buttonPurchase;
    public TextView price, title, author, contents;
    public View view;
    public DatabaseReference databaseBookmarkReference;
    public DatabaseReference databaseShareReference;
    Spinner spinner;
    EditText tag;
    String catogerySelected;


    private ArticlePageInteractionListener mListener;
    public ArticlePageFragment() {
        // Required empty public constructor
    }


    public static ArticlePageFragment newInstance(Bundle articleInfo) {
        ArticlePageFragment fragment = new ArticlePageFragment();
        fragment.setArguments(articleInfo);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseBookmarkReference =FirebaseDatabase.getInstance().getReference("Bookmarks")
                .child(app.fireBaseUser);
        databaseShareReference =FirebaseDatabase.getInstance().getReference("ArticleShares");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_articlepage, container, false);

        tag = (EditText)view.findViewById(R.id.editTextTag) ;

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.bookmarkCatogery,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner= view.findViewById(R.id.catogerySpinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        updateUI();

        return view;
    }

    private void updateUI() {
//        ((TextView)view.findViewById(R.id.textViewArticleTitle)).setText(getArguments().getString("Price"));
//        ((TextView)view.findViewById(R.id.textViewTitle)).setText(getArguments().getString("Title"));
//        ((TextView)view.findViewById(R.id.textAuthor)).setText(getArguments().getString("Source"));
    }

    public void buy(){
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("url", getArguments().getString("url"));
        ((Button)view.findViewById(R.id.buttonPurchase)).setClickable(false);

        Fragment fragment = ArticleWebFragment.newInstance(activityInfo);
//        getActivity().setTitle(R.string.editCoffeeLbl);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.articleContentFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void bookmark(){
        ((ImageView)view.findViewById(R.id.buttonBookmark)).setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_bookmark_full));
        ((ImageView)view.findViewById(R.id.buttonBookmark)).setClickable(false);
        String id, source, title, url, tagtext, catogery;

        id  = databaseBookmarkReference.push().getKey();
        source = getArguments().getString("Source");
        title = getArguments().getString("Title");
        url = getArguments().getString("url");

        tagtext= tag.getText().toString();
        catogery= catogerySelected;

        Bookmark bookmark = new Bookmark(id, source, title, url, tagtext, catogery);
        databaseBookmarkReference.child(id).setValue(bookmark);
        Toast.makeText(getActivity(),"Bookmark saved sucessfully !! ",Toast.LENGTH_LONG).show();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  CoffeeApi.attachListener(this);
        if (context instanceof ArticlePageInteractionListener) {
            mListener = (ArticlePageInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        //   CoffeeApi.detachListener();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        catogerySelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void share(String caption) {

        String id, source, title, url, userid, username, urlToImage;
        long sharedDate;

        id  = databaseShareReference.push().getKey();
        source = getArguments().getString("Source");
        title = getArguments().getString("Title");
        url = getArguments().getString("url");
        urlToImage = getArguments().getString("imageURL");

        userid = app.fireBaseUser;
        username = app.fireBaseUserName;
        sharedDate = System.currentTimeMillis();

        SharedArticle sharedArticle = new SharedArticle(id, title, caption, url,urlToImage, source, userid, username, sharedDate);
        databaseShareReference.child(id).setValue(sharedArticle);
        Toast.makeText(getActivity(),"Article shared sucessfully !! ",Toast.LENGTH_LONG).show();
    }

    public interface ArticlePageInteractionListener {
        void buy(View v);
        void bookmark(View v);
        void share(View v);
//        void  applyTexts(String caption);
    }
}
