package cs160.dataLayer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
        result = findViewById(R.id.result);
        tokenResult = findViewById(R.id.public_token_result);

        Button button = findViewById(R.id.open_link);
        button.setOnClickListener(view -> {
            openLink();
        });
    }

    private void openLink() {
        LinkTokenRequester.getToken()
                .subscribe(this::onLinkTokenSuccess, this::onLinkTokenError);
    }


    private void onLinkTokenSuccess(String token){
        LinkTokenConfiguration linkTokenConfiguration = new LinkTokenConfiguration.Builder()
                .token("link-sandbox-19b38d14-56d7-4e0c-9036-8f95504e53f0")
                .build();
        linkAccountToPlaid.launch(linkTokenConfiguration);
    }

    private void onLinkTokenError(Throwable error) {
        if (error instanceof java.net.ConnectException) {
            Toast.makeText(
                    this,
                    "Please run `sh start_server.sh <client_id> <sandbox_secret>`",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}