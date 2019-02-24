package org.skafcommunity.emsikeep.prez.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;

import org.skafcommunity.emsikeep.R;
import org.skafcommunity.emsikeep.models.Professor;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProfessorAdapter extends ArrayAdapter<Professor> {

    private Context pContext;
    private List<Professor> professors;

    public ProfessorAdapter(@NonNull Context context, @NonNull List<Professor> professors) {
        super(context, 0, professors);
        this.professors = professors;
        this.pContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(pContext).inflate(R.layout.professor_adapter,parent,false);

        try {
            Professor professor = professors.get(position);

            TextView name = (TextView) listItem.findViewById(R.id.prof_fullname);
            name.setText("Pr. " + professor.getFirstName() + " " + professor.getLastName());

            TextView emailView = (TextView) listItem.findViewById(R.id.prof_email);
            emailView.setText(professor.getEmail());

            TextView phoneView = (TextView) listItem.findViewById(R.id.prof_phone);
            phoneView.setText(professor.getPhone());

            MaterialButton sessionsButton =  listItem.findViewById(R.id.sessionsButton);
            sessionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//                    ManageSessionFragment fragment = new ManageSessionFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("professor", professor);
//                    fragment.setArguments(bundle);
//                    manager.beginTransaction().replace(R.id.main_content, fragment).commit();
                }
            });


            if(professor!= null && professor.getUrlPicture() != null &&  !professor.getUrlPicture().equals("")){
                View finalListItem = listItem;
                FirebaseStorage.getInstance().getReference().child(professor.getUrlPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageView imageView = (ImageView) finalListItem.findViewById(R.id.profil_img);
                        String imageURL = uri.toString();
                        Glide.with(getContext()).load(imageURL).into(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        ImageView imageView = (ImageView) finalListItem.findViewById(R.id.profil_img);
                        imageView.setImageResource(R.mipmap.ic_launcher_round);
                    }
                });
            } else {
                ImageView imageView = (ImageView) listItem.findViewById(R.id.profil_img);
                imageView.setImageResource(R.mipmap.ic_launcher_round);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listItem;
    }
}
