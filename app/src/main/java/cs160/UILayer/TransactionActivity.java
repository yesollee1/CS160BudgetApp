package cs160.UILayer;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class TransactionActivity extends SingleFragmentActivity {

    private static final String EXTRA_TRANSACTION_ID = "cs160.UILayer.transaction_id";

    // This method is called to start a new Transaction Activity
    public static Intent newIntent(Context packageContext, UUID transactionId) {
        Intent intent = new Intent(packageContext, TransactionActivity.class);
        intent.putExtra(EXTRA_TRANSACTION_ID, transactionId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID transactionId = (UUID) getIntent().getSerializableExtra(EXTRA_TRANSACTION_ID);
        return TransactionFragment.newInstance(transactionId);
    }
}