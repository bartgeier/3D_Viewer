package bertrand.myopengl.Tool.TextChooser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bertrand.myopengl.R;

public class TextChooserRecyclerViewAdapter extends RecyclerView.Adapter<TextChooserRecyclerViewAdapter.ViewHolder>{
        private static final String TAG = "TextChosRecyViewAdapter";

        private ArrayList<String> exampleNames = new ArrayList<>();
        private Context context;


        public interface OnClickListner {
                public void onClick(final int position);
        }
        private OnClickListner onClickListner;
        private void call_onclick(final int position) {
                if(onClickListner != null) {
                        onClickListner.onClick(position);
                }
        }

        public void setOnClickListner(OnClickListner listner) {
                this.onClickListner = listner;
        }

        public TextChooserRecyclerViewAdapter(Context context, ArrayList<String> exampleNames) {
                this.exampleNames = exampleNames;
                this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
                Log.d(TAG,"onBindViewHolder called.");
                holder.exampleName.setText(exampleNames.get(position));

                holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Log.d(TAG, "onClick: clicked on:" +exampleNames.get(position));
                                Toast.makeText(context, exampleNames.get(position), Toast.LENGTH_SHORT).show();
                                call_onclick(position);

                        }
                });

        }

        @Override
        public int getItemCount() {
                return exampleNames.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
                TextView exampleName;
                ConstraintLayout parentLayout;
                public ViewHolder(View itemView) {
                        super(itemView);
                        exampleName = itemView.findViewById(R.id.example_name);
                        parentLayout = itemView.findViewById(R.id.parent_layout);

                }
        }
}
