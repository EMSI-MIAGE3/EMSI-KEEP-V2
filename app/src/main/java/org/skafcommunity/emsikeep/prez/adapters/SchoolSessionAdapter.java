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
import org.skafcommunity.emsikeep.dao.ProfessorDAO;
import org.skafcommunity.emsikeep.dao.SchoolClassDAO;
import org.skafcommunity.emsikeep.dao.filtersListners.EntityListner;
import org.skafcommunity.emsikeep.models.Professor;
import org.skafcommunity.emsikeep.models.SchoolClass;
import org.skafcommunity.emsikeep.models.SchoolSession;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class SchoolSessionAdapter extends ArrayAdapter<SchoolSession> {

    private Context ssContext;
    private List<SchoolSession> schoolSessions;

    public SchoolSessionAdapter(@NonNull Context context, @NonNull List<SchoolSession> objects) {
        super(context, 0, objects);
        this.schoolSessions = objects;
        this.ssContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(ssContext).inflate(R.layout.schoolsession_adapter,parent,false);

        try {
            final SchoolSession schoolSession = schoolSessions.get(position);

            final TextView subjectView = (TextView) listItem.findViewById(R.id.subject);
            subjectView.setText(schoolSession.getSchoolSubject());

            final TextView profView = (TextView) listItem.findViewById(R.id.teacher);
            ProfessorDAO.get(schoolSession.getProfessorID(), new EntityListner<Professor>() {
                @Override
                public void doStuff(Professor professor) {
                    profView.setText("Pr. " +professor.getFirstName() + " " + professor.getLastName());
                }
            });

            SchoolClassDAO.get(schoolSession.getSchoolClassID(), new EntityListner<SchoolClass>() {
                @Override
                public void doStuff(SchoolClass schoolClass) {
                    subjectView.setText(schoolSession.getSchoolSubject() + " (" +schoolClass.getName()+")");
                }
            });

            TextView roomView =  (TextView) listItem.findViewById(R.id.room);
            roomView.setText(schoolSession.getClassroom());

            TextView periodView =  (TextView) listItem.findViewById(R.id.time);
            periodView.setText(schoolSession.getStartingTime().toString() + " - " +schoolSession.getEndingTime().toString());

            TextView dateView =  (TextView) listItem.findViewById(R.id.date);
            dateView.setText(schoolSession.getStartingDate().toString());

            TextView countView =  (TextView) listItem.findViewById(R.id.seancesCount);
            countView.setText("Nombre de Seance : " + schoolSession.getSeancesCount());


            ImageButton removeBtn = (ImageButton) listItem.findViewById(R.id.deleteButton);
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("DELETE_CLASS", schoolSession.getId());
//                    FireStoreDAO.deleteDocument("SchoolSessions", schoolSession.getId(), new EntityListner<Object>() {
//                        @Override
//                        public void doStuff(Object o) {
//                            if((Boolean) o){
//                                Toast.makeText(getContext(), ssContext.getString(R.string.deleted),(int)6).show();
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
//                    FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//                    ManageSeanceFragment fragment = new ManageSeanceFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("session", schoolSession);
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
