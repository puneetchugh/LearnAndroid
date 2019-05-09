package chugh.puneet.com.bluetoothclient;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static chugh.puneet.com.bluetoothclient.DeviceDetails.EXTRAS_DEVICE_ADDRESS;
import static chugh.puneet.com.bluetoothclient.DeviceDetails.EXTRAS_DEVICE_NAME;

public class MainActivity extends AppCompatActivity {

    //These constants are any constants over 0
    final static int PERM_ACCESS_COARSE_LOC = 101;
    final static int REQ_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    final static String BT_NAME = "bt_name";
    final static String BT_ADDR = "bt_addr";
    private BluetoothAdapter mBluetoothAdapter;
    final static String MY_BLUETOOTH = "my_bluetooth";
    private LeDeviceListAdapter mLeDeviceListAdapter;
    ListView listView;
    Button button;
    private Handler mHandler;
    private boolean mScanning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.news_item_list_view);
        button = findViewById(R.id.button);
        mHandler = new Handler();
        //scanBluetooth();

        mLeDeviceListAdapter = new LeDeviceListAdapter();
        listView.setAdapter(mLeDeviceListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        scanBluetooth();
    }

    private void scanBluetooth(){
        Log.e(MY_BLUETOOTH, "scanBluetooth() called..");
        if(canReqPerm() && needToReq())
            requestPermission();

        Log.e(MY_BLUETOOTH, "moving ");
        //Already requested user permission once, if user didn't grant, then don't ask again,
        //unless user tries the same operation
        if(canReqPerm() && needToReq()){
            finish();
            return;
        }

        checkBTAdapter();
        listView.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        scanLeDevice(true);
    }

    private boolean needToReq(){
        Log.e(MY_BLUETOOTH, "neetoReq() called.....");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.w(MY_BLUETOOTH, "Location access not granted!");
            //if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
            return true;
            //return false;
        }
        return false;
    }

    private boolean canReqPerm(){
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    private void requestPermission(){
        Log.e(MY_BLUETOOTH, "requestPermission() called...");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                PERM_ACCESS_COARSE_LOC);
    }

    private void checkBTAdapter(){
        Log.e(MY_BLUETOOTH, "checkBTAdapter() called....");
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQ_ENABLE_BT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQ_ENABLE_BT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQ_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERM_ACCESS_COARSE_LOC: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(MY_BLUETOOTH, "Permission Granted...");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void scanLeDevice(final boolean enable) {
        final BluetoothLeScanner bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothLeScanner.stopScan(mLeScanCallBack);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothLeScanner.startScan(mLeScanCallBack);
        } else {
            mScanning = false;
            bluetoothLeScanner.stopScan(mLeScanCallBack);
        }
        invalidateOptionsMenu();
    }


    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = MainActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public void addDevices(List<ScanResult> results){
            for(ScanResult result : results){
                mLeDevices.add(result.getDevice());
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());

            view.setOnClickListener((v)->{
                Intent intent = new Intent(getBaseContext(), DeviceDetails.class);
                intent.putExtra(EXTRAS_DEVICE_NAME, deviceName);
                intent.putExtra(EXTRAS_DEVICE_ADDRESS, device.getAddress());
                getBaseContext().startActivity(intent);
            });
            return view;
        }
    }

    private ScanCallback mLeScanCallBack = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, final ScanResult result) {
            super.onScanResult(callbackType, result);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addDevice(result.getDevice());
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDeviceListAdapter.addDevice(device);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
}
