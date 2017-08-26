package bike.rapido.passenger.functions;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.TextView;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import bike.rapido.passenger.AppBaseConstants;
import bike.rapido.passenger.R;

/**
 * Created by myinnos on 24/08/17.
 */

public class Translator {

    public static void Translate(final String sourceText, final TextView textView) {

        final Handler textViewHandler = new Handler();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey(AppBaseConstants.TRANSLATION_API)
                        .build();
                Translate translate = options.getService();

                final Translation translation =
                        translate.translate(sourceText,
                                Translate.TranslateOption.targetLanguage(RememberPreferences.getLocalLanguage()));
                textViewHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textView != null) {
                            textView.setText(translation.getTranslatedText());
                        }
                    }
                });
                return null;
            }
        }.execute();
    }
}
