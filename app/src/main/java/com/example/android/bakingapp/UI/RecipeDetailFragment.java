package com.example.android.bakingapp.UI;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int INGREDIENT_REQUEST_CODE = 222;
    private static final int STEP_REQUEST_CODE = 333;

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private IngredientAdapter mIngredientAdapter;
    private StepsAdapter mStepsAdapter;

    private RecyclerView mIngredientView;
    private RecyclerView mStepView;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(int param1,String param2) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mIngredientAdapter = new IngredientAdapter(getContext());
        mStepsAdapter = new StepsAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        mIngredientView = (RecyclerView) rootView.findViewById(R.id.ingredient_list);
        mStepView = (RecyclerView) rootView.findViewById(R.id.recipe_step_list);
        mIngredientView.setAdapter(mIngredientAdapter);
        mStepView.setAdapter(mStepsAdapter);

        mIngredientView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mStepView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        getLoaderManager().initLoader(INGREDIENT_REQUEST_CODE,null,this).forceLoad();
        getLoaderManager().initLoader(STEP_REQUEST_CODE,null,this).forceLoad();
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getContext());
        switch (id){
            case INGREDIENT_REQUEST_CODE:
                cursorLoader.setUri(RecipeContract.IngredientEntry.CONTENT_URI);
                cursorLoader.setSelection(RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + "=?");
                break;
            case STEP_REQUEST_CODE:
                cursorLoader.setUri(RecipeContract.StepEntry.CONTENT_URI);
                cursorLoader.setSelection(RecipeContract.StepEntry.COLUMN_RECIPE_ID + "=?");
                break;
            default:
        }
        cursorLoader.setProjection(null);
        cursorLoader.setSelectionArgs(new String[]{String.valueOf(mParam1)});
        cursorLoader.setSortOrder(null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       if(data.getColumnCount() == 5){
           mIngredientAdapter.swapData(data);
       } else if(data.getColumnCount() == 7){
           mStepsAdapter.swapData(data);
       }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mIngredientAdapter.swapData(null);
        mStepsAdapter.swapData(null);
    }
}
