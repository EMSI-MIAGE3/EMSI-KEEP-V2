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
import org.skafcommunity.emsikeep.models.Administrator;
import org.skafcommunity.emsikeep.models.Professor;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdministratorAdapter extends ArrayAdapter<Administrator> {

    private Context pContext;
    private List<Administrator> administrators;

    public AdministratorAdapter(@NonNull Context context, @NonNull List<Administrator> administrators) {
        super(context, 0, administrators);
        this.administrators = administrators;
        this.pContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(pContext).inflate(R.layout.admin_adapter,parent,false);

        try {
            Administrator administrator = administrators.get(position);

            TextView name = (TextView) listItem.findViewById(R.id.admin_fullname);
            name.setText(administrator.getFirstName() + " " + administrator.getLastName());

            TextView emailView = (TextView) listItem.findViewById(R.id.admin_email);
            emailView.setText(administrator.getEmail());

            TextView phoneView = (TextView) listItem.findViewById(R.id.admin_phone);
            phoneView.setText(administrator.getPhone());


            if(administrator!= null && administrator.getUrlPicture() != null && !"".equals(administrator.getUrlPicture())){
                View finalListItem = listItem;
                FirebaseStorage.getInstance().getReference().child(administrator.getUrlPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listItem;
    }
}
