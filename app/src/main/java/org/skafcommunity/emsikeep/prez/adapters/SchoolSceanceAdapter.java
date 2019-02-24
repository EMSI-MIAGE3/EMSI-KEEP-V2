package org.skafcommunity.emsikeep.prez.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.skafcommunity.emsikeep.R;
import org.skafcommunity.emsikeep.access.firebase.firestore.FireStoreDAO;
import org.skafcommunity.emsikeep.dao.ProfessorDAO;
import org.skafcommunity.emsikeep.dao.SchoolClassDAO;
import org.skafcommunity.emsikeep.dao.filtersListners.EntityListner;
import org.skafcommunity.emsikeep.models.Professor;
import org.skafcommunity.emsikeep.models.SchoolClass;
import org.skafcommunity.emsikeep.models.SchoolSeance;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class SchoolSceanceAdapter extends ArrayAdapter<SchoolSeance> {

    private Context ssContext;
    private List<SchoolSeance> schoolSeances;
    
    
    public SchoolSceanceAdapter(@NonNull Context context, @NonNull List<SchoolSeance> objects) {
        super(context, 0, objects);
        this.schoolSeances = objects;
        this.ssContext = context;
        
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(ssContext).inflate(R.layout.schoolseance_adapter,parent,false);

        try {
            final SchoolSeance schoolSeance = schoolSeances.get(position);

            final TextView subjectView = (TextView) listItem.findViewById(R.id.subject);
            subjectView.setText(schoolSeance.getSchoolSubject());

            final TextView profView = (TextView) listItem.findViewById(R.id.teacher);
            ProfessorDAO.get(schoolSeance.getProfessorID(), new EntityListner<Professor>() {
                @Override
                public void doStuff(Professor professor) {
                    profView.setText("Pr. " +professor.getFirstName() + " " + professor.getLastName());
                }
            });

            SchoolClassDAO.get(schoolSeance.getSchoolClassID(), new EntityListner<SchoolClass>() {
                @Override
                public void doStuff(SchoolClass schoolClass) {
                    subjectView.setText(schoolSeance.getSchoolSubject() + " (" +schoolClass.getName()+")");
                }
            });

            TextView roomView =  (TextView) listItem.findViewById(R.id.room);
            roomView.setText(schoolSeance.getClassroom());

            TextView periodView =  (TextView) listItem.findViewById(R.id.time);
            periodView.setText(schoolSeance.getStartingTime().toString() + " - " +schoolSeance.getEndingTime().toString());

            TextView dateView =  (TextView) listItem.findViewById(R.id.date);
            dateView.setText(schoolSeance.getDate());



            ImageButton removeBtn = (ImageButton) listItem.findViewById(R.id.deleteButton);
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("DELETE_CLASS", schoolSeance.getId());
//                    FireStoreDAO.deleteDocument("SchoolSeances", schoolSeance.getId(), new EntityListner<Object>() {
//                        @Override
//                        public void doStuff(Object o) {
//                            if((Boolean) o){
//                                Toast.makeText(getContext(),"Supprimee",(int)6).show();
//                                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//                                manager.beginTransaction().replace(R.id.main_content, new ManageSessionFragment()).commit();
//                            }
//                            else {
//                                Toast.makeText(getContext(), ssContext.getString(R.string.not_deleted),(int)6).show();
//                            }
//                        }
//                    });

                }
            });

            ImageButton seancesBtn = (ImageButton) listItem.findViewById(R.id.seanceButton);
            seancesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listItem;
    }
}
