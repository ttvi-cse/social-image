package com.hcmut.social.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hcmut.social.LoginManager;
import com.hcmut.social.R;
import com.hcmut.social.controller.controllerdata.CreateLocationRequestData;
import com.hcmut.social.controller.controllerdata.CreatePostRequestData;
import com.hcmut.social.controller.controllerdata.RequestData;
import com.hcmut.social.controller.controllerdata.ResponseData;
import com.hcmut.social.datacenter.DataCenter;
import com.hcmut.social.model.LocationModel;
import com.hcmut.social.utils.DialogUtil;
import com.hcmut.social.utils.FileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by John on 10/18/2016.
 */

public class PostActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_PATH = "e_path";

    private static final int ACTION_PICK_PLACE = 1;

    private static final int REQUEST_PLACE_PICKER = 1;

    private static final String TAG = PostActivity.class.getSimpleName();

    ImageButton mBackButton;
    ImageView mContentImageView;
    EditText mContentEditText;
    TextView mLocationTextView;
    TextView mPostTextView;

    private String imagePath;
    private DisplayImageOptions mOpts;

    LocationModel mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPostTextView = (TextView) findViewById(R.id.tv_post);
        mPostTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLocation == null) {
                    return;
                }

                showProgressDialog();
                CreateLocationRequestData requestData = new CreateLocationRequestData(
                        mLocation.place_id,
                        mLocation.name,
                        mLocation.address,
                        mLocation.phone
                );
                DataCenter.getInstance().doRequest(requestData);

            }
        });

        mContentImageView = (ImageView) findViewById(R.id.content_imageview);
        mContentEditText = (EditText) findViewById(R.id.content_edit_text);
        mLocationTextView = (TextView) findViewById(R.id.location_text);
        mLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(PostActivity.this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, REQUEST_PLACE_PICKER);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), PostActivity.this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(PostActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        adapter.notifyDataSetChanged();

        mOpts = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(6))
                .showImageOnLoading(R.drawable.img_no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        if (getIntent().getStringExtra(EXTRA_IMAGE_PATH) != null) {
            Uri uri = Uri.parse(getIntent().getStringExtra(EXTRA_IMAGE_PATH));
            imagePath = FileUtils.getPath(PostActivity.this, uri);
            ImageLoader.getInstance().displayImage(uri.toString(), mContentImageView, mOpts);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BEGIN_INCLUDE(activity_result)
        if (requestCode == REQUEST_PLACE_PICKER) {
            // This result is from the PlacePicker dialog.

            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(data, PostActivity.this);

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                String attribution = PlacePicker.getAttributions(data);
                if(attribution == null){
                    attribution = "";
                }

                // Print data to debug log
                Log.d(TAG, "Place selected: " + placeId + " (" + name.toString() + ")");

                mLocationTextView.setText(String.format("Location: %s", name));
                mLocation = new LocationModel(placeId, name.toString(), address.toString(), phone.toString());
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        // END_INCLUDE(activity_result)
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[] {
                RequestData.TYPE_CREATE_POST,
                RequestData.TYPE_CREATE_LOCATION,
                RequestData.TYPE_LIST_LOCATION
        };
    }

    @Override
    public void onLoadSuccessful(RequestData requestData, ResponseData responseData) {
        if(responseData.getError() != null) {
            onLoadFail(requestData, responseData);
            return;
        }

        hideProgressDialog();

        if (requestData.getType() == RequestData.TYPE_CREATE_POST) {
            finish();
        } else if (requestData.getType() == RequestData.TYPE_CREATE_LOCATION) {

            LocationModel resData = (LocationModel) responseData.getData();

            String content = mContentEditText.getText().toString();
            int userId = LoginManager.getInstance().getLoginModel().id;

            showProgressDialog();
//            if (showLocation) {
//
//            } else {
//
//            }
            CreatePostRequestData cpRequest = new CreatePostRequestData(String.valueOf(userId), imagePath, content, resData.id);
            DataCenter.getInstance().doRequest(cpRequest);

        } else if (requestData.getType() == RequestData.TYPE_LIST_LOCATION) {

        }
    }

    @Override
    public void onLoadFail(RequestData requestData, ResponseData responseData) {
        hideProgressDialog();
    }
}
