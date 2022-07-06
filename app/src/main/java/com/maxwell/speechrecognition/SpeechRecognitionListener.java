package com.maxwell.speechrecognition;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Maxwell on 14-Jan-18.
 */

final class SpeechRecognitionListener implements RecognitionListener {

    private final OnSpeechRecognitionListener listener;
    private final Context context;

    SpeechRecognitionListener(OnSpeechRecognitionListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    OnSpeechRecognitionListener getOnSpeechRecognitionListener() {
        return listener;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float v) {
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        this.listener.OnBufferReceived(bytes);
    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int i) {
        String errorMessage;
        int errorCode = -1;
        switch (i) {
            case SpeechRecognizer.ERROR_AUDIO:
                errorCode = SpeechRecognizer.ERROR_AUDIO;
                errorMessage = context.getString(R.string.error_audio);
                break;

            case SpeechRecognizer.ERROR_CLIENT:
                errorCode = SpeechRecognizer.ERROR_CLIENT;
                errorMessage = context.getString(R.string.error_client);
                break;

            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorCode = SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS;
                errorMessage = context.getString(R.string.error_permission);
                break;

            case SpeechRecognizer.ERROR_NETWORK:
                errorCode = SpeechRecognizer.ERROR_NETWORK;
                errorMessage = context.getString(R.string.error_network);
                break;

            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorCode = SpeechRecognizer.ERROR_NETWORK_TIMEOUT;
                errorMessage = context.getString(R.string.error_network);
                break;

            case SpeechRecognizer.ERROR_NO_MATCH:
                errorCode = SpeechRecognizer.ERROR_NO_MATCH;
                errorMessage = context.getString(R.string.error_no_match);
                break;

            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorCode = SpeechRecognizer.ERROR_RECOGNIZER_BUSY;
                errorMessage = context.getString(R.string.error_recognizer_busy);
                break;

            case SpeechRecognizer.ERROR_SERVER:
                errorCode = SpeechRecognizer.ERROR_SERVER;
                errorMessage = context.getString(R.string.error_server);
                break;

            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorCode = SpeechRecognizer.ERROR_SPEECH_TIMEOUT;
                errorMessage = context.getString(R.string.error_no_input);
                break;

            case SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED:
                errorCode = SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED;
                errorMessage = context.getString(R.string.error_language);
                break;

            case SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE:
                errorCode = SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE;
                errorMessage = context.getString(R.string.error_language_unavailable);
                break;

            case SpeechRecognizer.ERROR_SERVER_DISCONNECTED:
                errorCode = SpeechRecognizer.ERROR_TOO_MANY_REQUESTS;
                errorMessage = context.getString(R.string.error_server_disconnected);
                break;

            case SpeechRecognizer.ERROR_TOO_MANY_REQUESTS:
                errorCode = SpeechRecognizer.ERROR_TOO_MANY_REQUESTS;
                errorMessage = context.getString(R.string.error_too_many_requests);
                break;

            default:
                errorMessage = context.getString(R.string.error_undefined);
                break;
        }
        listener.OnSpeechRecognitionError(errorCode, errorMessage);
    }

    @Override
    public void onResults(Bundle bundle) {
        //sentence with highest confidence score is in position 0
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && matches.size() > 0) {
            String sentence = matches.get(0);
            Log.i(SpeechRecognitionListener.class.getSimpleName(), sentence);
            listener.OnSpeechRecognitionFinalResult(sentence);
        } else {
            onError(SpeechRecognizer.ERROR_NO_MATCH);
        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        //sentence with highest confidence score is in position 0
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && matches.size() > 0) {
            String word = matches.get(0);
            Log.i(SpeechRecognitionListener.class.getSimpleName(), word);
            listener.OnSpeechRecognitionCurrentResult(word);
        } else {
            onError(SpeechRecognizer.ERROR_NO_MATCH);
        }
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
    }

}
