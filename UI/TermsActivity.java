package android.sgstudentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.sgstudentscheduler.Entity.TermEntity;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.ViewModel.TermViewModel;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.sgstudentscheduler.Util.Constants.EXTRA_TERM_END;
import static android.sgstudentscheduler.Util.Constants.EXTRA_TERM_NAME;
import static android.sgstudentscheduler.Util.Constants.EXTRA_TERM_START;


public class TermsActivity extends AppCompatActivity {

    public static final String EXTRA_TERM_ID = "android.sgstudentscheduler.EXTRA_TERM_ID";
    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;
    private TermViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TermAdapter mAdapter = new TermAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mViewModel.getAllTerms().observe(this, mAdapter::setTerms);

        mAdapter.setOnItemClickListener(term -> {
            Intent intent = new Intent(TermsActivity.this, TermDetails.class);
            intent.putExtra(EXTRA_TERM_ID, term.getTermId());
            intent.putExtra(EXTRA_TERM_NAME, term.getTermName());
            intent.putExtra(EXTRA_TERM_START, term.getTermStart());
            intent.putExtra(EXTRA_TERM_END, term.getTermEnd());
            startActivityForResult(intent, EDIT_TERM_REQUEST);
        });
    }


    public void addTerm(View view) {
        Intent intent = new Intent(TermsActivity.this, TermDetails.class);
        startActivityForResult(intent, ADD_TERM_REQUEST);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {
            TermEntity term = new TermEntity(
                    intent.getStringExtra(EXTRA_TERM_NAME),
                    intent.getStringExtra(EXTRA_TERM_START),
                    intent.getStringExtra(EXTRA_TERM_END));
            mViewModel.insertTerm(term);
        } else if (requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
            int mTermId = intent.getIntExtra(TermDetails.EXTRA_TERM_ID, -1);
            TermEntity term = new TermEntity(
                    intent.getStringExtra(EXTRA_TERM_NAME),
                    intent.getStringExtra(EXTRA_TERM_START),
                    intent.getStringExtra(EXTRA_TERM_END));
            term.setTermId(mTermId);
            mViewModel.updateTerm(term);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.populate_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.populate_data) {
            mViewModel.populateData();
            return true;
        } else if (id == R.id.clear_data) {
            mViewModel.clearData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}