package com.example.balajiproperty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.amazonaws.amplify.generated.graphql.CreatePropertiesMutation;
import com.amazonaws.amplify.generated.graphql.ListPropertiessQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloCanceledException;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.exception.ApolloHttpException;
import com.apollographql.apollo.exception.ApolloNetworkException;
import com.apollographql.apollo.exception.ApolloParseException;
import com.example.balajiproperty.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import javax.annotation.Nonnull;

import type.CreatePropertiesInput;
import type.Status;

public class MainActivity extends AppCompatActivity {

    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runMutation();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void runMutation() {
        CreatePropertiesInput createPropertiesInput = CreatePropertiesInput.builder()
                .address("Dummy")
                .flatName("Dummy")
                .tenantName("Dummy")
                .status(Status.VACANT)
                .electricityPerUnitCost(9.5f)
                .previousMonthElectricMeterReading(150)
                .currentMonthElectricMeterReading(200)
                .waterCost(340f)
                .monthlyRent("15000")
                .owner("Rajender")
                .createdAt("2020-04-11T00:00:000Z")
                .updatedAt("2020-04-11T00:00:000Z")
                .build();

        mAWSAppSyncClient.mutate(CreatePropertiesMutation.builder().input(createPropertiesInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreatePropertiesMutation.Data> mutationCallback = new GraphQLCall.Callback<CreatePropertiesMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreatePropertiesMutation.Data> response) {
            Log.i("Results", "Added Note");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }

        @Override
        public void onCanceledError(@Nonnull ApolloCanceledException e) {
            super.onCanceledError(e);
        }

        @Override
        public void onParseError(@Nonnull ApolloParseException e) {
            super.onParseError(e);
        }

        @Override
        public void onHttpError(@Nonnull ApolloHttpException e) {
            super.onHttpError(e);
        }

        @Override
        public void onStatusEvent(@Nonnull GraphQLCall.StatusEvent event) {
            super.onStatusEvent(event);
        }

        @Override
        public void onNetworkError(@Nonnull ApolloNetworkException e) {
            super.onNetworkError(e);
        }

    };

    public void runQuery() {
        mAWSAppSyncClient.query(ListPropertiessQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(notesCallback);
    }

    private GraphQLCall.Callback<ListPropertiessQuery.Data> notesCallback = new GraphQLCall.Callback<ListPropertiessQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListPropertiessQuery.Data> response) {
            Log.i("Results", response.data().listPropertiess().items().toString());
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };
}