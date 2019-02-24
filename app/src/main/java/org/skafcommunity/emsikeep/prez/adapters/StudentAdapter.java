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
import org.skafcommunity.emsikeep.models.Student;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StudentAdapter extends ArrayAdapter<Student> {

    private Context pContext;
    private List<Student> students;

    public StudentAdapter(@NonNull Context context, @NonNull List<Student> students) {
        super(context, 0, students);
        this.students = students;
        this.pContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(pContext).inflate(R.layout.student_adapter,parent,false);

        try {
            Student student = students.get(position);

            TextView name = (TextView) listItem.findViewById(R.id.student_fullname);
            name.setText(student.getFirstName() + " " + student.getLastName());

            TextView emailView = (TextView) listItem.findViewById(R.id.student_email);
            emailView.setText(student.getEmail());

            TextView phoneView = (TextView) listItem.findViewById(R.id.student_phone);
            phoneView.setText(student.getPhone());




            if(student!= null && student.getUrlPicture() != null && !student.getUrlPicture().equals("")){
                View finalListItem = listItem;
                FirebaseStorage.getInstance().getReference().child(student.getUrlPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
