package ezequiel.whatthefridge.CustomViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

/**
 * Botón que inicia una actividad
 * Boton que se utilizara para toda la aplicacion, se le setea un intent
 *
 * @author ezequiel
 */
public class CustomButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {

    private Intent intent;
    private Bundle bundle;

    public CustomButton(Context context) {
        super(context);
        setUp();
    }

    public CustomButton(Context context, AttributeSet attr) {
        super(context, attr);
        setUp();
    }

    private void setUp() {
        setOnClickListener(this);
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void onClick(View v) {
        if (intent != null) {
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            getContext().startActivity(intent);
        }
    }
}
