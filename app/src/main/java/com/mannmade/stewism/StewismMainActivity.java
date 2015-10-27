package com.mannmade.stewism;

import android.app.Activity;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class StewismMainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    static boolean offStewList = false;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stewism_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            default:
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.stewism_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //if youre on the stew list, then you want the back button to act normally and exit the app, else go back to main list
        if(!offStewList){
            finish();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(0))
                    .commit();
            offStewList = false;
        }
    }

    protected static class ViewHolderItem {
        TextView textViewItem;
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_stewism_main, container, false);
            final String[] terms = getResources().getStringArray(R.array.terms_array);
            final ListView stewismList = (ListView) rootView.findViewById(R.id.stewism_list);
            stewismList.setAdapter(new ListAdapter() {
                @Override
                public boolean areAllItemsEnabled() {
                    return true;
                }

                @Override
                public boolean isEnabled(int i) {
                    return true;
                }

                @Override
                public void registerDataSetObserver(DataSetObserver dataSetObserver) {

                }

                @Override
                public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

                }

                @Override
                public int getCount() {
                    return terms.length;
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public boolean hasStableIds() {
                    return false;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    //Use view holder for getView function of all lists for smoother scrolling
                    ViewHolderItem viewholder;
                    if (view == null) {
                        //Inflate the view whenever it is null (this will happen for all visible list items on initial display of screen
                        view = inflater.inflate(R.layout.listitem_term, viewGroup, false);
                        //only create view holder when view is null
                        viewholder = new ViewHolderItem();
                        viewholder.textViewItem = (TextView) view.findViewById(R.id.stewism_textview_item);
                        viewholder.textViewItem.setText(terms[i]);
                        view.setTag(viewholder);
                    } else {
                        //Any recycled views (when you scroll past end of screen) will borrow the existing layout from the viewholder and will need to have their data set
                        viewholder = (ViewHolderItem) view.getTag();
                        viewholder.textViewItem.setText(terms[i]);
                    }
                    return view;
                }

                @Override
                public int getItemViewType(int i) {
                    return 1;
                }

                @Override
                public int getViewTypeCount() {
                    return 1;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }
            });
            stewismList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Fragment fragment = new DefinitionFragment();
                    Bundle indexBundle = new Bundle();
                    indexBundle.putInt("stewIndex", position);
                    fragment.setArguments(indexBundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.stewism_main_layout, fragment)
                            .commit();
                    stewismList.setEnabled(false);
                    offStewList = true;
                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((StewismMainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class DefinitionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private final String ARG_SECTION_NUMBER = "section_number";

        public DefinitionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int index = getArguments().getInt("stewIndex");
            final String[] definitions = getResources().getStringArray(R.array.definitions_array);
            View rootView = inflater.inflate(R.layout.fragment_stew_definition, container, false);
            TextView definitionText = (TextView) rootView.findViewById(R.id.definition_textview);
            definitionText.setText(definitions[index]);
            return rootView;
        }
    }
}
