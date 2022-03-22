package cs160.dataLayer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.plaid.link.OpenPlaidLink;
import com.plaid.link.configuration.LinkTokenConfiguration;
import com.plaid.link.result.LinkAccount;
import com.plaid.link.result.LinkAccountSubtype;
import com.plaid.link.result.LinkError;
import com.plaid.link.result.LinkExit;
import com.plaid.link.result.LinkExitMetadata;
import com.plaid.link.result.LinkSuccess;
import com.plaid.link.result.LinkSuccessMetadata;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    private TextView tokenResult;

    private ActivityResultLauncher<LinkTokenConfiguration> linkAccountToPlaid
            = registerForActivityResult(
                    new OpenPlaidLink(),
            result -> {
                       if(result instanceof LinkSuccess){
                           Log.i("Plaid Succes", result.toString());
                           LinkSuccess success = (LinkSuccess) result;
                           String publicToken = success.getPublicToken();
                           LinkSuccessMetadata metadata = success.getMetadata();
                           for (LinkAccount account : success.getMetadata().getAccounts()) {
                               String accountId = account.getId();
                               String accountName = account.getName();
                               String accountMask = account.getMask();
                               LinkAccountSubtype accountSubtype = account.getSubtype();
                           }
                           String institutionId = metadata.getInstitution().getId();
                           String institutionName = metadata.getInstitution().getName();
                       }else{
                           Log.i("Plaid Exit", result.toString());
                           LinkExit exit = (LinkExit) result;

                           LinkError error = exit.getError();
                           String errorCode = String.valueOf(error.getErrorCode());

                           String errorMessage = error.getErrorMessage();
                           String displayMessage = error.getDisplayMessage();

                           LinkExitMetadata metadata = exit.getMetadata();
                           String institutionId = metadata.getInstitution().getId();
                           String institutionName = metadata.getInstitution().getName();
                           String linkSessionId = metadata.getLinkSessionId();
                           String requestId = metadata.getRequestId();
                       }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}