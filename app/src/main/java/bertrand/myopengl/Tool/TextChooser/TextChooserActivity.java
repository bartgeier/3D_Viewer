package bertrand.myopengl.Tool.TextChooser;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import bertrand.myopengl.R;

public class TextChooserActivity extends AppCompatActivity {
        public class UI{
                ActionBar actionBar;
        }
        private UI ui = new UI();
        private ArrayList<String> names;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_textchooser);
                setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
                ui.actionBar = getSupportActionBar();
                if (ui.actionBar != null) {
                        ui.actionBar.setDisplayHomeAsUpEnabled(true);
                        ui.actionBar.setDisplayShowHomeEnabled(true);
                }
                initExtras();
        }

        private void initExtras() {
                names = getIntent().getStringArrayListExtra("ArrayList_names");
                ui.actionBar.setTitle(getIntent().getStringExtra("Titel"));
                initRecyclerView();
        }

        private void initRecyclerView() {
                TextChooserRecyclerViewAdapter a = new TextChooserRecyclerViewAdapter(this,names);
                a.setOnClickListner(new TextChooserRecyclerViewAdapter.OnClickListner() {
                        @Override
                        public void onClick(int position) {
                                Bundle b = new Bundle();
                                b.putString("name",names.get(position));
                                b.putInt("position",position);
                                getIntent().putExtra("activity_textchooser",b);
                                setResult(Activity.RESULT_OK, getIntent());
                                finish();
                        }
                });
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setAdapter(a);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                case android.R.id.home:
                        finish();
                }
                return super.onOptionsItemSelected(item);
        }
}
