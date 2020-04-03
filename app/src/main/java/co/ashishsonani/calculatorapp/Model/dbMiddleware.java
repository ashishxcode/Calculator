package co.ashishsonani.calculatorapp.Model;

import android.provider.Settings;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class dbMiddleware {
    private String inputExpr;
    private String result;
    private String calcType;
    private String timeStored;
    private String deviceID;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy 'at' h:mm a", Locale.getDefault());


    public dbMiddleware() {

    }

    public dbMiddleware(String _deviceID, String _inputExpr, String _result, String _calcType ) {
        deviceID = _deviceID;
        inputExpr = _inputExpr;
        result = _result;
        calcType = _calcType;
        timeStored = sdf.format(new Date());
    }

    public String getDeviceID() {
        return deviceID;
    }
    public String getInputExpr() {
        return inputExpr;
    }

    public String getResult() {
        return result;
    }

    public String getCalcType() {
        return calcType;
    }

    public String getTimeStored() {
        return timeStored;
    }

    public void writeDB() {
        FirebaseFirestore database;
        //Firebase
        database = FirebaseFirestore.getInstance();
        CollectionReference Histories = database.collection(deviceID);
        DocumentReference History = Histories.document(timeStored.toString());
        History.set(this);
    }

}
