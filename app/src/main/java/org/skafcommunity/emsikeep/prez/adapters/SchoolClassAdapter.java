package org.skafcommunity.emsikeep.prez.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.skafcommunity.emsikeep.R;
import org.skafcommunity.emsikeep.dao.SchoolClassDAO;
import org.skafcommunity.emsikeep.dao.filtersListners.EntityListner;
import org.skafcommunity.emsikeep.models.SchoolClass;
import org.skafcommunity.emsikeep.prez.fragments.ManageSchoolClassesFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class SchoolClassAdapter extends ArrayAdapter<SchoolClass> {
    private Context scContext;
    private List<SchoolClass> schoolClasses;

    public SchoolClassAdapter(@NonNull Context context, @NonNull List<SchoolClass> schoolClasses) {
        super(context, 0, schoolClasses);
        this.scContext = context;
        this.schoolClasses = schoolClasses;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(scContext).inflate(R.layout.schoolclass_adapter,parent,false);

        try {
            final SchoolClass schoolClass = schoolClasses.get(position);

            TextView name = (TextView) listItem.findViewById(R.id.school_class_title);
            name.setText(schoolClass.getName());

            TextView release = (TextView) listItem.findViewById(R.id.class_details);
            release.setText(" ( Niveau : " + schoolClass.getLevel() + " )");

            ImageButton removeBtn = (ImageButton) listItem.findViewById(R.id.deleteButton);
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DELETE_CLASS", schoolClass.getId());
                    SchoolClassDAO.delete(schoolClass.getId(), new EntityListner<Object>() {
                        @Override
                        public void doStuff(Object o) {
                            if((Boolean) o){
                                Toast.makeText(getContext(), "Supprimee",(int)6).show();
                                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                                manager.beginTransaction().replace(R.id.main_content, new ManageSchoolClassesFragment()).commit();
                            }
                            else {
                                Toast.makeText(getContext(), "non-Supprimee",(int)6).show();
                            }
                        }
                    });

                }
            });

            ImageButton sessionsBtn = (ImageButton) listItem.findViewById(R.id.sessionsButton);
            sessionsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("SESSIONS_CLASS", schoolClass.getId());
//                    FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//                    ManageSessionFragment fragment = new ManageSessionFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("class", schoolClass);
//                    fragment.setArguments(bundle);
//                    manager.beginTransaction().replace(R.id.main_content, fragment).commit();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listItem;
    }
}
