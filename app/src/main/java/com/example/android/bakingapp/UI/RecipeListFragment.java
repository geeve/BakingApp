package com.example.android.bakingapp.UI;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeContract;
import com.example.android.bakingapp.data.RecipesIdlingResource;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Administrator
 */
public class RecipeListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOAD_REQUEST_CODE = 1;

    private RecipeListAdapter mRecipeAdapter;

    private RecyclerView mRecyclerView;

    private getIdleResourse getIdleResource;

    private RecipesIdlingResource mIdelingResource;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeListFragment.
     */
    public static RecipeListFragment newInstance() {

        return new RecipeListFragment();
    }

    public interface getIdleResourse{
        RecipesIdlingResource getIdleRes();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipeAdapter = new RecipeListAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_recipe_list_view);
        mRecyclerView.setAdapter(mRecipeAdapter);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null){
            getIdleResource = (getIdleResourse) context;
            mIdelingResource = getIdleResource.getIdleRes();
        }
        getLoaderManager().initLoader(LOAD_REQUEST_CODE,null,this).forceLoad();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(mIdelingResource != null){
            mIdelingResource.setIdleState(false);
        }
        return new CursorLoader(getContext(),
                RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecipeAdapter.swapData(data);
        if(mIdelingResource != null){
            mIdelingResource.setIdleState(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecipeAdapter.swapData(null);
    }


}
