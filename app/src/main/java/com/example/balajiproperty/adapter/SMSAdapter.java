package com.example.balajiproperty.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balajiproperty.R;
import com.example.balajiproperty.model.SMSModel;
import com.example.balajiproperty.ui.main.notifications.NotificationsFragment;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.SMSViewHolder> {

    private Context mContext;
    private CopyOnWriteArrayList<SMSModel> mNavigatorList;
    private NotificationsFragment.NavigatorListener mListener;


    /**
     * Constructor for our adapter class
     */
    public SMSAdapter(Context context, CopyOnWriteArrayList<SMSModel> navigatorList, NotificationsFragment.NavigatorListener mListener) {
        this.mContext = context;
        this.mNavigatorList = navigatorList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public SMSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sms_detail, parent, false);
        return new SMSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SMSViewHolder holder, int position) {
        SMSModel navigator = mNavigatorList.get(position);
        holder.mNavigatorNameTextView.setText(navigator.getNavigatorName());
        holder.checkBox.setChecked(navigator.getToSend());
    }

    @Override
    public int getItemCount() {
        return mNavigatorList.size();
    }

    public CopyOnWriteArrayList<SMSModel> getSMSList() {
        return  mNavigatorList;
    }

    public class SMSViewHolder extends RecyclerView.ViewHolder {
        private EditText mNavigatorNameTextView;
        private CheckBox checkBox;

        public SMSViewHolder(@NonNull View itemView) {
            super(itemView);

            mNavigatorNameTextView = itemView.findViewById(R.id.smsDetail);
            checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            Log.e("", "Clicked on position" + getAdapterPosition());
                            mNavigatorList.get(getAdapterPosition()).setToSend(isChecked);
                        }
                    }
            );
            mNavigatorNameTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //mNavigatorList.get(getAdapterPosition()).setTextToSend(mNavigatorNameTextView.getText().toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mNavigatorList.get(getAdapterPosition()).setTextToSend(mNavigatorNameTextView.getText().toString());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onNavigatorSelected(mNavigatorList.get(getAdapterPosition()), itemView);
                }
            });
        }
    }

    /**
     * The interface that receives onClick listener.
     */
    public interface NavigatorAdapterListener {
        void onNavigatorSelected(SMSModel navigator, View view);
    }
}
